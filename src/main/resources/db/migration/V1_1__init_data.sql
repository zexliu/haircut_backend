INSERT sy_user(id, username, password, email, mobile, remark, enable, locked, nickname, avatar,create_at) VALUE (1231064253191327746,'admin','$2a$10$4SBtj67t7j3xLYSxbftunO0ov9MBZoH9LYsLsJc8qKT.Sf8jHYqyG','zexliu@icloud.com',18713888898,'超级管理员',true,false,'Admin','http://b-ssl.duitang.com/uploads/item/201511/21/20151121171107_zMZcy.jpeg',now());
INSERT sy_role(id,name, parent_id,  remark,create_at) VALUE(1231063023681441794,'ADMIN',0,'超级管理员',now());
INSERT sy_role(id,name, parent_id,  remark,create_at) VALUE(1186593156003532801,'SHOP_ADMIN',0,'店铺管理员',now());
INSERT sy_user_role_rel(id, role_id , user_id ) VALUE(1230875155390455810,1231063023681441794,1231064253191327746)
