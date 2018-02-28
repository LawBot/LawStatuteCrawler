/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author 陈光曦
 */
public class InterpretationCrawlerUtil {
    private final String url;
    private String title;
    private String content;

    public InterpretationCrawlerUtil(String url) {
        this.url = url;
    }
    
    public int retrieveInfo(){
        System.out.println("Fetching data from: " + url);
        StringBuilder sb = new StringBuilder();
        try{
            Document doc = Jsoup.connect(url).get();
            
            /// Get title
            Element titleElement = (Element) doc.getElementsByClass("title").get(0);
            title = titleElement.text();
            
            /// Get content
            Element contentElement = (Element) doc.getElementsByClass("txt_txt").get(0);
            content = contentElement.text().replaceAll(" 　　", "\n");
        }catch(IOException e){
            System.err.println("Failed!!!");
            return -1;
        }
        System.out.println("Finished. Title: " + title);
        return 0;
    }
    
    public int writeFile(String path) {
        String fullPath = path + "/" + title + ".txt";
        System.out.println("Writing data to file: " + fullPath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Failed!!!");
            return -1;
        }
        System.out.println("Done.");
        return 0;
    }
}
