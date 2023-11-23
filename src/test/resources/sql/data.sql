
insert into account(id,email, hash_password,role ,state) values (1,'kristina.romanova@gmail.com','qwerty007','USER','CONFIRMED');
insert into account(id,email, hash_password,role ,state) values (2,'romanova@gmail.com','qwerty007','ADMIN','CONFIRMED');


insert into test(level,name,type) values ('JUNIOR','frontend','FRONTEND');


insert into question(question,test_id) values ('What is a?',1);
insert into question(question,test_id) values ('What is c?',1);
insert into question(question,test_id) values ('What is d?',1);
insert into question(question,test_id) values ('What is e?',1);
insert into question(question,test_id) values ('What is f?',1);
insert into question(question,test_id) values ('What is g?',1);


insert into answer(answer,is_correct,question_id) values ('answer1',true,1);
insert into answer(answer,is_correct,question_id) values ('answer1',false,1);
insert into answer(answer,is_correct,question_id) values ('answer1',false,1);
insert into answer(answer,is_correct,question_id) values ('answer1',false,1);


insert into answer(answer,is_correct,question_id) values('answer1',true,2);
insert into answer(answer,is_correct,question_id) values('answer2',false,2);
insert into answer(answer,is_correct,question_id) values('answer3',false,2);
insert into answer(answer,is_correct,question_id) values('answer4',false,2);

insert into answer(answer,is_correct,question_id) values('answer1',true,3);
insert into answer(answer,is_correct,question_id) values('answer2',false,3);
insert into answer(answer,is_correct,question_id) values('answer3',false,3);
insert into answer(answer,is_correct,question_id) values('answer4',false,3);

insert into answer(answer,is_correct,question_id) values('answer1',true,4);
insert into answer(answer,is_correct,question_id) values('answer2',false,4);
insert into answer(answer,is_correct,question_id) values('answer3',false,4);
insert into answer(answer,is_correct,question_id) values('answer4',false,4);

insert into answer(answer,is_correct,question_id) values('answer1',true,5);
insert into answer(answer,is_correct,question_id) values('answer2',false,5);
insert into answer(answer,is_correct,question_id) values('answer3',false,5);
insert into answer(answer,is_correct,question_id) values('answer4',false,5);

insert into answer(answer,is_correct,question_id) values('answer1',true,6);
insert into answer(answer,is_correct,question_id) values('answer2',false,6);
insert into answer(answer,is_correct,question_id) values('answer3',false,6);
insert into answer(answer,is_correct,question_id) values('answer4',false,6);

SELECT * FROM question WHERE test_id = 1 ORDER BY RANDOM() LIMIT 3;
