INSERT INTO auth_user_comp (id) VALUES ('2a0859e2-e73f-4ebe-85c0-6a39d231bbbb') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_comp (id) VALUES ('b097505f-be60-414b-83a8-cf4f44bc30ed') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_comp (id) VALUES ('1ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_comp (id) VALUES ('2ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;

INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES ('2a0859e2-e73f-4ebe-85c0-6a39d231bbbb','','Maya','maya.retno21@gmail.com') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES ('b097505f-be60-414b-83a8-cf4f44bc30ed','','admin','admin@user.com') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES ('1ea9ff33-6dd2-4c0a-b113-a90ee32a01ca','','dosen','dosen@user.com') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES ('2ea9ff33-6dd2-4c0a-b113-a90ee32a01ca','','mahasiswa','mahasiswa@user.com') ON CONFLICT DO NOTHING;

INSERT INTO auth_user_passworded (id,password,user_id) VALUES ('2a0859e2-e73f-4ebe-85c0-6a39d231bbbb','349cbccafc082902f6d88098da92b998129d98c079996b96f305705ffddc67baa935e07353a00b6068e6b0f8e1245ee8d499c80ece5232ad938825cb292bce3b','2a0859e2-e73f-4ebe-85c0-6a39d231bbbb') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_passworded (id,password,user_id) VALUES ('b097505f-be60-414b-83a8-cf4f44bc30ed','349cbccafc082902f6d88098da92b998129d98c079996b96f305705ffddc67baa935e07353a00b6068e6b0f8e1245ee8d499c80ece5232ad938825cb292bce3b','b097505f-be60-414b-83a8-cf4f44bc30ed') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_passworded (id,password,user_id) VALUES ('1ea9ff33-6dd2-4c0a-b113-a90ee32a01ca','349cbccafc082902f6d88098da92b998129d98c079996b96f305705ffddc67baa935e07353a00b6068e6b0f8e1245ee8d499c80ece5232ad938825cb292bce3b','1ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_passworded (id,password,user_id) VALUES ('2ea9ff33-6dd2-4c0a-b113-a90ee32a01ca','349cbccafc082902f6d88098da92b998129d98c079996b96f305705ffddc67baa935e07353a00b6068e6b0f8e1245ee8d499c80ece5232ad938825cb292bce3b','2ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;

INSERT INTO auth_role_comp (id) VALUES ('75f6727e-66f8-484f-b77f-83eeec82cd10') ON CONFLICT DO NOTHING;
INSERT INTO auth_role_comp (id) VALUES ('12372338-2822-420d-8c06-cff0d411d776') ON CONFLICT DO NOTHING;
INSERT INTO auth_role_comp (id) VALUES ('12472338-2822-420d-8c06-cff0d411d776') ON CONFLICT DO NOTHING;

INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES ('75f6727e-66f8-484f-b77f-83eeec82cd10','Administrator','administrator') ON CONFLICT DO NOTHING;
INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES ('12372338-2822-420d-8c06-cff0d411d776','Mahasiswa','home') ON CONFLICT DO NOTHING;
INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES ('12472338-2822-420d-8c06-cff0d411d776','Dosen','home,CreateKelas,UpdateKelas,DeleteKelas,CreatePenilaian,UpdatePenilaian') ON CONFLICT DO NOTHING;

INSERT INTO auth_user_role_comp (id) VALUES ('baeea95e-549a-4148-b7cf-27748b9cacfb') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_comp (id) VALUES ('118fac06-f754-4c36-bf92-7213a1a08c56') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_comp (id) VALUES ('568d7175-1028-4d11-8074-17ecb4536709') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_comp (id) VALUES ('668d7175-1028-4d11-8074-17ecb4536709') ON CONFLICT DO NOTHING;

INSERT INTO auth_user_role_impl (id,authRole,authUser) VALUES ('baeea95e-549a-4148-b7cf-27748b9cacfb','75f6727e-66f8-484f-b77f-83eeec82cd10','2a0859e2-e73f-4ebe-85c0-6a39d231bbbb') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_impl (id,authRole,authUser) VALUES ('118fac06-f754-4c36-bf92-7213a1a08c56','75f6727e-66f8-484f-b77f-83eeec82cd10','b097505f-be60-414b-83a8-cf4f44bc30ed') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_impl (id,authRole,authUser) VALUES ('568d7175-1028-4d11-8074-17ecb4536709','12472338-2822-420d-8c06-cff0d411d776','1ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;
INSERT INTO auth_user_role_impl (id,authRole,authUser) VALUES ('668d7175-1028-4d11-8074-17ecb4536709','12372338-2822-420d-8c06-cff0d411d776','2ea9ff33-6dd2-4c0a-b113-a90ee32a01ca') ON CONFLICT DO NOTHING;
