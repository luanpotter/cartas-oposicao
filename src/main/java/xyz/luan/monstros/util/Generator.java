package xyz.luan.monstros.util;

import com.github.feroult.gapi.DocsAPI;
import com.github.feroult.gapi.GoogleAPI;
import lombok.experimental.UtilityClass;
import xyz.luan.monstros.domain.company.Model;
import xyz.luan.monstros.domain.user.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@UtilityClass
public class Generator {

	// TODO generate method
	public String generate(Model model, User user) {
		DocsAPI doc = new GoogleAPI().doc(model.getFileId());
		Map<String, String> params = user.getParameterMap();
		// replace params
		// generate pdf
		// send mail
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
		return Arrays.stream(content.replaceAll("[^{]*\\{\\{([^}]*)}}[^{]*", "$1}").split("}"))
				.filter(parameter -> !Arrays.asList("name", "date", "cpf", "rg", "email").contains(parameter))
				.collect(toList());
	}
}
