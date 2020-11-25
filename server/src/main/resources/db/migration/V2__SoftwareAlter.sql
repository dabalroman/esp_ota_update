alter table DEVICE alter column LAST_CHECKED rename to LAST_SOFTWARE_CHECK;
alter table DEVICE alter column LAST_UPDATED rename to LAST_SOFTWARE_UPDATE;

alter table SOFTWARE alter column PATH rename to FILE;
alter table SOFTWARE
    add PREVIOUS_VERSION_ID int default null;

alter table SOFTWARE
    add constraint SOFTWARE_SOFTWARE_ID_FK
        foreign key (PREVIOUS_VERSION_ID) references SOFTWARE;