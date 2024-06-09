package org.hoey.jdbc;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jdbc测试类
 *
 * @author Joy
 * @date 2020-09-11
 */
public class JdbcTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTest.class);

    private static final String PATH = "src/main/resources/database.properties";

    public static void main(String[] args) {

        try (Connection connection = getConnection();
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(" create table books (message CHAR (20)) ");
            statement.executeUpdate("insert into books values ('Hello World!')");
            try (ResultSet resultSet = statement.executeQuery("select * from books")) {
                if (resultSet.next()) {
                    LOGGER.info(resultSet.getString(1));
                }
            }
        }catch (Exception e){
            LOGGER.error("",e);
        }
    }

    private static Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get(PATH))) {
            prop.load(inputStream);
        }catch (Exception e){
            LOGGER.error("读取配置文件失败");
        }
        String driver = prop.getProperty("org.h2.Driver");
        if (driver != null) {
            System.setProperty("jdbc.drivers", driver);
        }
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String password = prop.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, password);
    }

}
