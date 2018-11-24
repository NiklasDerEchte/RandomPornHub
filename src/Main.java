import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;


public class Main {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {

        String username = System.getProperty("user.name");
        String csvFile = "C:\\Users\\" + username + "\\Desktop\\RandomPorn\\pornhub.com-db.csv";
        File f = new File(csvFile);

        if (!f.exists()) {

            new File("C:\\Users\\" + username + "\\Desktop\\RandomPorn").mkdirs();
            String url = "http://www.pornhub.com/files/pornhub.com-db.zip";
            url = "https://github.com/PHPVibe/pornhub-cvs-dumps/archive/master.zip";
            String downloadName = "C:\\Users\\" + username + "\\Desktop\\RandomPorn\\pornhub.com-db.zip";
            String zielPath = "C:\\Users\\" + username + "\\Desktop\\RandomPorn";
            System.out.println("Downloading csv...");
            try {
                downloadFile(url, downloadName);
                new Zipping(downloadName, zielPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File flLines = new File("C:\\Users\\" + username + "\\Desktop\\RandomPorn\\lines.txt");
        File flHistory = new File("C:\\Users\\" + username + "\\Desktop\\RandomPorn\\history.txt");
        String line;
        int random;
        int zeilen;
        line = "";
        random = 0;
        zeilen = 0;

        if (flLines.exists()) {
            try {
                BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\" + username + "\\Desktop\\RandomPorn\\lines.txt"));
                zeilen = Integer.parseInt(in.readLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                System.out.println("Counting lines...");
                zeilen = countLines(csvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!flHistory.exists()) {
            try {
                flHistory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        random = ThreadLocalRandom.current().nextInt(1, 20000 + 1);

        try {
            Stream<String> lines = Files.lines(Paths.get(csvFile));
            line = lines.skip(random).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        line = line.substring(13, line.indexOf('"', line.indexOf('"') + 1));

        try {
            PrintWriter writerLines = new PrintWriter("C:\\Users\\" + username + "\\Desktop\\RandomPorn\\lines.txt");
            PrintWriter writerHistory = new PrintWriter(new FileWriter("C:\\Users\\" + username + "\\Desktop\\RandomPorn\\history.txt", true));

            writerHistory.println(line);
            writerLines.println(zeilen);

            writerHistory.close();
            writerLines.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(line);
        openWebpage(line);
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int countLines(String path) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(path));
        try {
            byte[] c = new byte[1024];

            int readChars = is.read(c);
            if (readChars == -1) {
                return 0;
            }

            int counter = 0;
            while (readChars == 1024) {
                for (int i = 0; i < 1024; ) {
                    if (c[i++] == '\n') {
                        ++counter;
                    }
                }
                readChars = is.read(c);
            }

            while (readChars != -1) {
                for (int i = 0; i < readChars; i++) {
                    if (c[i] == '\n') {
                        ++counter;
                    }
                }
                readChars = is.read(c);
            }

            return counter == 0 ? 1 : counter;
        } finally {
            is.close();
        }
    }

    private void downloadFile(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
}
