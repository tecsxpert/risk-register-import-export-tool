CREATE TABLE audit_log (

    id BIGSERIAL PRIMARY KEY,

    action VARCHAR(100) NOT NULL,

    entity_name VARCHAR(100) NOT NULL,

    entity_id BIGINT,

    performed_by VARCHAR(100),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_action
ON audit_log(action);

CREATE INDEX idx_audit_entity
ON audit_log(entity_name);