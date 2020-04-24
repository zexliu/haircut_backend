ALTER TABLE `om_shop_transaction`
    CHANGE COLUMN `user_id` `shop_id` bigint(20) NULL DEFAULT NULL AFTER `target_id`;