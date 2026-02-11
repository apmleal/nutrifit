CREATE TABLE "subscription".tb_tenant_user
(
    id         uuid                                             NOT NULL,
    id_user    uuid                                             NOT NULL,
    id_tenant  uuid                                             NOT NULL,
    "role"     varchar(100)                                     NOT NULL,
    active     boolean                                          NOT NULL,
    created_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz                                      NULL,

    CONSTRAINT tb_tenant_user_pk PRIMARY KEY (id),
    CONSTRAINT tb_tenant_user_unique UNIQUE (id_user, id_tenant, "role"),
    CONSTRAINT tb_tenant_user_tb_user_fk FOREIGN KEY (id_user) REFERENCES "subscription".tb_user (id),
    CONSTRAINT tb_tenant_user_tb_tenant_fk FOREIGN KEY (id_tenant) REFERENCES "subscription".tb_tenant (id)
);


ALTER TABLE "subscription".tb_user
    DROP CONSTRAINT fk_tb_user_tb_tenant_id_tenant;
ALTER TABLE "subscription".tb_user
    DROP COLUMN id_tenant;
