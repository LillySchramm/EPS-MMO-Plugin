package de.epsdev.plugins.MMO.data.mysql;


import de.epsdev.plugins.MMO.data.output.Out;

import org.mariadb.jdbc.*;

import javax.sql.RowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysql {
    private static final String mysql_host = "localhost";
    private static final String mysql_user = "admin";
    private static final String mysql_pwd = "1111";

    public static MariaDbDataSource DB_DATASOURCE;
    public static Connection DB_CONN;

    public static void connect() {
        Out.printToConsole("Connecting to database.....");
        try {
        DB_DATASOURCE = new MariaDbDataSource();

        DB_DATASOURCE.setUser(mysql_user);
        DB_DATASOURCE.setPassword(mysql_pwd);
        DB_DATASOURCE.setServerName(mysql_host);

        DB_CONN = DB_DATASOURCE.getConnection();

        Out.printToConsole("Connected to database!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ResultSet query(String sql){
        try {

            Statement stmt = null;
            stmt = DB_CONN.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            stmt.close();

            return rs;

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    public static void createDatabase(String db)  {
        query("CREATE DATABASE " + db);
        Out.printToConsole("Created database '" + db + "'");
    }

    public static void createTable(String db, String table, String args)  {
        query("CREATE TABLE `" + db + "`.`" + table + "` ("+ args +") ENGINE = InnoDB;");
        Out.printToConsole("Created table '" + db + "'.'" + table + "'");
    }

    public static void createDatabaseIfNotExists(String db)  {
        if(!databaseExist(db)){
            createDatabase(db);
        }
    }

    public static void createTableIfNotExists(String db, String table, String args)  {
        if(!tableExists(table)){
            createTable(db,table,args);
        }
    }

    public static boolean tableExists(String table){
        ResultSet rs = query("SELECT * FROM INFORMATION_SCHEMA.TABLES where TABLE_TYPE = 'BASE TABLE' AND TABLE_NAME = '" + table +"'");
        return isResultSetEmpty(rs);
    }

    public static boolean databaseExist(String db)  {
        ResultSet rs = query("SHOW DATABASES WHERE `Database` = '"+ db +"'");
        return isResultSetEmpty(rs);
    }

    public static boolean isResultSetEmpty(ResultSet rs) {
        try{
            if (rs.next()){
                return true;
            }
        }catch(SQLException throwables){
            throwables.printStackTrace();
        }

        return false;
    }

    public static void disconnect(){
        try {
            DB_CONN.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
