import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Repository implements Runnable{
    private static volatile TreeMap<String, String> repositoryXML;
    private static volatile TreeMap<String, String> repositoryDAT;
    private static String startXml;
    private static String endXml;
    private static String startDat;

    public static TreeMap<String, String> getRepositoryXML() {
        return repositoryXML;
    }

    public static TreeMap<String, String> getRepositoryDAT() {
        return repositoryDAT;
    }

    public static String getStartXml() {
        return startXml;
    }

    public static String getEndXml() {
        return endXml;
    }

    public static String getStartDat() {
        return startDat;
    }

    public Repository() {
        repositoryXML = new TreeMap<>();
        repositoryDAT = new TreeMap<>();
    }
    @Override
    public void run() {
        File folder = new File("data/");
        File[] folders = folder.listFiles();
        List<File> listOfFiles = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(folders).length; i++) {
            File[] files = folders[i].listFiles();
            assert files != null;
            listOfFiles.addAll(Arrays.asList(files).subList(0, Objects.requireNonNull(files).length));
        }
        listOfFiles.forEach(file -> {
            try {
                String canPath = file.getCanonicalPath();
                String xml = ".xml";
                String dat = ".dat";
                if (canPath.contains(xml)){
                    loadXmlListFromFile(file);
                }
                if (canPath.contains(dat)){
                    loadDatListFromFile(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    public static void loadXmlListFromFile(File fileXML) {

        try {
            List<String> all1 = Files.readAllLines(fileXML.toPath());
            ArrayList<String> all = new ArrayList<>();
            for (String a : all1) {
                if (a.length() > 1) {
                    all.add(a);
                }
            }
            startXml = all.get(0) + "\n" + all.get(1);
            endXml = all.get(all.size() - 1);
            all.subList(0, 2).clear();
            all.remove(all.size() - 1);
            for (int i = 0; i < all.size(); i += 12) {
                String key = all.get(i).substring(15, 23);
                StringBuilder value = new StringBuilder();
                for (int j = 0; j < 12; j++) {
                    value.append(all.get(i + j));
                    value.append("\n");
                }
                String value1 = value.toString().trim();
                repositoryXML.put(key, value1);
            }

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Файл бызы XML отсутствует");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void loadDatListFromFile(File fileDAT) {

        try {
            List<String> all1 = Files.readAllLines(fileDAT.toPath());
            ArrayList<String> all = new ArrayList<>();
            for (String s : all1) {
                if (s.length() > 1) {
                    all.add(s);
                }
            }
            startDat = all.get(0);
            all.subList(0, 1).clear();
            for (int i = 0; i < all.size(); i += 6) {
                String key = all.get(i).substring(23, 31);
                StringBuilder value = new StringBuilder();
                for (int j = 0; j < 6; j++) {
                    value.append(all.get(i + j));
                    value.append("\n");
                }
                String value1 = value.toString().trim();
                repositoryDAT.put(key, value1);
            }

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Файл бызы DAT отсутствует");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
