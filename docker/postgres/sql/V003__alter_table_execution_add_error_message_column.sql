\connect "pipegine"

ALTER TABLE pipegine_platform.execution
    ADD COLUMN error_message varchar;