import org.junit.Assert;
import org.junit.Test;
import xyz.luan.monstros.util.Normalizer;

public class NormalizerTest {

	@Test
	public void testCpf() {
		Assert.assertEquals("123.123.123-00", Normalizer.normalizeCpf("12312312300"));
	}
}
