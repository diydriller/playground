ALTER TABLE member
    ADD CONSTRAINT fk_member_team
        FOREIGN KEY (team_id)
            REFERENCES team(id)
            ON DELETE SET NULL;

ALTER TABLE activity_log
    ADD CONSTRAINT fk_activity_log_team
        FOREIGN KEY (team_id)
            REFERENCES team(id)
            ON DELETE SET NULL;