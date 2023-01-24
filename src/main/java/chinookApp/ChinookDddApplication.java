package chinookApp;

import chinookApp.access.ChinookDBConn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChinookDddApplication {
    @Autowired
    private final ChinookDBConn chinookDBConn;

    public ChinookDddApplication(ChinookDBConn chinookDBConn) {
        this.chinookDBConn = chinookDBConn;
    }

    public static void main(String[] args) {
        SpringApplication.run(ChinookDddApplication.class, args);
    }


}
