package chinookApp.repositories;
import chinookApp.models.Customer;

import java.util.List;

public interface CustomerRepositoryInterface extends CRUDRepositoryInterface<Customer,Integer> {
    List<Customer> findByName(String name);
}
