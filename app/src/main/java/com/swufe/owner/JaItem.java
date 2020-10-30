package com.swufe.owner;

public class JaItem {
    private int id;
    private String JaString;
    private String kaString;
    private String ChString;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJaString() {
        return JaString;
    }

    public void setJaString(String jaString) {
        JaString = jaString;
    }

    public String getKaString() {
        return kaString;
    }

    public void setKaString(String kaString) {
        this.kaString = kaString;
    }

    public String getChString() {
        return ChString;
    }

    public void setChString(String chString) {
        ChString = chString;
    }

    public JaItem() {
        super();
        JaString = "";
        kaString = "";
        ChString="";
    }
    public JaItem(String jaString, String kaString,String chString) {
        super();
        this.JaString = jaString;
        this.kaString = kaString;
        this.ChString=chString;
    }

}
