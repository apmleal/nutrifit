CREATE TABLE subscription.tb_user (
    id uuid DEFAULT uuid_generate_v1() NOT NULL,
    name varchar(200) NOT NULL,
    email varchar(200) NOT NULL,
    created_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz NULL,

    id_tenant uuid  NOT NULL,

    CONSTRAINT pk_tb_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_tb_user_email ON subscription.tb_user (email);

ALTER TABLE subscription.tb_user ADD CONSTRAINT fk_tb_user_tb_tenant_id_tenant FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant(id);