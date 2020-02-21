drop table if exists am_audit_history;

drop table if exists hm_domain;

drop table if exists hm_job_title;

drop table if exists hm_service;

drop table if exists hm_stylist;

drop table if exists hm_stylist_domain_relation;

drop table if exists hm_stylist_service_relation;

drop table if exists hm_work_case;

drop table if exists sm_shop;

drop table if exists sm_shop_apply;

drop table if exists sm_shop_coupon;

drop table if exists sm_shop_discount;

drop table if exists sm_shop_gropu;

drop table if exists sm_shop_service_relation;

drop table if exists sm_shop_title;

drop table if exists sm_user_coupon;

drop table if exists sy_group_role_rel;

drop table if exists sy_group_user_rel;

drop table if exists sy_permission;

drop table if exists sy_permission_module;

drop table if exists sy_role;

drop table if exists sy_role_permission_rel;

drop table if exists sy_user;

drop table if exists sy_user_group;

drop table if exists sy_user_role_rel;

/*==============================================================*/
/* Table: am_audit_history                                      */
/*==============================================================*/
create table am_audit_history
(
    id                   bigint not null,
    target_id            bigint,
    target_type          tinyint,
    snapshot             longtext,
    status               tinyint,
    message              varchar(200),
    operator_id          bigint,
    operator_ip          varchar(30),
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_domain                                             */
/*==============================================================*/
create table hm_domain
(
    id                   bigint not null,
    name                 varchar(30),
    description          varchar(200),
    icon                 varchar(128),
    create_at            datetime default CURRENT_TIMESTAMP,
    seq                  int,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_job_title                                          */
/*==============================================================*/
create table hm_job_title
(
    id                   bigint not null,
    name                 varchar(30),
    description          varchar(200),
    icon                 varchar(128),
    create_at            datetime default CURRENT_TIMESTAMP,
    seq                  int,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_service                                            */
/*==============================================================*/
create table hm_service
(
    id                   bigint not null,
    name                 varchar(30),
    create_at            datetime default CURRENT_TIMESTAMP,
    group_status         bool,
    description          varchar(30),
    icon                 varchar(128),
    seq                  int,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_stylist                                            */
/*==============================================================*/
create table hm_stylist
(
    id                   bigint not null,
    avatar               varchar(128),
    nickname             varchar(30),
    job_title_id         bigint,
    introduction         varchar(200),
    cover_images         text,
    html_intro           text,
    work_status          tinyint,
    shop_id              bigint,
    delete_status        bool default false,
    create_at            datetime,
    seq                  int,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_stylist_domain_relation                            */
/*==============================================================*/
create table hm_stylist_domain_relation
(
    id                   bigint not null,
    domain_id            bigint,
    stylist_id           bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_stylist_service_relation                           */
/*==============================================================*/
create table hm_stylist_service_relation
(
    id                   bigint not null,
    service_id           bigint,
    stylist_id           bigint,
    male_price           decimal,
    female_price         decimal,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: hm_work_case                                          */
/*==============================================================*/
create table hm_work_case
(
    id                   bigint not null,
    stylist_id           bigint,
    title                varchar(30),
    introduction         varchar(200),
    images               text,
    shop_id              bigint,
    seq                  int,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop                                               */
/*==============================================================*/
create table sm_shop
(
    id                   bigint not null,
    name                 varchar(30),
    logo                 varchar(120),
    cover_image          varchar(120),
    introduction         varchar(120),
    address              varchar(30),
    province_code        int,
    city_code            int,
    district_code        int,
    business_license     varchar(120),
    leader_name          varchar(30),
    leader_mobile        varchar(30),
    enable               bool,
    longitude            double,
    latitude             double,
    create_at            datetime default CURRENT_TIMESTAMP,
    score                float,
    html_info            longtext,
    work_start_at        time,
    work_end_at          time,
    work_status          tinyint,
    user_id              bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_apply                                         */
/*==============================================================*/
create table sm_shop_apply
(
    id                   bigint not null,
    name                 varchar(30),
    address              varchar(30),
    province_code        int,
    city_code            int,
    district_code        int,
    business_license     varchar(120),
    photo                varchar(120),
    leader_name          varchar(30),
    leader_mobile        varchar(30),
    audit_status         tinyint,
    longitude            double,
    latitude             double,
    create_at            datetime default CURRENT_TIMESTAMP,
    update_at            datetime,
    version              int,
    user_id              bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_coupon                                        */
/*==============================================================*/
create table sm_shop_coupon
(
    id                   bigint not null,
    shop_id              bigint,
    name                 varchar(30),
    description          varchar(200),
    amount               decimal,
    coupon_type          tinyint,
    limit_min            decimal,
    publish_type         tinyint,
    member_status        tinyint,
    pull_limit_status    tinyint,
    pull_start_at        datetime,
    pull_end_at          datetime,
    publish_status       bool,
    publish_count        int,
    surplus_count        int,
    create_at            datetime default CURRENT_TIMESTAMP,
    absolute_status      bool,
    absolute_start_at    datetime,
    absolute_end_at      datetime,
    relative_day         int,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_discount                                      */
/*==============================================================*/
create table sm_shop_discount
(
    id                   bigint not null,
    name                 varchar(30),
    service_id           bigint,
    shop_id              bigint,
    discount             decimal,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_gropu                                         */
/*==============================================================*/
create table sm_shop_gropu
(
    id                   bigint not null,
    name                 varchar(30),
    service_id           bigint,
    shop_id              bigint,
    count                int,
    discount             decimal,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_service_relation                              */
/*==============================================================*/
create table sm_shop_service_relation
(
    id                   bigint not null,
    shop_id              bigint,
    title_id             bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_title                                         */
/*==============================================================*/
create table sm_shop_title
(
    id                   bigint not null,
    name                 varchar(30),
    description          varchar(200),
    icon                 varchar(128),
    create_at            datetime default CURRENT_TIMESTAMP,
    seq                  int,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_user_coupon                                        */
/*==============================================================*/
create table sm_user_coupon
(
    id                   bigint not null,
    shop_id              bigint,
    create_at            datetime default CURRENT_TIMESTAMP,
    use_status           int,
    use_start_at         datetime,
    use_end_at           datetime,
    coupon_id            bigint,
    user_id              bigint,
    order_id             bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_group_role_rel                                     */
/*==============================================================*/
create table sy_group_role_rel
(
    id                   bigint not null,
    group_id             bigint,
    role_id              bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_group_user_rel                                     */
/*==============================================================*/
create table sy_group_user_rel
(
    id                   bigint not null,
    user_id              bigint,
    group_id             bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_permission                                         */
/*==============================================================*/
create table sy_permission
(
    id                   bigint not null,
    name                 varchar(30),
    url                  varchar(30),
    method_type          tinyint,
    seq                  int default 0,
    remark               varchar(200),
    create_at            datetime default CURRENT_TIMESTAMP,
    operator_id          bigint,
    operator_ip          varchar(30),
    operator_at          datetime,
    module_id            bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_permission_module                                  */
/*==============================================================*/
create table sy_permission_module
(
    id                   bigint not null,
    name                 varchar(30),
    parent_id            bigint default 0,
    seq                  int default 0,
    operator_id          bigint,
    operator_at          datetime,
    operator_ip          varchar(30),
    create_at            datetime default CURRENT_TIMESTAMP,
    Column_9             char(10),
    primary key (id)
);

/*==============================================================*/
/* Table: sy_role                                               */
/*==============================================================*/
create table sy_role
(
    id                   bigint not null,
    name                 varchar(30),
    parent_id            bigint default 0,
    operator_id          bigint,
    operator_ip          varchar(30),
    operator_at          datetime,
    create_at            datetime default CURRENT_TIMESTAMP,
    remark               varchar(200),
    seq                  int default 0,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_role_permission_rel                                */
/*==============================================================*/
create table sy_role_permission_rel
(
    id                   bigint not null,
    permission_id        bigint,
    role_id              bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_user                                               */
/*==============================================================*/
create table sy_user
(
    id                   bigint not null,
    username             varchar(30) not null,
    password             varchar(200),
    email                varchar(128),
    create_at            datetime default CURRENT_TIMESTAMP,
    mobile               varchar(18),
    login_at             datetime,
    login_ip             datetime,
    operator_id          bigint,
    operator_ip          varchar(20),
    remark               varchar(200),
    operator_at          datetime,
    enable               bool default true,
    expire_at            datetime,
    locked               bool default false,
    nickname             varchar(30),
    avatar               varchar(128),
    primary key (id)
);

/*==============================================================*/
/* Table: sy_user_group                                         */
/*==============================================================*/
create table sy_user_group
(
    id                   bigint not null,
    name                 varchar(30),
    create_at            datetime default CURRENT_TIMESTAMP,
    operator_id          bigint,
    operator_at          datetime,
    operator_ip          varchar(30),
    remark               varchar(200),
    seq                  int default 0,
    primary key (id)
);

/*==============================================================*/
/* Table: sy_user_role_rel                                      */
/*==============================================================*/
create table sy_user_role_rel
(
    id                   bigint not null,
    role_id              bigint,
    user_id              bigint,
    primary key (id)
);
