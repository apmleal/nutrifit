CREATE SCHEMA IF NOT EXISTS subscription;

CREATE TABLE subscription.tb_tenant (
    id_tenant uuid DEFAULT uuid_generate_v1() NOT NULL,
    name varchar(200) NOT NULL,
    created_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz NULL,

    CONSTRAINT pk_tb_tenant PRIMARY KEY (id_tenant)
);

CREATE UNIQUE INDEX uk_tb_tenant_name ON subscription.tb_tenant (name);