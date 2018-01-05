package xyz.luan.monstros.util;

import com.github.feroult.gapi.DocsAPI;
import com.github.feroult.gapi.GoogleAPI;
import lombok.experimental.UtilityClass;
import xyz.luan.monstros.domain.company.Model;
import xyz.luan.monstros.domain.user.User;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

// TODO generator class impl

@UtilityClass
public class Generator {

	public String generate(Model model, User user) {
		return "Not implemented yet!";
	}

	public Model createModel(String fileId) {
		Model model = new Model();
		model.setFileId(fileId);
		DocsAPI doc = new GoogleAPI().doc(fileId);
		model.setFileName(doc.getName());
		model.setCustomParameterNames(extractParameters(doc.getText()));
		return model;
	}

	public static List<String> extractParameters(String content) {
		return Arrays.stream(content.replaceAll("[^{]*\\{\\{([^}]*)}}[^{]*", "$1}").split("}")).collect(toList());
	}
}
