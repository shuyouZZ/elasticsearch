select time,latitude,longitude,depth,mag,ST_AsGeoJSON(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKT(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsWKB(geopoint) as point from earthquake;
select id,time,latitude,longitude,depth,mag,ST_AsText(geopoint) as point from earthquake;
insert into earthquake(time, latitude, longitude, depth, mag, geopoint) values ('1995',2,2,2,2,ST_GeomFromText('POINT(2 2)'));
select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake;


show index from earthquake;

explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where depth = 5.2;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake use index(earthquake_id_uindex) where depth = 5.2;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake ignore index (earthquake_id_uindex) where id = 1000;

explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where id = 1000;
explain select id,time,latitude,longitude,depth,mag,ST_AsBinary(geopoint) as point from earthquake where mag = 2.1 limit 3;

select * from comment where ids = 1000;
explain select * from comment where id < 100000000 limit 30;

show profiles ;
show variables;
show variables like '%profiling%';
set profiling=1;

select * from comment where ids = 1000;
show profiles ;
select * from comment where id > 10000000 && id < 100000000 order by id desc ;
show profiles ;


create table test (
  id int primary key ,
  username varchar(20) not null ,
  password varchar(50) not null ,
  tel long not null ,
  address varchar(50) not null
    );

insert into test values (0,'董书佑','123456',15038093696,'中国北京');
insert into test (username,password,tel,address) values (1,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (2,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (3,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (4,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (5,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (6,123456,15038093696,'中国武汉');
insert into test (username,password,tel,address) values (7,123456,15038093696,'中国武汉');

start transaction;
select * from test where id >2;
commit ;
SHOW VARIABLES LIKE '%storage_engine%';
SELECT @@tx_isolation;

explain SELECT * from earthquake where id in (1000,1010,1002,10,20,30,40,560,60,70,8,90,100,110,120,130,140,150,160,170,180,190,200);
show profiles;

select version();

SHOW VARIABLES LIKE '%pro%';
SET profiling=1;

SELECT * from ums_admin where username = 'dshuyou' and password = '123456';

INSERT INTO comment_copy (comment_id, music_id, content, liked_count,time,user_id,nickname,user_img ) VALUES (1,1,1,'1',1,1,'1','1');

SELECT * from comments where comment_id >= (select comment_id from comments Order By comment_id limit 1000, 1) limit 1000;
select comment_id from comments Order By comment_id limit 3, 1;

SHOW FULL COLUMNS FROM comment_copy;
SHOW FULL COLUMNS FROM comments;

show variables like 'character%';
set character_set_database=utf8;
set character_set_server=utf8;
set character_set_connection=utf8;
set character_set_database=utf8;
set character_set_results=utf8;
select * FROM comment_copy where (id = 10378);

SELECT count(*) from comment_copy;
SELECT * from comment_copy limit 0,50000;
SELECT * from comment_copy limit 50000,50000;
SELECT * from comment_copy limit 100000,50000;