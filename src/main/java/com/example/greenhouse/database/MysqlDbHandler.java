package com.example.greenhouse.database;


import com.example.greenhouse.exceptions.MysqlHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.greenhouse.constants.Constants.MYSQL_DB_HOST;
import static com.example.greenhouse.constants.Constants.MYSQL_DB_USER;
import static com.example.greenhouse.constants.Constants.MYSQL_DB_PASSWORD;

public class MysqlDbHandler {

    private String dbUrl = MYSQL_DB_HOST;
    private String dbUser = MYSQL_DB_USER;
    private String dbPassword = MYSQL_DB_PASSWORD;

    private static final Logger logger = LoggerFactory.getLogger(MysqlDbHandler.class);
    private Connection conn;

    public MysqlDbHandler() throws MysqlHandlerException {
        try {
            validateDBParams(dbUrl, dbUser, dbPassword);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            throw new MysqlHandlerException(
                    "database connection failure state: " + e.getSQLState() + "message:" + e.getMessage() + ", dbUrl: "
                            + dbUrl + " dbUser:" + dbUser + " dbPassword:" + dbPassword, e);
        } catch (MysqlHandlerException e) {
            throw new MysqlHandlerException(e.getMessage(), e);
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() throws MysqlHandlerException {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new MysqlHandlerException("closing db connection failed", e);
        }
    }

    public static void validateDBParams(String dbUrl, String dbUser, String dbPass) throws MysqlHandlerException {
        if (!(dbUrl != null && !"".equals(dbUrl.trim()) && dbUser != null && !"".equals(dbUser.trim())
                && dbPass != null && !"".equals(dbPass.trim()))) {
            throw new MysqlHandlerException(
                    "database parameters not set, dbUrl:" + dbUrl + ", dbUser:" + dbUser + ", dbPassword:"
                            + dbPass);
        }
    }

    public void validateDBParams() throws MysqlHandlerException {
        validateDBParams(this.dbUrl, this.dbUser, this.dbPassword);
    }

}
