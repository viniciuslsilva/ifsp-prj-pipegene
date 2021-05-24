package br.edu.ifsp.scl.pipegene.usecases.execution.queue;

import br.edu.ifsp.scl.pipegene.web.model.execution.request.ExecutionRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.UUID;

@Service
public class QueueServiceImpl implements QueueService {

    private final LinkedList<ExecutionQueueElement> queue = new LinkedList<>();

    @Override
    public UUID add(ExecutionRequest executionRequest) {
        UUID id = UUID.randomUUID();
        if (queue.add(ExecutionQueueElement.of(id, executionRequest))) {
            return id;
        }
        throw new IllegalStateException(); // TODO(create a custom exception)
    }

    @Override
    public ExecutionQueueElement pool() {
        return queue.poll();
    }
}
