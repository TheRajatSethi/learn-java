
-----------------------------
-- Reference Data --
-----------------------------

create table OverrideAt(
	id integer not null primary key,
	description text
);

insert into OverrideAt (description) values ('Company');
insert into OverrideAt (description) values ('Store');
insert into OverrideAt (description) values ('Tag');
insert into OverrideAt (description) values ('Person');

create table ApplicableFor(
	id integer not null primary key,
	description text
);

insert into ApplicableFor (description) values ('Person');
insert into ApplicableFor (description) values ('Company');


-----------------------------
-- Rules Data --
-----------------------------

create table Rules(
	id integer not null primary key,
	description text,
	userAdjustable integer, -- bolean
	appliedAutomatically integer, -- boolean
	foreign key (applicableForId) reference ApplicableFor(id)
);


insert into Rules (description, userAdjustable, appliedAutomatically, applicableForId) values ('OneEmployeeForSingleShift', 0, 1, 1);
insert into Rules (description, userAdjustable, appliedAutomatically, applicableForId) values ('EmployeeAvailability', 1, 1, 1);
insert into Rules (description, userAdjustable, appliedAutomatically, applicableForId) values ('EmployeePreferredAvailability', 1, 1, 1);
insert into Rules (description, userAdjustable, appliedAutomatically, applicableForId) values ('LanguagesSpoken', 1, 0, 2);
insert into Rules (description, userAdjustable, appliedAutomatically, applicableForId) values ('Skills', 1, 0, 2);


create table DefaultRuleData(
	id integer not null primary key,
	foreign key (ruleId) reference Rules(id),
	value text,
	rpType text,
	rpWeight integer,
	fromDate text,
	toDate text
);


insert into DefaultRuleData (ruleId, value, rpType, rpWeight, fromDate, toDate) values (1, NULL, 'HARD', 1, '2020-01-01', NULL);
insert into DefaultRuleData (ruleId, value, rpType, rpWeight, fromDate, toDate) values (2, NULL, 'HARD', 1, '2020-01-01', NULL);
insert into DefaultRuleData (ruleId, value, rpType, rpWeight, fromDate, toDate) values (3, NULL, 'SOFT', 1, '2020-01-01', NULL);
insert into DefaultRuleData (ruleId, value, rpType, rpWeight, fromDate, toDate) values (4, NULL, 'SOFT', 1, '2020-01-01', NULL);
insert into DefaultRuleData (ruleId, value, rpType, rpWeight, fromDate, toDate) values (5, NULL, 'HARD', 1, '2020-01-01', NULL);

-----------------------------
-- Clients Data --
-----------------------------


create table OverrideRules(
	id integer not null primary key,
	foreign key (ruleId) reference Rules(id),
	foreign key (overrideAtId) references OverrideAt(id),
	value text,
	rpType text,
	rpWeight integer,
	fromDate text,
	toDate text
);















-- create table
-- 	employee (
-- 		id integer not null primary key,
-- 		level text,
-- 		startAvailability text,
-- 		endAvailability text,
-- 		preferredStartAvailability text,
-- 		preferredEndAvailability text,
-- 		age integer,
-- 		latitude real,
-- 		longitude real
-- 	);


-- insert into employee (level, startAvailability, endAvailability, preferredStartAvailability, preferredEndAvailability, age, latitude, longitude)