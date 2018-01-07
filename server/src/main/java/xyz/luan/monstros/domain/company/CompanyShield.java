package xyz.luan.monstros.domain.company;

import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.shields.Shield;
import xyz.luan.monstros.util.Generator;
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
		boolean ownCompany = AuthHolder.user.get().getCompany().equals(id);
		if (!ownCompany) {
			throw new HttpException(403, "Can't update other people's companies!");
		}
		Company original = id.fetch();
		if (!original.getDomain().equals(object.getDomain())) {
			throw new HttpException(422, "Forbidden to change the domain!");
		}
		object.getModels().forEach(Generator::enrichModel);
		allow();
	}

	@Override
	public void destroy(IdRef<Company> id) {
		allow(false);
	}

	@Override
	public void show(IdRef<Company> id) {
		allow(AuthHolder.user.get().getCompany().equals(id));
	}

	@PUT("generate")
	public void generate(IdRef<Company> id, Map<String, String> args) {
		allow(AuthHolder.user.get().getCompany().equals(id));
	}
}
