# DROP TABLE IF EXISTS `bank_punishment`;

CREATE TABLE bank_punishment
(
    id                  INT NOT NULL AUTO_INCREMENT,
    punishmentName      varchar(64)  NULL,
    punishmentDocNo     varchar(64)  NULL,
    punishmentType      varchar(8)   NULL,
    punishedPartyName   varchar(64)  NULL,
    mainResponsibleName varchar(64)  NULL,
    mainIllegalFact     varchar(256) NULL,
    punishmentBasis     varchar(256) NULL,
    punishmentDecision  varchar(256) NULL,
    punisherName        varchar(64)  NULL,
    punishDate          varchar(64)  NULL,
    status              varchar(8)   NULL,
    long_id             MEDIUMTEXT   NULL,
    PRIMARY KEY (`id`),
    constraint bank_punishment_id_uindex
        unique (id),
    constraint bank_punishment_docno_uindex
        unique (punishmentDocNo)
);



