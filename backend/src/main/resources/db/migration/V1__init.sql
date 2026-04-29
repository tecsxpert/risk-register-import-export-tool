CREATE TABLE risk_register (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    risk_level VARCHAR(50),
    status VARCHAR(50),
    owner_name VARCHAR(100),
    due_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_risk_status ON risk_register(status);
CREATE INDEX idx_risk_level ON risk_register(risk_level);
CREATE INDEX idx_owner_name ON risk_register(owner_name);