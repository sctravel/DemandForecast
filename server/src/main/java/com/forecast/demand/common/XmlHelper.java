package com.forecast.demand.common;

import com.forecast.demand.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

public class XmlHelper {

    public static boolean addNewTableConfig(String xmlFilePath) {
        String sql = "insert into TableMetadata (tablename, xmlconfig, owners, description) values (?, ?, ?, ?)";
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(xmlFilePath)), "UTF-8");
            Table table = readTableFromXmlConfig(xmlContent);
            DBHelper.runQuery(sql, new String[]{table.getName(), xmlContent, table.getOwners(), table.getDescription()},
                    new ColumnType[]{ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updateTableConfig(String xmlFilePath) {
        String sql = "update TableMetadata set xmlconfig= ?, owners = ? , description = ? where tablename = ?";
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(xmlFilePath)), "UTF-8");
            Table table = readTableFromXmlConfig(xmlContent);
            DBHelper.runQuery(sql, new String[]{xmlContent, table.getOwners(), table.getDescription(), table.getName()},
                    new ColumnType[]{ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING});
            System.out.println("Updated: " + xmlFilePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static Table readTableFromXmlConfigFile(String xmlFilePath) {
        Table table = null;
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(xmlFilePath)), "UTF-8");
            table = readTableFromXmlConfig(xmlContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return table;
    }

    public static Table readTableFromXmlConfig(String xmlContent) {
        String owners = null;
        String tableName = null;
        String description = null;
        List<Column> columns = new ArrayList<>();
        List<Dimension> dimensions = new ArrayList<>();
        List<String> measureColumnNames = new ArrayList<>();

        try {

            //File fXmlFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));
            Document doc = dBuilder.parse(inputStream);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            Element table = doc.getDocumentElement();

            owners = table.getElementsByTagName("owners").item(0).getTextContent();
            tableName = table.getElementsByTagName("name").item(0).getTextContent();
            description = table.getElementsByTagName("description").item(0).getTextContent();

            //System.out.println("Root element :" + table.getNodeName());
            //System.out.println("Name: " + tableName);
            //System.out.println("Owners: " + owners + "\n");
            //System.out.println("----------------------------");

            columns = getColumnsFromRoot(table);
            dimensions = getDimensionsFromRoot(table);
            for(Column c : columns) {
                if(c.getIsMeasure()) measureColumnNames.add(c.getName());
            }


        } catch (Exception e) {
            e.printStackTrace();
            //throw e;
            return null;
        }

        return new Table(tableName, owners, columns, dimensions, measureColumnNames, description);
    }


    private static List<Dimension> getDimensionsFromRoot(Element root) {
        List<Dimension> list = new ArrayList<>();
        Node dimensionsNode = root.getElementsByTagName("dimensions").item(0);
        NodeList dimensionList = dimensionsNode.getChildNodes();
        for (int temp = 0; temp < dimensionList.getLength(); temp++) {

            Node nNode = dimensionList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                List<String> levels = new ArrayList<>();
                String dimensionName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String dimensionDisplayName = eElement.getElementsByTagName("displayName").item(0).getTextContent();
                Node levelsNode = eElement.getElementsByTagName("levels").item(0);
                NodeList levelList = levelsNode.getChildNodes();
                for (int i = 0; i < levelList.getLength(); i++) {
                    Node lNode = levelList.item(i);
                    if (lNode.getNodeType() == Node.ELEMENT_NODE) {
                        String level = lNode.getTextContent();
                        levels.add(level);
                        //System.out.println("Level: " + level);
                    }
                }
                Dimension dimension = new Dimension(dimensionName, dimensionDisplayName, levels);
                list.add(dimension);
            }
        }
        return list;
    }

    private static List<Column> getColumnsFromRoot(Element root) {

        List<Column> list = new ArrayList<>();
        Node columnsNode = root.getElementsByTagName("columns").item(0);
        NodeList columnList = columnsNode.getChildNodes();
        for (int temp = 0; temp < columnList.getLength(); temp++) {

            Node nNode = columnList.item(temp);

            //System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                String columnName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String columnTypeString = eElement.getElementsByTagName("type").item(0).getTextContent();
                System.out.println("columnName : " + columnTypeString);
                ColumnType columnType = StringUtil.searchEnum(ColumnType.class, columnTypeString);

                String isNullableString = eElement.getElementsByTagName("isNullable").getLength()==0?null:eElement.getElementsByTagName("isNullable").item(0).getTextContent();
                boolean isNullable = (isNullableString==null||isNullableString.trim().isEmpty()) ? false : Boolean.valueOf(isNullableString);
                String displayName = eElement.getElementsByTagName("displayName").item(0).getTextContent();
                String isMeasureString = eElement.getElementsByTagName("isMeasure").getLength()==0?null:eElement.getElementsByTagName("isMeasure").item(0).getTextContent();
                String isEditableString = eElement.getElementsByTagName("isEditable").getLength()==0?null:eElement.getElementsByTagName("isEditable").item(0).getTextContent();
                String aggregationTypeString = eElement.getElementsByTagName("aggregationType").getLength()==0?null:eElement.getElementsByTagName("aggregationType").item(0).getTextContent();
                boolean isMeasure = (isMeasureString==null||isMeasureString.trim().isEmpty()) ? false : Boolean.valueOf(isMeasureString);
                boolean isEditable = (isEditableString==null||isEditableString.trim().isEmpty()) ? false : Boolean.valueOf(isEditableString);
                AggregationType aggregationType = (aggregationTypeString==null||aggregationTypeString.trim().isEmpty()) ? AggregationType.SUM : StringUtil.searchEnum(AggregationType.class, aggregationTypeString);

                String clientExpression = eElement.getElementsByTagName("clientExpression").getLength()==0?null:eElement.getElementsByTagName("clientExpression").item(0).getTextContent();

                //System.out.println("columnType : " + columnType);
                //System.out.println("displayName : " + displayName);
                //System.out.println("isMeasure : " + isMeasure);
                //System.out.println("isEditable: " + isEditable);
                Column column = new Column(columnName, columnType, isNullable, displayName, isMeasure, isEditable, aggregationType, clientExpression);
                list.add(column);
            }
        }

        return list;
    }
}
