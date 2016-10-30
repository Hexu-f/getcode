package org.hexu.getcode.generator;

import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hexu on 16/10/30.
 */
public class CommonUtils {
    private static Properties props = new Properties();
    private static final String propsPath = getClassResources(CommonUtils.class, "/conf.properties");

    static {
        try {
            if (propsPath != null) {
                props.load(new FileInputStream(propsPath));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setProperty(String key, String value) {
        props.setProperty(key, value);
        try {
            props.store(new FileOutputStream(propsPath), "save or update " + key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getParamFromPattern(Pattern pattern, String str) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    /**
     * @param @param  s
     * @param @return 设定文件  toLowerCaseFirstOne("HelloWorld")="helloWorld"
     * @return String 返回类型
     * @throws
     * @Title: toLowerCaseFirstOne
     * @Description: TODO(首字母转小写)
     */
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


    private static String splitString(String str, String param) {
        String result = str;
        if (str.contains(param)) {
            int start = str.indexOf(param);
            result = str.substring(0, start);
        }
        return result;
    }

    /*
     * 获取classpath1
     */
    public static String getClasspath() {
        String path = null;
        try {
            path = URLDecoder.decode(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")), "utf-8").replaceAll("file:/", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    /*
     * 获取classpath2
     */
    public static String getClassResources() {
        String path = null;
        try {
            path = URLDecoder.decode(String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")), "utf-8").replaceAll("file:/", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    public static String getClassResources(Class cls, String mPath) {
        String path;
        try {
            path = URLDecoder.decode(String.valueOf(cls.getResource(mPath)), "utf-8").replaceAll("file:/", "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        if (path.indexOf(":") != 1) {
            path = File.separator + path;
        }
        return path;
    }

    public static void copyDirctoryFromJar() {
        try {
            JarFile jarFile = new JarFile(getClassResources(CommonUtils.class, "../"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }

        s = s.toLowerCase();

        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    /**
     * 驼峰命名法工具
     *
     * @return toCamelCase("hello_world") == "helloWorld"
     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
     * toUnderScoreCase("helloWorld") = "hello_world"
     */
    private static final char SEPARATOR = '_';

    public static String toUnderScoreCase(String s) {
        if (s == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            boolean nextUpperCase = true;

            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }

            if ((i > 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }
}
