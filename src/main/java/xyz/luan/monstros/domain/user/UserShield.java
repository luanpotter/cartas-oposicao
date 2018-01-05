package xyz.luan.monstros.domain.user;

import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.List;

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
}
