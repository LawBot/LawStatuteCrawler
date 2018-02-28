/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.laowstatutecrawler.entity;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author 陈光曦
 */
public class LawStatute {

    private String name;
    private String leadingText;
    private String tableOfContent;
    private List<StatuteItem> itemList;

    public LawStatute(String name) {
        this.name = name;
        this.leadingText = "";
        this.tableOfContent = "";
        this.itemList = new LinkedList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeadingText() {
        return leadingText;
    }

    public void setLeadingText(String leadingText) {
        this.leadingText = leadingText;
    }

    public String getTableOfContent() {
        return tableOfContent;
    }

    public void setTableOfContent(String tableOfContent) {
        this.tableOfContent = tableOfContent;
    }

    public List<StatuteItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<StatuteItem> itemList) {
        this.itemList = itemList;
    }
    
    public boolean addItem(StatuteItem item){
        return itemList.add(item);
    }

}
