/*======================Johnny 2013-3-18=========================
==			init			               ==
===============================================================*/

DROP TABLE IF EXISTS `banksalerrest`.`customer_users`;
CREATE TABLE  `banksalerrest`.`customer_users` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `customer_number` varchar(32) default NULL,
  `company_name` varchar(32) default NULL,
  `customer_manager` varchar(32) default NULL,
  `manager_id_card` varchar(32) default NULL,
  `id_card_name` varchar(32) default NULL,
  `id_card_sex` int(1) unsigned default NULL,
  `id_card_birthday` varchar(16) default NULL,
  `id_card_address` varchar(128) default NULL,
  `id_card_num` varchar(20) default NULL,
  `id_card_photo` varchar(32) default NULL,
  `business_license` varchar(32) default NULL,
  `organization_code_certificate` varchar(32) default NULL,
  `tax_certificate` varchar(32) default NULL,
  `credit_card_nuber` varchar(32) default NULL,
  `credit_card_img` varchar(32) default NULL,
  `company_articles` varchar(32) default NULL,
  `report_of_assets` varchar(32) default NULL,
  `shareholder` text,
  `belong_user` int(10) unsigned default NULL,
  `create_user` int(10) unsigned default NULL,
  `create_date` datetime default NULL,
  `modify_user` int(10) unsigned default NULL,
  `modify_date` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;