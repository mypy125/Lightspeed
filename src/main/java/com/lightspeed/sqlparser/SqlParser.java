package com.lightspeed.sqlparser;

import com.lightspeed.sqlparser.querycomand.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlParser {

    public Query parse(String sql){
        Query query = new Query();

        List<String> columns = parseColumns(sql);
        query.setColumns(columns);

        List<Source> fromSources = parseFromSources(sql);
        query.setFromSources(fromSources);

        List<Join> joins = parseJoins(sql);
        query.setJoins(joins);

        List<WhereClause> whereClauses = parseWhereClauses(sql);
        query.setWhereClauses(whereClauses);

        List<String> groupByColumns = parseGroupByColumns(sql);
        query.setGroupByColumns(groupByColumns);

        List<Sort> sortColumns = parseSortColumns(sql);
        query.setSortColumns(sortColumns);

        query.setLimit(parseLimit(sql));
        query.setOffset(parseOffset(sql));

        return query;
    }

    private List<String> parseColumns(String sql){
        String selectPattern = "SELECT (.+?) FROM";
        Matcher matcher = Pattern.compile(selectPattern, Pattern.CASE_INSENSITIVE).matcher(sql);
        if(matcher.find()){
            String columnPart = matcher.group(1);
            return Arrays.asList(columnPart.split(",\\s*"));
        }
        return new ArrayList<>();
    }

    private List<Source> parseFromSources(String sql){
        String fromPattern = "FROM (.+?)( WHERE| JOIN| GROUP BY| ORDER BY| LIMIT| OFFSET|$)";
        Matcher matcher = Pattern.compile(fromPattern, Pattern.CASE_INSENSITIVE).matcher(sql);
        if(matcher.find()){
            String fromPart = matcher.group(1);
            List<Source> sources = new ArrayList<>();
            for(String source : fromPart.split(",\\s*")){
                String[] parts = source.split(",\\s*");
                sources.add(new Source(parts[0], parts.length > 1 ? parts[1] : null));
            }
            return sources;
        }
        return new ArrayList<>();
    }

    private List<Join> parseJoins(String sql){
        return new ArrayList<>();
    }

    private List<WhereClause> parseWhereClauses(String sql){
        String wherePattern = "WHERE (.+?)( GROUP BY| ORDER BY| LIMIT| OFFSET|$)";
        Matcher matcher = Pattern.compile(wherePattern, Pattern.CASE_INSENSITIVE).matcher(sql);
        if(matcher.find()){
            String wherePart = matcher.group(1);
            return Arrays.asList(new WhereClause(wherePart));
        }
        return new ArrayList<>();
    }

    private List<String> parseGroupByColumns(String sql){
       String groupByPattern = "GROUP BY (.+?)( ORDER BY| LIMIT| OFFSET|$)";
       Matcher matcher = Pattern.compile(groupByPattern, Pattern.CASE_INSENSITIVE).matcher(sql);
       if(matcher.find()){
           String groupByPart = matcher.group(1);
           return Arrays.asList(groupByPart.split(",\\s*"));
       }
       return new ArrayList<>();
    }

    private List<Sort> parseSortColumns(String sql){
        return new ArrayList<>();
    }

    private Integer parseLimit(String sql){
        String limitPattern = "LIMIT (\\d+)";
        Matcher matcher = Pattern.compile(limitPattern, Pattern.CASE_INSENSITIVE).matcher(sql);
        if(matcher.find()){
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    private Integer parseOffset(String sql){
        String offsetPattern = "OFFSET (\\d+)";
        Matcher matcher = Pattern.compile(offsetPattern, Pattern.CASE_INSENSITIVE).matcher(sql);
        if(matcher.find()){
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }
}
