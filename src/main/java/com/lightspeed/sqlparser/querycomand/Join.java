package com.lightspeed.sqlparser.querycomand;

public class Join {
    private String joinType;
    private Source source;
    private String condition;

    public Join(String joinType, Source source, String condition) {
        this.joinType = joinType;
        this.source = source;
        this.condition = condition;
    }

    public String getJoinType() {
        return joinType;
    }

    public void setJoinType(String joinType) {
        this.joinType = joinType;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Join{" +
                "joinType='" + joinType + '\'' +
                ", source=" + source +
                ", condition='" + condition + '\'' +
                '}';
    }
}
