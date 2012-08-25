CREATE TABLE `cis726`.`DeviceReading` (`id` INT NOT NULL AUTO_INCREMENT ,`deviceId` INT NOT NULL ,`value` VARCHAR( 1000 ) NOT NULL ,`timestamp` TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ,PRIMARY KEY ( `id` )) ENGINE = MYISAM;

CREATE TABLE `cis726`.`RegisteredDevice` (`id` INT NOT NULL AUTO_INCREMENT ,`deviceId` INT NOT NULL , `roomId` INT NOT NULL, `deviceType` VARCHAR( 100 ) NOT NULL, `topic` VARCHAR( 100 ) NOT NULL, PRIMARY KEY ( `id` )) ENGINE = MYISAM;

CREATE TABLE `cis726`.`Warnings` (`id` INT NOT NULL AUTO_INCREMENT, `device` int(11) NOT NULL, `type` varchar(20) NOT NULL, `value` float NOT NULL, `reading` float NOT NULL, `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,PRIMARY KEY ( `id` )) ENGINE = MYISAM;

CREATE TABLE `patientprofile` (
  `Patient_Id` varchar(100) NOT NULL,
  `Patient_Name` char(100) NOT NULL,
  `Age` int(11) NOT NULL,
  `Weight` int(11) NOT NULL,
  `Medical_History` varchar(2000) NOT NULL,
  `Allergies` varchar(2000) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Patient_Id`)
) ENGINE=MyISAM;



CREATE TABLE `patientroomdetails` (
  `Patient_Id` varchar(100) NOT NULL,
  `Room_Id` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`Patient_Id`,`Room_Id`),
  UNIQUE KEY `Patient_Id` (`Patient_Id`),
  UNIQUE KEY `Room_Id` (`Room_Id`)
) ENGINE=MyISAM ;


insert into cis726.patientprofile
 	values('P3','Yakub','23','300','fever#Headache#Swine','dust#Ayur#polluted air',NOW());

insert into cis726.patientprofile
 	values('P4','Roshan','25','200','fever#Nasal#Jaundice','Hot water# dust',NOW());

insert into cis726.patientroomdetails
 	values('P4',100,NOW());

insert into cis726.patientroomdetails
 	values('P3',200,NOW());

commit;


