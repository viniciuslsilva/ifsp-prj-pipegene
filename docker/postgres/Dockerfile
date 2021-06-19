FROM postgres:10

ENV POSTGIS_MAJOR 2.5

RUN apt-get update \
      && apt-get install -y --no-install-recommends \
      && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /docker-entrypoint-initdb.d
COPY ./sql/*.sql /docker-entrypoint-initdb.d/

COPY *.sh /bootstrap/
RUN chmod 755 /bootstrap/*.sh

HEALTHCHECK --start-period=30s --interval=1s --timeout=60s --retries=30\
  CMD /bootstrap/init.sh