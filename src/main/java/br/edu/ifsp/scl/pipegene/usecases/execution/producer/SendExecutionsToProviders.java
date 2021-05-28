package br.edu.ifsp.scl.pipegene.usecases.execution.producer;

import br.edu.ifsp.scl.pipegene.configuration.properties.model.ExecutionProperties;
import br.edu.ifsp.scl.pipegene.usecases.execution.ExecutionTransaction;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class SendExecutionsToProviders {

    private final Logger logger = LoggerFactory.getLogger(SendExecutionsToProviders.class);
    private final QueueService queueService;
    private final ExecutionProperties executionProperties;
    private final ExecutionTransaction executionTransaction;

    public SendExecutionsToProviders(QueueService queueService, ExecutionProperties executionProperties,
                                     ExecutionTransaction executionTransaction) {
        this.queueService = queueService;
        this.executionProperties = executionProperties;
        this.executionTransaction = executionTransaction;
    }

    public void send() {
        logger.info("Starting to send executions async - " + System.currentTimeMillis() / 1000);

        Integer size = executionProperties.getBatchSize();
        Integer amountSent = 0;

        while (amountSent < size) {
            ExecutionQueueElement queueElement = queueService.pool();
            if (Objects.isNull(queueElement)) {
                break;
            }

            executionTransaction.start(queueElement);

            amountSent++;
        }

        logger.info("Finished send executions async  - " + amountSent);
    }

}