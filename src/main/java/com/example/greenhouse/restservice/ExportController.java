package com.example.greenhouse.restservice;

import com.example.greenhouse.exceptions.CustomException;
import com.example.greenhouse.exporter.MysqlTable2CsvExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import static com.example.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.example.greenhouse.sensors.Constants.SENSOR_TABLE;

@RestController
@RequestMapping("/export")
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);
    private static final String COMMA = ", ";

    @GetMapping("/sensors")
    public ResponseEntity exportWHLocations() {
        String sql = "SELECT * FROM " + SENSOR_TABLE;
        return executeExporter(sql);
    }

    private ResponseEntity executeExporter(String sql) {
        MysqlTable2CsvExporter exporter = new MysqlTable2CsvExporter();
        Path csvFilePath = null;
        try {
            csvFilePath = exporter.export(sql);
            if (csvFilePath.toFile().exists()) {
                InputStreamResource resource = new InputStreamResource(
                        new FileInputStream(String.valueOf(csvFilePath)));

                HttpHeaders responseHeaders = new HttpHeaders();
                responseHeaders.set("file-name", csvFilePath.toFile().getName());

                return ResponseEntity.ok().contentLength(new File(String.valueOf(csvFilePath)).length())
                        .contentType(MediaType.parseMediaType("application/octet-stream")).headers(responseHeaders)
                        .body(resource);
            }
        } catch (CustomException e) {
            logger.error("export table to csv failed, " + e.getMessage(), e);
        } catch (FileNotFoundException e) {
            logger.error("reading csv file failed, " + e.getMessage(), e);
        } finally {
            if (csvFilePath != null && csvFilePath.toFile().exists()) {
                if (!csvFilePath.toFile().delete()) {
                    logger.error("failed to delete the file created: {}", csvFilePath);
                }
            }
        }
        return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
