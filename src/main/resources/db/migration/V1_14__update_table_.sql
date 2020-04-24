ALTER TABLE `om_withdrawal_apply`
    MODIFY COLUMN `audit_status` tinyint(10) NULL DEFAULT NULL AFTER `amount`;