# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table area (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255),
  diretor_id                integer,
  gestor_id                 integer)
;

create table candidato (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255),
  cpf                       integer,
  constraint uq_candidato_1 unique (cpf))
;

create table cargo (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255))
;

create table cargo_necessario (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255))
;

create table diretor (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255))
;

create table gestor (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255))
;

create table situacao (
  id                        integer primary key AUTOINCREMENT,
  nome                      varchar(255))
;

create table usuario (
  id                        integer primary key AUTOINCREMENT,
  email                     varchar(255),
  senha                     varchar(255),
  tipo                      varchar(255))
;

create table vaga (
  id                        integer primary key AUTOINCREMENT,
  data_abertura             timestamp,
  remuneracao               double,
  data_inicio               timestamp,
  prioridade                integer,
  area_id                   integer,
  cargo_id                  integer,
  cargo_necessario_id       integer,
  status_id                 integer,
  data_criacao              timestamp,
  criado_por                varchar(255))
;

create table vaga_candidato (
  vaga_id                   integer,
  candidato_id              integer,
  data_criacao              timestamp,
  criado_por                varchar(255),
  aprovado                  varchar(255),
  constraint uq_vaga_candidato_1 unique (vaga_id,candidato_id))
;


create table vaga_candidato (
  vaga_id                        integer not null,
  candidato_id                   integer not null,
  constraint pk_vaga_candidato primary key (vaga_id, candidato_id))
;
alter table area add constraint fk_area_diretor_1 foreign key (diretor_id) references diretor (id);
create index ix_area_diretor_1 on area (diretor_id);
alter table area add constraint fk_area_gestor_2 foreign key (gestor_id) references gestor (id);
create index ix_area_gestor_2 on area (gestor_id);
alter table vaga add constraint fk_vaga_area_3 foreign key (area_id) references area (id);
create index ix_vaga_area_3 on vaga (area_id);
alter table vaga add constraint fk_vaga_cargo_4 foreign key (cargo_id) references cargo (id);
create index ix_vaga_cargo_4 on vaga (cargo_id);
alter table vaga add constraint fk_vaga_cargoNecessario_5 foreign key (cargo_necessario_id) references cargo_necessario (id);
create index ix_vaga_cargoNecessario_5 on vaga (cargo_necessario_id);
alter table vaga add constraint fk_vaga_status_6 foreign key (status_id) references situacao (id);
create index ix_vaga_status_6 on vaga (status_id);
alter table vaga_candidato add constraint fk_vaga_candidato_vaga_7 foreign key (vaga_id) references vaga (id);
create index ix_vaga_candidato_vaga_7 on vaga_candidato (vaga_id);
alter table vaga_candidato add constraint fk_vaga_candidato_candidato_8 foreign key (candidato_id) references candidato (id);
create index ix_vaga_candidato_candidato_8 on vaga_candidato (candidato_id);



alter table vaga_candidato add constraint fk_vaga_candidato_vaga_01 foreign key (vaga_id) references vaga (id);

alter table vaga_candidato add constraint fk_vaga_candidato_candidato_02 foreign key (candidato_id) references candidato (id);

# --- !Downs

PRAGMA foreign_keys = OFF;

drop table area;

drop table candidato;

drop table vaga_candidato;

drop table cargo;

drop table cargo_necessario;

drop table diretor;

drop table gestor;

drop table situacao;

drop table usuario;

drop table vaga;

drop table vaga_candidato;

PRAGMA foreign_keys = ON;

