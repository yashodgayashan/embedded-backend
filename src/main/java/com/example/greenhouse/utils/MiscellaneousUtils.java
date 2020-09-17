package com.example.greenhouse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MiscellaneousUtils {

    private MiscellaneousUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(MiscellaneousUtils.class);

    public static String withComma(String field) {
        return encapFieldWithBackTick(field) + ", ";
    }

    public static String encapFieldWithBackTick(String field) {
        return "`" + field + "`";
    }

    public static void closeResultSet(ResultSet rs, String resultSetClosingErr, Logger logger) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(resultSetClosingErr);
            }
        }
    }
}
