create table om_withdrawal_apply
(
    id                   bigint not null,
    target_id            bigint,
    target_type          tinyint,
    amount               decimal(10,2),
    audit_status         char(10),
    brand_name           char(30),
    brand_no             varchar(30),
    brand_username       varchar(20),
    brand_opening        varchar(30),
    create_at            datetime,
    audit_at             datetime,
    primary key (id)
);