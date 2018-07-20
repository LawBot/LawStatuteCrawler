/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler.main;

import cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity.Interpretation;
import cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity.LawStatute;
import cn.com.xiaofabo.xiaofabo.lawstatutecrawler.InterpretationAnalyzer;
import cn.com.xiaofabo.xiaofabo.lawstatutecrawler.InterpretationCrawlerUtil;
import cn.com.xiaofabo.xiaofabo.lawstatutecrawler.LawStatuteAnalyzer;
import cn.com.xiaofabo.xiaofabo.lawstatutecrawler.LawStatuteCrawlerUtil;
import cn.com.xiaofabo.xiaofabo.lawstatutecrawler.LawItemFinder;
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
//        crawlLawStatute();
//        genLawStatuteCsv();
//        crawlInterpretation();

//        genLawStatuteCsv();
//        genInterpretationCsv();

        genV5Format();
//
//        LawItemFinder lf = new LawItemFinder();
//        String refFilePath = "D:\\OneDrive\\小法博科技\\产品\\类案推送\\民间借贷\\referLawCount-all.csv";
//        String statuteDataFilePath = "D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\law_dataset.csv";
//        String interDataFilePath = "D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\inter_dataset.csv";
//        String resFilePath = "D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\referLawCountWithContent100.csv";
//        lf.genRefFile(refFilePath, statuteDataFilePath, interDataFilePath, resFilePath, 100);
    }

    public static int crawlInterpretation() throws IOException {
        List<String> indexList = new LinkedList<>();
        String baseUrl = "http://www.court.gov.cn/fabu-gengduo-16.html";
        indexList.add(baseUrl);
        for (int i = 2; i <= 12; ++i) {
            indexList.add(baseUrl + "?page=" + i);
        }

        for (int i = 0; i < indexList.size(); ++i) {
            String url = indexList.get(i);
            Document doc = Jsoup.connect(url).get();
            Element divElement = doc.getElementsByClass("sec_list").get(0);
            Element ulElement = divElement.children().select("ul").get(0);
            List<Element> linkList = ulElement.select("a");
            for (int j = 0; j < linkList.size(); ++j) {
                Element link = linkList.get(j);
                String interUrl = "http://www.court.gov.cn" + link.attr("href");
                InterpretationCrawlerUtil interUtil = new InterpretationCrawlerUtil(interUrl);
                interUtil.retrieveInfo();
                interUtil.writeFile("data/司法解释");
            }
        }
        return 0;
    }

    public static int crawlLawStatute() throws IOException {
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

    public static void genLawStatuteCsv() {
        LawStatuteAnalyzer analyzer = new LawStatuteAnalyzer();
        analyzer.getFileList("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\法律");
        List fileList = analyzer.getFileList();
        StringBuilder csvString = new StringBuilder();
        for (int i = 0; i < fileList.size(); ++i) {
            String filePath = (String) fileList.get(i);
            LawStatute statute = analyzer.analyze(filePath);
            String name = analyzer.getName(filePath);
            for (int j = 0; j < statute.getItemList().size(); ++j) {

                String indexStr = name + "第" + (j + 1) + "条";
                String content = statute.getItemList().get(j).getContent();
//                csvString.append(indexStr).append(";;;;;").append(content).append("\n");
                csvString.append(name).append(",").append(j + 1).append(",").append(content).append("\n");
            }
        }

        try (PrintWriter out = new PrintWriter("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\law_dataset.csv")) {
            out.println(csvString.toString());
        } catch (FileNotFoundException e) {
            System.err.println("Write to file error!");
        }
//        LawStatute statute = analyzer.analyze("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\民法商法\\中华人民共和国海商法.txt");
//        for(int i = 0; i < statute.getItemList().size(); ++i){
//            System.out.println("Item " + i + ": " + statute.getItemList().get(i).getContent());
//        }
    }

    public static void genV5Format() {
        LawStatuteAnalyzer analyzer = new LawStatuteAnalyzer();
        analyzer.getFileList("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\法律");
        List fileList = analyzer.getFileList();
        for (int i = 0; i < fileList.size(); ++i) {
            StringBuilder sb = new StringBuilder();
            String filePath = (String) fileList.get(i);
            LawStatute statute = analyzer.analyze(filePath);
            String name = analyzer.getName(filePath);
            for (int j = 0; j < statute.getItemList().size(); ++j) {
                String indexStr = "【" + (j + 1) + "】";
                String content = statute.getItemList().get(j).getContent().trim();
                sb.append(indexStr).append("\n").append(content).append("\n");
            }
            try (PrintWriter out = new PrintWriter("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\v5output\\" + name + ".txt")) {
                out.println(sb.toString());
            } catch (FileNotFoundException e) {
                System.err.println("Write to file error!");
            }
        }
    }

    public static void genInterpretationCsv() {
        InterpretationAnalyzer analyzer = new InterpretationAnalyzer();
        analyzer.getFileList("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\司法解释");
        List fileList = analyzer.getFileList();
        StringBuilder csvString = new StringBuilder();
        for (int i = 0; i < fileList.size(); ++i) {
            String filePath = (String) fileList.get(i);
            Interpretation inter = analyzer.analyze(filePath);
            String name = analyzer.getName(filePath);
            for (int j = 0; j < inter.getItemList().size(); ++j) {

                String indexStr = name + "第" + (j + 1) + "条";
                String content = inter.getItemList().get(j);
//                csvString.append(indexStr).append(";;;;;").append(content).append("\n");
                csvString.append(name).append(",").append(j + 1).append(",").append(content).append("\n");
            }
        }

        try (PrintWriter out = new PrintWriter("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\inter_dataset.csv")) {
            out.println(csvString.toString());
        } catch (FileNotFoundException e) {
            System.err.println("Write to file error!");
        }
//        LawStatute statute = analyzer.analyze("D:\\Code\\XiaoFaBo\\LawStatuteCrawler\\data\\民法商法\\中华人民共和国海商法.txt");
//        for(int i = 0; i < statute.getItemList().size(); ++i){
//            System.out.println("Item " + i + ": " + statute.getItemList().get(i).getContent());
//        }
    }
}
