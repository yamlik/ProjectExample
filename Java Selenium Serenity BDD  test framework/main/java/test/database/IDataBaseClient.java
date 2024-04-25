package com..test.database;

import java.sql.SQLException;

public interface IDataBaseClient {

    SQLQuery.Builder connect() throws SQLException;

}
