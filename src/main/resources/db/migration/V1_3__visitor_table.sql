drop table if exists um_visitors_log;

/*==============================================================*/
/* Table: um_visitors_log                                       */
/*==============================================================*/
create table um_visitors_log
(
    id                   bigint not null,
    user_id              bigint,
    target_id            bigint,
    target_type          bigint,
    create_at            datetime default CURRENT_TIMESTAMP,
    primary key (id)
);
