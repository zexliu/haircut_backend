drop table if exists sm_agent;

/*==============================================================*/
/* Table: sm_agent                                              */
/*==============================================================*/
create table sm_agent
(
    id                   bigint not null,
    province_code        int,
    city_code            int,
    name                 char(30),
    link_mobile          varchar(20),
    email                varchar(30),
    identity_card_front  varchar(120),
    identity_card_back   varchar(120),
    create_at            datetime default CURRENT_TIMESTAMP,
    update_at            datetime,
    user_id              bigint,
    enable               bool,
    remark               varchar(200),
    primary key (id)
);


drop table if exists sm_agent_apply;

/*==============================================================*/
/* Table: sm_agent_apply                                        */
/*==============================================================*/
create table sm_agent_apply
(
    id                   bigint not null,
    province_code        int,
    city_code            int,
    name                 char(30),
    link_mobile          varchar(20),
    email                varchar(30),
    identity_card_front  varchar(120),
    identity_card_back   varchar(120),
    create_at            datetime default CURRENT_TIMESTAMP,
    update_at            datetime,
    version              int,
    user_id              bigint,
    audit_status         tinyint,
    remark               varchar(200),
    primary key (id)
);

drop table if exists om_agent_transaction;

/*==============================================================*/
/* Table: om_agent_transaction                                  */
/*==============================================================*/
create table om_agent_transaction
(
    id                   bigint not null,
    target_id            bigint,
    agent_id             bigint,
    amount               decimal(10,2),
    type                 tinyint,
    incr_status          bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

drop table if exists sm_agent_brand;

/*==============================================================*/
/* Table: sm_agent_brand                                        */
/*==============================================================*/
create table sm_agent_brand
(
    id                   bigint not null,
    agent_id             bigint not null,
    brand_name           char(30),
    brand_no             varchar(30),
    brand_username       varchar(20),
    brand_opening        varchar(30),
    primary key (id, agent_id)
);
