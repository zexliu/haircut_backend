ALTER TABLE `haircut`.`sm_shop`
    MODIFY COLUMN `cover_image` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL AFTER `logo`;


ALTER TABLE `haircut`.`sm_shop_apply`
    ADD COLUMN `cover_image` text NULL AFTER `social_credit_code`;