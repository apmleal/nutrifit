ALTER TABLE "subscription".tb_user
    ADD "role" varchar(100) NOT NULL;
ALTER TABLE "subscription".tb_user
    ADD active bool DEFAULT true NOT NULL;
