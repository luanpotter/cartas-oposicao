package xyz.luan.monstros.ws;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.yawp.commons.http.HttpException;
import io.yawp.repository.Yawp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.luan.monstros.domain.company.Company;
import xyz.luan.monstros.domain.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthFilter extends HttpFilter {

	private Logger LOGGER = LoggerFactory.getLogger(FirebaseAuthFilter.class);

	@Override
	protected void filter(HttpServletRequest request, HttpServletResponse response) throws ExecutionException, InterruptedException {
		String authToken = request.getHeader("Authorization");
		LOGGER.info("Request received [url: %s][auth: %s]", request.getRequestURI(), authToken);

		if (request.getMethod().equals("OPTIONS")) {
			return;
		}

		if (Objects.isNull(authToken) || !authToken.contains(" ")) {
			throw new HttpException(403, "Login is mandatory for every API request, add a 'Authorization: Bearer <token>' header with firebase auth");
		}

		String idToken = authToken.split(" ")[1];
		FirebaseToken decodedToken = getFirebase().verifyIdTokenAsync(idToken).get();
		if (!decodedToken.isEmailVerified()) {
			throw new HttpException(403, "You can only login with a verified e-mail!");
		}

		String email = decodedToken.getEmail();
		String[] parts = email.split("@");
		if (parts.length != 2) {
			throw new HttpException(422, "You can only login with an email that contains exactly one '@' character (sorry, I know that spec allows weird stuff, but we can't handle it)");
		}

		String domain = parts[1];
		Company company = getOrCreateCompany(domain);
		User user = getOrCreateUser(email, company);

		AuthHolder.company.set(company);
		AuthHolder.user.set(user);
	}

	private User getOrCreateUser(String email, Company company) {
		User user = Yawp.yawp(User.class).where("company", "=", company.getId()).and("email", "=", email).first();
		if (user == null) {
			user = createNewUser(email, company);
		}
		return user;
	}

	private User createNewUser(String email, Company company) {
		User user;
		User newUser = new User();
		newUser.setCompany(company.getId());
		newUser.setEmail(email);
		user = Yawp.yawp.save(newUser);
		return user;
	}

	private Company getOrCreateCompany(String domain) {
		Company c = Yawp.yawp(Company.class).where("domain", "=", domain).first();
		if (c != null) {
			return c;
		}
		return createNewCompany(domain);
	}

	private Company createNewCompany(String domain) {
		Company c2 = new Company();
		c2.setDomain(domain);
		return Yawp.yawp.save(c2);
	}

	private static FirebaseAuth _auth;

	private static FirebaseAuth getFirebase() {
		if (_auth == null) {
			FirebaseOptions options = new FirebaseOptions.Builder().setProjectId("cartas-oposicao").setCredentials(GoogleCredentials.newBuilder().build()).build();
			FirebaseApp app = FirebaseApp.initializeApp(options);
			_auth = FirebaseAuth.getInstance(app);
		}
		return _auth;
	}
}
