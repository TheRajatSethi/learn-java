drop table account;

drop table session;

create table account (
id integer not null primary key,
email text not null unique,
password text,
openid text,
createdAt text default (STRFTIME('%Y-%m-%dT%H:%M:%fZ'))
);

create table session (
id text not null primary key,
userid text not null,
createdAt text default (STRFTIME('%Y-%m-%dT%H:%M:%fZ')),
lastAccessedAt text
);

drop table product;

create table brand(
id integer not null primary key,
slug text,
name text,
description text
);

create table product(
id integer not null primary key,
slug text,
brandId text references brand(id),
category text,
name text,
description text,
status text,
createdAt text default (STRFTIME('%Y-%m-%dT%H:%M:%fZ')) 
);

create table ingredient(
id integer not null primary key,
name text,
description text,
);

create table price(
id integer not null primary key,
product_id 
providor
type - online,
store
link - affiliate link (for online / offline)
price currency start date
);

create table likes();

create table reviews();

create table analytics();

create table packaging ();
-- Do we need this ??

create table priceHistory(
--id integer not null primary key,
--product_id,
--providor,
--type - online,
--store,
--link - affiliate link (for online / offline),
--price currency start_date end_date,
--);
insert
	into
		account (email,
		password,
		openid)
	values ('sam@gmail.com',
	'password',
	'google');
insert
	into
		account (email,
		password,
		openid,
		createdAt)
	values ('sam@gmail.com',
	'password',
	'GOOGLE',
	'2025-03-15T20:53:48.536418400Z');
select
	STRFTIME('%Y-%m-%dT%H:%M:%fZ');
select
	STRFTIME('%s');
select
	*
from
	account;
delete
from
	account;
select
	*
from
	account
where
	id = 1;
insert
	into
		session (id,
		userid,
		createdat,
		lastaccessedat)
	values ('sdx',
	'sd',
	'sd',
	'sd')
	select
		*
	from
		session;