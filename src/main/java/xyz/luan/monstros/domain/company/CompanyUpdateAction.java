package xyz.luan.monstros.domain.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;
import xyz.luan.monstros.ws.AuthHolder;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CompanyUpdateAction extends Action<Company> {

	@PUT("/fields")
	public Company updateFields(IdRef<Company> id, Map<String, String> args) {
		Company company = AuthHolder.company.get();
		args.forEach((key, value) -> {
			switch (key) {
				case "name":
					company.setName(value);
				case "models":
					Type type = new TypeToken<List<Model>>() {
					}.getType();
					company.setModels(new Gson().fromJson(value, type));
				default:
					throw new HttpException(422, "Invalid field to update, mate: " + key + "; only allowed name and models");
			}
		});
		return yawp.save(company);
	}
}
