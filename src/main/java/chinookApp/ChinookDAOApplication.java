package chinookApp;

import chinookApp.access.ChinookDBConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChinookDAOApplication {
    @Autowired
    private final ChinookDBConn chinookDBConn;

    public ChinookDAOApplication(ChinookDBConn chinookDBConn) {
        this.chinookDBConn = chinookDBConn;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChinookDAOApplication.class, args);
    }


}
