package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DatasetDAOImpl implements DatasetDAO {

    private final JdbcTemplate jdbcTemplate;

    @Value("${queries.sql.dataset-dao.select.datasets-by-project-ids}")
    private String selectDatasetsByProjectIdsQuery;

    @Value("${queries.sql.dataset-dao.select.datasets-by-project-id}")
    private String selectDatasetsByProjectIdQuery;

    public DatasetDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Dataset> findDatasetsByProjectIds(Collection<UUID> projectIds) {
        Object[] ids = projectIds.toArray();

        return jdbcTemplate.query(selectDatasetsByProjectIdsQuery,
                ps -> ps.setObject(1, ps.getConnection().createArrayOf("uuid", ids)),
                this::mapperDatasetFromRs);
    }

    @Override
    public List<Dataset> findDatasetsByProjectId(UUID projectId) {
        return jdbcTemplate.query(selectDatasetsByProjectIdQuery, this::mapperDatasetFromRs, projectId);
    }

    private Dataset mapperDatasetFromRs(ResultSet rs, int rowNum) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String filename = rs.getString("filename");
        UUID projectId = (UUID) rs.getObject("project_id");

        return new Dataset(id, filename, Project.createWithOnlyId(projectId));
    }
}
