CREATE TABLE Org
     (orgId INT PRIMARY KEY AUTO_INCREMENT,
     orgName VARCHAR(20) NOT NULL,
     displayName VARCHAR(63) , 
	 email VARCHAR(255) NOT NULL, 
     imageIconUrl VARCHAR(63), 
     creationDatetime TIMESTAMP default now(), 
     lastUpdatedBy VARCHAR(15) default 'sysuser',
     lastUpdatedTime TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
    UNIQUE(orgId) 
     )
     ENGINE=InnoDB AUTO_INCREMENT=100000001 DEFAULT CHARSET=utf8;


DROP TABLE Users;
CREATE TABLE Users
     (userId INT PRIMARY KEY AUTO_INCREMENT,
     orgId INT,
     roleId INT,
     email VARCHAR(255) NOT NULL, 
     userName VARCHAR(255) NOT NULL, 
     passwordHash VARCHAR(63) NOT NULL,
     firstName VARCHAR(20) NOT NULL,
     middleName VARCHAR(20) , 
	 lastName VARCHAR(20) NOT NULL, 
     displayName VARCHAR(63) , 
     imageIconUrl VARCHAR(63), 
     creationDatetime TIMESTAMP default now(), 
     lastUpdatedBy VARCHAR(15) default 'sysuser',
     lastUpdatedTime TIMESTAMP  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, 
	 FOREIGN KEY (orgId) REFERENCES Org(orgId),
    UNIQUE(userId) 
     )
     ENGINE=InnoDB AUTO_INCREMENT=100000001 DEFAULT CHARSET=utf8

DROP TABLE if exists Role;
CREATE TABLE Role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
	roleName VARCHAR(255) NOT NULL
);

DROP TABLE if exists Permission;
CREATE TABLE Permission(
	permissionId INT PRIMARY KEY AUTO_INCREMENT,
	permissionName VARCHAR(255) NOT NULL
);

DROP TABLE if exists RolePermission;
CREATE TABLE RolePermission(
	permissionId INT,
	roleId INT,
	FOREIGN KEY (permissionId) REFERENCES Permission(permissionId),
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);


DROP TABLE if exists RoleUser;
CREATE TABLE RoleUser(
	userId INT,
	roleId INT,
	FOREIGN KEY (roleId) REFERENCES Role(roleId),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);


