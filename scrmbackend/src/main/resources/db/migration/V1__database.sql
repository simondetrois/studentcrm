create sequence if not exists id_sequence_of_courses
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
NO CYCLE;

create sequence if not exists id_sequence_of_students
INCREMENT BY 1
MINVALUE 1
MAXVALUE 9223372036854775807
NO CYCLE;

create table if not exists student
(
    student_id int8 not null,
    first_name varchar(255) not null,
    last_name varchar(255) not null,
    e_mail varchar(255) not null,
    subject varchar(255) not null,
    primary key (student_id)
);

create table if not exists course
(
    course_id int8 not null,
    course_name varchar(255) not null,
    professor varchar(255) not null,
    primary key (course_id)
);

create table if not exists join_student_course
(
    student_id int8 not null references student (student_id),
    course_id int8 not null references course (course_id),
    primary key (student_id, course_id)
);

alter table student
    add constraint unique_mail unique (e_mail)
