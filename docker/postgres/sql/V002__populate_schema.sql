\connect "pipegine"

insert into pipegine_platform.application_user(id, name, username, password, is_account_non_expired,
    is_account_nonLocked, is_credentials_non_expired, is_enabled)
    values ('78cec5db-6396-4fd9-803f-1fd469d76330'::uuid, 'vini', 'vini',
        '$2a$10$M2lu6nBJ4yCdJ/qtbT7aZeofmxsFqYlMQtz1M2QQgRm1sCwTi2i/m', true, true, true, true);

insert into pipegine_platform.provider(id, name, description, url, input_supported_types, output_supported_types,
                                       owner_id, operations)
    values('78cec5db-6396-4fd9-803f-1fd469d76312'::uuid, 'Rodrigo - Serviço 1', 'Rodrigo - Serviço 1 (testes)', 'http://localhost:5000',
           'maf','maf', '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid,
           to_json('[{
            "type": "column",
            "description": "Normalização por meio de colunas",
            "params": [
                {
                    "type": "text",
                    "name": "colunas desejadas",
                    "key": "columns",
                    "example": "Hugo_Symbol, Chromosome"
                }
            ]
        }]'::jsonb));

insert into pipegine_platform.project(id, name, description, owner_id)
    values ('f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid, 'Normalizando arquivos MAF', 'Projeto para normalizar arquivos MAF utilizando serviços disponíveis na plataforma',
            '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid);

insert into pipegine_platform.dataset(id, filename, project_id)
    values('042e4e39-ba0b-49d1-a01a-237333d0b1a5'::uuid,
           '042e4e39-ba0b-49d1-a01a-237333d0b1a5_uploads_mock_GBM_MEMo.maf', 'f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid);

INSERT INTO pipegine_platform.pipeline(id, project_id, description)
    VALUES('959f2ff3-ed29-435b-83bb-c8390f6385bf'::uuid, 'f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid, 'Normalização em duas etapas');


INSERT INTO pipegine_platform.pipeline_step(step_id, pipeline_id, provider_id, input_type, output_type, params, step_number)
    VALUES('715d7d76-b5d1-4d45-b1f6-66b46d3c2964'::uuid, '959f2ff3-ed29-435b-83bb-c8390f6385bf'::uuid,
        '78cec5db-6396-4fd9-803f-1fd469d76312'::uuid, 'maf', 'maf',
        (to_json('{"columns": "Hugo_Symbol, Chromosome, Start_Position, End_Position, Reference_Allele, Tumor_Seq_Allele2, Variant_Classification, Variant_Type, Tumor_Sample_Barcode"}'::jsonb)),
           1);

INSERT INTO pipegine_platform.pipeline_step(step_id, pipeline_id, provider_id, input_type, output_type, params, step_number)
    VALUES('35daceba-c1f0-4cd2-85b5-db733e7e6d55'::uuid, '959f2ff3-ed29-435b-83bb-c8390f6385bf'::uuid,
        '78cec5db-6396-4fd9-803f-1fd469d76312'::uuid, 'maf', 'maf',
        (to_json('{"columns": "Hugo_Symbol, Chromosome"}'::jsonb)), 2);

