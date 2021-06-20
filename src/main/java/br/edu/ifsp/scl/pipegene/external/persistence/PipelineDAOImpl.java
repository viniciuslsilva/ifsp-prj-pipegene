package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.domain.PipelineStep;
import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.external.persistence.util.JsonUtil;
import br.edu.ifsp.scl.pipegene.usecases.pipeline.gateway.PipelineDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PipelineDAOImpl implements PipelineDAO {

    private final JdbcTemplate jdbcTemplate;
    private final JsonUtil jsonUtil;

    @Value("${queries.sql.pipeline-dao.insert.pipeline}")
    private String insertPipelineQuery;

    @Value("${queries.sql.pipeline-dao.insert.pipeline-step}")
    private String insertPipelineStepQuery;

    @Value("${queries.sql.pipeline-dao.select.pipeline-all}")
    private String selectAllPipelineQuery;

    @Value("${queries.sql.pipeline-dao.select.pipelines-by-project-id}")
    private String selectPipelinesByProjectIdQuery;

    @Value("${queries.sql.pipeline-dao.select.pipeline-by-id}")
    private String selectPipelineByIdQuery;

    @Value("${queries.sql.pipeline-dao.select.pipeline-steps-by-pipeline-ids}")
    private String selectPipelineStepsByPipelineIdsQuery;

    @Value("${queries.sql.pipeline-dao.select.pipeline-steps-by-pipeline-id}")
    private String selectPipelineStepsByPipelineIdQuery;


    public PipelineDAOImpl(JdbcTemplate jdbcTemplate, JsonUtil jsonUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.jsonUtil = jsonUtil;
    }

    @Override
    public Boolean providerListIsValid(List<Provider> providers) {
        return true;
    }

    @Transactional
    @Override
    public Pipeline savePipeline(Pipeline pipeline) {
        UUID pipelineId = UUID.randomUUID();
        jdbcTemplate.update(insertPipelineQuery, pipelineId, pipeline.getProjectId(), pipeline.getDescription());

        List<PipelineStep> steps = pipeline.getSteps();

        jdbcTemplate.batchUpdate(insertPipelineStepQuery, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, steps.get(i).getStepId());
                ps.setObject(2, pipelineId);
                ps.setObject(3, steps.get(i).getProvider().getId());
                ps.setString(4, steps.get(i).getInputType());
                ps.setString(5, steps.get(i).getOutputType());
                ps.setString(6, jsonUtil.writeMapStringObjectAsJsonString(steps.get(i).getParams()));
                ps.setInt(7, steps.get(i).getStepNumber());
            }

            @Override
            public int getBatchSize() {
                return steps.size();
            }
        });

        return pipeline.getNewInstanceWithId(pipelineId);
    }

    @Override
    public List<Pipeline> findAll(UUID projectId) {
        Map<UUID, Pipeline> pipelineMap = new HashMap<>();

        jdbcTemplate.query(selectAllPipelineQuery, (rs, rowNum) -> {
            try {
                UUID pipelineId = (UUID) rs.getObject("pipeline_id");
                PipelineStep step = PipelineStep.of(
                        (UUID) rs.getObject("pipeline_step_id"),
                        Provider.createWithOnlyId((UUID) rs.getObject("pipeline_step_provider_id")),
                        rs.getString("pipeline_step_input_type"),
                        rs.getString("pipeline_step_output_type"),
                        jsonUtil.retrieveStepParams(rs.getString("pipeline_step_params")),
                        rs.getInt("pipeline_step_number"),
                        Pipeline.createWithOnlyId(pipelineId)
                );

                if (pipelineMap.containsKey(pipelineId)) {
                    pipelineMap.get(pipelineId).addStep(step);
                } else {
                    String description = rs.getString("pipeline_description");

                    Pipeline pipeline = new Pipeline(pipelineId, null, description, step);
                    pipelineMap.put(pipelineId, pipeline);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SQLException();
            }

            return null;
        }, projectId);

        Collection<Pipeline> pipelines = pipelineMap.values();
        pipelines.forEach(Pipeline::sortedSteps);

        return new ArrayList<>(pipelines);
    }

    @Override
    public Collection<Pipeline> findPipelinesByProjectId(UUID projectId) {
        Map<UUID, Pipeline> pipelineMap = jdbcTemplate.query(selectPipelinesByProjectIdQuery,
                (rs, rowNum) -> Pipeline.createWithoutProjectAndSteps((UUID) rs.getObject("id"),
                        rs.getString("description"))
                , projectId).stream().collect(Collectors.toMap(Pipeline::getId, Function.identity()));

        Object[] ids = pipelineMap.keySet().toArray();


        List<PipelineStep> steps = jdbcTemplate.query(selectPipelineStepsByPipelineIdsQuery,
                ps -> ps.setObject(1, ps.getConnection().createArrayOf("uuid", ids)),
                this::mapperPipelineStepFromRs);
        steps.forEach(step -> pipelineMap.get(step.getPipelineId()).addStep(step));

        return pipelineMap.values();
    }

    @Transactional
    @Override
    public Optional<Pipeline> findPipelineById(UUID pipelineId) {
        try {
            Pipeline pipeline = jdbcTemplate.queryForObject(selectPipelineByIdQuery, (rs, rowNum) -> {
                UUID id = (UUID) rs.getObject("id");
                String description = rs.getString("description");

                return Pipeline.createWithoutProjectAndSteps(id, description);
            }, pipelineId);

            if (Objects.isNull(pipeline)) {
                throw new IllegalStateException();
            }

            List<PipelineStep> steps = jdbcTemplate.query(selectPipelineStepsByPipelineIdQuery,
                    this::mapperPipelineStepFromRs, pipelineId);
            steps.forEach(pipeline::addStep);

            return Optional.of(pipeline);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    private PipelineStep mapperPipelineStepFromRs(ResultSet rs, int rowNum) throws SQLException {
        UUID stepId = (UUID) rs.getObject("step_id");
        UUID providerId = (UUID) rs.getObject("provider_id");
        String inputType = rs.getString("input_type");
        String outputType = rs.getString("output_type");

        String params = rs.getString("params");
        Integer stepNumber = rs.getInt("step_number");

        String providerName = rs.getString("provider_name");
        String providerDescription = rs.getString("provider_description");
        Provider provider = Provider.createWithPartialValues(providerId, providerName, providerDescription);

        return PipelineStep.of(stepId, provider, inputType, outputType, jsonUtil.retrieveStepParams(params), stepNumber,
                Pipeline.createWithOnlyId((UUID) rs.getObject("pipeline_id")));
    }
}
