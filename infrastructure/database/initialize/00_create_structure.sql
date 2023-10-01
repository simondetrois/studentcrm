create sequence if not exists comment_id_sequence
INCREMENT BY 1
MINVALUE 1000
MAXVALUE 9223372036854775807
NO CYCLE;

create sequence if not exists post_id_sequence
INCREMENT BY 1
MINVALUE 1000
MAXVALUE 9223372036854775807
NO CYCLE;

create table account (
       account_id varchar(255) not null,
        email varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        profile_picture bytea,
        creation_timestamp timestamp(6) with time zone,
        user_name varchar(255),
        primary key (account_id)
);

create table account_post_saved (
   account_id varchar(255) not null,
    post_id bigint not null,
    primary key (account_id, post_id)
);

create table account_post_vote (
   account_id varchar(255) not null,
    post_id bigint not null,
    positive_vote boolean not null,
    primary key (account_id, post_id)
);

create table comment (
   comment_id bigint not null,
    text varchar(255),
    timestamp timestamp(6) with time zone,
    account varchar(255),
    post bigint,
    primary key (comment_id)
);

create table follows (
   account_id varchar(255) not null,
    followed_account_id varchar(255) not null,
    primary key (account_id, followed_account_id)
);

create table post (
   post_id bigint not null,
    post_picture bytea,
    text varchar(255),
    creation_timestamp timestamp(6) with time zone,
    creator_account varchar(255),
    primary key (post_id)
);

alter table if exists account_post_saved 
   add constraint FKr8yw3bayghuspaivy0r2hmkgt 
   foreign key (post_id) 
   references post;

alter table if exists account_post_saved 
   add constraint FK5k6kbesx9rv07hecccu81q1u6 
   foreign key (account_id) 
   references account;

alter table if exists account_post_vote 
   add constraint FK5mk6rb9nunq6c4h4wt83oks6u 
   foreign key (account_id) 
   references account 
   on delete cascade;

alter table if exists account_post_vote 
   add constraint FKbemijsf3d2pm66dyvod73yra0 
   foreign key (post_id) 
   references post;

alter table if exists comment 
   add constraint FKg4pifdpsrn19a8hubl96s2ncp 
   foreign key (account) 
   references account;

alter table if exists comment 
   add constraint FKomrdwc0ub3x7hvvlyu6htn8ti 
   foreign key (post) 
   references post;

alter table if exists follows 
   add constraint FKlstwtggh70jh1nrs49ctu6gai 
   foreign key (followed_account_id) 
   references account;

alter table if exists follows 
   add constraint FKr350ef1y2oywpvkcfxahleux5 
   foreign key (account_id) 
   references account;

alter table if exists post 
   add constraint FKrfg3q7ndelwfc5mtacgh61ir2 
   foreign key (creator_account) 
   references account;
