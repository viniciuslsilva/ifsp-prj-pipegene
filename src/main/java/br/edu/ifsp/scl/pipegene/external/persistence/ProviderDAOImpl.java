package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Provider;
import br.edu.ifsp.scl.pipegene.domain.ProviderOperation;
import br.edu.ifsp.scl.pipegene.usecases.provider.gateway.ProviderDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.*;

import static br.edu.ifsp.scl.pipegene.external.persistence.ProjectDAOImpl.OWNER_ID;

@Repository
public class ProviderDAOImpl implements ProviderDAO {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    @Value("${queries.sql.provider-dao.select.provider-by-id}")
    private String selectProviderByIdQuery;

    @Value("${queries.sql.provider-dao.select.provider-all}")
    private String selectAllProvidersQuery;

    @Value("${queries.sql.provider-dao.insert.provider}")
    private String insertProviderQuery;

    public ProviderDAOImpl(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<Provider> findProviderById(UUID id) {
        Provider provider = jdbcTemplate.queryForObject(selectProviderByIdQuery, this::mapperToProvider, id);

        if (Objects.nonNull(provider)) {
            return Optional.of(provider);
        }

        return Optional.empty();
    }

    @Override
    public List<Provider> findAllProviders() {
        return jdbcTemplate.query(selectAllProvidersQuery, this::mapperToProvider);
    }

    private Provider mapperToProvider(ResultSet rs, int rowNum) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        String url = rs.getString("url");

        String inputSupported = rs.getString("input_supported_types");
        List<String> inputSupportedTypes = Objects.isNull(inputSupported) ? Collections.emptyList()
                : Arrays.asList(inputSupported.split(","));

        String outputSupported = rs.getString("output_supported_types");
        List<String> outputSupportedTypes = Objects.isNull(outputSupported) ? Collections.emptyList()
                : Arrays.asList(outputSupported.split(","));

        try {
            String operationStr = rs.getString("operations");
            List<ProviderOperation> operations = Objects.isNull(operationStr) ? Collections.emptyList()
                    : objectMapper.readValue(operationStr, new TypeReference<>() {});

            return Provider.createWithAllValues(id, name, description, url, inputSupportedTypes, outputSupportedTypes,
                    operations);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new SQLDataException();
        }
    }

    @Override
    public Provider saveNewProvider(Provider provider) {
        UUID providerId = UUID.randomUUID();
        String operations;
        try {
            operations = objectMapper.writeValueAsString(provider.getOperations());
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException();
        }

        jdbcTemplate.update(insertProviderQuery, providerId, provider.getName(), provider.getDescription(),
                provider.getUrl(), String.join(",", provider.getInputSupportedTypes()),
                String.join(",", provider.getOutputSupportedTypes()), operations, OWNER_ID);

        return provider.getNewInstanceWithId(providerId);
    }
}
