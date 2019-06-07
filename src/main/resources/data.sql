DROP TABLE IF EXISTS DBConnectionDetails;
 
CREATE TABLE DBConnectionDetails (
	id int auto_increment,
  	 name varchar(40),
	 hostname varchar(40),
	 port varchar(5),
	 databaseName varchar(40),
	 username varchar(40),
	 password varchar(40)
);
 