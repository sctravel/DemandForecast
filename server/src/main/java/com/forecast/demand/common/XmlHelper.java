package com.forecast.demand.common;

import com.forecast.demand.model.Column;
import com.forecast.demand.model.ColumnType;
import com.forecast.demand.model.Dimension;
import com.forecast.demand.model.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.w3c.dom.*;

public class XmlHelper {

    public static boolean addNewTableConfig(String filepath) {
        String sql = "insert into TableMetadata (tablename, xmlconfig, owners, description) values (?, ?, ?, ?)";
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(filepath)));
            Table table = readTableFromXmlConfig(filepath);
            DBHelper.runQuery(sql, new String[]{table.getName(), xmlContent, table.getOwners(), table.getDescription()},
                    new ColumnType[]{ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean updateTableConfig(String filepath) {
        String sql = "update TableMetadata set xmlconfig= ?, owners = ? , description = ? where tablename = ?";
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(filepath)));
            Table table = readTableFromXmlConfig(filepath);
            DBHelper.runQuery(sql, new String[]{xmlContent, table.getOwners(), table.getDescription(), table.getName()},
                    new ColumnType[]{ColumnType.STRING, ColumnType.STRING, ColumnType.STRING, ColumnType.STRING});
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
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
            InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes());

            Document doc = dBuilder.parse(inputStream);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();
            Element table = doc.getDocumentElement();

            owners = table.getElementsByTagName("owners").item(0).getTextContent();
            tableName = table.getElementsByTagName("name").item(0).getTextContent();
            description = table.getElementsByTagName("description").item(0).getTextContent();

            System.out.println("Root element :" + table.getNodeName());
            System.out.println("Name: " + tableName);
            System.out.println("Owners: " + owners + "\n");
            System.out.println("----------------------------");

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
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                List<String> levels = new ArrayList<>();
                String dimensionName = eElement.getElementsByTagName("name").item(0).getTextContent();
                Node levelsNode = eElement.getElementsByTagName("levels").item(0);
                NodeList levelList = levelsNode.getChildNodes();
                for (int i = 0; i < levelList.getLength(); i++) {
                    Node lNode = levelList.item(i);
                    if (lNode.getNodeType() == Node.ELEMENT_NODE) {
                        String level = lNode.getTextContent();
                        levels.add(level);
                        System.out.println("Level: " + level);
                    }
                }
                Dimension dimension = new Dimension(dimensionName, levels);
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

            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                String columnName = eElement.getElementsByTagName("name").item(0).getTextContent();
                String columnType = eElement.getElementsByTagName("type").item(0).getTextContent();
                String displayName = eElement.getElementsByTagName("displayName").item(0).getTextContent();
                String isMeasureString = eElement.getElementsByTagName("isMeasure").getLength()==0?null:eElement.getElementsByTagName("isMeasure").item(0).getTextContent();
                String isEditableString = eElement.getElementsByTagName("isEditable").getLength()==0?null:eElement.getElementsByTagName("isEditable").item(0).getTextContent();
                boolean isMeasure = (isMeasureString==null||isMeasureString.trim().isEmpty()) ? false : Boolean.valueOf(isMeasureString);
                boolean isEditable = (isEditableString==null||isEditableString.trim().isEmpty()) ? false : Boolean.valueOf(isEditableString);

                System.out.println("columnName : " + columnName);
                System.out.println("columnType : " + columnType);
                System.out.println("displayName : " + displayName);
                System.out.println("isMeasure : " + isMeasure);
                System.out.println("isEditable: " + isEditable);

                Column column = new Column(columnName, columnType, displayName, isMeasure, isEditable);
                list.add(column);
            }
        }

        return list;
    }
}
