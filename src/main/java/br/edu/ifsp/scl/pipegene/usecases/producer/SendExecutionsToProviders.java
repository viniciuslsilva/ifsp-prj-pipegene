package br.edu.ifsp.scl.pipegene.usecases.producer;

import br.edu.ifsp.scl.pipegene.configuration.properties.model.ExecutionProperties;
import br.edu.ifsp.scl.pipegene.domain.ExecutionStatus;
import br.edu.ifsp.scl.pipegene.usecases.execution.gateway.ExecutionRepository;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.ExecutionQueueElement;
import br.edu.ifsp.scl.pipegene.usecases.execution.queue.QueueService;
import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequestFlowDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@EnableAsync
public class SendExecutionsToProviders {

    private final Logger logger = LoggerFactory.getLogger(SendExecutionsToProviders.class);
    private final QueueService queueService;
    private final ExecutionProperties executionProperties;
    private final ExecutionRepository executionRepository;

    public SendExecutionsToProviders(QueueService queueService, ExecutionProperties executionProperties, ExecutionRepository executionRepository) {
        this.queueService = queueService;
        this.executionProperties = executionProperties;
        this.executionRepository = executionRepository;
    }

    @Async
    @Scheduled(cron = "${cron.expression}")
    public void send() {
        logger.info("Starting to send executions async - " + System.currentTimeMillis() / 1000);

        Integer size = executionProperties.getBatchSize();
        Integer amountSent = 0;

        while (amountSent < size) {
            ExecutionQueueElement queueElement = queueService.pool();
            if (Objects.isNull(queueElement)) {
                break;
            }

            ExecutionStatus executionStatus = executionRepository
                    .findExecutionStatusByExecutionId(queueElement.getId())
                    .orElseThrow();

            executionStatus.addExecutionRequestFlowDetails(queueElement.getExecutionRequestFlowDetails());
            // Updated database with new state of execution status could be a great idea

            applyExecutionRequest(executionStatus.firstStep());

            amountSent++;
        }

        logger.info("Finished send executions async  - " + System.currentTimeMillis() / 1000);
    }


    private void applyExecutionRequest(ExecutionRequestFlowDetails executionRequestFlowDetails) {
        // TODO retrieve project and provider then send request for provider service
    }

}