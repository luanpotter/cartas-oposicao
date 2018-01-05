import org.junit.Assert;
import org.junit.Test;
import xyz.luan.monstros.util.Generator;

import java.util.List;

public class GeneratorTest {

	@Test
	public void testExtractParameters() {
		List<String> params = Generator.extractParameters("Shalom!\nHey {{fullName}}! And {{age}}...");
		Assert.assertArrayEquals(new String[]{"fullName", "age"}, params.toArray());
	}

	@Test
	public void testExtractParametersIgnoreDefaultParameters() {
		List<String> params = Generator.extractParameters("Shalom!\nHey {{name}}! And {{age}}...");
		Assert.assertArrayEquals(new String[]{"age"}, params.toArray());
	}
}
