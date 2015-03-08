# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table area (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  diretor_id                bigint,
  gestor_id                 bigint,
  constraint pk_area primary key (id))
;

create table candidato (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  cpf                       bigint,
  constraint pk_candidato primary key (id))
;

create table cargo (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  constraint pk_cargo primary key (id))
;

create table cargo_necessario (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  constraint pk_cargo_necessario primary key (id))
;

create table diretor (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  constraint pk_diretor primary key (id))
;

create table gestor (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  constraint pk_gestor primary key (id))
;

create table status (
  id                        bigint auto_increment not null,
  nome                      varchar(255),
  constraint pk_status primary key (id))
;

create table vaga (
  id                        bigint auto_increment not null,
  data_abertura             datetime,
  remuneracao               double,
  data_inicio               datetime,
  prioridade                integer,
  area_id                   bigint,
  cargo_id                  bigint,
  cargo_necessaio_id        bigint,
  status_id                 bigint,
  constraint pk_vaga primary key (id))
;


create table vaga_candidato (
  vaga_id                        bigint not null,
  candidato_id                   bigint not null,
  constraint pk_vaga_candidato primary key (vaga_id, candidato_id))
;
alter table area add constraint fk_area_diretor_1 foreign key (diretor_id) references diretor (id) on delete restrict on update restrict;
create index ix_area_diretor_1 on area (diretor_id);
alter table area add constraint fk_area_gestor_2 foreign key (gestor_id) references gestor (id) on delete restrict on update restrict;
create index ix_area_gestor_2 on area (gestor_id);
alter table vaga add constraint fk_vaga_area_3 foreign key (area_id) references area (id) on delete restrict on update restrict;
create index ix_vaga_area_3 on vaga (area_id);
alter table vaga add constraint fk_vaga_cargo_4 foreign key (cargo_id) references cargo (id) on delete restrict on update restrict;
create index ix_vaga_cargo_4 on vaga (cargo_id);
alter table vaga add constraint fk_vaga_cargoNecessaio_5 foreign key (cargo_necessaio_id) references cargo_necessario (id) on delete restrict on update restrict;
create index ix_vaga_cargoNecessaio_5 on vaga (cargo_necessaio_id);
alter table vaga add constraint fk_vaga_status_6 foreign key (status_id) references status (id) on delete restrict on update restrict;
create index ix_vaga_status_6 on vaga (status_id);



alter table vaga_candidato add constraint fk_vaga_candidato_vaga_01 foreign key (vaga_id) references vaga (id) on delete restrict on update restrict;

alter table vaga_candidato add constraint fk_vaga_candidato_candidato_02 foreign key (candidato_id) references candidato (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table area;

drop table candidato;

drop table cargo;

drop table cargo_necessario;

drop table diretor;

drop table gestor;

drop table status;

drop table vaga;

drop table vaga_candidato;

SET FOREIGN_KEY_CHECKS=1;

