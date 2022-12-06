import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static void saveGame(String path, GameProgress gp) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(path))) {
            outputStream.writeObject(gp);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String path, List<String> listOfObjects) {
        FileInputStream fis = null;
        try (ZipOutputStream zipInputStream = new ZipOutputStream(new FileOutputStream(path))) {
            for (String a: listOfObjects) {
                fis = new FileInputStream(a);
                ZipEntry entry = new ZipEntry(a.substring(a.lastIndexOf("/") + 1));
                zipInputStream.putNextEntry(entry);
                zipInputStream.write(fis.readAllBytes());
                zipInputStream.closeEntry();
                try {
                    if (fis!=null) {
                        fis.close();
                    }
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void delete(List<String> listOfObjects) {
        for (String a: listOfObjects) {
            File gp = new File(a);
            gp.delete();
        }
    }

    public static void main(String[] args) {
        saveGame("Games//savegames//gp1.dat",
                new GameProgress(100, 2, 5, 100));
        saveGame("Games//savegames//gp2.dat",
                new GameProgress(80, 3, 50, 20));
        saveGame("Games//savegames//gp3.dat",
                new GameProgress(99, 99, 1, 1));

        List<String> arr = new ArrayList<>(Arrays.asList(
                "Games//savegames//gp1.dat",
                "Games//savegames//gp2.dat",
                "Games//savegames//gp3.dat"
        ));

        zipFiles("Games//savegames//zip.zip", arr);

        delete(arr);
    }
}