INSERT sy_role(id,name, parent_id,  remark,create_at) VALUES(1235551571549786876,'AGENT',0,'代理商',now());
INSERT sy_user_group(id, name, remark, seq)VALUES(1235554038396129341,'AGENT_GROUP','代理商组',0);
INSERT sy_group_role_rel(id, role_id, group_id) VALUES(1235555691346169842,1235551571549786876,1235554038396129341);
