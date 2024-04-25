package com..test.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SQLQuery {

    private String sqlQueryString;

    public String getSqlQueryString() {
        return this.sqlQueryString;
    }

    public static class Builder {

        private final DataBaseClient dataBaseClient;
        private String sqlQuery;

        public Builder(DataBaseClient dataBaseClient) {
            this.dataBaseClient = dataBaseClient;
        }

        public Builder withQuery(String queryString) {
            sqlQuery = queryString;
            return this;
        }
//        public Builder addWhereCondition(String param, String value) {
//            sqlQuery.concat(String.valueOf(ApplicationConstants.COLUMN_NAMES.valueOf(param))
//                    .concat("'"+value+"' "));
//            return this;
//        }

        public Builder executeQuery() throws SQLException {
            dataBaseClient.executeSelect(sqlQuery);
            return this;
        }

        public List<Map<String,String>> getResultSetAsMapList() throws SQLException {
            return dataBaseClient.getResultSetAsMapList();
        }

    }
}
