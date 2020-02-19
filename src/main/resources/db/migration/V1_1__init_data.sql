ALTER TABLE sm_shop add point point;

INSERT sy_user(id, username, password, email, mobile, remark, enable, locked, nickname, avatar,create_at) VALUE (1,'admin','$2a$10$4SBtj67t7j3xLYSxbftunO0ov9MBZoH9LYsLsJc8qKT.Sf8jHYqyG','zexliu@icloud.com',18713888898,'超级管理员',true,false,'Admin','http://b-ssl.duitang.com/uploads/item/201511/21/20151121171107_zMZcy.jpeg',now());
INSERT sy_role(id,name, parent_id,  remark,create_at) VALUE(1,'admin',0,'超级管理员',now());
INSERT sy_user_role_rel(id, role_id , user_id ) VALUE(1,1,1)
