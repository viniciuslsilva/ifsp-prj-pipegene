package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Pipeline;
import br.edu.ifsp.scl.pipegene.domain.Project;
import br.edu.ifsp.scl.pipegene.usecases.pipeline.gateway.PipelineDAO;
import br.edu.ifsp.scl.pipegene.usecases.project.gateway.ProjectDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ProjectDAOImpl implements ProjectDAO {

    public final static UUID OWNER_ID = UUID.fromString("78cec5db-6396-4fd9-803f-1fd469d76330");

    private final JdbcTemplate jdbcTemplate;
    private final PipelineDAO pipelineDAO;

    @Value("${queries.sql.project-dao.insert.project}")
    private String insertProjectQuery;

    @Value("${queries.sql.project-dao.insert.dataset}")
    private String insertDatasetQuery;

    @Value("${queries.sql.project-dao.select.project-by-id}")
    private String selectProjectByIdQuery;

    @Value("${queries.sql.project-dao.select.project-datasets}")
    private String selectProjectDatasetsQuery;

    @Value("${queries.sql.project-dao.select.project-all}")
    private String selectAllProjectQuery;

    @Value("${queries.sql.project-dao.exists.project-id}")
    private String existsProjectIdQuery;

    @Value("${queries.sql.project-dao.update.project}")
    private String updateProjectQuery;

    public ProjectDAOImpl(JdbcTemplate jdbcTemplate, PipelineDAO pipelineDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.pipelineDAO = pipelineDAO;
    }

    @Transactional
    @Override
    public Project saveNewProject(String name, String description, List<Dataset> datasets) {
        UUID projectId = UUID.randomUUID();
        jdbcTemplate.update(insertProjectQuery, projectId, name, description, OWNER_ID);

        jdbcTemplate.batchUpdate(insertDatasetQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setObject(1, datasets.get(i).getId());
                ps.setString(2, datasets.get(i).getFilename());
                ps.setObject(3, projectId);
            }

            @Override
            public int getBatchSize() {
                return datasets.size();
            }
        });

        return Project.of(projectId, datasets, name, description, null, OWNER_ID);
    }

    @Override
    public Project updateProject(Project project) {
        jdbcTemplate.update(updateProjectQuery, project.getName(), project.getDescription(), project.getId());
        return project;
    }

    @Override
    public Optional<Project> findProjectById(UUID id) {
        try {
            Project project = jdbcTemplate.queryForObject(selectProjectByIdQuery, (rs, rowNum) -> {
                String name = rs.getString("name");
                String description = rs.getString("description");
                UUID ownerId = (UUID) rs.getObject("owner_id");

                return Project.of(id, null, name, description, null, ownerId);
            }, id);

            if (Objects.isNull(project)) {
                throw new IllegalStateException();
            }

            jdbcTemplate.query(selectProjectDatasetsQuery, (rs, rowNum) -> new Dataset(
                    (UUID) rs.getObject("id"), rs.getString("filename")
            ), id).forEach(project::addDataset);


            List<Pipeline> pipelines = pipelineDAO.findPipelinesByProjectId(project.getId())
                    .stream().peek(pipeline -> pipeline.setProject(project))
                    .collect(Collectors.toList());
            project.addPipeline(pipelines);

            return Optional.of(project);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Boolean projectExists(UUID projectId) {
        Boolean exists = jdbcTemplate.queryForObject(existsProjectIdQuery, Boolean.class, projectId);
        return Objects.nonNull(exists) && exists;
    }

    @Override
    public List<Project> findAllProjects() {
        return jdbcTemplate.query(selectAllProjectQuery, (rs, row) -> {
            UUID id = (UUID) rs.getObject("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            UUID ownerId = (UUID) rs.getObject("owner_id");

            return Project.of(id, null, name, description, null, ownerId);
        });
    }
}
