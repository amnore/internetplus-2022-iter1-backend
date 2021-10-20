create table bank_punishment
(
    id                  int auto_increment,
    punishmentName      varchar(64)  null,
    punishmentDocNo     varchar(64)  null,
    punishmentType      varchar(8)   null,
    punishedPartyName   varchar(64)  null,
    mainResponsibleName varchar(64)  null,
    mainIllegalFact     varchar(256) null,
    punishmentBasis     varchar(256) null,
    punishmentDecision  varchar(256) null,
    punisherName        varchar(64)  null,
    punishDate          varchar(64)  null,
    status              varchar(8)   null,
    long_id             mediumtext   null,
    constraint bank_punishment_id_uindex
        unique (id)
);

alter table bank_punishment
    add primary key (id);

