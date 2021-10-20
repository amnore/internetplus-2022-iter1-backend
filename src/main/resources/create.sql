create table bank_punishment
(
	id int auto_increment primary key ,
	punishmentName varchar(64) null,
	punishmentType varchar(8) null,
	punishedPartyName varchar(64) null,
	main_responsible_name varchar(64) null,
	mainIllegalFact varchar(256) null,
	punishmentBasis varchar(256) null,
	punishmentDecision varchar(256) null,
	punisherName varchar(64) null,
	punishDate varchar(64) null,
	status varchar(8) null
);

create unique index bank_punishment_id_uindex
	on bank_punishment (id);


