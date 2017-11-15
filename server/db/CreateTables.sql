use Inventory;

CREATE TABLE TableMetadata (
    TableName varchar(128) PRIMARY KEY NOT NULL,
    XMLConfig blob,
    owners varchar(1024),
    description varchar(1024),
    createdTime timestamp default now(),
    lastUpdatedTime timestamp default now() on update now()
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE YumSalesForecast (
    District varchar(128),
    Province varchar(128),
    City varchar(128),
    DistributionCenter varchar(128),
    Item varchar(128),
    Category varchar(128),
    Subcategory varchar(128),
    DetailCategory varchar(128),
    SalesType varchar(128),
    SalesDate date,
    HistoricalSalesNumber int,
    ForecastSalesNumber int,
    MarketingAdjustment int,
    DemandPlannerAdjustment int,
    ConsensusForecast int,
    createdTime timestamp default now(),
    lastUpdatedTime timestamp default now() on update now()
) ENGINE=InnoDB CHARACTER SET=utf8;

CREATE TABLE UserViews (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userViewName varchar(128) NOT NULL,
    userId INT NOT NULL,
    tableName varchar(128),
    details blob ,
    createdTime timestamp default now(),
    lastUpdatedTime timestamp default now() on update now(),
    lastUpdatedBy VARCHAR(15) default 'sysuser',
    UNIQUE(userViewName, userId)
    --FOREIGN KEY (userId) REFERENCES Users(userId),
    --FOREIGN KEY (tableName) REFERENCES TableMetadata(TableName)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE UNIQUE INDEX UserViews_userId on UserViews (userId);

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


CREATE TABLE Users
     (userId INT PRIMARY KEY AUTO_INCREMENT,
     orgId INT,
     roleId INT,
     email VARCHAR(255) NOT NULL, 
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
     ENGINE=InnoDB AUTO_INCREMENT=100000001 DEFAULT CHARSET=utf8;


CREATE TABLE Role(
	roleId INT PRIMARY KEY AUTO_INCREMENT,
	roleName VARCHAR(255) NOT NULL
);


CREATE TABLE Permission(
	permissionId INT PRIMARY KEY AUTO_INCREMENT,
	permissionName VARCHAR(255) NOT NULL
);


CREATE TABLE RolePermission(
	permissionId INT,
	roleId INT,
	FOREIGN KEY (permissionId) REFERENCES Permission(permissionId),
    FOREIGN KEY (roleId) REFERENCES Role(roleId)
);



CREATE TABLE RoleUser(
	userId INT,
	roleId INT,
	FOREIGN KEY (roleId) REFERENCES Role(roleId),
    FOREIGN KEY (userId) REFERENCES Users(userId)
);

