package xyz.luan.monstros.domain.user;

import io.yawp.repository.IdRef;
import io.yawp.repository.annotations.Endpoint;
import io.yawp.repository.annotations.Id;
import io.yawp.repository.annotations.Index;
import io.yawp.repository.annotations.Json;
import lombok.Data;
import xyz.luan.monstros.domain.company.Company;

import java.util.HashMap;
import java.util.Map;

@Data
@Endpoint(path = "/users")
public class User {

	@Id
	private IdRef<User> id;

	@Index
	private IdRef<Company> company;

	@Index
	private String email;

	private String name;

	private String rg;

	private String cpf;

	@Json
	private Map<String, String> customParameters = new HashMap<>();

	public Map<String, String> getParameterMap() {
		Map<String, String> m = new HashMap<>();
		m.putAll(customParameters);
		m.put("email", email);
		m.put("name", name);
		m.put("rg", rg);
		m.put("cpf", cpf);
		return m;
	}
}
