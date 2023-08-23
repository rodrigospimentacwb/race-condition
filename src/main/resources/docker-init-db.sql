DO
$do$
BEGIN
   IF
NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_roles
      WHERE  rolname = 'root') THEN
      CREATE
USER root WITH PASSWORD 'root';
END IF;
   IF
NOT EXISTS (
      SELECT
      FROM   pg_catalog.pg_database
      WHERE  datname = 'demo') THEN
      CREATE
DATABASE demo;
END IF;
   GRANT ALL PRIVILEGES ON DATABASE
demo TO root;
CREATE SCHEMA IF NOT EXISTS racecondition AUTHORIZATION root;
CREATE
extension "uuid-ossp";
END
$do$;