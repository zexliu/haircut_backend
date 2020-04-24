ALTER TABLE `sm_half_time`
    CHANGE COLUMN `ebd_at` `end_at` time(0) NULL DEFAULT NULL AFTER `start_at`;