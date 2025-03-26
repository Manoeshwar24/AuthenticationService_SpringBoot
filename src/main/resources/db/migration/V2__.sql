ALTER TABLE session
    ADD expiry_at datetime NULL;

ALTER TABLE session
    ADD session_status VARCHAR(255) NULL;

ALTER TABLE session
    DROP COLUMN device;

ALTER TABLE session
    DROP COLUMN start_time;