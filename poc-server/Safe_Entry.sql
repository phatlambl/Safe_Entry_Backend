create database safe_entry;
use safe_entry;

create table user (
	id varchar(256) primary key  not null,
    username varchar(256) unique,
    name varchar(256),	
    email varchar(256) not null,
    password varchar(256)
);

create table role (
	id int primary key auto_increment not null,
    role_name varchar(256)
);

create table card (
	id varchar(256) primary key not null,
    type varchar(256)
);

create table user_role (
	id int primary key auto_increment not null,
    user_id varchar(256) null,
	role_id int null,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

create table user_card (
	id int primary key auto_increment not null,
    user_id varchar(256) null,
	card_id varchar(256) null,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (card_id) REFERENCES card(id)
);

create table device (
	id varchar(256) primary key  not null,
    location varchar(256)
);

create table temperature(
	id int primary key auto_increment not null,
    temperature int not null
);

create table email_config(
	id int primary key auto_increment not null,
    user_id varchar(256) null,
    temperature_id int null,
	FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (temperature_id) REFERENCES temperature(id)
);

create table device_log (
	id int primary key auto_increment not null,
    user_id varchar(256) not null,
    device_id varchar(256) not null,
    card_type varchar(256),
    temperature int null,
    timestamp bigint,
	FOREIGN KEY (device_id) REFERENCES device(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT constraint_usertimestamp 
	UNIQUE(user_id,timestamp)    
);