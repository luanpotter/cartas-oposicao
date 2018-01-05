package xyz.luan.monstros.domain.user;

import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;
import xyz.luan.monstros.util.Util;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.Map;

public class UserUpdateAction extends Action<User> {

	@PUT("/fields")
	public User updateFields(IdRef<User> id, Map<String, String> args) {
		User user = AuthHolder.user.get();
		args.forEach((key, value) -> {
			switch (key) {
				case "id":
					throw new HttpException(422, "Can't change your email, mate!");
				case "company":
					throw new HttpException(422, "Can't change your company, mate!");
				case "email":
					user.setEmail(value);
					break;
				case "name":
					user.setName(value);
					break;
				case "rg":
					user.setRg(Util.normalizeRg(value));
					break;
				case "cpf":
					user.setCpf(Util.normalizeCpf(value));
					break;
				default:
					if (value == null) {
						user.getCustomParameters().remove(key);
					} else {
						user.getCustomParameters().put(key, value);
					}
					break;
			}
		});
		return yawp.save(user);
	}
}
