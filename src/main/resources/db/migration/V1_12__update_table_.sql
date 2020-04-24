ALTER TABLE `haircut`.`sm_agent_apply`
    ADD COLUMN `identity_card_no` varchar(30) NULL AFTER `remark`;

ALTER TABLE `haircut`.`sm_agent`
    ADD COLUMN `identity_card_no` varchar(30) NULL AFTER `remark`;