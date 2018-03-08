/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

import cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity.Interpretation;
import cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity.LawStatute;
import cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity.StatuteItem;
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
public class InterpretationAnalyzer {

    private List<String> fileList;

    public InterpretationAnalyzer() {
        fileList = new LinkedList();
    }

    public String readFile(String filePath) {
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
            return null;
        } catch (IOException e) {
            System.err.println("IOException when reading file: " + filePath);
            return null;
        }
        return sb.toString();
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

    public Interpretation analyze(String filePath) {
        String lineStr = readFile(filePath);
        String name = getName(filePath).trim();
        String matchRegex = "第[一二三四五六七八九十百零]*?条(　| | |  )";
//        String unmatchRegex = "(^第[一|二|三|四|五|六|七|八|九|十|百|零]*?章)|(^第[一|二|三|四|五|六|七|八|九|十|百|零]*?节)";
        Interpretation inter = new Interpretation(name);
        StringBuilder itemText = new StringBuilder();
        boolean foundFirstIdentifier = false;
        
        
        Pattern pattern = Pattern.compile(matchRegex);
        Matcher matcher = pattern.matcher(lineStr);

        int txtStartIdx = 0;
        int txtEndIdx = 0;

        while (matcher.find()) {
            if (!foundFirstIdentifier) {
                txtStartIdx = matcher.end();
                txtEndIdx = txtStartIdx;
                String leadingText = lineStr.substring(0, matcher.start()).trim();
                inter.setLeadingText(leadingText);
                foundFirstIdentifier = true;
                continue;
            }
            txtEndIdx = matcher.start();
            String interItem = lineStr.substring(txtStartIdx, txtEndIdx).trim();
            txtStartIdx = matcher.end();
            inter.addItem(interItem);
        }
        String interItem = lineStr.substring(txtStartIdx);
        inter.addItem(interItem);
        
        
        return inter;
    }
}
