package chinookApp.repositories;
import chinookApp.models.Customer;
public interface CustomerRepositoryInterface extends CRUDRepositoryInterface<Customer,Integer> {
    Customer findByName(String name);
}
