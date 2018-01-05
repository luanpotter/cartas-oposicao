package xyz.luan.monstros.domain.company;

import io.netty.util.internal.StringUtil;
import io.yawp.commons.http.HttpException;
import io.yawp.commons.http.annotation.PUT;
import io.yawp.repository.IdRef;
import io.yawp.repository.actions.Action;
import xyz.luan.monstros.util.Generator;
import xyz.luan.monstros.ws.AuthHolder;

import java.util.Map;

public class CompanyGenerateAction extends Action<Company> {

	@PUT("/generate")
	public String generate(IdRef<Company> id, Map<String, String> args) {
		Company company = AuthHolder.company.get();
		String modelRef = args.get("modelRef");
		if (StringUtil.isNullOrEmpty(modelRef)) {
			throw new HttpException(422, "modelRef required to generate letter");
		}
		Model model = company.getModels().stream()
				.filter(m -> m.getFileName().equals(modelRef) || m.getFileId().equals(modelRef))
				.findFirst().orElseThrow(() -> new HttpException(422, "modelRef not found on your company list."));
		String result = Generator.generate(model, AuthHolder.user.get());
		return "{\"status\":\"" + result + "\"}";
	}
}
