use Inventory;

CREATE TABLE TableMetadata (
    TableName varchar(128) PRIMARY KEY NOT NULL,
    XMLConfig blob,
    owners varchar(1024),
    description varchar(1024),
    createdTime timestamp default now(),
    lastUpdatedTime timestamp default now() on update now()
)

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