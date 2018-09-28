package com.project.todolist;

import java.util.Date;

public class Item {

    private String ttl;
    private String desc;
    private Date doTime;

    //state of item
    private boolean expanded;

    public Item(){

    }

    public Item(String ttl, String desc, Date doTime) {
        this.ttl = ttl;
        this.desc = desc;
        this.doTime = doTime;
    }

    public Item(String ttl, String desc) {
        this.ttl = ttl;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Item) obj).getTtl ().equals (this.ttl);
    }

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
