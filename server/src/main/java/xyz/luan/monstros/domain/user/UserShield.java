package xyz.luan.monstros.domain.user;

import io.yawp.commons.http.annotation.GET;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.List;
import java.util.Map;

public class UserShield extends Shield<User> {

	@Override
	public void index(IdRef<?> parentId) {
		allow(false);
	}

	@Override
	public void create(List<User> objects) {
		allow(false);
	}

	@Override
	public void update(IdRef<User> id, User object) {
		allow(false);
	}

	@Override
	public void destroy(IdRef<User> id) {
		allow(false);
	}

	@Override
	public void show(IdRef<User> id) {
		allow(AuthHolder.user.get().getId().equals(id));
	}

	@PUT("/fields")
	public void updateFields(IdRef<User> id, Map<String, String> args) {
		allow(AuthHolder.user.get().getId().equals(id));
	}

	@GET
	public void me() {
		allow(true);
	}
}
