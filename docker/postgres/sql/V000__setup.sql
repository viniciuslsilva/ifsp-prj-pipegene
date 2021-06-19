CREATE ROLE "pipegine" WITH SUPERUSER;
CREATE USER "pipegine-ro" WITH PASSWORD 'pipegine-ro' IN ROLE "pipegine";
CREATE USER "pipegine-dml" WITH PASSWORD 'pipegine-dml' IN ROLE "pipegine";
CREATE USER "pipegine-app" WITH PASSWORD 'pipegine-app' IN ROLE "pipegine";
ALTER USER "pipegine-app" SET search_path = public,pipegine_platform;

CREATE DATABASE "pipegine" WITH OWNER "pipegine-app";

\connect "pipegine"