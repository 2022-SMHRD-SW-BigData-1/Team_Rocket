
create table join(
id varchar2(20),
pw varchar2(20) not null,
nick varchar2(20) not null,
join_date date default sysdate,
constraint join_id_pk primary key(id));


create table Poke (
poke_name varchar2(20),
skill_name varchar2(20),
hp number(10) not null,
level_ number(10) default 1,
exp number(10) default 0,
type varchar2(20),
id varchar2(20),
constraint Poke_name_pk primary key(poke_name,id),
constraint Poke_type_ck check(type in ('물타입','불타입','풀타입')),
constraint Poke_id_fk foregin key (id) references join(id));

insert into join values('test','testing','tester','');

drop table poke;
select * from poke;
select * from join;

