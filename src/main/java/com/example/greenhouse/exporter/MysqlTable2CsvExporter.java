package com.example.greenhouse.exporter;

import com.example.greenhouse.constants.Constants;
import com.example.greenhouse.exceptions.CustomException;
import com.example.greenhouse.exceptions.MysqlHandlerException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.greenhouse.constants.Constants.EXPORTS_DIR;
import static com.example.greenhouse.database.MysqlDbHandler.validateDBParams;

public class MysqlTable2CsvExporter {

    //    @Value("${cogi.metrics.db-url}")
    private String dbUrl = Constants.MYSQL_DB_HOST;

    //    @Value("${db.user}")
    private String dbUser = Constants.MYSQL_DB_USER;

    //    @Value("${db.password}")
    private String dbPassword = Constants.MYSQL_DB_PASSWORD;

    private BufferedWriter fileWriter;

    public Path export(String sql) throws CustomException {
        try {
            validateDBParams(dbUrl, dbUser, dbPassword);
        } catch (MysqlHandlerException e) {
            throw new CustomException("pls check db configurations, " + e.getMessage(), e);
        }

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet result = statement.executeQuery(sql)) {

            return writeExportFile(result);
        } catch (SQLException e) {
            throw new CustomException("db error, sql:" + sql, e);
        } catch (IOException e) {
            throw new CustomException("csv file handling error", e);
        }
    }

    public Path writeExportFile(ResultSet result) throws IOException, SQLException {
        Path csvFileName = Paths.get(EXPORTS_DIR, getFileName("table".concat("_export")));
        fileWriter = new BufferedWriter(new FileWriter(String.valueOf(csvFileName)));

        int columnCount = writeHeaderLine(result);

        while (result.next()) {
            String line = "";

            for (int i = 1; i <= columnCount; i++) {
                Object valueObject = result.getObject(i);
                String valueString = "";

                if (valueObject != null)
                    valueString = valueObject.toString();

                if (valueObject instanceof String) {
                    valueString = "\"" + escapeDoubleQuotes(valueString) + "\"";
                }

                line = line.concat(valueString);

                if (i != columnCount) {
                    line = line.concat(",");
                }
            }

            fileWriter.newLine();
            fileWriter.write(line);
        }
        fileWriter.close();
        return csvFileName;
    }

    private String getFileName(String baseName) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String dateTimeInfo = dateFormat.format(new Date());
        return baseName.concat(String.format("_%s.csv", dateTimeInfo));
    }

    private int writeHeaderLine(ResultSet result) throws SQLException, IOException {
        // write header line containing column names
        ResultSetMetaData metaData = result.getMetaData();
        int numberOfColumns = metaData.getColumnCount();
        String headerLine = "";

        // exclude the first column which is the ID field
        for (int i = 1; i <= numberOfColumns; i++) {
            String columnName = metaData.getColumnName(i);
            headerLine = headerLine.concat(columnName).concat(",");
        }

        fileWriter.write(headerLine.substring(0, headerLine.length() - 1));

        return numberOfColumns;
    }

    private String escapeDoubleQuotes(String value) {
        return value.replaceAll("\"", "\"\"");
    }
}
