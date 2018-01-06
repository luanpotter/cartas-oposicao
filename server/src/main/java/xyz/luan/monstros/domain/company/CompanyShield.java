package xyz.luan.monstros.domain.company;

import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.List;
import java.util.Map;

public class CompanyShield extends Shield<Company> {

	@Override
	public void index(IdRef<?> parentId) {
		allow(false);
	}

	@Override
	public void create(List<Company> objects) {
		allow(false);
	}

	@Override
	public void update(IdRef<Company> id, Company object) {
		allow(false);
	}

	@Override
	public void destroy(IdRef<Company> id) {
		allow(false);
	}

	@Override
	public void show(IdRef<Company> id) {
		allow(AuthHolder.user.get().getCompany().equals(id));
	}

	@PUT("fields")
	public void updateFields(IdRef<Company> id, Map<String, String> args) {
		allow(AuthHolder.user.get().getCompany().equals(id));
	}

	@PUT("generate")
	public void generate(IdRef<Company> id, Map<String, String> args) {
		allow(AuthHolder.user.get().getCompany().equals(id));
	}
}
