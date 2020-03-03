drop table if exists am_audit_history;

drop table if exists hm_domain;

drop table if exists hm_job_title;

drop table if exists hm_service;

drop table if exists hm_stylist;

drop table if exists hm_stylist_domain_relation;

drop table if exists hm_stylist_service_relation;

drop table if exists hm_work_case;

drop table if exists om_comment;

drop table if exists om_comment_score;

drop table if exists om_flower;

drop table if exists om_order;

drop table if exists om_platform_transaction;

drop table if exists om_refund_order;

drop table if exists om_shop_order;

drop table if exists om_shop_transaction;

drop table if exists om_user_groupon;

drop table if exists om_user_reward;

drop table if exists om_user_transaction;

drop table if exists sm_basic_setting;

drop table if exists sm_commission_discount;

drop table if exists sm_feedback;

drop table if exists sm_half_time;

drop table if exists sm_shop;

drop table if exists sm_shop_apply;

drop table if exists sm_shop_coupon;

drop table if exists sm_shop_discount;

drop table if exists sm_shop_groupon;

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

drop table if exists um_popularize;

drop table if exists um_user_collect;

drop table if exists um_user_praise;

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
    male_price           decimal(10,2),
    female_price         decimal(10,2),
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
/* Table: om_comment                                            */
/*==============================================================*/
create table om_comment
(
    id                   bigint not null,
    topic_id             bigint,
    topic_type           tinyint,
    from_id              bigint,
    from_avatar          varchar(128),
    from_name            varchar(30),
    from_type            tinyint,
    to_id                bigint,
    to_name              varchar(30),
    content              text,
    images               text,
    reply_id             bigint,
    delete_status        bool default false,
    root                 bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    comment_count        int,
    praise_count         int,
    anonymous_status     bool,
    primary key (id)
);

/*==============================================================*/
/* Table: om_comment_score                                      */
/*==============================================================*/
create table om_comment_score
(
    id                   bigint,
    comment_id           bigint,
    shop_id              bigint,
    stylist_id           bigint,
    score                tinyint,
    skill_score          tinyint,
    hygiene_score        tinyint,
    service_score        tinyint,
    delete_status        bool
);

/*==============================================================*/
/* Table: om_flower                                             */
/*==============================================================*/
create table om_flower
(
    id                   bigint not null,
    user_id              bigint,
    shop_id              bigint,
    order_id             bigint,
    count                int,
    type                 tinyint,
    description          varchar(200),
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: om_order                                              */
/*==============================================================*/
create table om_order
(
    id                   bigint not null,
    order_type           tinyint,
    pay_status           tinyint,
    pay_at               datetime,
    user_Id              bigint,
    amount               decimal(10,2),
    channel_type         tinyint,
    status               tinyint,
    subject              varchar(128),
    body                 longtext,
    ip_address           varchar(30),
    create_at            datetime,
    expire_at            datetime,
    third_party_id       varchar(200),
    primary key (id)
);

/*==============================================================*/
/* Table: om_platform_transaction                               */
/*==============================================================*/
create table om_platform_transaction
(
    id                   bigint not null,
    target_id            bigint,
    user_id              bigint,
    amount               decimal(10,2),
    type                 tinyint,
    incr_status          bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: om_refund_order                                       */
/*==============================================================*/
create table om_refund_order
(
    id                   bigint not null,
    order_id             bigint,
    status               bool,
    amount               decimal(10,2),
    description          varchar(200),
    third_party_id       varchar(200),
    create_at            datetime default CURRENT_TIMESTAMP,
    channel_type         tinyint,
    user_id              bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: om_shop_order                                         */
/*==============================================================*/
create table om_shop_order
(
    id                   bigint not null,
    shop_id              bigint,
    user_id              bigint,
    stylist_id           bigint,
    order_id             bigint,
    total_amount         decimal(10,2),
    real_amount          decimal(10,2),
    status               tinyint,
    create_at            datetime,
    appointment_at       datetime,
    use_at               datetime,
    expire_at            datetime,
    subject              varchar(128),
    body                 longtext,
    sex_type             tinyint,
    primary key (id)
);

/*==============================================================*/
/* Table: om_shop_transaction                                   */
/*==============================================================*/
create table om_shop_transaction
(
    id                   bigint not null,
    target_id            bigint,
    shop_id              bigint,
    amount               decimal(10,2),
    type                 tinyint,
    incr_status          bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: om_user_groupon                                       */
/*==============================================================*/
create table om_user_groupon
(
    id                   bigint not null,
    user_id              bigint,
    shop_id              bigint,
    stylist_id           bigint,
    create_at            datetime,
    expire_at            datetime,
    total_count          int,
    remain_count         int,
    service_id           bigint,
    sex_type             tinyint,
    amount               decimal(10,2),
    status               tinyint,
    order_id             bigint,
    primary key (id)
);

/*==============================================================*/
/* Table: om_user_reward                                        */
/*==============================================================*/
create table om_user_reward
(
    id                   bigint not null,
    user_id              bigint,
    content              varchar(256),
    images               text,
    sex_type             tinyint,
    height               int,
    weight               int,
    job                  varchar(30),
    hair_volume          varchar(30),
    reward_amount        decimal(10,2),
    reward_status        tinyint,
    delete_status        bool,
    publish_status       tinyint,
    order_id             bigint,
    anonymous_status     bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    nickname             varchar(30),
    avatar               varchar(128),
    comment_count        int,
    praise_count         int,
    primary key (id)
);

/*==============================================================*/
/* Table: om_user_transaction                                   */
/*==============================================================*/
create table om_user_transaction
(
    id                   bigint not null,
    target_id            bigint,
    user_id              bigint,
    amount               decimal(10,2),
    type                 tinyint,
    incr_status          bool,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_basic_setting                                      */
/*==============================================================*/
create table sm_basic_setting
(
    id                   bigint not null,
    shop_free_month      int,
    shop_commission_proportion decimal(10,2),
    user_commission_proportion decimal(10,2),
    user_first_amount    decimal(10,2),
    create_at            datetime default CURRENT_TIMESTAMP,
    operator_ip          varchar(30),
    operator_id          bigint,
    operator_at          datetime,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_commission_discount                                */
/*==============================================================*/
create table sm_commission_discount
(
    id                   bigint not null,
    count                int,
    discount             decimal(10,2),
    create_at            datetime default CURRENT_TIMESTAMP,
    operator_ip          varchar(30),
    operator_id          bigint,
    operator_at          datetime,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_feedback                                           */
/*==============================================================*/
create table sm_feedback
(
    id                   bigint not null,
    user_id              bigint,
    link_mobile          varchar(20),
    link_name            varchar(30),
    content              varchar(256),
    images               text,
    status               tinyint,
    create_at            datetime default CURRENT_TIMESTAMP,
    operator_id          bigint,
    operator_ip          varchar(30),
    operator_at          datetime,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_half_time                                          */
/*==============================================================*/
create table sm_half_time
(
    id                   bigint not null,
    week_day             tinyint,
    shop_id              bigint,
    start_at             time,
    ebd_at               time,
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
    amount               decimal(10,2),
    coupon_type          tinyint,
    limit_min            decimal(10,2),
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
    discount             decimal(10,2),
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);

/*==============================================================*/
/* Table: sm_shop_groupon                                       */
/*==============================================================*/
create table sm_shop_groupon
(
    id                   bigint not null,
    name                 varchar(30),
    service_id           bigint,
    shop_id              bigint,
    count                int,
    discount             decimal(10,2),
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
    use_status           bool,
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

/*==============================================================*/
/* Table: um_popularize                                         */
/*==============================================================*/
create table um_popularize
(
    id                   bigint not null,
    user_id              bigint,
    target_type          tinyint,
    target_id            bigint,
    status               tinyint,
    create_at            datetime,
    first_pay_at         datetime,
    amount               decimal(10,2),
    primary key (id)
);

/*==============================================================*/
/* Table: um_user_collect                                       */
/*==============================================================*/
create table um_user_collect
(
    id                   bigint not null,
    user_id              bigint,
    target_id            bigint,
    target_type          tinyint,
    create_at            datetime,
    primary key (id)
);

/*==============================================================*/
/* Table: um_user_praise                                        */
/*==============================================================*/
create table um_user_praise
(
    id                   bigint not null,
    user_id              bigint,
    target_id            bigint,
    target_type          tinyint,
    create_at            datetime,
    primary key (id)
);
