ALTER TABLE `sm_shop`
    ADD COLUMN `half_status` bool NULL DEFAULT false AFTER `user_id`;