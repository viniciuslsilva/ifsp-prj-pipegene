\connect "pipegine"




-- Cria um usuário com username 'vini' e senha 'password'

insert into pipegine_platform.application_user(id, name, username, password, is_account_non_expired,
    is_account_nonLocked, is_credentials_non_expired, is_enabled)
    values ('78cec5db-6396-4fd9-803f-1fd469d76330'::uuid, 'vini', 'vini',
        '$2a$10$M2lu6nBJ4yCdJ/qtbT7aZeofmxsFqYlMQtz1M2QQgRm1sCwTi2i/m', true, true, true, true);


-- cria provider para grafico pre processamento
insert into pipegine_platform.provider(id, name, description, url, input_supported_types, output_supported_types,
                                       owner_id, operations)
    values('78cec5db-6396-4fd9-803f-1fd469d76312'::uuid, 'Grafico pré processamento',
           'Exporta grafico do resultante do preprocessamento', 'http://localhost:5011',
           'maf','png', '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid,
           to_json('[{
            "type": "column",
            "description": "Grafico resultante do pré processamento",
            "params": [
                {
                    "type": "text",
                    "name": "colunas desejadas",
                    "key": "columns",
                    "example": "Hugo_Symbol, Chromosome"
                }
            ]
        }]'::jsonb));


-- cria projeto que vai utilizar do provider de Grafico pré processamento
insert into pipegine_platform.project(id, name, description, owner_id)
    values ('f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid, 'Explorando pré processamentos MAF',
            'Analise de gráficos resultante de pre processamentos de arquivos MAF',
            '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid);

-- cria dataset do projeto que servira de input para o Grafico pré processamento
insert into pipegine_platform.dataset(id, filename, project_id)
    values('042e4e39-ba0b-49d1-a01a-237333d0b1a5'::uuid,
           '042e4e39-ba0b-49d1-a01a-237333d0b1a5_uploads_mock_GBM_MEMo.maf', 'f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid);

-- cria pipeline para o Grafico pré processamento
INSERT INTO pipegine_platform.pipeline(id, project_id, description)
    VALUES('959f2ff3-ed29-435b-83bb-c8390f6385bf'::uuid, 'f2d6a949-8bb5-4df5-8ca7-e5b8d2292488'::uuid, 'Exporta grafico resultante do pre processamento');

-- cria step unico para o Grafico pré processamento
INSERT INTO pipegine_platform.pipeline_step(step_id, pipeline_id, provider_id, input_type, output_type, params, step_number)
    VALUES('715d7d76-b5d1-4d45-b1f6-66b46d3c2964'::uuid, '959f2ff3-ed29-435b-83bb-c8390f6385bf'::uuid,
        '78cec5db-6396-4fd9-803f-1fd469d76312'::uuid, 'maf', 'png',
        (to_json('{"columns": "Hugo_Symbol, Chromosome, Start_Position, End_Position, Reference_Allele, Tumor_Seq_Allele2, Variant_Classification, Variant_Type, Tumor_Sample_Barcode"}'::jsonb)),
           1);



-- cria novo provider que realiza o pre processamento exportando um maf que sera utilizado de input
insert into pipegine_platform.provider(id, name, description, url, input_supported_types, output_supported_types,
                                       owner_id, operations)
values('e8bf42e4-2ffc-4935-a546-ee5d9263f419'::uuid, 'Pré processamento - Rodrigo',
       'Realiza o pré processamento de arquivos maf e txt para maf', 'http://localhost:5001',
       'txt,maf','maf', '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid,
       to_json('[{
            "type": "column",
            "description": "Pré processamento de arquivo maf e txt",
            "params": [
                {
                    "type": "text",
                    "name": "colunas desejadas",
                    "key": "columns",
                    "example": "Hugo_Symbol, Chromosome"
                }
            ]
        }]'::jsonb));

-- cria novo provider que recebera como input o maf resultando do pre processamento e fara a classificação da variante
insert into pipegine_platform.provider(id, name, description, url, input_supported_types, output_supported_types,
                                       owner_id, operations)
values('49df4595-b8af-4e32-8791-65e583ae08a2'::uuid, 'Classificação de variante - Rodrigo',
       'Partindo de um maf pré processado realiza a classificação de variante', 'http://localhost:5002',
       'maf','png', '78cec5db-6396-4fd9-803f-1fd469d76330'::uuid,
       to_json('[{
            "type": "column",
            "description": "Classificação de variante",
            "params": [
                {
                    "type": "text",
                    "name": "colunas desejadas",
                    "key": "columns",
                    "example": "Hugo_Symbol, Chromosome"
                }
            ]
        }]'::jsonb));


-- cria projeto que vai utilizar do provider de Pré processamento - Rodrigo e Classificação de variante - Rodrigo
INSERT INTO pipegine_platform.project (id, name, description, owner_id)
VALUES ('28559f11-0c41-4444-a087-c14efde8b482', 'Pré processamento - Rodrigo e Classificação de variante - Rodrigo',
        'Recebe um input e devolve um maf para ser utilizado como input em outro serviço',
        '78cec5db-6396-4fd9-803f-1fd469d76330');

-- cria dataset do projeto que servira de input para o Pré processamento - Rodrigo
INSERT INTO pipegine_platform.dataset (id, filename, project_id)
VALUES ('e1a8ba35-d029-4a7c-bb98-ae7e84c41a41',
        'e1a8ba35-d029-4a7c-bb98-ae7e84c41a41_uploads_BRCA2_data_mutations_extended.maf',
        '28559f11-0c41-4444-a087-c14efde8b482');

-- cria pipeline para o Pré processamento - Rodrigo e Classificação de variante - Rodrigo
INSERT INTO pipegine_platform.pipeline (id, project_id, description)
VALUES ('3677aa2e-6be8-43f9-ad8c-a0f1f71e2040', '28559f11-0c41-4444-a087-c14efde8b482', 'Realiza pre processamento e faz a classificação de variante - Rodrigo');


-- cria steps para o Pré processamento - Rodrigo e Classificação de variante - Rodrigo
INSERT INTO pipegine_platform.pipeline_step (step_id, pipeline_id, provider_id, input_type, output_type, params, step_number)
VALUES ('7b97b9b1-346b-47ed-aeef-bee8bbdcf064', '3677aa2e-6be8-43f9-ad8c-a0f1f71e2040', 'e8bf42e4-2ffc-4935-a546-ee5d9263f419',
        'maf', 'maf', '{"columns": "Hugo_Symbol,Chromosome,Start_Position,End_Position,Reference_Allele,Tumor_Seq_Allele2,Variant_Classification,Variant_Type,Tumor_Sample_Barcode"}', 1);

INSERT INTO pipegine_platform.pipeline_step (step_id, pipeline_id, provider_id, input_type, output_type, params, step_number)
 VALUES ('2fe129d3-f3d2-46c5-b34a-e472ed217b07', '3677aa2e-6be8-43f9-ad8c-a0f1f71e2040', '49df4595-b8af-4e32-8791-65e583ae08a2',
         'maf', 'png', '{"columns": "Hugo_Symbol,Chromosome,Start_Position,End_Position,Reference_Allele,Tumor_Seq_Allele2,Variant_Classification,Variant_Type,Tumor_Sample_Barcode"}', 2);