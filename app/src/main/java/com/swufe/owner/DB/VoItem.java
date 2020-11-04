package com.swufe.owner.DB;

public class VoItem {
    private int id;
    private String EnString;
    private String ChString;
    private String EnUrl;

    public String getEnUrl() {
        return EnUrl;
    }

    public void setEnUrl(String enUrl) {
        EnUrl = enUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnString() {
        return EnString;
    }

    public void setEnString(String enString) {
        EnString = enString;
    }

    public String getChString() {
        return ChString;
    }

    public void setChString(String chString) {
        ChString = chString;
    }

    public VoItem() {
        super();
        EnString = "";
        ChString="";
        EnString="";
    }
    public VoItem(String enString,  String chString) {
        super();
        this.EnString = enString;
        this.ChString=chString;
    }

}
