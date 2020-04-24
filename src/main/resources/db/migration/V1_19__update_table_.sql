ALTER TABLE `haircut`.`om_user_reward`
    ADD COLUMN `preview_count` int(11) NULL DEFAULT 0 AFTER `praise_count`;