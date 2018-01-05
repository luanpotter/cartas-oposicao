package xyz.luan.monstros.domain.company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;
import xyz.luan.monstros.util.Generator;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class CompanyUpdateAction extends Action<Company> {

	@PUT("/fields")
	public Company updateFields(IdRef<Company> id, Map<String, String> args) {
		Company company = AuthHolder.company.get();
		args.forEach((key, value) -> {
			switch (key) {
				case "name":
					company.setName(value);
				case "models":
					try {
						List<String> models = new Gson().fromJson(value, new TypeToken<List<String>>() {
						}.getType());
						company.setModels(models.stream().map(Generator::createModel).collect(toList()));
					} catch (JsonSyntaxException ex) {
						throw new HttpException(422, "Invalid JSON, models must be a list of strings with the file ids of the models on google drive; they must be accessible via url.");
					}
				default:
					throw new HttpException(422, "Invalid field to update, mate: " + key + "; only allowed name and models");
			}
		});
		return yawp.save(company);
	}
}
