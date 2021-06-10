create table state (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table state add constraint uk_state_name unique (name);

create table city (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	state_id bigint not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table city add constraint fk_city_state 
foreign key (state_id) references state (id);

alter table city add constraint uk_city_name_state unique (name, state_id);

create table user (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	email varchar(80) not null, 
	password varchar(200) not null, 
	creation_date datetime(6) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table user add constraint uk_user_email unique (email);

create table group_access (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table group_access add constraint uk_grp_access_name unique (name);

create table user_group_access (
	user_id bigint not null, 
	group_access_id bigint not null,
	
	primary key (user_id, group_access_id)
) engine=InnoDB default charset=utf8mb4;

alter table user_group_access add constraint fk_usr_grp_access_user 
foreign key (user_id) references user (id);

alter table user_group_access add constraint fk_usr_grp_access_group_access 
foreign key (group_access_id) references group_access (id);

create table permission (
	id bigint not null auto_increment,
	name varchar(80) not null, 	
	description varchar(80) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table permission add constraint uk_permission_name unique (name);

create table group_access_permission (
	group_access_id bigint not null, 
	permission_id bigint not null,
	
	primary key (group_access_id, permission_id)
) engine=InnoDB default charset=utf8mb4;

alter table group_access_permission add constraint fk_grp_access_per_group_access 
foreign key (group_access_id) references group_access (id);

alter table group_access_permission add constraint fk_grp_access_per_permission 
foreign key (permission_id) references permission (id);

create table cuisine (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table cuisine add constraint uk_cuisine_name unique (name);

create table restaurant (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	freight_rate decimal(19,2) not null, 
	cuisine_id bigint not null, 
	active tinyint(1) not null,
	open tinyint(1) not null,
	address_zip_code varchar(8) not null,
	address_street varchar(80) not null, 
	address_number varchar(10) not null, 
	address_complement varchar(100), 
	address_neighborhood varchar(80) not null, 
	address_city_id bigint not null, 
	creation_date datetime(6) not null, 
	update_date datetime(6), 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table restaurant add constraint fk_restaurant_cuisine 
foreign key (cuisine_id) references cuisine (id);

alter table restaurant add constraint fk_restaurant_city 
foreign key (address_city_id) references city (id);

alter table restaurant add constraint uk_restaurant_name_city unique (name, address_city_id);

create table product (
	id bigint not null auto_increment, 
	name varchar(80) not null, 
	description varchar(80) not null, 
	price decimal(19,2) not null, 
	active bit not null, 
	restaurant_id bigint not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table product add constraint fk_product_restaurant 
foreign key (restaurant_id) references restaurant (id);

alter table product add constraint uk_product_name_restaurant unique (name, restaurant_id);

create table product_photo (
	product_id bigint not null,
	file_name varchar(150) not null,
	description varchar(150),
	content_type varchar(80) not null,
	size int not null,
	
	primary key (product_id)
) engine=InnoDB default charset=utf8mb4;

alter table product_photo add constraint fk_product_photo_product
foreign key (product_id) references product (id);

create table payment_method (
	id bigint not null auto_increment, 
	description varchar(40) not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table payment_method add constraint uk_payment_method_description unique (description);

create table restaurant_payment_method (
	restaurant_id bigint not null, 
	payment_method_id bigint not null,
	
	primary key (restaurant_id, payment_method_id)
) engine=InnoDB default charset=utf8mb4;

alter table restaurant_payment_method add constraint fk_rest_pay_meth_restaurant 
foreign key (restaurant_id) references restaurant (id);

alter table restaurant_payment_method add constraint fk_rest_pay_meth_payment_method 
foreign key (payment_method_id) references payment_method (id);

create table restaurant_responsible_user (
	restaurant_id bigint not null, 
	user_id bigint not null,
	
	primary key (restaurant_id, user_id)
) engine=InnoDB default charset=utf8mb4;

alter table restaurant_responsible_user add constraint fk_rest_resp_user_restaurant
foreign key (restaurant_id) references restaurant (id);

alter table restaurant_responsible_user add constraint fk_rest_resp_user_user
foreign key (user_id) references user (id);

create table purchase_order (
	id bigint not null auto_increment, 
	sub_total decimal(19,2) not null,  
	freight_rate decimal(19,2) not null, 
	amount decimal(19,2) not null,
	address_zip_code varchar(8) not null,
	address_street varchar(80) not null, 
	address_number varchar(10) not null, 
	address_complement varchar(100), 
	address_neighborhood varchar(80) not null, 
	address_city_id bigint not null,  
	order_status varchar(10) not null, 
	payment_method_id bigint not null, 
	restaurant_id bigint not null, 
	user_id bigint not null, 
	creation_date datetime(6) not null, 
	confirmation_date datetime(6), 
	cancellation_date datetime(6), 
	delivery_date datetime(6), 		
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table purchase_order add constraint fk_purchase_order_city 
foreign key (address_city_id) references city (id);

alter table purchase_order add constraint fk_purchase_order_payment_method 
foreign key (payment_method_id) references payment_method (id);

alter table purchase_order add constraint fk_purchase_order_restaurant 
foreign key (restaurant_id) references restaurant (id);

alter table purchase_order add constraint fk_purchase_order_user 
foreign key (user_id) references user (id);

create table purchase_order_item (
	id bigint not null auto_increment, 
	unit_price decimal(19,2) not null, 
	total_price decimal(19,2) not null, 
	quantity integer not null, 
	note varchar(255), 
	purchase_order_id bigint not null, 
	product_id bigint not null, 
	
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

alter table purchase_order_item add constraint fk_purchase_order_item_order 
foreign key (purchase_order_id) references purchase_order (id);

alter table purchase_order_item add constraint fk_purchase_order_item_product 
foreign key (product_id) references product (id);

alter table purchase_order_item add constraint uk_purchase_order_item unique (purchase_order_id, product_id);

create table oauth_client_details (
  client_id varchar(255),
  resource_ids varchar(256),
  client_secret varchar(256),
  scope varchar(256),
  authorized_grant_types varchar(256),
  web_server_redirect_uri varchar(256),
  authorities varchar(256),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information varchar(4096),
  autoapprove varchar(256),
  
  primary key (client_id)
) engine=InnoDB default charset=utf8mb4;

insert into permission (id, name, description) values (1, 'EDIT_CUISINES', 'Allows to edit cuisines');
insert into permission (id, name, description) values (2, 'EDIT_PAYMENT_METHODS', 'Allows to create or edit payment methods');
insert into permission (id, name, description) values (3, 'EDIT_CITIES', 'Allows to create or edit cities');
insert into permission (id, name, description) values (4, 'EDIT_STATES', 'Allows to create or edit states');
insert into permission (id, name, description) values (5, 'QUERY_USERS_GROUPS_PERMISSIONS', 'Allows to query users, groups and permissions');
insert into permission (id, name, description) values (6, 'EDIT_USERS_GROUPS_PERMISSIONS', 'Allows to create or edit users, groups and permissions');
insert into permission (id, name, description) values (7, 'EDIT_RESTAURANTS', 'Allows to create, edit or manage restaurants');
insert into permission (id, name, description) values (8, 'QUERY_PURCHASE_ORDERS', 'Allows to query purchase orders');
insert into permission (id, name, description) values (9, 'MANAGE_PURCHASE_ORDERS', 'Allows to manage purchase orders');
insert into permission (id, name, description) values (10, 'GENERATE_REPORTS', 'Allows to generate reports');

insert into group_access (id, name) values (1, 'Manager'), (2, 'Seller'), (3, 'Secretary'), (4, 'Signer');

insert into group_access_permission (group_access_id, permission_id)
select 1, id from permission;

insert into group_access_permission (group_access_id, permission_id)
select 2, id from permission where name like 'QUERY_%';

insert into group_access_permission (group_access_id, permission_id) values (2, 7);

insert into group_access_permission (group_access_id, permission_id)
select 3, id from permission where name like 'QUERY%';

insert into group_access_permission (group_access_id, permission_id)
select 4, id from permission where name like '%_RESTAURANTS';

insert into user (id, name, email, password, creation_date) values
(1, 'Ewerson dos Reis Carmo', 'ewerson.user@email.com', '$2y$12$NSsM4gEOR7MKogflKR7GMeYugkttjNhAJMvFdHrBLaLp2HzlggP5W', utc_timestamp);

insert into user_group_access (user_id, group_access_id) values (1, 1);

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'fooddeliveryapi-web', null, '$2y$12$oKGMpdDHGxYAGzRDCjgawe41UKz8JS3.osNjbyecygCBDNb84m0Ea',
  'READ,WRITE', 'password', null, null,
  6 * 60 * 60, 60 * 24 * 60 * 60, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret, 
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'fooddeliveryapi-batch', null, '$2y$12$xJBAED1lWusb6VDDl1QG6ume9NFNpxcSloYMLbmgl2Y/xOuHFGH5q',
  'READ,WRITE', 'client_credentials', null, null,
  null, null, null
);

insert into oauth_client_details (
  client_id, resource_ids, client_secret,
  scope, authorized_grant_types, web_server_redirect_uri, authorities,
  access_token_validity, refresh_token_validity, autoapprove
)
values (
  'checktoken', null, '$2a$10$zewmWilycKsDsFZvD3qoGeQ.qI43LycxMPB8qOmnXvyEuqnPrEN7S',
  null, null, null, null,
  null, null, null
);