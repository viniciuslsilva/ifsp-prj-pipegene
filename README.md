ifsp-prj-pipegene


O banco já está está sendo populado com alguns dados para facilitar os testes.

É criado um usuário com username "vini" e "senha password".

Ao todo é criado 3 providers/serviços que representam as aplicações em python:

1 - O primeiro é o "Grafico pré processamento" com PROVIDER_ID="78cec5db-6396-4fd9-803f-1fd469d76312", ele representa
a app python "app_grafico_pre_processamento.py" que roda na porta 5011. Já existe um projeto chamado
"Explorando pré processamentos MAF" (f2d6a949-8bb5-4df5-8ca7-e5b8d2292488) o qual possui um dataset e uma pipeline configurada
para chamar esse serviço. Ele é o mais simples, recebe um maf e devolve um png.

2 - O segundo é o "Pré processamento - Rodrigo" com PROVIDER_ID="e8bf42e4-2ffc-4935-a546-ee5d9263f419", ele representa
a app python "app_pre_processamento_output_maf.py" que roda na porta 5001. A ideia dele é receber um maf de input
e como output devolver um maf, que sera utilizado como input para outro serviço. 

3 - O terceiro é o "Classificação de variante - Rodrigo" com PROVIDER_ID="49df4595-b8af-4e32-8791-65e583ae08a2", ele 
representa a app python "app_classificacao_variant.py" que roda na porta 5002. A ideia dele é receber um maf de input
resultante de um pre processamento e devolver um png com a classificação da variant.


Na pasta docker tem um arquivo chamado docker-compose.yml ele simplifica para subir o container do postgres, basta a partir
da raiz do projeto rodar:

```
cd docker
docker-compose up --build --remove-orphans -d
```

Ainda na pasta docker, entrando em postgres/sql/V002__populate_schema.sql tem os scripts para popular a base
com os serviços descritos acima. Lembrando que os ids dos providers são colocados hardcode nas apps python.

Para subir o serviços em python siga as instruções no outro readme.