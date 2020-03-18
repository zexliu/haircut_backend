drop table if exists sy_user_extension;

/*==============================================================*/
/* Table: sy_user_extension                                     */
/*==============================================================*/
create table sy_user_extension
(
    id                   bigint not null,
    union_id             varchar(128),
    user_id              bigint,
    birth_day            datetime,
    job                  varchar(30),
    education            varchar(30),
    face                 varchar(30),
    hair_height          varchar(30),
    hair_quality         varchar(30),
    hair_style           varchar(30),
    idol                 varchar(30),
    primary key (id)
);


drop table if exists um_user_hair_history;

/*==============================================================*/
/* Table: um_user_hair_history                                  */
/*==============================================================*/
create table um_user_hair_history
(
    id                   bigint,
    images               text,
    create_at            datetime default CURRENT_TIMESTAMP,
    user_id              bigint
);
