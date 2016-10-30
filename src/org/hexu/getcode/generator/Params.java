package org.hexu.getcode.generator;

import java.util.List;

/**
 * Created by hexu on 16/10/30.
 */
public class Params {
    private String daoPath;
    private String entityPath;
    private String xmlPath;
    private String entityPackageName;
    private String keyProperty;
    private List<DBColumn> columns;
    private DBColumn keyColumn;
    private String daoPackageName;
    private String entityName;
    private String tableName;
    private String nowDate;
    private String vmPath;
    private String columnsString;
    private Integer inputDate;

    public Integer getInputDate() {
        return inputDate;
    }

    public void setInputDate(Integer inputDate) {
        this.inputDate = inputDate;
    }

    public DBColumn getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(DBColumn keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getColumnsString() {
        return columnsString;
    }

    public void setColumnsString(String columnsString) {
        this.columnsString = columnsString;
    }

    public String getDaoPath() {
        return daoPath;
    }

    public void setDaoPath(String daoPath) {
        this.daoPath = daoPath;
    }

    public String getEntityPath() {
        return entityPath;
    }

    public void setEntityPath(String entityPath) {
        this.entityPath = entityPath;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getEntityPackageName() {
        return entityPackageName;
    }

    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public void setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
    }

    public List<DBColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<DBColumn> columns) {
        this.columns = columns;
    }

    public String getDaoPackageName() {
        return daoPackageName;
    }

    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getVmPath() {
        return vmPath;
    }

    public void setVmPath(String vmPath) {
        this.vmPath = vmPath;
    }
}
