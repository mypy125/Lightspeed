package com.lightspeed.sqlparser;

import com.lightspeed.sqlparser.querycomand.Query;

public class SqlStart {
    public static void main(String[] args) {
        String sql = "SELECT author.name, count(book.id), sum(book.cost) " +
                "FROM author " +
                "LEFT JOIN book ON (author.id = book.author_id) " +
                "WHERE book.cost > 100 " +
                "GROUP BY author.name " +
                "ORDER BY sum(book.cost) DESC " +
                "LIMIT 10 OFFSET 5;";

        SqlParser parser = new SqlParser();
        Query query = parser.parse(sql);

        System.out.println(query.getColumns());
        System.out.println(query.getFromSources());
        System.out.println(query.getWhereClauses());
    }
}
