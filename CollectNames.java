package org.apache.pdfbox.examples.wind;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: flatlined
 * Date: 14-5-18
 * To change this template use File | Settings | File Templates.
 */
public class CollectNames {
    public static void main(String[] args) throws Exception {
        //String dirPath = "G:\\temp\\沪市主板2007年年度报告\\validResult";
        //String dirPath = "G:\\temp\\深市主板2012年年度报告（新名）\\validResult";

        File topDir = new File("F:\\郝阳\\Done");
        String outputFilePath = "F:\\郝阳\\featureStatistic.txt";
        String templateSource = "F:\\郝阳\\wordsTemplate.txt";
        //CountFeatures(topDir, outputFilePath);
        System.out.println(countLegal(topDir, templateSource));
    }

    static public double countLegal(File topDir, String templateSource) {
        List<File> files = new ArrayList<File>();


        // F:\郝阳\Done\创业板2009年年度财务报告\validResult\feature
        if (topDir.isDirectory()) {
            for (File file : topDir.listFiles()) {
                String dirPath = file.getAbsolutePath() + File.separator + "validResult" + File.separator + "feature";
                files.addAll(CommonUtils.getFilesWithSuffix(dirPath, "txt"));
            }
        }

        List<String> words = new ArrayList<String>();
        List<String> templateWords = CollectNames.getLinesFromFile(new File(templateSource));

        int legal = 0;
        int count = 0;
        for (File file : files) {
            boolean allLegal = true;
            for (String item : getLinesFromFile(file)) {
                // Find if a feature item in-or-not-in a template word list
                // Method 'contains' doesn't work
                boolean find = false;
                for (String word : templateWords) {
                    if (word.equals(item)) {
                        find = true;
                        break;
                    }
                }

                if (find = false) {
                    allLegal = false;
                    break;
                }
            }

            if (allLegal == true) {
                legal++;
            }
            count++;
        }
        return (legal * 1.0) / files.size();
    }

    private static void CountFeatures(File topDir, String outputFilePath) throws IOException {
        List<File> files = new ArrayList<File>();


        // F:\郝阳\Done\创业板2009年年度财务报告\validResult\feature
        if (topDir.isDirectory()) {
            for (File file : topDir.listFiles()) {
                String dirPath = file.getAbsolutePath() + File.separator + "validResult" + File.separator + "feature";
                files.addAll(CommonUtils.getFilesWithSuffix(dirPath, "txt"));
            }
        }


        List<String> words = new ArrayList<String>();

        for (File file : files) {
            words.addAll(getLinesFromFile(file));
        }

        Map<String, Integer> pureWords = counterWords(words);

        File writeName = new File(outputFilePath);
        writeName.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writeName));
        for (String item : pureWords.keySet()) {
            if (isValid(item) == false)
                continue;

            out.write(item);
            out.newLine();
        }

        out.flush();
        out.close();
    }

    public static boolean isValid(String str) {
        boolean result = true;

        if (str.length() < 2
                || str.contains(".")
                || str.contains(",")
                || str.contains("。")
                || str.contains("，")
                || str.contains("%")
                || str.contains("、")
                || str.contains("《")
                || str.contains("》")
                || str.contains("：")
                || str.contains(":")
                || str.contains("/")
                || str.contains("％")
                || str.contains("-")
                || str.contains("—")
                || str.contains("”")
                || str.contains("年")
                || str.contains(";")
                || str.contains("；"))
            return false;

        for (Character c : str.toCharArray()) {
            if (Character.isDigit(c))
                result = false;
        }

        return result;
    }

    public static List<String> getLinesFromFile(File file) {
        InputStream fis;

        String line;
        List<String> lines = new ArrayList<String>();

        try {
            fis = new FileInputStream(file.getAbsolutePath());
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            while ((line = br.readLine()) != null) {
                // Deal with the line
                lines.add(line);
            }

            br.close();

        } catch (Exception e) {

        }
        return lines;
    }

    public static Map<String, Integer> counterWords(List<String> words) {
        Map<String, Integer> counter = new HashMap<String, Integer>();

        for (String word : words) {
            if (counter.containsKey(word)) {
                counter.put(word, counter.get(word) + 1);
            } else {
                counter.put(word, 1);
            }
        }


        return MapUtil.sortByValue(counter);
    }
}

class MapUtil {
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
