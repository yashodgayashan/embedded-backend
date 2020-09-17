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
import static com.example.greenhouse.nodes.Constants.NODE_TABLE;
import static com.example.greenhouse.nodes.Constants.NODE_ID;
import static com.example.greenhouse.nodes.Constants.NODE_PN_ID;
import static com.example.greenhouse.nodes.Constants.NODE_CREATED_UDER_ID;
import static com.example.greenhouse.nodes.Constants.NODE_DISABLED;
import static com.example.greenhouse.nodes.Constants.NODE_LM_USER;
import static com.example.greenhouse.nodes.Constants.NODE_LM_DATE_TIME;
import static com.example.greenhouse.utils.MiscellaneousUtils.closeResultSet;

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
}
