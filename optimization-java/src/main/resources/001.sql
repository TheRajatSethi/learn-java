create table
	employee (
		id integer not null primary key,
		level text,
		startAvailability text,
		endAvailability text,
		preferredStartAvailability text,
		preferredEndAvailability text,
		age integer,
		latitude real,
		longitude real
	);


insert into employee (level, startAvailability, endAvailability, preferredStartAvailability, preferredEndAvailability, age, latitude, longitude)