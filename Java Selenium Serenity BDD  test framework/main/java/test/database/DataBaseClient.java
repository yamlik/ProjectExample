package com..test.database;

import com..test.context.ScenarioContext;
import com..test.utilities.PropertiesManager;
import net.thucydides.core.annotations.Shared;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseClient implements IDataBaseClient {

    private final PropertiesManager propertiesManager = PropertiesManager.get();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    private final String databaseURL = propertiesManager.getProperty("database.server");
    private final String databaseUserName = propertiesManager.getProperty("database.user");
    private final String databasePassword = propertiesManager.getProperty("database.password");
    private final String databaseName = ScenarioContext.getTSNTance().getDatabaseName();


    String query = "SELECT * FROM dbo.A_Application";

    public void connectToDataBase() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
        try {
            connection = DriverManager.getConnection(databaseURL, databaseUserName, databasePassword);
        } catch (SQLException sqlException) {
            throw new SQLException("Data base connection failed: Database URL: " + databaseURL + " Database Username: " + databaseUserName
                    + " Database Password: " + databasePassword, sqlException);
        }
    }


    public void executeSelect(String sqlQuery) throws SQLException {
        if (connection == null) {
            connectToDataBase();
        }
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
        } catch (SQLException sqlException) {
            close();
            throw new SQLException("Query Execution failed:", sqlException);
        }
    }

    public List<Map<String, String>> getResultSetAsMapList() throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        List<String> columns = new ArrayList<>(metaData.getColumnCount());
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            columns.add(metaData.getColumnName(i));
        }
        List<Map<String, String>> data = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> row = new HashMap<>(columns.size());
            for (String col : columns) {
                row.put(col, resultSet.getString(col));
            }
            data.add(row);
        }
        close();
        return data;
    }

    public void executeJob() throws SQLException {
        if (connection == null) {
            connectToDataBase();
        }
        String BatchJobName="EXEC msdb.dbo.sp_start_job N'"+ databaseName +" - Generate Indexed Fuzzy Match Keys'";
        try {
            CallableStatement cs=connection.prepareCall(BatchJobName);
            cs.execute();
        } catch (SQLException sqlException) {
            connection.close();
            throw new SQLException("Query Execution failed:", sqlException);
        }
    }

    protected void close() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

    @Override
    public SQLQuery.Builder connect() throws SQLException {
        connectToDataBase();
        return new SQLQuery.Builder(this);
    }

    public void executeScriptUsingScriptRunner(String requiredFileName) throws IOException, SQLException {
        // initialize script path
        String scriptFilePath = System.getProperty("user.dir") + "/src/main/resources/data/UI/sqlScripts/" + requiredFileName + ".sql";
        Reader reader = null;
        try {
            if (connection == null) {
                connectToDataBase();
            }
            // create ScripRunner object
            ScriptRunner scriptExecutor = new org.apache.ibatis.jdbc.ScriptRunner(connection);
            // initialize file reader
            reader = new BufferedReader(new FileReader(scriptFilePath));
            // execute script with file reader as input
            scriptExecutor.runScript(reader);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // close file reader
            if (reader != null) {
                reader.close();
            }
            // close db connection
            if (connection != null) {
                connection.close();
            }
        }
    }
}