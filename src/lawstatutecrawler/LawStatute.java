/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lawstatutecrawler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author 陈光曦
 */
public class LawStatute {

    private final String url;
    private String title;
    private String content;

    public LawStatute(String url) {
        this.url = url;
    }

    public int retrieveInfo() {
        System.out.println("Fetching data from: " + url);
        StringBuilder sb = new StringBuilder();
        try {
            Document doc = Jsoup.connect(url).get();

            /// Get title
            Element titleElement = (Element) doc.getElementsByClass("title").get(0);
            title = titleElement.text();

            /// Get content
            List contents = doc.getElementsByClass("newsCon");
            Element contentElement = (Element) contents.get(0);
            contentElement = (Element) contentElement.getElementsByTag("div").get(0);
            List items = contentElement.getElementsByTag("p");
            for (int i = 0; i < items.size(); ++i) {
                Element e = (Element) items.get(i);
                sb.append(e.text()).append("\n");
            }
            content = sb.toString();
        } catch (IOException e) {
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
