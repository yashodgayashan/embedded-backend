package com.example.greenhouse.data;

import com.example.greenhouse.database.MysqlDbHandler;

import com.example.greenhouse.exceptions.MysqlHandlerException;
import com.example.greenhouse.nodes.NodeSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.greenhouse.constants.Constants.ERR_CLOSE_RESULT_SET;
import static com.example.greenhouse.data.Constants.*;
import static com.example.greenhouse.database.Constants.SQL_ERROR_MSG;
import static com.example.greenhouse.utils.MiscellaneousUtils.closeResultSet;
import static com.example.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;

public class DataDbHandler {

    private MysqlDbHandler mysqlDbHandler;
    private static final Logger logger = LoggerFactory.getLogger(DataDbHandler.class);

    public DataDbHandler() throws MysqlHandlerException {
        this.mysqlDbHandler = new MysqlDbHandler();
    }

    public void close() throws MysqlHandlerException {
        this.mysqlDbHandler.close();
    }

    public List<DualData> getDualDataByNode(int nodeId, String table) throws MysqlHandlerException {
        List<DualData> dualData = new ArrayList<>();
        DualData dualData1 = null;
        String query = "SELECT * FROM " + table  + " WHERE " + encapFieldWithBackTick(DATA_NODE_ID) + " = ?";

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            ps.setInt(1, nodeId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                dualData1 = createDualData(resultSet);
                dualData.add(dualData1);
            }
            return dualData;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public List<TriData> getTriDataByNode(int nodeId, String table) throws MysqlHandlerException {
        List<TriData> triData = new ArrayList<>();
        TriData triData1 = null;
        String query = "SELECT * FROM " + table  + " WHERE " + encapFieldWithBackTick(DATA_NODE_ID) + " = ?";

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            ps.setInt(1, nodeId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                triData1 = createTriData(resultSet);
                triData.add(triData1);
            }
            return triData;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public DualData createDualData(ResultSet rs) throws MysqlHandlerException {
        try {
            DualData dualData = new DualData();
            dualData.setDataId(rs.getInt(DATA_ID));
            dualData.setNodeId(rs.getInt(DATA_NODE_ID));
            dualData.setData1(rs.getFloat(DATA_1));
            dualData.setData2(rs.getFloat(DATA_2));
            dualData.setValidated(rs.getInt(DATA_VALIDATED));
            dualData.setIsDisabled(rs.getInt(DATA_DISABLED));
            dualData.setSavedDateTime(rs.getTimestamp(DATA_SAVED_DATE_TIME));
            return dualData;
        } catch (SQLException e) {
            throw new MysqlHandlerException("error occurred when creating node, " + e.getMessage(), e);
        }
    }

    public TriData createTriData(ResultSet rs) throws MysqlHandlerException {
        try {
            TriData triData = new TriData();
            triData.setDataId(rs.getInt(DATA_ID));
            triData.setNodeId(rs.getInt(DATA_NODE_ID));
            triData.setData1(rs.getFloat(DATA_1));
            triData.setData2(rs.getFloat(DATA_2));
            triData.setData3(rs.getFloat(DATA_3));
            triData.setValidated(rs.getInt(DATA_VALIDATED));
            triData.setIsDisabled(rs.getInt(DATA_DISABLED));
            triData.setSavedDateTime(rs.getTimestamp(DATA_SAVED_DATE_TIME));
            return triData;
        } catch (SQLException e) {
            throw new MysqlHandlerException("error occurred when creating node, " + e.getMessage(), e);
        }
    }
}
