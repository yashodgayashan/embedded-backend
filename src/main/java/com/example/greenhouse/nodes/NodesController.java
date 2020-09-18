package com.example.greenhouse.nodes;

import com.example.greenhouse.data.DataDbHandler;
import com.example.greenhouse.data.DualData;
import com.example.greenhouse.data.TriData;
import com.example.greenhouse.exceptions.MysqlHandlerException;
import com.example.greenhouse.sensors.Sensor;
import com.example.greenhouse.sensors.SensorDbHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.example.greenhouse.constants.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.example.greenhouse.constants.Constants.NOT_FOUND_MSG;

@RestController
public class NodesController {

    private static final Logger logger = LoggerFactory.getLogger(NodesController.class);

    @GetMapping("/nodes")
    public ResponseEntity getNodes() {

        NodesDbHandler nodesDbHandler = null;
        List<Node> nodeList = null;
        try{
            nodesDbHandler = new NodesDbHandler();
            nodeList = nodesDbHandler.getNodes();
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            closeDbConnection(nodesDbHandler);
        }
        return new ResponseEntity(nodeList, HttpStatus.OK);
    }

    @GetMapping("/nodes/{node-id}/sensors")
    public ResponseEntity getSensorsByNode(@PathVariable("node-id") int nodeId) {

        SensorDbHandler sensorDbHandler = null;
        List<Sensor> sensorList = null;
        try{
            sensorDbHandler = new SensorDbHandler();
            sensorList = sensorDbHandler.getSensorsByNode(nodeId);
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (sensorDbHandler != null) {
                try {
                    sensorDbHandler.close();
                } catch (MysqlHandlerException e) {
                    logger.error("error occurred while closing db connection\n" + e.getMessage(), e);
                }
            }
        }
        return new ResponseEntity(sensorList, HttpStatus.OK);
    }

    @GetMapping("/nodes/{node-id}/data")
    public ResponseEntity getDataByNode(@PathVariable("node-id") int nodeId) {
        List<DualData> dualDataList = new ArrayList<>();
        List<TriData> triDataList = new ArrayList<>();
        try{
            if(nodeId == 1 || nodeId == 2){
                triDataList = getTriData(nodeId);
                return new ResponseEntity(triDataList, HttpStatus.OK);
            } else if (nodeId == 3) {
                dualDataList = getDualData(nodeId);
                return new ResponseEntity(dualDataList, HttpStatus.OK);
            } else {
                return new ResponseEntity(NOT_FOUND_MSG, HttpStatus.NOT_FOUND);
            }
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private List<DualData> getDualData(int nodeId) throws MysqlHandlerException {
        String tableName = "data_" + nodeId;
        DataDbHandler dataDbHandler = null;
        List<DualData> dualDataList = new ArrayList<>();
        try{
            dataDbHandler = new DataDbHandler();
            dualDataList = dataDbHandler.getDualDataByNode(nodeId, tableName);
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            if (dataDbHandler != null) {
                try {
                    dataDbHandler.close();
                } catch (MysqlHandlerException e) {
                    logger.error("error occurred while closing db connection\n" + e.getMessage(), e);
                }
            }
        }
        return dualDataList;
    }

    private List<TriData> getTriData(int nodeId) throws MysqlHandlerException {
        String tableName = "data_" + nodeId;
        DataDbHandler dataDbHandler = null;
        List<TriData> triDataList = new ArrayList<>();
        try{
            dataDbHandler = new DataDbHandler();
            triDataList = dataDbHandler.getTriDataByNode(nodeId, tableName);
        } catch (MysqlHandlerException e) {
            logger.error(e.getMessage(), e);
            throw new MysqlHandlerException(e.getMessage(), e);
        } finally {
            if (dataDbHandler != null) {
                try {
                    dataDbHandler.close();
                } catch (MysqlHandlerException e) {
                    logger.error("error occurred while closing db connection\n" + e.getMessage(), e);
                }
            }
        }
        return triDataList;
    }

    private void closeDbConnection(NodesDbHandler nodesDbHandler) {
        if (nodesDbHandler != null) {
            try {
                nodesDbHandler.close();
            } catch (MysqlHandlerException e) {
                logger.error("error occurred while closing db connection\n" + e.getMessage(), e);
            }
        }
    }
}
