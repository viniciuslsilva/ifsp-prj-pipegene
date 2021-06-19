package br.edu.ifsp.scl.pipegene.external.persistence;

import br.edu.ifsp.scl.pipegene.domain.Dataset;
import br.edu.ifsp.scl.pipegene.domain.ProviderOperation;
import br.edu.ifsp.scl.pipegene.domain.ProviderOperationParam;
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

    private final String DATASET_MOCK_FILE_NAME = "042e4e39-ba0b-49d1-a01a-237333d0b1a5_uploads_mock_GBM_MEMo.maf";

    private final UUID PROJECT_1_ID = UUID.fromString("53bb719a-e982-4785-bb53-40dc71d6dd9a");
    private final Dataset PROJECT_1_DATASET = new Dataset(UUID.fromString("042e4e39-ba0b-49d1-a01a-237333d0b1a5"), DATASET_MOCK_FILE_NAME);

    private final UUID PROJECT_2_ID = UUID.fromString("e1d33cc3-f04d-45c8-8998-20cd0d4af878");
    private final Dataset PROJECT_2_DATASET = new Dataset(UUID.fromString("b319fbe9-e9ec-44bf-80fc-6897603cad14"), DATASET_MOCK_FILE_NAME);

    public FakeDatabase() {
        PROJECTS = Stream.of(
                ProjectEntity.createEntityInstance(
                        PROJECT_1_ID,
                        Collections.singletonList(PROJECT_1_DATASET),
                        "Fake Project",
                        "description Fake Project"
                ),
                ProjectEntity.createEntityInstance(
                        PROJECT_2_ID,
                        Collections.singletonList(PROJECT_2_DATASET),
                        "Fake Project 2",
                        "descripiton Fake Project 2"
                )

        ).collect(Collectors.toMap(ProjectEntity::getId, Function.identity()));

        PROVIDERS = Stream.of(
                ProviderEntity.of(
                        UUID.fromString("baca1f38-5501-476f-a7f5-fe5958a55772"),
                        "jorge provider maf",
                        "jorge provider description",
                        "http://localhost:5000",
                        Collections.singletonList("maf"),
                        Collections.singletonList("maf"),
                        Collections.singletonList(
                                new ProviderOperation(
                                        "column", "operation description",
                                        Collections.singletonList(new ProviderOperationParam(
                                                "text", null, "columns", "Hugo_Symbol, Chromosome"))
                                )
                        )
                ),
                ProviderEntity.of(
                        UUID.fromString("0fd493f8-fb58-455a-8cb9-d3561d111e70"),
                        "jorge provider taf",
                        "jorge provider description",
                        "http://localhost:5000",
                        Collections.singletonList("taf"),
                        Collections.singletonList("taf"),
                        Collections.singletonList(
                                new ProviderOperation(
                                        "column", "operation description",
                                        Collections.singletonList(new ProviderOperationParam(
                                                "text", null, "columns", "Hugo_Symbol, Chromosome"))
                                )
                        )
                )
        ).collect(Collectors.toMap(ProviderEntity::getId, Function.identity()));
    }
}
