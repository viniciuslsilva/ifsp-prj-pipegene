package br.edu.ifsp.scl.pipegene.usecases.pipeline;

import br.edu.ifsp.scl.pipegene.configuration.security.IAuthenticationFacade;
import br.edu.ifsp.scl.pipegene.domain.PipelineStep;
import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.pipeline.gateway.PipelineDAO;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectDAO;
import br.edu.ifsp.scl.pipegene.web.exception.GenericResourceException;
import br.edu.ifsp.scl.pipegene.web.exception.ResourceNotFoundException;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.request.CreatePipelineRequest;
import br.edu.ifsp.scl.pipegene.web.model.pipeline.request.PipelineStepRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class PipelineCRUDImpl implements PipelineCRUD {

    private final ProjectDAO projectDAO;
    private final PipelineDAO pipelineDAO;
    private final IAuthenticationFacade authenticationFacade;

    public PipelineCRUDImpl(ProjectDAO projectDAO, PipelineDAO pipelineDAO, IAuthenticationFacade authenticationFacade) {
        this.projectDAO = projectDAO;
        this.pipelineDAO = pipelineDAO;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public Pipeline addNewPipeline(UUID projectId, CreatePipelineRequest request) {
        Optional<Project> optionalProject = projectDAO.findProjectById(projectId);

        if (optionalProject.isEmpty()) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Project project = optionalProject.get();
        Boolean pipelineRequestIsValid = pipelineDAO.providerListIsValid(
                request.getSteps().stream()
                        .map(PipelineStepRequest::convertToProvider)
                        .collect(Collectors.toList())
        );

        if (!pipelineRequestIsValid) {
            throw new GenericResourceException("Please, verify provider id, inputType and outputType", "Invalid Pipeline Request");
        }

        if (request.executionStepsIsEmpty()) {
            throw new GenericResourceException("Please, add one step", "Invalid Pipeline Request");
        }

        List<PipelineStep> steps = mapToPipelineStep(request.getSteps());
        Pipeline pipeline = Pipeline.createWithoutId(project, request.getDescription(), steps);

        return pipelineDAO.savePipeline(pipeline);
    }

    private List<PipelineStep> mapToPipelineStep(List<PipelineStepRequest> pipelineStepRequest) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        return pipelineStepRequest
                .stream()
                .map(e -> PipelineStep.createGeneratingStepId(e.getProviderId(), e.getInputType(), e.getOutputType(),
                        e.getParams(), atomicInteger.getAndIncrement()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pipeline> findAllPipeline(UUID projectId) {
        return pipelineDAO.findAll(projectId);
    }

    @Override
    public List<Pipeline> listAllPipelinesByUserId(UUID userId) {
        if (!userId.equals(authenticationFacade.getUserAuthenticatedId())) {
            throw new IllegalArgumentException();
        }

        return pipelineDAO.listAllByOwnerId(userId);
    }

    @Override
    public Pipeline findByProjectIdAndPipelineId(UUID projectId, UUID pipelineId) {
        Boolean projectExists = projectDAO.projectExists(projectId);
        if (!projectExists) {
            throw new ResourceNotFoundException("Not found project with id: " + projectId);
        }

        Optional<Pipeline> opt = pipelineDAO.findPipelineById(pipelineId);

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException("Not found pipeline with id: " + pipelineId);
        }

        return opt.get();
     }
}
