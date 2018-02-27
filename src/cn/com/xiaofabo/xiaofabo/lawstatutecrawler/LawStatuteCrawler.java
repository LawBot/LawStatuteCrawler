/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *
 * @author 陈光曦
 */
public class LawStatuteCrawler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        crawl();
        LawStatuteAnalyzer analyzer = new LawStatuteAnalyzer();
        analyzer.getFileList("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data");
        List fileList = analyzer.getFileList();
        StringBuilder csvString = new StringBuilder();
        for (int i = 0; i < fileList.size(); ++i) {
            String filePath = (String) fileList.get(i);
            LawStatute statute = analyzer.analyze(filePath);
            String name = analyzer.getName(filePath);
            for (int j = 0; j < statute.getItemList().size(); ++j) {
                String indexStr = name + "第" + (j + 1) + "条";
                String content = statute.getItemList().get(j).getContent();
                csvString.append(indexStr).append(";;;;;").append(content).append("\n");
            }
        }
        
        try (PrintWriter out = new PrintWriter("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\dataset.csv")) {
            out.println(csvString.toString());
        }catch(FileNotFoundException e){
            System.err.println("Write to file error!");
        }
//        LawStatute statute = analyzer.analyze("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\民法商法\\中华人民共和国海商法.txt");
//        for(int i = 0; i < statute.getItemList().size(); ++i){
//            System.out.println("Item " + i + ": " + statute.getItemList().get(i).getContent());
//        }
    }

    public static int crawl() throws IOException {
//        List<String> constitution = new LinkedList<>();
//        List<String> constitutionRelated = new LinkedList<>();
//        List<String> civilBusiness = new LinkedList<>();
//        List<String> administrative = new LinkedList<>();
//        List<String> economic = new LinkedList<>();
//        List<String> social = new LinkedList<>();
//        List<String> penal = new LinkedList<>();
//        List<String> lawsuit = new LinkedList<>();
//        List<String> interpretation = new LinkedList<>();
//        
        List<String> indexList = new LinkedList<>();
        indexList.add("http://jlfxhw.com/flxf/index.jhtml");    ///宪法
        indexList.add("http://jlfxhw.com/flxgf/index.jhtml");   ///宪法相关法
        indexList.add("http://jlfxhw.com/flxgf/index_2.jhtml"); ///宪法相关法
        indexList.add("http://jlfxhw.com/flxgf/index_3.jhtml"); ///宪法相关法
        indexList.add("http://jlfxhw.com/flsf/index.jhtml");    ///民法商法
        indexList.add("http://jlfxhw.com/flsf/index_2.jhtml");  ///民法商法
        indexList.add("http://jlfxhw.com/flxzf/index.jhtml");   ///行政法  
        indexList.add("http://jlfxhw.com/flxzf/index_2.jhtml"); ///行政法
        indexList.add("http://jlfxhw.com/flxzf/index_3.jhtml"); ///行政法
        indexList.add("http://jlfxhw.com/flxzf/index_4.jhtml"); ///行政法
        indexList.add("http://jlfxhw.com/flxzf/index_5.jhtml"); ///行政法
        indexList.add("http://jlfxhw.com/fljjf/index.jhtml");   ///经济法
        indexList.add("http://jlfxhw.com/fljjf/index_2.jhtml"); ///经济法
        indexList.add("http://jlfxhw.com/fljjf/index_3.jhtml"); ///经济法
        indexList.add("http://jlfxhw.com/fljjf/index_4.jhtml"); ///经济法
        indexList.add("http://jlfxhw.com/flshf/index.jhtml");   ///社会法
        indexList.add("http://jlfxhw.com/flshf/index_2.jhtml"); ///社会法
        indexList.add("http://jlfxhw.com/flxingfa/index.jhtml");///刑法
        indexList.add("http://jlfxhw.com/flcxf/index.jhtml");   ///诉讼与非诉讼程序法
        indexList.add("http://jlfxhw.com/fllf/index.jhtml");    ///立法解释
        indexList.add("http://jlfxhw.com/fllf/index_2.jhtml");  ///立法解释

        for (int i = 0; i < indexList.size(); ++i) {
            String url = indexList.get(i);
            Document doc = Jsoup.connect(url).get();
            List divList = doc.getElementsByClass("hasPic");
            for (int j = 0; j < divList.size(); ++j) {
                Element div = (Element) divList.get(j);
                String statuteUrl = div.getElementsByTag("a").get(0).attr("href");
                LawStatuteCrawlerUtil ls = new LawStatuteCrawlerUtil(statuteUrl);
                ls.retrieveInfo();
                StringBuilder pathSb = new StringBuilder("data");
                switch (i) {
                    case 0:
                        pathSb.append("/宪法");
                        break;
                    case 1:
                    case 2:
                    case 3:
                        pathSb.append("/宪法相关法");
                        break;
                    case 4:
                    case 5:
                        pathSb.append("/民法商法");
                        break;
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        pathSb.append("/行政法");
                        break;
                    case 11:
                    case 12:
                    case 13:
                    case 14:
                        pathSb.append("/经济法");
                        break;
                    case 15:
                    case 16:
                        pathSb.append("/社会法");
                        break;
                    case 17:
                        pathSb.append("/刑法");
                        break;
                    case 18:
                        pathSb.append("/诉讼与非诉讼程序法");
                        break;
                    case 19:
                    case 20:
                        pathSb.append("/立法解释");
                        break;
                    default:
                        break;
                }
                ls.writeFile(pathSb.toString());
            }
        }

        return 0;
    }

}