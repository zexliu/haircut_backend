drop table if exists am_audit_hisotry;

drop table if exists sm_shop;

drop table if exists sm_shop_apply;

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
/* Table: am_audit_history                                 */
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
/* Table: sm_shop                                               */
/*==============================================================*/
create table sm_shop
(
    id                   bigint not null,
    user_id              bigint,
    name                 varchar(30),
    logo                 varchar(120),
    cover_image          varchar(120),
    introduction         varchar(120),
    address              varchar(30),
    province_code        varchar(20),
    city_code            varchar(20),
    district_code        varchar(20),
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
    province_code        varchar(20),
    city_code            varchar(20),
    district_code        varchar(20),
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
