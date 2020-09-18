package com.example.greenhouse.sensors;

import com.example.greenhouse.exceptions.MysqlHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;

@RestController
public class SensorController {

    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

    @GetMapping("/sensors")
    public ResponseEntity getSensors() {

        SensorDbHandler sensorDbHandler = null;
        List<Sensor>  sensorList = null;
        try{
            sensorDbHandler = new SensorDbHandler();
            sensorList = sensorDbHandler.getSensors();
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            closeDbConnection(sensorDbHandler);
        }
        return new ResponseEntity(sensorList, HttpStatus.OK);
    }

    private void closeDbConnection(SensorDbHandler sensorDbHandler) {
        if (sensorDbHandler != null) {
            try {
                sensorDbHandler.close();
            } catch (MysqlHandlerException e) {
                logger.error("error occurred while closing db connection\n" + e.getMessage(), e);
            }
        }
    }
}
