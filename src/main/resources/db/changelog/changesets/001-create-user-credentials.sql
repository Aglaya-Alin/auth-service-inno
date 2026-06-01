CREATE TABLE IF NOT EXISTS user_credentials (
    id UUID NOT NULL,
    email VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_credentials PRIMARY KEY (id),
    CONSTRAINT uq_user_credentials_email UNIQUE (email),
    CONSTRAINT chk_user_credentials_role CHECK (role IN ('USER', 'ADMIN'))
);
