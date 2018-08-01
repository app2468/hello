/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018/4/12 9：51                           */
/*==============================================================*/

/**
*Author: itcast
*Date:2018/4/12 9：51 
*Desc:创建投诉受理管理数据库表
*/

drop table if exists complain;

/*==============================================================*/
/* Table: complain                                              */
/*==============================================================*/
create table complain
(
   comp_id              varchar(32) not null,
   comp_company         varchar(100),
   comp_name            varchar(20),
   comp_mobile          varchar(20),
   comp_time            datetime,
   to_comp_dept         varchar(100),
   to_comp_name         varchar(20),
   comp_title           varchar(200),
   comp_content         text,
   is_NM                bool,
   state                varchar(1),
   primary key (comp_id)
);

drop table if exists complain_reply;

/*==============================================================*/
/* Table: complain_reply                                        */
/*==============================================================*/
create table complain_reply
(
   reply_id             varchar(32) not null,
   comp_id              varchar(32) not null,
   reply_time           datetime,
   reply_dept           varchar(100),
   replyer              varchar(20),
   content              varchar(300),
   primary key (reply_id)
);

alter table complain_reply add constraint FK_r_comp_reply foreign key (comp_id)
      references complain (comp_id) on delete restrict on update restrict;

      
      
/**
*Author: itcast
*Date:2015-08-21
*Desc:创建月份表
*/
CREATE TABLE `tmonth` (
  `imonth` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`imonth`)
)
      
      
      
      
      
      
      
      
      
      