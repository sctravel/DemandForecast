use Inventory;

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
    ConsensusForecast int
) ENGINE=InnoDB CHARACTER SET=utf8;