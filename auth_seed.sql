INSERT INTO auth_user_comp (id) VALUES (1);
INSERT INTO auth_user_comp (id) VALUES (2);
INSERT INTO auth_user_comp (id) VALUES (3);
INSERT INTO auth_user_comp (id) VALUES (4);
INSERT INTO auth_user_comp (id) VALUES (5);
INSERT INTO auth_user_comp (id) VALUES (6);
INSERT INTO auth_user_comp (id) VALUES (7);

INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (1,'administrator','Ichlasul Affan','ichlaffterlalu@gmail.com');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (2,'UpdateUserImpl,UpdateRoleImpl,UpdatePermissions','Ichlasul Affan (UI)','ichlasul.affan@ui.ac.id');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (3,'','Ichlasul Affan (Dummy)','ichlasul.affan@gmail.com');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (4,'','Falah Prasetyo Waluyo','falah.prasetyo01@ui.ac.id');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (5,'','Samuel Tupa Febrian','samuel.febrian@gmail.com');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (6,'','Christopher Samuel','christopher.samuel@ui.ac.id');
INSERT INTO auth_user_impl (id,allowedPermissions,name,email) VALUES (7,'','Maya','maya.retno21@gmail.com');

INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (1,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','administrator','Ichlasul Affan','ichlaffterlalu@gmail.com');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (2,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','UpdateUserImpl,UpdateRoleImpl,UpdatePermissions','Ichlasul Affan (UI)','ichlasul.affan@ui.ac.id');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (3,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','','Ichlasul Affan (Dummy)','ichlasul.affan@gmail.com');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (4,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','','Falah Prasetyo Waluyo','falah.prasetyo01@ui.ac.id');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (5,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','','Samuel Tupa Febrian','samuel.febrian@gmail.com');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (6,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','administrator','Christopher Samuel','christopher.samuel@ui.ac.id');
INSERT INTO auth_user_passworded (id,password,allowedPermissions,name,email) VALUES (7,'8f8054997ee1c9637d2e7d8ebd607bb9e4e6a90d7be8070119c69a0ae30fc9458f99a8da248aa3f9879e3489edb543556438f113bbcc879c3aed36431727d20d','administrator','Maya','maya.retno21@gmail.com');

INSERT INTO auth_role_comp (id) VALUES (1);
INSERT INTO auth_role_comp (id) VALUES (2);
INSERT INTO auth_role_comp (id) VALUES (3);
INSERT INTO auth_role_comp (id) VALUES (4);

INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES (1,'register','');
INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES (2,'administrator','administrator');
INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES (3,'staff','ModifyAccountImpl');
INSERT INTO auth_role_impl (id,name,allowedPermissions) VALUES (4,'customer','ModifyDonationImpl');

INSERT INTO auth_user_role (id,authRole,authUser) VALUES (1,2,1);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (2,3,2);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (3,4,3);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (4,3,4);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (5,2,5);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (6,2,6);
INSERT INTO auth_user_role (id,authRole,authUser) VALUES (7,3,7);
