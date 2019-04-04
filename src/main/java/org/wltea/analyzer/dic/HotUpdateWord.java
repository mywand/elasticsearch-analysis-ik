package org.wltea.analyzer.dic;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gs36278 on 2019/4/3.
 */
public class HotUpdateWord {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        getDictFromMysql();
    }

    public static void loadDictFromMysql(DictSegment _MainDict) {
        ArrayList<String> list = getDictFromMysql();
        list.forEach(p -> _MainDict.fillSegment(p.toCharArray()));
    }

    public static ArrayList<String> getDictFromMysql() {
        ArrayList<String> result = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://gs36278:3306/esconfig?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false&zeroDateTimeBehavior=convertToNull",
                    "root",
                    "root");

            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT text FROM ext_dict;");
            while (rs.next()) {
                String text = rs.getString("text");
                System.out.println(text);
                result.add(text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
