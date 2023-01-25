package chinookApp.repositories;
import chinookApp.models.Customer;
import chinookApp.models.CustomerCountry;

import java.util.List;

public interface CustomerRepositoryInterface extends CRUDRepositoryInterface<Customer,Integer> {
    List<Customer> findByName(String name);
    List<Customer> getWithLimit(int limit, int offset);

    List<CustomerCountry> getCountry();
}
