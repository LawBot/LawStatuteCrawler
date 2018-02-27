/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 陈光曦
 */
public class LawStatuteAnalyzer {

    private List<String> fileList;

    public LawStatuteAnalyzer() {
        fileList = new LinkedList();
    }

    public List readFile(String filePath) {
        List<String> lineList = new LinkedList<>();
        String line = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                lineList.add(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            return null;
        } catch (IOException e) {
            System.err.println("IOException when reading file: " + filePath);
            return null;
        }
        return lineList;
    }

    public String getName(String filePath) {
        int lastIndexOfSlash = filePath.lastIndexOf("\\");
        int lastIndexOfExt = filePath.lastIndexOf(".txt");
        if (lastIndexOfExt != -1) {
            return filePath.substring(lastIndexOfSlash + 1, lastIndexOfExt);
        } else {
            return filePath.substring(lastIndexOfSlash);
        }
    }

    public LawStatute analyze(String filePath) {
        List<String> lineList = readFile(filePath);
        String name = getName(filePath).trim();
        String matchRegex = "^第[一|二|三|四|五|六|七|八|九|十|百|零]*?条";
        String unmatchRegex = "(^第[一|二|三|四|五|六|七|八|九|十|百|零]*?章)|(^第[一|二|三|四|五|六|七|八|九|十|百|零]*?节)";
        LawStatute statute = new LawStatute(name);
        StringBuilder itemText = new StringBuilder();
        boolean foundFirstIdentifier = false;
        for (int lineIndex = 0; lineIndex < lineList.size(); ++lineIndex) {
            String lineStr = (String) lineList.get(lineIndex);

            Pattern pattern = Pattern.compile(unmatchRegex);
            Matcher matcher = pattern.matcher(lineStr);

            if (matcher.find()) {
                continue;
            }

            pattern = Pattern.compile(matchRegex);
            matcher = pattern.matcher(lineStr);

            if (matcher.find()) {
                foundFirstIdentifier = true;
                if (!itemText.toString().isEmpty()) {
                    StatuteItem item = new StatuteItem();
                    item.setContent(itemText.toString().trim());
                    statute.addItem(item);
                    itemText = new StringBuilder();
                }
                int end = matcher.end();
                itemText.append(lineStr.substring(end).trim());
            } else {
                if (foundFirstIdentifier) {
                    itemText.append(lineStr.trim());
                }
            }

            if (lineIndex == lineList.size() - 1) {
                if (!itemText.toString().isEmpty()) {
                    StatuteItem item = new StatuteItem();
                    item.setContent(itemText.toString().trim());
                    statute.addItem(item);
                    itemText = new StringBuilder();
                }
            }
        }
        return statute;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void getFileList(String path) {
        File folder = new File(path);

        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                getFileList(file.getAbsolutePath());
            }
        }
    }
}
