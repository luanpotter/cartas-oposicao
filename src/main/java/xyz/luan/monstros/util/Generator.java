package xyz.luan.monstros.util;

import lombok.experimental.UtilityClass;
import xyz.luan.monstros.domain.company.Model;
import xyz.luan.monstros.domain.user.User;

// TODO generator class impl

@UtilityClass
public class Generator {

	public String generate(Model model, User user) {
		return "Not implemented yet!";
	}

	public Model createModel(String fileId) {
		Model model = new Model();
		model.setFileId(fileId);
		return model;
	}
}
