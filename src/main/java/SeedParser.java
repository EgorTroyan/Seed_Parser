import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeedParser {

    public static String startXml = Repository.getStartXml();
    public static String endXml = Repository.getEndXml();;
    public static String startDat = Repository.getStartDat();;
    public static String name = "";
    public static boolean canStop = true;
    public static String message;
    public static String n = "\n";

    public static void start (String arg) throws IOException {
        message = "";
            List<String> bbb = loadKeyListFromFile();

            if (arg.equals("3")) {

                TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
                TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                xmlToFile(done1);
                datToFile(done2);

                message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: "
                        + done1.size() + "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: "
                        + done2.size();
            }
            if (arg.equals("1")) {

                TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
                xmlToFile(done1);
                message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: " + done1.size();
            }
            if (arg.equals("2")) {

                TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                datToFile(done2);
                message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size();
            }

            if (arg.equals("99")) {
                List<String> ccc = loadETokenListFromFile();
                txtToFile(ccc);
                message = "\nФайл " + name + ".TXT готов. Находится в папке \\Out. Количество ключей в файле: " + ccc.size();
            }

        }

    public static List<String> loadKeyListFromFile() {
        List<String> list;
        List<String> list1 = new ArrayList<>();
        try {
            list = Files.readAllLines(Paths.get("in/" + name + ".txt"));
            for (String s : list) {

                Pattern pattern = Pattern.compile("[A-Z]{2}\\d{6}.?");
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    String temp = s.substring(matcher.start(), matcher.end());
                    if (temp.length() == 8)
                        list1.add(temp);
                    else
                        list1.add(temp.substring(0, temp.length() - 1));
                }

                Collections.sort(list1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list1;
    }

    public static List<String> loadETokenListFromFile() {
        List<String> list;
        List<String> list1 = new ArrayList<>();
        try {
            list = Files.readAllLines(Paths.get("in/" + name + ".txt"));
            for (String s : list) {

                Pattern pattern = Pattern.compile("[A-Za-z0-9]{8}.?");
                Matcher matcher = pattern.matcher(s);
                while (matcher.find()) {
                    String temp = s.substring(matcher.start(), matcher.end());
                    if (temp.length() == 8)
                        list1.add(temp);
                    else
                        list1.add(temp.substring(0, temp.length() - 1));
                }

                Collections.sort(list1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list1;
    }

    public static TreeMap<String, String> searchXml(TreeMap<String, String> map, List<String> list) {
        TreeMap<String, String> done = new TreeMap<>();
        for (String s : list) {
            if (map.containsKey(s)) {
                done.put(s, map.get(s));
            }
        }
        return done;
    }

    public static TreeMap<String, String> searchDat(TreeMap<String, String> map, List<String> list) {
        TreeMap<String, String> done = new TreeMap<>();
        for (String s : list) {
            if (map.containsKey(s)) {
                done.put(s, map.get(s));
            }
        }
        return done;
    }


    public static void xmlToFile(TreeMap<String, String> map) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startXml);
        stringBuilder.append("\n");
        for (Map.Entry<String, String> a : map.entrySet()) {
            String value = a.getValue();
            stringBuilder.append(value);
            stringBuilder.append("\n\n\n");
        }
        String done = stringBuilder.toString().trim() + "\n" + endXml + "\n";
        Files.writeString(Paths.get("out/" + name + "_out.xml"), done);
    }

    public static void datToFile(TreeMap<String, String> map) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startDat);
        stringBuilder.append("\n");
        for (Map.Entry<String, String> a : map.entrySet()) {
            String value = a.getValue();
            stringBuilder.append(value);
            stringBuilder.append("\n\n");
        }
        String done = stringBuilder.toString().trim() + "\n";
        Files.writeString(Paths.get("out/" + name + "_out.dat"), done);
    }

    public static void txtToFile(List<String> list) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        for (String a : list) {
            stringBuilder.append(a);
            stringBuilder.append("\n");
        }
        String done = stringBuilder.toString().trim() + "\n";
        Files.writeString(Paths.get("out/" + name + "_out.txt"), done);
    }
}


