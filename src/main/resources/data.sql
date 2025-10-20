-- H2 SQL script to create dogs table
DROP TABLE IF EXISTS dogs;

CREATE TABLE dogs (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  breed VARCHAR(255),
  supplier_id BIGINT,
  badge_id VARCHAR(255),
  gender VARCHAR(50),
  birth_date DATE,
  date_acquired DATE,
  current_status VARCHAR(50),
  leaving_date DATE,
  leaving_reason VARCHAR(50),
  kennelling_characteristics_id BIGINT,
  is_deleted BOOLEAN DEFAULT FALSE
);

-- indexes for common lookup columns
CREATE INDEX idx_dogs_name ON dogs(name);
CREATE INDEX idx_dogs_supplier ON dogs(supplier_id);
CREATE INDEX idx_dogs_badge ON dogs(badge_id);

-- optional: sample foreign key (uncomment if referenced table exists)
-- ALTER TABLE dogs
--   ADD CONSTRAINT fk_kennelling_characteristics
--   FOREIGN KEY (kennelling_characteristics_id)