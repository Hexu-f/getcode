package org.hexu.getcode.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexu on 16/10/30.
 */
public class DBColumn {
    private String columnName;
    private String columnType;
    private String comment;
    private String propertyName;
    private String propertyType;
    private String jdbcTyep;
    private String firstMaxPropertyType;

    public String getFirstMaxPropertyType() {
        return firstMaxPropertyType;
    }

    public void setFirstMaxPropertyType(String firstMaxPropertyType) {
        this.firstMaxPropertyType = firstMaxPropertyType;
    }




    public void setColumnName(String columnName) {
        this.columnName = columnName;
        this.propertyName = CommonUtils.toCamelCase(columnName);
    }

    public void setColumnType(String columnType) {
        if (columnType == null || columnType.length() == 0) {
            return;
        }
        this.columnType = columnType;
        switch (columnType) {
            case "int":
            case "smallint":
            case "mediumint":
            case "bigint":
            case "tinyint":
                this.propertyType = "Integer";
                break;
            case "decimal":
                this.propertyType = "BigDecimal";
                break;
            case "varchar":
            case "text":
            case "longtext":
            case "mediumtext":
            case "tinytext":
                this.propertyType = "String";
                break;
            case "date":
            case "datetime":
                this.propertyType = "Date";
                break;
            case "float":
                this.propertyType = "Float";
            default:
                throw new IllegalArgumentException(columnType + " is Unknown");
        }
    }

    public String getJdbcTyep() {
        return jdbcTyep;
    }

    public void setJdbcTyep(String jdbcTyep) {
        switch (jdbcTyep) {
            case "Integer":
                this.jdbcTyep = "INTEGER";
                break;
            case "String":
                this.jdbcTyep = "VARCHAR";
                break;
            case "BigDecimal":
                this.jdbcTyep = "DECIMAL";
                break;
            case "Boolean":
                this.jdbcTyep = "BIT";
                break;
            case "Float":
                this.jdbcTyep = "REAL";
                break;
            case "Double":
                this.jdbcTyep = "DOUBLE";
                break;
            case "Date":
                this.jdbcTyep = "DATE";
                break;
        }
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
