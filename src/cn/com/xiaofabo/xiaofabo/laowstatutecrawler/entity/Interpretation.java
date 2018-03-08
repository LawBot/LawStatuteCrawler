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
public class Interpretation {
    private String name;
    private String establishTime;
    private String leadingText;
    private List<String> itemList;
    
    public Interpretation(String name){
        this.name = name;
        establishTime = "";
        leadingText = "";
        itemList = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstablishTime() {
        return establishTime;
    }

    public void setEstablishTime(String establishTime) {
        this.establishTime = establishTime;
    }

    public String getLeadingText() {
        return leadingText;
    }

    public void setLeadingText(String leadingText) {
        this.leadingText = leadingText;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }
    
    public boolean addItem(String item){
        return itemList.add(item);
    }
}
