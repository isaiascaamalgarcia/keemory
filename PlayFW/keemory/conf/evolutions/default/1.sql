# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table carer (
  id                        integer not null,
  name                      varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  ckc                       varchar(255),
  constraint pk_carer primary key (id))
;

create table patient (
  id                        integer not null,
  user_id                   integer,
  name                      varchar(255),
  phone                     varchar(255),
  address                   varchar(255),
  base64                    TEXT,
  type                      varchar(255),
  url                       varchar(255),
  ckp                       varchar(255),
  constraint pk_patient primary key (id))
;

create sequence carer_seq;

create sequence patient_seq;

alter table patient add constraint fk_patient_user_1 foreign key (user_id) references carer (id) on delete restrict on update restrict;
create index ix_patient_user_1 on patient (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists carer;

drop table if exists patient;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists carer_seq;

drop sequence if exists patient_seq;

