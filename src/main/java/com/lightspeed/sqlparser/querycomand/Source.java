package com.lightspeed.sqlparser.querycomand;

public class Source {
    private String tableName;
    private String alias;

    public Source(String tableName, String alias) {
        this.tableName = tableName;
        this.alias = alias;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String toString() {
        return "Source{" +
                "tableName='" + tableName + '\'' +
                ", alias='" + alias + '\'' +
                '}';
    }
}
