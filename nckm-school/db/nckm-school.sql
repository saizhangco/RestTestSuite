drop table if exists Teacher;

create table Teacher(
    Id integer primary key AUTOINCREMENT,
    Name varchar(255) not null,
    Gender varchar(255) not null,
    Age integer not null
);

insert into Teacher(Name, Gender, Age) values('王菲', 'F', 45);