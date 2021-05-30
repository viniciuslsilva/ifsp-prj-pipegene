package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.external.persistence.entities.ExecutionEntity;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProjectEntity;
import br.edu.ifsp.scl.pipegene.external.persistence.entities.ProviderEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class FakeDatabase {
    public final Map<UUID, ProjectEntity> PROJECTS;
    public final Map<UUID, ProviderEntity> PROVIDERS;
    public final Map<UUID, ExecutionEntity> EXECUTION_STATUS_MAP = new HashMap<>();

    public FakeDatabase() {
        PROJECTS = Stream.of(
                ProjectEntity.of(
                        UUID.fromString("53bb719a-e982-4785-bb53-40dc71d6dd9a"),
                        Collections.singletonList("mock_GBM_MEMo.maf"),
                        "Fake Project",
                        "description Fake Project"
                ),
                ProjectEntity.of(
                        UUID.fromString("e1d33cc3-f04d-45c8-8998-20cd0d4af878"),
                        Collections.singletonList("mock_GBM_MEMo.maf"),
                        "Fake Project 2",
                        "descripiton Fake Project 2"
                )

        ).collect(Collectors.toMap(ProjectEntity::getId, Function.identity()));

        PROVIDERS = Stream.of(
                ProviderEntity.of(
                        UUID.fromString("baca1f38-5501-476f-a7f5-fe5958a55772"),
                        "jorge provider",
                        "jorge provider description",
                        "http://localhost:5000",
                        Collections.singletonList("maf"),
                        Collections.singletonList("maf")
                ),
                ProviderEntity.of(
                        UUID.fromString("0fd493f8-fb58-455a-8cb9-d3561d111e70"),
                        "jorge provider",
                        "jorge provider description",
                        "http://localhost:5000",
                        Collections.singletonList("taf"),
                        Collections.singletonList("taf")
                )
        ).collect(Collectors.toMap(ProviderEntity::getId, Function.identity()));
    }
}
