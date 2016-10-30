package org.hexu.getcode.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by hexu on 16/10/30.
 */
public class DBCreate {
    private static Pattern TableNamePattern = Pattern.compile("TABLE\\s+`(.+?)`");
    private static Pattern KeyNamePattern = Pattern.compile("^\\s+PRIMARY\\s+KEY\\s+\\(`(.+?)`\\)");
    private static Pattern EntityNamePattern = Pattern.compile("class:([^\\s,]+)[\\s|,]?.*'$");
    private static Pattern ColumnNamePattern = Pattern.compile("^\\s+`(.*)`");
    private static Pattern TypeNamePattern = Pattern.compile("`\\s+(.+?)(\\(.+?\\))?\\s+");
    private static Pattern CommentPattern = Pattern.compile("COMMENT\\s+'(.+?)'");

    private String tableName;

    private String entity;

    private DBColumn keyColumn;

    private Boolean hasLogicDelete = false;
    private String logicDeleteName = "";

    private static final String[] LogicDeleteColumn = new String[]{"is_del", "is_delete", "delete"};

    private List<DBColumn> dbColumns = new ArrayList<>();

    private DBCreate() {
    }

    /**
     * 解析建表语句
     *
     * @param createSql
     * @return
     */
    public static DBCreate decodeCreate(String createSql) {
        String[] createLines = createSql.split("\n");
        DBCreate dbCreate = new DBCreate();
        for (String line : createLines) {
            String columnName = CommonUtils.getParamFromPattern(ColumnNamePattern, line);   //获取列
            if (columnName != null) {
                DBColumn column = new DBColumn();
                column.setColumnName(columnName);
                if (!dbCreate.getHasLogicDelete() && checkHasLogicDelete(columnName)) {
                    dbCreate.setHasLogicDelete(true);
                    dbCreate.setLogicDeleteName(columnName);
                }
                String typeName = CommonUtils.getParamFromPattern(TypeNamePattern, line);
                column.setColumnType(typeName);
                String comment = CommonUtils.getParamFromPattern(CommentPattern, line);
                column.setComment(comment);
                column.setJdbcTyep(column.getPropertyType());
                String firstMaxPropertyType = column.getPropertyName().substring(0, 1);
                firstMaxPropertyType = firstMaxPropertyType.toUpperCase();
                firstMaxPropertyType = firstMaxPropertyType + column.getPropertyName().substring(1, column.getPropertyName().length());
                column.setFirstMaxPropertyType(firstMaxPropertyType);
                dbCreate.getDbColumns().add(column);
                continue;
            }

            String keyColumnName = CommonUtils.getParamFromPattern(KeyNamePattern, line);   //获取主键
            if (keyColumnName != null) {
                for (DBColumn column : dbCreate.getDbColumns()) {
                    if (column.getColumnName().equals(keyColumnName)) {
                        dbCreate.setKeyColumn(column);
                        break;
                    }
                }
                continue;
            }
            String tableName = CommonUtils.getParamFromPattern(TableNamePattern, line);     //获取表名
            if (tableName != null) {
                dbCreate.setTableName(tableName);
                System.out.println("TableName: " + tableName);
                continue;
            }
            String entity = CommonUtils.getParamFromPattern(EntityNamePattern, line);       //对象名称
            if (entity != null) {
                dbCreate.setEntity(entity);
                System.out.println("Entity: " + entity);
            }
        }
        return dbCreate;
    }

    public String getEntity() {
        if (entity == null) {
            entity = CommonUtils.toCapitalizeCamelCase(tableName);
        }
        return entity;
    }


    public static boolean checkHasLogicDelete(String column) {
        for (String deleteColumn : LogicDeleteColumn) {
            if (deleteColumn.equals(column)) {
                return true;
            }
        }
        return false;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public DBColumn getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(DBColumn keyColumn) {
        this.keyColumn = keyColumn;
    }

    public List<DBColumn> getDbColumns() {
        return dbColumns;
    }

    public void setDbColumns(List<DBColumn> dbColumns) {
        this.dbColumns = dbColumns;
    }

    public Boolean getHasLogicDelete() {
        return hasLogicDelete;
    }

    public void setHasLogicDelete(Boolean hasLogicDelete) {
        this.hasLogicDelete = hasLogicDelete;
    }

    public String getLogicDeleteName() {
        return logicDeleteName;
    }

    public void setLogicDeleteName(String logicDeleteName) {
        this.logicDeleteName = logicDeleteName;
    }
}
