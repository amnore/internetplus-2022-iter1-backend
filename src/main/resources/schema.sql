DROP TABLE IF EXISTS `bank_punishment`;

create table bank_punishment
(
    id                  BIGINT(20) NOT NULL AUTO_INCREMENT,
    punishmentName       varchar(64),
    punishmentDocNo      varchar(128),
    punishmentType       varchar(32),
    punishedPartyName    varchar(512),
    punishedPersonName   varchar(64),
    punishedBusinessName varchar(512),
    mainResponsibleName  varchar(64),
    mainIllegalFact      varchar(2048),
    punishmentBasis      varchar(256),
    punishmentLawName    varchar(256),
    punishmentDecision   varchar(2048),
    punishmentMoney      double,
    punisherName         varchar(64),
    punishDate           varchar(64),
    province             varchar(64),
    status               varchar(8),
    PRIMARY KEY (`id`),
    constraint bank_punishment_id_uindex
        unique (id)
);
