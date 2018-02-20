/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lawstatutecrawler;

import java.io.IOException;
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
    public static void main(String[] args) throws IOException{
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
        
        for(int i = 0; i < indexList.size(); ++i){
            String url = indexList.get(i);
            Document doc = Jsoup.connect(url).get();
            List divList = doc.getElementsByClass("hasPic");
            for(int j = 0; j < divList.size(); ++j){
                Element div = (Element)divList.get(j);
                String statuteUrl = div.getElementsByTag("a").get(0).attr("href");
                LawStatute ls = new LawStatute(statuteUrl);
                ls.retrieveInfo();
                ls.writeFile("data");
            }
        }
    }
    
}
