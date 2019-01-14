import java.io.*;
import java.util.zip.*;

public class Zipping {

    public Zipping(String zipFilePath, String destDir) {
    	System.out.println("Start");

        File dir = new File(destDir);
        FileInputStream fis;
        byte[] buffer = new byte[1024];

        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + System.getProperty("file.separator") + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                //new File(newFile.getParent()).mkdirs();
                dir.mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                buffer = new byte[1024];
                while ((len = zis.read(buffer)) != -1) {
                    //fos.write(buffer, 0, len);
                    fos.write(buffer);
                }
                fos.close();
                //ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
