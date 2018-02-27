/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.xiaofabo.lawstatutecrawler;

/**
 *
 * @author 陈光曦
 */
public class StatuteItem {

    private int index;
    private int section;
    private int subsection;
    private String content;

    public StatuteItem() {
    }

    public StatuteItem(int index, String content) {
        this.index = index;
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getSubsection() {
        return subsection;
    }

    public void setSubsection(int subsection) {
        this.subsection = subsection;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
