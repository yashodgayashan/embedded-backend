package com.example.greenhouse.nodes;
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
