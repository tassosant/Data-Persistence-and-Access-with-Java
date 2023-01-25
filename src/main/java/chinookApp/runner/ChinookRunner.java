package chinookApp.runner;

import chinookApp.repositories.CustomerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChinookRunner implements ApplicationRunner {
    final CustomerRepository customerRepository;

    public ChinookRunner(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(customerRepository.findById(12));
    }

}
