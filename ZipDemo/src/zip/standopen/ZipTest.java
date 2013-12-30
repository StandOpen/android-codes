package zip.standopen;

import java.io.FileInputStream;

/**
 * @author agilewang
 * 
 */
public class ZipTest {

	public static void main(String[] args) throws Exception {
		FileInputStream in = new FileInputStream("D:\\中文.zip");
		ZipInputStream jins = null;
		jins = new ZipInputStream(in, "GBK");
		// jins = new ZipInputStream(in);
		long dataSize = 0;

		String fileName = null;
		String destFileName = null;

		ZipEntry jarentry = null;
		while (jins.available() > 0) {
			jarentry = jins.getNextEntry();
			if (jarentry == null) {
				break;
			}
			if (jarentry.isDirectory())
				// continue;

				fileName = jarentry.getName();
			dataSize = jarentry.getSize();
			System.out.println(fileName + ":" + dataSize);
		}
	}
}
