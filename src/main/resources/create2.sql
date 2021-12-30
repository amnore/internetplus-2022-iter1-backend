# DROP TABLE IF EXISTS `case_library`;
# drop table if exists `new_bank_punishment`;
#
drop table if exists `case_library`;
CREATE TABLE case_library
(
    id                 BIGINT(20)   NOT NULL AUTO_INCREMENT,
    punishmentDocNo    varchar(128) NULL,
    punishedPersonName varchar(64)  NULL,
    punishedPartyName  varchar(256) NULL,
    punishedPartyType  varchar(256) NULL,
    province           varchar(256) NULL,
    mainIllegalFact    varchar(512) NULL,
    punishmentLawName  varchar(256) NULL,
    punishmentBasis    varchar(256) NULL,
    punishmentDecision varchar(512) NULL,
    punishmentMoney    double,
    punisherName       varchar(64)  NULL,
    punishDate         varchar(64)  NULL,
    PRIMARY KEY (`id`),
    constraint case_library_id_uindex
        unique (id)
    #     constraint case_library_docno_uindex
    #         unique (punishmentDocNo)
);
#
# create table new_bank_punishment
# (
#     id                  BIGINT(20)   NOT NULL AUTO_INCREMENT,
#     punishmentName      varchar(64)  NULL,
#     punishmentDocNo     varchar(128) NULL,
#     punishmentType      varchar(512) NULL,
#     punishedPartyName   varchar(256) NULL,
#     mainResponsibleName varchar(64)  NULL,
#     mainIllegalFact     varchar(256) NULL,
#     punishmentBasis     varchar(256) NULL,
#     punishmentDecision  varchar(512) NULL,
#     punisherName        varchar(64)  NULL,
#     punishDate          varchar(64)  NULL,
#     province    varchar(64) null,
#     PRIMARY KEY (`id`),
#     constraint bank_punishment_id_uindex
#         unique (id)
# #     constraint bank_punishment_docno_uindex
# #         unique (punishmentDocNo)
# );
