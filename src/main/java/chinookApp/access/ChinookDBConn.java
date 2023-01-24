package chinookApp.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@Component
public class ChinookDBConn {
    private final String url;
    private final String username;
    private final String password;

    public ChinookDBConn(@Value("${spring.datasource.url}")String url,
                         @Value("${spring.datasource.username}") String username,
                         @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void testConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            //conn.close();
            System.out.println("Conn..ected");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
