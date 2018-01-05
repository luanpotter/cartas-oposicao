package xyz.luan.monstros.util;

import io.yawp.commons.http.HttpException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Normalizer {

	public String normalizeRg(String rg) {
		// Nothing to do, RG's vary widely
		return rg;
	}

	public String normalizeCpf(String cpf) {
		String unmasked = cpf.replaceAll("[^\\d]", "");
		if (unmasked.length() != 14) {
			throw new HttpException(422, "CPF must have exactly 14 digits");
		}
		return unmasked.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3-$4");
	}
}
