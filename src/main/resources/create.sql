DROP TABLE IF EXISTS `bank_punishment`;

CREATE TABLE bank_punishment
(
    id                  BIGINT(20)  NOT NULL AUTO_INCREMENT,
    punishmentName      varchar(64)  NULL,
    punishmentDocNo     varchar(128)  NULL,
    punishmentType      varchar(512)   NULL,
    punishedPartyName   varchar(256)  NULL,
    mainResponsibleName varchar(64)  NULL,
    mainIllegalFact     varchar(256) NULL,
    punishmentBasis     varchar(256) NULL,
    punishmentDecision  varchar(512) NULL,
    punisherName        varchar(64)  NULL,
    punishDate          varchar(64)  NULL,
    status              varchar(8)   NULL,
    PRIMARY KEY (`id`),
    constraint bank_punishment_id_uindex
        unique (id)
#     constraint bank_punishment_docno_uindex
#         unique (punishmentDocNo)
);
