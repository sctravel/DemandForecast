# DemandForecast

## Requirements

## Java APIs

### Metadata
- List<Table> getTables()
- List<Column> getAllColumns(String tableName)
- List<Dimension> getDimensions(String tableName)
- List<Measure> getMeasures(String tableName)

### DataValue
- List<String> getDistinctValues(String tableName, Dimension dim, Predicate filter)
- List<Record> query(String tableName, List<Dimension> dimList, List<Measure> measureList, Predicate filter)
- boolean adjustValue(String tableName, List<Dimension> dimList, List<Change> changeList, Predicate filter)

## REST APIs