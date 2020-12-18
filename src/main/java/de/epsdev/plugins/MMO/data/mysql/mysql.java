package de.epsdev.plugins.MMO.data.mysql;


import de.epsdev.plugins.MMO.data.output.Out;

import org.mariadb.jdbc.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class mysql {
    private static final String mysql_host = "localhost";
    private static final String mysql_user = "root";
    private static final String mysql_pwd = "";

    public static MariaDbDataSource DB_DATASOURCE;

    public static void connect() {
        Out.printToConsole("Connecting to database");
        try {
        DB_DATASOURCE = new MariaDbDataSource();

        DB_DATASOURCE.setUser(mysql_user);
        DB_DATASOURCE.setPassword(mysql_pwd);
        DB_DATASOURCE.setServerName(mysql_host);

        Connection conn = DB_DATASOURCE.getConnection();
        Statement stmt = null;

        stmt = conn.createStatement();

        //ResultSet rs = stmt.executeQuery("SELECT * FROM USERS");

        //rs.close();
        stmt.close();
        conn.close();

        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
