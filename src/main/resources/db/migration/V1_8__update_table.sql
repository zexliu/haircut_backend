ALTER TABLE `haircut`.`sy_user_extension`
    MODIFY COLUMN `birth_day` date NULL DEFAULT NULL AFTER `user_id`;