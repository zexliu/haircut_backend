ALTER TABLE `sy_user`
    MODIFY COLUMN `avatar` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL AFTER `nickname`;