import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    public static String message;

    public static void start (Type type, boolean zip) throws IOException {
        message = "";
            List<String> bbb = loadKeyListFromFile();
            switch (type){
                case ALL:
                    if (zip){
                        TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String p = zipMe(xmlToFile(done1), datToFile(done2));
                        message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: "
                                + done1.size() + "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: "
                                + done2.size() + "\nПароль архива:\n" + p;
                        Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
                    } else {
                        TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String d = datToFile(done2);
                        String x = xmlToFile(done1);
                        message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: "
                                + done1.size() + "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: "
                                + done2.size();
                        Files.writeString(Paths.get("out/" + name + "_out.xml"), x);
                        Files.writeString(Paths.get("out/" + name + "_out.dat"), d);
                    }
                break;
                case DAT:
                    if (zip){
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String p = zipMeDat(datToFile(done2));
                        message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size() + "\nПароль архива:\n" + p;
                        Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
                    } else {
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String d = datToFile(done2);
                        message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size();
                        Files.writeString(Paths.get("out/" + name + "_out.dat"), d);
                    }
                    break;
                case XML:
                    if (zip){
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String p = zipMeXml(xmlToFile(done2));
                        message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size() + "\nПароль архива:\n" + p;
                        Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
                    } else {
                        TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
                        String x = xmlToFile(done2);
                        message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size();
                        Files.writeString(Paths.get("out/" + name + "_out.xml"), x);
                    }
                    break;
                case CLEAR5110:
                    List<String> ccc = loadETokenListFromFile();
                    txtToFile(ccc);
                    message = "\nФайл " + name + ".TXT готов. Находится в папке \\Out. Количество ключей в файле: " + ccc.size();
            }
//            if (arg.equals("3")) {
//
//                TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
//                TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
//                String p = zipMe(xmlToFile(done1), datToFile(done2));
//                message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: "
//                        + done1.size() + "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: "
//                        + done2.size() + "\nПароль архива:\n" + p;
//                Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
//            }
//            if (arg.equals("1")) {
//
//                TreeMap<String, String> done1 = searchXml(Repository.getRepositoryXML(), bbb);
//                String p = zipMeXml(xmlToFile(done1));
//                message = "\nФайл " + name + ".XML готов. Находится в папке \\Out. Количество ключей в файле: " + done1.size() + "\nПароль архива:\n" + p;
//                Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
//            }
//            if (arg.equals("2")) {
//
//                TreeMap<String, String> done2 = searchDat(Repository.getRepositoryDAT(), bbb);
//                String p = zipMeDat(datToFile(done2));
//                message = "\nФайл " + name + ".DAT готов. Находится в папке \\Out. Количество ключей в файле: " + done2.size() + "\nПароль архива:\n" + p;
//                Files.writeString(Paths.get("out/" + name + "_password.txt"), p);
//            }
//
//            if (arg.equals("99")) {
//                List<String> ccc = loadETokenListFromFile();
//                txtToFile(ccc);
//                message = "\nФайл " + name + ".TXT готов. Находится в папке \\Out. Количество ключей в файле: " + ccc.size();
//            }

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

                //Collections.sort(list1);
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


    public static String xmlToFile(TreeMap<String, String> map) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startXml);
        stringBuilder.append("\n");
        for (Map.Entry<String, String> a : map.entrySet()) {
            String value = a.getValue();
            stringBuilder.append(value);
            stringBuilder.append("\n\n\n");
        }
        return stringBuilder.toString().trim() + "\n" + endXml + "\n";



    }

    public static String datToFile(TreeMap<String, String> map) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(startDat);
        stringBuilder.append("\n");
        for (Map.Entry<String, String> a : map.entrySet()) {
            String value = a.getValue();
            stringBuilder.append(value);
            stringBuilder.append("\n\n");
        }
        return stringBuilder.toString().trim() + "\n";
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

    public static String zipMe (String xml, String dat) throws ZipException {
        String password = new PasswordOfDream().generatePassword(10);
        InputStream inputStreamXml = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        InputStream inputStreamDat = new ByteArrayInputStream(dat.getBytes(StandardCharsets.UTF_8));
        ZipParameters zipParametersXml = new ZipParameters();
        ZipParameters zipParametersDat = new ZipParameters();
        zipParametersXml.setFileNameInZip(name + "_out.xml");
        zipParametersXml.setEncryptFiles(true);
        zipParametersXml.setEncryptionMethod(EncryptionMethod.AES);
        zipParametersDat.setFileNameInZip(name + "_out.dat");
        zipParametersDat.setEncryptFiles(true);
        zipParametersDat.setEncryptionMethod(EncryptionMethod.AES);
        ZipFile zipFile = new ZipFile("out/" + name + ".zip", password.toCharArray());
        zipFile.addStream(inputStreamXml, zipParametersXml);
        zipFile.addStream(inputStreamDat, zipParametersDat);
        return password;
    }
    public static String zipMeXml (String xml) throws ZipException {
        String password = new PasswordOfDream().generatePassword(10);
        InputStream inputStreamXml = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        ZipParameters zipParametersXml = new ZipParameters();
        zipParametersXml.setFileNameInZip(name + "_out.xml");
        zipParametersXml.setEncryptFiles(true);
        zipParametersXml.setEncryptionMethod(EncryptionMethod.AES);
        ZipFile zipFile = new ZipFile("out/" + name + ".zip", password.toCharArray());
        zipFile.addStream(inputStreamXml, zipParametersXml);
        return password;
    }
    public static String zipMeDat (String dat) throws ZipException {
        String password = new PasswordOfDream().generatePassword(10);
        InputStream inputStreamDat = new ByteArrayInputStream(dat.getBytes(StandardCharsets.UTF_8));
        ZipParameters zipParametersDat = new ZipParameters();
        zipParametersDat.setFileNameInZip(name + "_out.dat");
        zipParametersDat.setEncryptFiles(true);
        zipParametersDat.setEncryptionMethod(EncryptionMethod.AES);
        ZipFile zipFile = new ZipFile("out/" + name + ".zip", password.toCharArray());
        zipFile.addStream(inputStreamDat, zipParametersDat);
        return password;
    }

}


