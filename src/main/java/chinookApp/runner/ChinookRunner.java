package chinookApp.runner;

import chinookApp.repositories.CustomerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ChinookRunner implements ApplicationRunner {
    final CustomerRepository chinookDAO;

    public ChinookRunner(CustomerRepository chinookDAO) {
        this.chinookDAO = chinookDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
