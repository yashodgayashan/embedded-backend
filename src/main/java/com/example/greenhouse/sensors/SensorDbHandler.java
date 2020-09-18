package com.example.greenhouse.sensors;

import com.example.greenhouse.database.MysqlDbHandler;

import com.example.greenhouse.exceptions.MysqlHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.greenhouse.constants.Constants.ERR_CLOSE_RESULT_SET;
import static com.example.greenhouse.database.Constants.SQL_ERROR_MSG;
import static com.example.greenhouse.sensors.Constants.SENSOR_TABLE;
import static com.example.greenhouse.sensors.Constants.SENSOR_ID;
import static com.example.greenhouse.sensors.Constants.SENSOR_NAME;
import static com.example.greenhouse.sensors.Constants.SENSOR_DESCRIPTION;
import static com.example.greenhouse.sensors.Constants.SENSOR_DATA_TYPE;
import static com.example.greenhouse.sensors.Constants.SENSOR_DATA_SIZE;
import static com.example.greenhouse.sensors.Constants.SENSOR_RANGE;
import static com.example.greenhouse.sensors.Constants.SENSOR_TECHNOLOGY;
import static com.example.greenhouse.sensors.Constants.SENSOR_WORKING_VOLTAGE;
import static com.example.greenhouse.sensors.Constants.SENSOR_DIMENSIONS;
import static com.example.greenhouse.sensors.Constants.SENSOR_SPECIAL_FACT;
import static com.example.greenhouse.sensors.Constants.SENSOR_IMAGE_URL;
import static com.example.greenhouse.sensors.Constants.SENSOR_DISABLED;
import static com.example.greenhouse.sensors.Constants.SENSOR_LM_USER;
import static com.example.greenhouse.sensors.Constants.SENSOR_LM_DATE_TIME;
import static com.example.greenhouse.utils.MiscellaneousUtils.closeResultSet;

public class SensorDbHandler {

    private MysqlDbHandler mysqlDbHandler;
    private static final Logger logger = LoggerFactory.getLogger(SensorDbHandler.class);

    public SensorDbHandler() throws MysqlHandlerException {
        this.mysqlDbHandler = new MysqlDbHandler();
    }

    public void close() throws MysqlHandlerException {
        this.mysqlDbHandler.close();
    }

    public List<Sensor> getSensors() throws MysqlHandlerException {
        List<Sensor> sensors = new ArrayList<>();
        Sensor sensor = null;
        String query = "SELECT * FROM " + SENSOR_TABLE ;

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                sensor = createSensor(resultSet);
                sensors.add(sensor);
            }
            return sensors;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public Sensor createSensor(ResultSet rs) throws MysqlHandlerException {
        try {
            Sensor sensor = new Sensor();
            sensor.setSensorId(rs.getInt(SENSOR_ID));
            sensor.setSensorName(rs.getString(SENSOR_NAME));
            sensor.setSensorDescription(rs.getString(SENSOR_DESCRIPTION));
            sensor.setDataType(rs.getString(SENSOR_DATA_TYPE));
            sensor.setDataSize(rs.getString(SENSOR_DATA_SIZE));
            sensor.setSensingRange(rs.getString(SENSOR_RANGE));
            sensor.setTechnology(rs.getString(SENSOR_TECHNOLOGY));
            sensor.setWorkingVoltage(rs.getString(SENSOR_WORKING_VOLTAGE));
            sensor.setDimensions(rs.getString(SENSOR_DIMENSIONS));
            sensor.setSpecialFacts(rs.getString(SENSOR_SPECIAL_FACT));
            sensor.setImageURL(rs.getString(SENSOR_IMAGE_URL));
            sensor.setIsDisabled(rs.getInt(SENSOR_DISABLED));
            sensor.setLastModifiedUser(rs.getString(SENSOR_LM_USER));
            sensor.setModifiedTime(rs.getTimestamp(SENSOR_LM_DATE_TIME));
            return sensor;
        } catch (SQLException e) {
            throw new MysqlHandlerException("error occurred when creating node, " + e.getMessage(), e);
        }
    }
}
