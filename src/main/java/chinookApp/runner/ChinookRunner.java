package chinookApp.runner;

import chinookApp.models.Customer;
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
        /*int result = customerRepository.update(new Customer(63,"tasos63333", "ant", "gr", "123", "1234567890", "tass"));
        System.out.println(result);*/
        //System.out.println(customerRepository.findById(63));
        //System.out.println(customerRepository.findByName("tasos"));
        //System.out.println(customerRepository.getWithLimit(10,3));
        //System.out.println(customerRepository.getCountry());
        System.out.println(customerRepository.getFavouriteGenre(12));
    }

}
