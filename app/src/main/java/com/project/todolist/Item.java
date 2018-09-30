package com.project.todolist;


public class Item {

    private String ttl;
    private String desc;

    //state of item
    private boolean expanded;

    public Item(){

    }

    public Item(String ttl, String desc) {
        this.ttl = ttl;
        this.desc = desc;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Item) obj).getTtl ().equals (this.ttl);
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
