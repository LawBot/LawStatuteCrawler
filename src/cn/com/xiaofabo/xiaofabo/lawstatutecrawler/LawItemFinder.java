/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 陈光曦
 */
public class LawItemFinder {

    public LawItemFinder() {
    }

    public int genRefFile(String refFilePath, String statuteDataFilePath, String interDataFilePath, String resFilePath, int limit) {
        String line = null;
        StringBuilder resultSB = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(refFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int lineNumber = 0;
            int notFound = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.startsWith("《")) {
                    continue;
                }
                int idx = line.indexOf(",");
                if (idx <= 0) {
                    continue;
                }
                String chunk = line.substring(0, idx);
                String freq = line.substring(idx + 1);
                String content = searchItem(chunk, statuteDataFilePath, interDataFilePath);
                if (content == null) {
                    ++notFound;
                    content = "N/A";
                }
                resultSB = resultSB.append(chunk).append(",").append(freq).append(",").append(content).append("\n");

                System.out.println(lineNumber);
                if (++lineNumber >= limit) {
                    break;
                }
            }
            bufferedReader.close();
            System.err.println("Number of not found: " + notFound);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + refFilePath);
            return -1;
        } catch (IOException e) {
            System.err.println("IOException when reading file: " + refFilePath);
            return -1;
        }

        
        System.out.println("Writing data to file: " + resFilePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resFilePath))) {
            writer.write(resultSB.toString());
        } catch (IOException e) {
            System.err.println("Failed!!!");
            return -1;
        }
        System.out.println("Done.");
        return 0;
    }

    // Input format e.g. 《中华人民共和国合同法》第二百零五条
    private String searchItem(String input, String statuteDataFilePath, String interDataFilePath) {
        String item = null;
        String lawName = input.substring(input.indexOf("《") + 1, input.indexOf("》"));
        String lawIndexStr = input.substring(input.indexOf("第") + 1, input.indexOf("条"));
        if (lawIndexStr == null || lawIndexStr.isEmpty()) {
            return null;
        }
        int lawIndex = CnNumber2Int(lawIndexStr);

        String line = null;
        try {
            boolean found = false;
            FileReader fileReader = new FileReader(statuteDataFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String dataList[] = line.split(",");
                if (dataList.length < 3) {
                    continue;
                }
                String name = dataList[0];
                String index = dataList[1];
                String content = dataList[2];

                if (lawName.equals(name) && lawIndex == Integer.parseInt(index)) {
                    item = content;
                    found = true;
                    break;
                }
            }
            bufferedReader.close();

            if (!found) {
                fileReader = new FileReader(interDataFilePath);
                bufferedReader = new BufferedReader(fileReader);
                
                while ((line = bufferedReader.readLine()) != null) {
                String dataList[] = line.split(",");
                if (dataList.length < 3) {
                    continue;
                }
                String name = dataList[0];
                String index = dataList[1];
                String content = dataList[2];

                if (lawName.equals(name) && lawIndex == Integer.parseInt(index)) {
                    item = content;
                    found = true;
                    break;
                }
            }
            bufferedReader.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + statuteDataFilePath);
            return null;
        } catch (IOException e) {
            System.err.println("IOException when reading file: " + statuteDataFilePath);
            return null;
        }
        return item;
    }

    static int CnNumber2Int(String s) {
        String x = " 一二三四五六七八九十百";
        String matchRegex = "[一二三四五六七八九十百零]";
        
        Pattern pattern = Pattern.compile(matchRegex);
        Matcher matcher = pattern.matcher(s);
        if(!matcher.find()){
            return Integer.parseInt(s);
        }
        
        int l = s.length();
        int i = x.indexOf(s.charAt(l - 1));
        int j = x.indexOf(s.charAt(0));
        if (l < 2) {
            return i; // 1-10
        }
        if (l < 3) {
            if (i == 10) {
                return j * 10; // 20,30,40,50,60,70,80,90
            }
            if (i > 10) {
                return j * 100; // 100,200,300,400,500,600,700,800,900
            }
            return 10 + i; // 11-19
        }
        if (l < 4) {
            return j * 10 + i; // 21-29,31-39,41-49,51-59,61-69,71-79,81-89,91-99
        }
        if (l < 5) {
            return j * 100 + i; // 101-109,201-209,301-309,401-409,501-509,601-609,701-709,801-809,901-909
        }
        return j * 100 + i + x.indexOf(s.charAt(2)) * 10; // 111-119,121-129,131-139,...,971-979,981-989,991-999
    }
}
