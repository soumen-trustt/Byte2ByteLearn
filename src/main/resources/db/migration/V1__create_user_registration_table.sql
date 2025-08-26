CREATE TABLE IF NOT EXISTS user_registration (
    id BIGSERIAL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id ASC)
);

-- Create indexes for better query performance
CREATE INDEX idx_user_registration_username ON user_registration (username);
CREATE INDEX idx_user_registration_email ON user_registration (email);
