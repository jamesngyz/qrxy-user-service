--liquibase formatted sql
--changeset jamesngyz:202006032300-1
--comment: create_person_table

CREATE TABLE person (
    id uuid PRIMARY KEY,
    auth_id text NOT NULL,
    username text NOT NULL,
    email text NOT NULL,
    bio text NOT NULL DEFAULT '',
    created_at timestamptz NOT NULL DEFAULT now(),
    created_by text NOT NULL,
    updated_at timestamptz NOT NULL DEFAULT now(),
    updated_by text NOT NULL,

    CONSTRAINT person_username_uq  UNIQUE(username)
);
