ALTER TABLE "subscription".tb_user
    DROP CONSTRAINT fk_tb_user_tb_plan_id_plan;
ALTER TABLE "subscription".tb_user
    DROP COLUMN id_plan;

ALTER TABLE "subscription".tb_tenant
    ADD id_plan uuid NOT NULL;
ALTER TABLE "subscription".tb_tenant
    ADD CONSTRAINT tb_tenant_tb_plan_fk FOREIGN KEY (id_plan) REFERENCES "subscription".tb_plan (id);
