INSERT INTO `Inventory`.`Org`
(`orgId`,
`orgName`,
`displayName`,
`email`,
`imageIconUrl`,
`creationDatetime`,
`lastUpdatedBy`,
`lastUpdatedTime`)
VALUES
(1,
'test',
'testOrg',
'testOrg@test.com',
'www.test.com',
 NOW(),
'admin',
 NOW());
 
 INSERT INTO `Inventory`.`Permission`
(`permissionId`,
`permissionName`)
VALUES
(1,
'admin');

INSERT INTO `Inventory`.`Role`
(`roleId`,
`roleName`)
VALUES
(1,
'admin');



INSERT INTO `Inventory`.`RolePermission`
(`permissionId`,
`roleId`)
VALUES
(1,
1);

INSERT INTO `Inventory`.`RoleUser`
(`userId`,
`roleId`)
VALUES
(1,
1);

INSERT INTO `Inventory`.`Users`
(`userId`,
`orgId`,
`roleId`,
`email`,
`userName`,
`passwordHash`,
`firstName`,
`middleName`,
`lastName`,
`displayName`,
`imageIconUrl`,
`creationDatetime`,
`lastUpdatedBy`,
`lastUpdatedTime`)
VALUES
(1,
1,
1,
'test@test.com',
'admin',
'test',
'Steven',
'Cat',
'Currry',
'Guard',
'www.nba.com',
NOW(),
'admin',
NOW());




