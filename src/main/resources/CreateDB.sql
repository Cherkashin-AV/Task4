create table users(
id 				SERIAL primary key,
userName		VARCHAR not null unique,
fio				VARCHAR
);


create table logins(
id 				SERIAL primary key,
access_data		TIMESTAMP not null,
user_id			INTEGER	references users(id) on delete cascade,
application		VARCHAR
);


