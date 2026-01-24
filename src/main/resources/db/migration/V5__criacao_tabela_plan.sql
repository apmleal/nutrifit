CREATE TABLE subscription.tb_plan (
    id uuid DEFAULT uuid_generate_v1() NOT NULL,
    name varchar(200) NOT NULL,
    created_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz NULL,

    CONSTRAINT pk_tb_plan PRIMARY KEY (id)
);

CREATE UNIQUE INDEX uk_tb_plan_name ON subscription.tb_plan (name);

ALTER TABLE "subscription".tb_user ADD id_plan uuid NOT NULL;
ALTER TABLE subscription.tb_user ADD CONSTRAINT fk_tb_user_tb_plan_id_PLAN FOREIGN KEY (id_plan) REFERENCES subscription.tb_plan(id);