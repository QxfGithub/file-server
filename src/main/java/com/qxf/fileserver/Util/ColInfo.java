package com.qxf.fileserver.Util;

import java.util.Map;

public class ColInfo {
    public static enum TYPE {
        STRING("字符串"),
        //
        NUMBER("数字"),
        //
        DATE("日期");
        private String describe;

        TYPE(String describe) {
            this.describe = describe;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }
    }

    private String colName;
    private String showName;
    private TYPE type;
    private String format;
    private Map<String, String> map;

    public ColInfo() {
    }

    public ColInfo(String colName, String showName, TYPE type) {
        this.colName = colName;
        this.showName = showName;
        this.type = type;
    }

    public ColInfo(String colName, String showName, TYPE type, String format) {
        this.colName = colName;
        this.showName = showName;
        this.type = type;
        this.format = format;
    }

    public ColInfo(String colName, String showName, TYPE type, Map<String, String> map) {
        this.colName = colName;
        this.showName = showName;
        this.type = type;
        this.map = map;
    }

    public ColInfo(String colName, String showName, TYPE type, String format, Map<String, String> map) {
        this.colName = colName;
        this.showName = showName;
        this.type = type;
        this.format = format;
        this.map = map;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
