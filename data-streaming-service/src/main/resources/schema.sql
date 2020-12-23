drop table EVENTS if exists;
create table EVENTS
(
id IDENTITY ,
timestamp_millis TIMESTAMP not null,
event_col1 DECIMAL not null,
event_col2 BIGINT not null
);