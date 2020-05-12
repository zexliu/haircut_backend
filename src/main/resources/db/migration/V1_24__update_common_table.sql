
ALTER TABLE `haircut`.`om_comment`
    MODIFY COLUMN `from_avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL AFTER `from_id`;
