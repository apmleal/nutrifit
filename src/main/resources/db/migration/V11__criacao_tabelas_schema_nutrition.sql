CREATE SCHEMA IF NOT EXISTS nutrition;

CREATE TABLE "nutrition".tb_nutritionist
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_user    UUID                                                  NOT NULL UNIQUE,
    id_tenant  UUID                                                  NOT NULL,
    crn        VARCHAR(50)                                           NOT NULL,
    specialty  VARCHAR(100),
    phone      VARCHAR(20),
    created_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz                                           NULL,

    CONSTRAINT fk_nutritionist_user
        FOREIGN KEY (id_user) REFERENCES subscription.tb_user (id),

    CONSTRAINT fk_nutritionist_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE "nutrition".tb_patient
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_user    UUID                                                  NOT NULL UNIQUE,
    id_tenant  UUID                                                  NOT NULL,
    birth_date DATE,
    gender     VARCHAR(20),
    height_cm  NUMERIC(5, 2),
    notes      TEXT,
    created_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz                                           NULL,

    CONSTRAINT fk_patient_user
        FOREIGN KEY (id_user) REFERENCES subscription.tb_user (id),

    CONSTRAINT fk_patient_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE "nutrition".tb_nutritional_assessment
(
    id                  UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_patient          UUID                                                  NOT NULL,
    id_nutritionist     UUID                                                  NOT NULL,
    id_tenant           UUID                                                  NOT NULL,
    assessment_date     DATE                                                  NOT NULL,
    weight_kg           NUMERIC(5, 2),
    body_fat_percentage NUMERIC(5, 2),
    lean_mass_kg        NUMERIC(5, 2),
    bmi                 NUMERIC(5, 2),
    notes               TEXT,
    created_at          timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at          timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at          timestamptz                                           NULL,

    CONSTRAINT fk_assessment_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id),

    CONSTRAINT fk_assessment_nutritionist
        FOREIGN KEY (id_nutritionist) REFERENCES nutrition.tb_nutritionist (id),

    CONSTRAINT fk_nutritional_assessment_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE "nutrition".tb_body_measurement
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_assessment UUID                                                  NOT NULL,
    id_tenant     UUID                                                  NOT NULL,
    waist_cm      NUMERIC(5, 2),
    hip_cm        NUMERIC(5, 2),
    abdomen_cm    NUMERIC(5, 2),
    arm_cm        NUMERIC(5, 2),
    leg_cm        NUMERIC(5, 2),
    created_at    timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at    timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at    timestamptz                                           NULL,

    CONSTRAINT fk_body_measurement_assessment
        FOREIGN KEY (id_assessment) REFERENCES nutrition.tb_nutritional_assessment (id),

    CONSTRAINT fk_body_measurement_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_meal_plan
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_patient      UUID                                                  NOT NULL,
    id_nutritionist UUID                                                  NOT NULL,
    id_tenant       UUID                                                  NOT NULL,
    start_date      DATE                                                  NOT NULL,
    end_date        DATE,
    goal            VARCHAR(100),
    notes           TEXT,
    created_at      timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at      timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at      timestamptz                                           NULL,

    CONSTRAINT fk_meal_plan_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id),

    CONSTRAINT fk_meal_plan_nutritionist
        FOREIGN KEY (id_nutritionist) REFERENCES nutrition.tb_nutritionist (id),

    CONSTRAINT fk_meal_plan_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_meal
(
    id           UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_meal_plan UUID                                                  NOT NULL,
    id_tenant    UUID                                                  NOT NULL,
    type         VARCHAR(50)                                           NOT NULL,
    time         TIME,
    created_at   timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at   timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at   timestamptz                                           NULL,

    CONSTRAINT fk_meal_meal_plan
        FOREIGN KEY (id_meal_plan) REFERENCES nutrition.tb_meal_plan (id),

    CONSTRAINT fk_meal_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_food
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name          VARCHAR(150) NOT NULL,
    brand         VARCHAR(100),
    calories      NUMERIC(6, 2),
    carbohydrates NUMERIC(6, 2),
    proteins      NUMERIC(6, 2),
    fats          NUMERIC(6, 2)
);

CREATE TABLE nutrition.tb_meal_item
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_meal    UUID                                                  NOT NULL,
    id_tenant  UUID                                                  NOT NULL,
    id_food    UUID                                                  NOT NULL,
    quantity   NUMERIC(6, 2)                                         NOT NULL,
    unit       VARCHAR(20)                                           NOT NULL,
    created_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz                                           NULL,

    CONSTRAINT fk_meal_item_meal
        FOREIGN KEY (id_meal) REFERENCES nutrition.tb_meal (id),

    CONSTRAINT fk_meal_item_food
        FOREIGN KEY (id_food) REFERENCES nutrition.tb_food (id),

    CONSTRAINT fk_meal_item_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_nutrient
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    unit VARCHAR(20)  NOT NULL
);

CREATE TABLE nutrition.tb_food_nutrient
(
    id_food     UUID          NOT NULL,
    id_nutrient UUID          NOT NULL,
    value       NUMERIC(8, 2) NOT NULL,

    PRIMARY KEY (id_food, id_nutrient),

    CONSTRAINT fk_food_nutrient_food
        FOREIGN KEY (id_food) REFERENCES nutrition.tb_food (id),

    CONSTRAINT fk_food_nutrient_nutrient
        FOREIGN KEY (id_nutrient) REFERENCES nutrition.tb_nutrient (id)
);

CREATE TABLE nutrition.tb_food_restriction
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE nutrition.tb_patient_food_restriction
(
    id_patient     UUID                                             NOT NULL,
    id_restriction UUID                                             NOT NULL,
    id_tenant      UUID                                             NOT NULL,
    created_at     timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at     timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at     timestamptz                                      NULL,

    PRIMARY KEY (id_patient, id_restriction, id_tenant),

    CONSTRAINT fk_pfr_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id),

    CONSTRAINT fk_pfr_restriction
        FOREIGN KEY (id_restriction) REFERENCES nutrition.tb_food_restriction (id),

    CONSTRAINT fk_pacient_food_restriction_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_daily_log
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_patient UUID                                                  NOT NULL,
    id_tenant  UUID                                                  NOT NULL,
    log_date   DATE                                                  NOT NULL,
    weight_kg  NUMERIC(5, 2),
    notes      TEXT,
    created_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at timestamptz                                           NULL,

    CONSTRAINT uq_daily_log UNIQUE (id_patient, log_date),

    CONSTRAINT fk_daily_log_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id)
);

CREATE TABLE nutrition.tb_meal_compliance
(
    id              UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_meal         UUID                                                  NOT NULL,
    id_tenant       UUID                                                  NOT NULL,
    compliance_date DATE                                                  NOT NULL,
    followed        BOOLEAN                                               NOT NULL,
    notes           TEXT,
    created_at      timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at      timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at      timestamptz                                           NULL,

    CONSTRAINT fk_meal_compliance_meal
        FOREIGN KEY (id_meal) REFERENCES nutrition.tb_meal (id),

    CONSTRAINT fk_meal_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id),

    CONSTRAINT fk_meal_compliance_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_health_condition
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE nutrition.tb_patient_health_condition
(
    id_patient   UUID                                             NOT NULL,
    id_condition UUID                                             NOT NULL,
    id_tenant    UUID                                             NOT NULL,
    created_at   timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at   timestamptz DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at   timestamptz                                      NULL,

    PRIMARY KEY (id_patient, id_condition, id_tenant),

    CONSTRAINT fk_phc_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id),

    CONSTRAINT fk_phc_condition
        FOREIGN KEY (id_condition) REFERENCES nutrition.tb_health_condition (id),

    CONSTRAINT fk_patient_health_condition_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);

CREATE TABLE nutrition.tb_supplement
(
    id    UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name  VARCHAR(150) NOT NULL,
    brand VARCHAR(100)
);

CREATE TABLE nutrition.tb_supplement_prescription
(
    id            UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    id_patient    UUID                                                  NOT NULL,
    id_supplement UUID                                                  NOT NULL,
    id_tenant     UUID                                                  NOT NULL,
    dosage        VARCHAR(100),
    frequency     VARCHAR(100),
    start_date    DATE,
    end_date      DATE,
    created_at    timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    updated_at    timestamptz      DEFAULT timezone('utc'::text, now()) NOT NULL,
    deleted_at    timestamptz                                           NULL,

    CONSTRAINT fk_supplement_prescription_patient
        FOREIGN KEY (id_patient) REFERENCES nutrition.tb_patient (id),

    CONSTRAINT fk_supplement_prescription_supplement
        FOREIGN KEY (id_supplement) REFERENCES nutrition.tb_supplement (id),

    CONSTRAINT fk_supplement_prescription_tenant
        FOREIGN KEY (id_tenant) REFERENCES subscription.tb_tenant (id)
);