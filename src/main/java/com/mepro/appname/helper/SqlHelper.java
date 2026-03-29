package com.mepro.appname.helper;

public class SqlHelper {
    private StringBuilder sql = new StringBuilder();
    public SqlHelper append(String s) {
        sql.append(s).append(" ");
        return this;
    }
    
    public SqlHelper where(boolean condition, String clause) {
        if (condition) {
            sql.append(" AND ").append(clause);
        }
        return this;
    }
    
    public String build() {
        return sql.toString();
    }
}
