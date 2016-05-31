/*****************************************
Creat database and open it
*****************************************/
CREATE DATABASE lsv_ct_st;
use lsv_ct_st;

/*****************************************
table Name: ctMasterTbl
Date created: 11/02/2012
Author: ecaojun
Description: main data for CT/ST
Input variables: no
Output: Table created
*****************************************/
create table ctMasterTbl
(
ctId int AUTO_INCREMENT, 
RN int,
PN int,
tst_storage nvarchar(50),
tst_machine nvarchar(255),
traffic_model nvarchar(255),
ne_link nvarchar(255),
proclog_lv int,
simulator_info nvarchar(255),
interface nvarchar(255),
init_capacity nvarchar(255),
software nvarchar(255),
deviation nvarchar(255),
tester nvarchar(255),
license nvarchar(255),
location varchar(255),
location2 varchar(255),
location3 varchar(255),
location4 varchar(255),
subDir varchar(100),
AbsUploadDir varchar(255),
test_type int,
node_number int,
run_date datetime,
product nvarchar(255),
version nvarchar(255),
historical boolean,
result_pass boolean,

primary key (ctId)
);


/*****************************************
table Name: cso_session_local_all
Date created: 11/02/2012
Author: ecaojun
Description: cso session data
Input variables: no
Output: Table created
*****************************************/

create table cso_session_local_all
(
id int AUTO_INCREMENT,
ctId int, 
sessions int,
cso_sec double,
failure_ratio varchar(50),
nso_cso float4,
ip nvarchar(255),

primary key (id)
);


/*****************************************
table Name: cpu_session_all
Date created: 11/02/2012
Author: ecaojun
Description: Cpu session data
Input variables: no
Output: Table created
*****************************************/
create table cpu_session_all
(
id int AUTO_INCREMENT,
ctId int, 
sessions int,
session_port1 int,
session_port2 int,
cpu_load varchar(50),
free_mem nvarchar(100),
swap nvarchar(100),
cpu_wait int,
cpu_saturation int,
ip nvarchar(255),

primary key (id)
);


/*****************************************
View Name: main_cso
Date created: 11/02/2012
Author: ecaojun
Description: provide a view to joint query for ctmastertbl and cso_session_local_all
Input variables: no
Output: view
*****************************************/

create view main_cso as
select mst.ctId,cso.sessions, cso.cso_sec,mst.traffic_model, 
mst.interface, mst.tst_machine, mst.product, mst.version,mst.run_date, mst.tester,
mst.result_pass, mst.software
from ctmastertbl mst, cso_session_local_all cso 
where mst.ctId=cso.ctId and mst.result_pass!=-1;



/*****************************************
Spoc Name: create_new_master
Date created: 11/02/2012
Author: ecaojun
Description: Insert the master data then return the id
Input variables: features for master data
Output: the new insertion id returned
Samle command: CALL create_new_master(1,1,'EMC CX3-20F','SPARC SAMid T5220','HLR141',NULL,2,'resp',
'CAI','N/A','Gear',NULL, 'ewahong', 'Full license',NULL,NULL,NULL,NULL,NULL,NULL, 0, 
'PGClassic', 'MA63',true, true);

*****************************************/

DELIMITER $$
DROP PROCEDURE IF EXISTS `create_new_master`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `create_new_master`
(IN rn INT, 
IN pn INT,
IN storageType nvarchar(50),
IN tst_machine nvarchar(255),
IN traffic_model nvarchar(255),
IN ne_link nvarchar(255),
IN proclog_lv int,
IN simulator_info nvarchar(255),
IN interface nvarchar(255),
IN init_capacity nvarchar(255),
IN software nvarchar(255),
IN deviation nvarchar(255),
IN tester nvarchar(255),
IN license nvarchar(255),
IN location varchar(255),
IN location2 varchar(255),
IN location3 varchar(255),
IN location4 varchar(255),
IN subDir varchar(100),
IN AbsUploadDir varchar(255),
IN test_type int,
IN product nvarchar(255),
IN version nvarchar(255),
IN historical boolean,
IN result_pass boolean
 )
BEGIN


insert ctmastertbl (RN,PN,tst_storage,tst_machine,traffic_model,ne_link,proclog_lv,
simulator_info,interface,init_capacity,software,deviation,tester,license,location,
location2,location3,location4,subDir,AbsUploadDir,test_type,run_date,product,version,
historical,result_pass) values (rn,pn,storageType,tst_machine,traffic_model,ne_link,
proclog_lv,simulator_info, interface, init_capacity, software, deviation, tester,
license, location, location2, location3, location4, subDir, AbsUploadDir, test_type,
now(), product, version, historical, result_pass);

select last_insert_id();

END $$
DELIMITER ;




