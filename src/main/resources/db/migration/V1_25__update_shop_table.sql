ALTER TABLE `haircut`.`sm_shop`
    ADD COLUMN `identity_card_front` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL AFTER `half_status`,
    ADD COLUMN `identity_card_back` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL AFTER `identity_card_front`;