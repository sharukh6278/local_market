insert into user(id,email, password, name,phone,blocked,created_date,updated_date) values(1,'srk@gmail.com','srk','sharukh khan',9663989934,0,SYSDATE(),SYSDATE());
insert into role(id,name)values(1,'ROLE_ADMIN');
insert into user_role_list (user_id,role_list_id) values(1,1);