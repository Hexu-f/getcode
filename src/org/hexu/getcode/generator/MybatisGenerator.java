package org.hexu.getcode.generator;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.log.NullLogChute;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by hexu on 16/10/29.
 */
public class MybatisGenerator {
    private static MybatisGenerator _instance;

    private MybatisGenerator() {
    }

    public static MybatisGenerator getInstance() {
        if (_instance == null) {
            synchronized (MybatisGenerator.class) {
                if (_instance == null) {
                    _instance = new MybatisGenerator();
                }
            }
        }
        return _instance;
    }

    public Integer getCode(String drive, String ipPort, String dbName, String userId, String password, String tableName, String entityName, String daoPath, String entityPath, String xmlPath, String daoPackageName, String entityPackageName) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        String url = "jdbc:mysql://" + ipPort + "/" + dbName;
        try {
            connection = DriverManager.getConnection(url, userId, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SHOW CREATE TABLE `" + tableName + "`;");
            resultSet.last();
            String createSql = resultSet.getString(2);
            DBCreate dbCreate = DBCreate.decodeCreate(createSql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Params params = new Params();
            params.setDaoPath(daoPath);
            params.setEntityPath(entityPath);
            params.setXmlPath(xmlPath);
            params.setEntityPackageName(entityPackageName);
            params.setKeyProperty(dbCreate.getKeyColumn().getPropertyName());
            params.setColumns(dbCreate.getDbColumns());
            params.setDaoPackageName(daoPackageName);
            params.setEntityName(entityName);
            params.setTableName(tableName);
            params.setNowDate(sdf.format(new Date()));
            params.setKeyColumn(dbCreate.getKeyColumn());
            params.setVmPath(CommonUtils.getClassResources(this.getClass(), "/vm/"));
            params.setInputDate(0);
            String cString = "";
            for (DBColumn c : dbCreate.getDbColumns()) {
                if (c.getPropertyType().equals("Date")) {
                    params.setInputDate(1);
                }
                cString = cString + c.getColumnName() + ",";
            }
            params.setColumnsString(cString.substring(0, cString.length() - 1));
            Integer re = getCodeByVoleCity(params);
            return re;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    private Integer getCodeByVoleCity(Params params) {
        Integer xml = createXml(params);
        Integer entity = createEntity(params);
        Integer dao = createDao(params);
        Integer daoimpl = createImplDao(params);
        if (xml != 1 || dao != 1 || entity != 1 || daoimpl != 1) {
            return 0;
        }
        return 1;
    }

    private Integer createXml(Params params) {
//        String filename = daoPath + "/intf/" + className + "Dao" + ".java";
        String filename = params.getXmlPath() + params.getEntityName() + "Mapper.xml";
        try {
            File dirf = new File(params.getDaoPath() + "impl");
            if (!dirf.exists()) {
                dirf.mkdirs();
            }
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            String fileDir = MybatisGenerator.class.getResource("/vm").getPath();
            fileDir = fileDir.substring(1, fileDir.length());

            RuntimeInstance ri = new RuntimeInstance();
            if (!ri.isInitialized()) {
                ri.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
                ri.init();
            }
            VelocityEngine ve = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, fileDir);
            properties.setProperty("input.encoding", "utf-8");
            properties.setProperty("output.encoding", "utf-8");
            properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            ve.init(properties);
            VelocityContext context = new VelocityContext();
            context.put("params", params);
            Template template = ve.getTemplate("/mapper.vm");
            template.setEncoding("UTF-8");
            template.merge(context, writer);
            writer.flush();
            write.close();
            writer.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Integer createEntity(Params params) {
        String filename = params.getEntityPath() + params.getEntityName() + ".java";
        try {
            File dirf = new File(params.getDaoPath() + "impl");
            if (!dirf.exists()) {
                dirf.mkdirs();
            }
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            String fileDir = MybatisGenerator.class.getResource("/vm").getPath();
            VelocityEngine ve = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, fileDir);
            properties.setProperty("input.encoding", "utf-8");
            properties.setProperty("output.encoding", "utf-8");
            properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            ve.init(properties);
            VelocityContext context = new VelocityContext();
            context.put("params", params);
            Template template = ve.getTemplate("/entity.vm");
            template.setEncoding("UTF-8");
            template.merge(context, writer);
            writer.flush();
            write.close();
            writer.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Integer createDao(Params params) {
        String filename = params.getDaoPath() + params.getEntityName() + "Dao.java";
        try {
            File dirf = new File(params.getDaoPath() + "impl");
            if (!dirf.exists()) {
                dirf.mkdirs();
            }
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            String fileDir = MybatisGenerator.class.getResource("/vm").getPath();
            VelocityEngine ve = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, fileDir);
            properties.setProperty("input.encoding", "utf-8");
            properties.setProperty("output.encoding", "utf-8");
            properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            ve.init(properties);
            VelocityContext context = new VelocityContext();
            context.put("params", params);
            Template template = ve.getTemplate("/daoIntf.vm");
            template.setEncoding("UTF-8");
            template.merge(context, writer);
            writer.flush();
            write.close();
            writer.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private Integer createImplDao(Params params) {
        String filename = params.getDaoPath() + "impl/" + params.getEntityName() + "DaoImpl.java";
        try {
            File dirf = new File(params.getDaoPath() + "impl");
            if (!dirf.exists()) {
                dirf.mkdirs();
            }
            File f = new File(filename);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            String fileDir = MybatisGenerator.class.getResource("/vm").getPath();
            VelocityEngine ve = new VelocityEngine();
            Properties properties = new Properties();
            properties.setProperty(ve.FILE_RESOURCE_LOADER_PATH, fileDir);
            properties.setProperty("input.encoding", "utf-8");
            properties.setProperty("output.encoding", "utf-8");
            properties.setProperty(VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            ve.init(properties);
            VelocityContext context = new VelocityContext();
            context.put("params", params);
            Template template = ve.getTemplate("/daoImpl.vm");
            template.setEncoding("UTF-8");
            template.merge(context, writer);
            writer.flush();
            write.close();
            writer.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
