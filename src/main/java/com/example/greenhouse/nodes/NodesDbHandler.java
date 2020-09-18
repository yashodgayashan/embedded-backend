package com.example.greenhouse.nodes;

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
import static com.example.greenhouse.nodes.Constants.*;
import static com.example.greenhouse.utils.MiscellaneousUtils.closeResultSet;
import static com.example.greenhouse.utils.MiscellaneousUtils.encapFieldWithBackTick;

public class NodesDbHandler {

    private MysqlDbHandler mysqlDbHandler;
    private static final Logger logger = LoggerFactory.getLogger(NodesDbHandler.class);

    public NodesDbHandler() throws MysqlHandlerException {
        this.mysqlDbHandler = new MysqlDbHandler();
    }

    public void close() throws MysqlHandlerException {
        this.mysqlDbHandler.close();
    }

    public List<Node> getNodes() throws MysqlHandlerException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        String query = "SELECT * FROM " + NODE_TABLE ;

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                node = createNode(resultSet);
                nodes.add(node);
            }
            return nodes;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public List<NodeSensor> getNodeSensors() throws MysqlHandlerException {
        List<NodeSensor> nodeSensors = new ArrayList<>();
        NodeSensor nodeSensor = null;
        String query = "SELECT * FROM " + NS_TABLE ;

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                nodeSensor = createNodeSensor(resultSet);
                nodeSensors.add(nodeSensor);
            }
            return nodeSensors;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public List<NodeSensor> getNodeSensors(int nodeId) throws MysqlHandlerException {
        List<NodeSensor> nodeSensors = new ArrayList<>();
        NodeSensor nodeSensor = null;
        String query = "SELECT * FROM " + NS_TABLE + " WHERE " + encapFieldWithBackTick(NS_NODE_ID) + " = ?";

        ResultSet resultSet = null;
        try (PreparedStatement ps = mysqlDbHandler.getConnection().prepareStatement(query)) {
            ps.setInt(1, nodeId);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                nodeSensor = createNodeSensor(resultSet);
                nodeSensors.add(nodeSensor);
            }
            return nodeSensors;
        } catch (SQLException e) {
            throw new MysqlHandlerException(String.format(SQL_ERROR_MSG, e.getSQLState(), e.getMessage()), e);
        } catch (MysqlHandlerException e ) {
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            closeResultSet(resultSet, ERR_CLOSE_RESULT_SET, logger);
        }
    }

    public Node createNode(ResultSet rs) throws MysqlHandlerException {
        try {
            Node node = new Node();
            node.setNodeId(rs.getInt(NODE_ID));
            node.setParentNodeId(rs.getInt(NODE_PN_ID));
            node.setCreatedUserId(rs.getInt(NODE_CREATED_UDER_ID));
            node.setDisabled(rs.getInt(NODE_DISABLED));
            node.setLastModifiedUser(rs.getString(NODE_LM_USER));
            node.setModifiedTime(rs.getTimestamp(NODE_LM_DATE_TIME));
            return node;
        } catch (SQLException e) {
            throw new MysqlHandlerException("error occurred when creating node, " + e.getMessage(), e);
        }
    }

    public NodeSensor createNodeSensor(ResultSet rs) throws MysqlHandlerException {
        try {
            NodeSensor nodeSensor = new NodeSensor();
            nodeSensor.setNodeSensorId(rs.getInt(NS_ID));
            nodeSensor.setNodeId(rs.getInt(NS_NODE_ID));
            nodeSensor.setSensorId(rs.getInt(NS_SENSOR_ID));
            nodeSensor.setIsDisabled(rs.getInt(NS_DISABLED));
            nodeSensor.setLastModifiedUser(rs.getString(NS_LM_USER));
            nodeSensor.setModifiedTime(rs.getTimestamp(NS_LM_DATE_TIME));
            return nodeSensor;
        } catch (SQLException e) {
            throw new MysqlHandlerException("error occurred when creating node, " + e.getMessage(), e);
        }
    }
}
