CREATE TABLE subscription.plan_features (
       id_plan UUID NOT NULL,
       feature VARCHAR(50) NOT NULL,

       CONSTRAINT fk_plan_features_plan
           FOREIGN KEY (id_plan)
               REFERENCES subscription.tb_plan (id)
               ON DELETE CASCADE,

       CONSTRAINT pk_plan_features
           PRIMARY KEY (id_plan, feature)
);