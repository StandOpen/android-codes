package zip.standopen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ZipDemo extends Activity {
	private Button start=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        start=(Button)findViewById(R.id.start);
       start.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str=Environment.getExternalStorageDirectory().toString();
			Toast.makeText(ZipDemo.this, str, Toast.LENGTH_SHORT).show();
			//Unzip(str+"/test.zip",str+"/test");
		}
	});
    }
   /* private static void Unzip(String zipFile, String targetDir) {
    	   int BUFFER = 4096; //这里缓冲区我们使用4KB，
    	   String strEntry; //保存每个zip的条目名称
    
    try {
        BufferedOutputStream dest = null; //缓冲输出流
        FileInputStream fis = new FileInputStream(zipFile);
        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
        ZipEntry entry; //每个zip条目的实例

        while ((entry = zis.getNextEntry()) != null) {

         try {
           Log.i("Unzip: ","="+ entry);
          int count; 
          byte data[] = new byte[BUFFER];
          strEntry = entry.getName();

          File entryFile = new File(targetDir + strEntry);
          File entryDir = new File(entryFile.getParent());
          if (!entryDir.exists()) {
           entryDir.mkdirs();
          }

          FileOutputStream fos = new FileOutputStream(entryFile);
          dest = new BufferedOutputStream(fos, BUFFER);
          while ((count = zis.read(data, 0, BUFFER)) != -1) {
           dest.write(data, 0, count);
          }
          dest.flush();
          dest.close();
         } catch (Exception ex) {
          ex.printStackTrace();
         }
        }
        zis.close();
       } catch (Exception cwj) {
        cwj.printStackTrace();
       }
      }
    */
    
    public static void zip(File outZipPath, File zipFileRoot) {
    	 
        try {

                // filePath.

                OutputStream os = new FileOutputStream(outZipPath);

                BufferedOutputStream bos = new BufferedOutputStream(os);

                ZipOutputStream zip = new ZipOutputStream(bos, "GBK");

                zip(zip, zipFileRoot, "");

                zip.flush();

                zip.closeEntry();

                zip.close();

        } catch (FileNotFoundException e) {

                e.printStackTrace();

        } catch (IOException e) {

                e.printStackTrace();

        } catch (Exception e) {

                e.printStackTrace();

        }

}



/*

* 写入文件目录与文件内容

*/

private static void zip(ZipOutputStream out, File f, String base) throws Exception {

        System.out.println("Zipping  " + f.getName());

        if (f.isDirectory()) {

                File[] fl = f.listFiles();

                // base = new String(base.getBytes(), "GBK");

                out.putNextEntry(new ZipEntry(base + "/"));

                base = base.length() == 0 ? "" : base + "/";

                for (int i = 0; i < fl.length; i++) {

                        zip(out, fl[i], base + fl[i].getName());

                }

        } else if (!f.getName().endsWith(".zip")) {

                // base = new String(base.getBytes(), "GBK");

                out.putNextEntry(new ZipEntry(base));

                BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

                byte[] bt = new byte[1024];

                int b;

                while ((b = in.read(bt)) != -1) {

                        out.write(bt, 0, b);

                }

                in.close();

        }

}



public void unzip(String zipFileName) throws Exception {

        ZipInputStream in = null;

        try {

                in = new ZipInputStream(new FileInputStream(zipFileName), "gbk");

                ZipEntry z;

                int totalCount = 0;

                while ((z = in.getNextEntry()) != null) {

                        if (z.isDirectory()) {

                                String name = z.getName();

                                if (StringUtils.isNotBlank(name)) {

                                        System.out.println("目录：" + name);

                                        int index = name.lastIndexOf("/");

                                        String currentName = null;

                                        String parentName = null;

                                        if (index != -1) {

                                                currentName = name.substring(index + 1, name.length());

                                                parentName = name.substring(0, index);

                                        } else {

                                                currentName = name;

                                        }



                                        currentName = currentName.substring(0, name.length() - 1); //去掉斜线

                                        System.out.println("输出目录名称:"+currentName);

                                }

                        } else {

                                String name = z.getName();

                                System.out.println("文件名：" + name);

                                if (StringUtils.isNotBlank(name)) {

                                        String currentName = null;

                                        String parentName = null;

                                        int index = name.lastIndexOf("/");

                                        if (index != -1) {

                                                currentName = name.substring(index + 1, name.length());

                                                parentName = name.substring(0, index);

                                        } else {

                                                currentName = name;

                                        }

                                        BufferedReader br = new BufferedReader(new InputStreamReader(in));

                                        String b;

                                        int row = 0;

                                        while ((b = br.readLine()) != null) {

                                                totalCount++;

                                                row++;

                                                System.out.println("读出文件内容:"+b);  //打出读取的内容

                                                  

                                        }

                                        System.out.println("读取文件 " + currentName + " 结束");

                                }

                        }

                }

        } catch (Exception e) {

                throw e;

        } finally {

                in.close();

                File tempFile = new File(zipFileName);

                tempFile.delete();

                tempFile.deleteOnExit();

        }



}
}