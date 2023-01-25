package chinookApp.repositories;


import chinookApp.models.Customer;
import chinookApp.models.CustomerCountry;
import chinookApp.models.CustomerGenre;
import chinookApp.models.CustomerSpender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.boot.;
@Repository
public class CustomerRepository implements CustomerRepositoryInterface{
    private final String url;
    private final String username;
    private final String password;
    public CustomerRepository(@Value("${spring.datasource.url}")String url,
                         @Value("${spring.datasource.username}") String username,
                         @Value("${spring.datasource.password}") String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public void test() {
        try(Connection conn = DriverManager.getConnection(url, username,password);) {
            System.out.println("Connected to Postgres...!!!!!!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer ORDER BY customer_id";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer where customer_id = ?";
        Customer customer = null;
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                customer = new Customer(

                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public List<Customer> findByName(String name) {
        String sql = "SELECT * FROM customer " +
                "WHERE first_name " +
                "LIKE ? "; //be careful, name is CASE SENSITIVE

        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,"%".concat(name).concat("%"));
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return customers;
    }
    @Override
    public int insert(Customer customer) {
        int result = 0;
        String sql="INSERT INTO customer (first_name, last_name, country, postal_code, phone, email)"+
                "VALUES(?,?,?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            //does not need to set id because it is auto incremental Primary Key
            statement.setString(1,customer.first_name());
            statement.setString(2,customer.last_name());
            statement.setString(3,customer.country());
            statement.setString(4,customer.postal_code());
            statement.setString(5,customer.phone());
            statement.setString(6,customer.email());
            result = statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int update(Customer customer) {
        int result = 0;
        String sql = "UPDATE customer " +
                "SET first_name = ?, last_name= ?, country= ?, postal_code= ?, phone= ?, email= ? " +
                "WHERE customer_id = ?";
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,customer.first_name());
            statement.setString(2,customer.last_name());
            statement.setString(3,customer.country());
            statement.setString(4,customer.postal_code());
            statement.setString(5,customer.phone());
            statement.setString(6,customer.email());
            statement.setInt(7,customer.id());
            result = statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param limit     Number of rows to display
     * @param offset    Start from a specific row
     * @return          Returns a list of record Customers with the specified number of rows
     * @throws SQLException
     * Thrown if connection with database is lost or the query is wrong
     */
    public List<Customer> getWithLimit(int limit, int offset){
        String sql = "SELECT * FROM customer ORDER BY customer LIMIT ? OFFSET ?";
        List<Customer> customers = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,limit);
            statement.setInt(2,offset);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                Customer customer = new Customer(
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    /**
     * This method returns the country with the most customers. If two or more countries have the same maximum number of customers, this method returns them as well.
     * @return Returns a list of record CustomerCountry
     * @throws SQLException
     * Thrown if connection with database is lost or the query is wrong
     */
    public List<CustomerCountry> getCountry(){
        //This query is long because there may exist more countries with the same maximum number of customers
        String sql = "SELECT COUNT(*) AS customers_per_country, country FROM customer " +
                "GROUP BY country HAVING COUNT(*) = " +
                "(SELECT MAX(customers_per_country) AS max_customers FROM " +
                "(SELECT COUNT(*) AS customers_per_country, country FROM customer " +
                "GROUP BY country" +
                ") AS countriesRightTable)";
        List<CustomerCountry> countries = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                CustomerCountry country = new CustomerCountry(
                        result.getInt("customers_per_country"),
                        result.getString("country")
                );
                countries.add(country);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return countries;
    }

    /**
     * This method returns a list of the most favourite customer's genres. All genres with same popularity(number of tracks) are in the returned List.
     * @param id    An integer which is the id of record Customer.
     * @return      Returns a list of record CustomerGenre.
     * @throws SQLException
     * Thrown if connection with database is lost or the query is wrong
     */
    public List<CustomerGenre> getFavouriteGenre(int id){
        //SQL does not allow to select the method count() inside of max() method, so this is why we wrote this railway query
        String sql = "SELECT genre_name FROM " +
                "(SELECT COUNT(*) AS tracks, G.genre_id AS g_id, I.customer_id, G.name AS genre_name FROM " +
                "invoice AS I, invoice_line AS IL, track AS T, genre AS G WHERE IL.track_id=T.track_id AND G.genre_id=T.genre_id AND I.invoice_id=IL.invoice_id " +
                "GROUP BY G.genre_id,I.customer_id " +
                "HAVING customer_id = ? " +
                ")AS GENRES_PER_CUSTOMER_LEFT WHERE tracks = " +
                "(SELECT MAX(GENRES_PER_CUSTOMER_RIGHT.tracks) FROM (SELECT COUNT(*) AS tracks, G.genre_id AS g_id, I.customer_id, G.name AS genre_name FROM " +
                "invoice AS I, invoice_line AS IL, track AS T, genre AS G WHERE IL.track_id=T.track_id AND G.genre_id=T.genre_id AND I.invoice_id=IL.invoice_id " +
                "GROUP BY G.genre_id,I.customer_id " +
                "HAVING customer_id = ?) AS GENRES_PER_CUSTOMER_RIGHT)";
        List<CustomerGenre> genres = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            statement.setInt(2,id);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                CustomerGenre customerGenre = new CustomerGenre(
                        result.getString("genre_name")
                );
                genres.add(customerGenre);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return  genres;
    }

    /**
     * This method returns a customer who has spent the most. In case of two or more customers who have same spends,  are in the returned list.
     * @return      Returns a list of record CustomerSpender
     * @throws SQLException
     * Thrown if connection with database is lost or the query is wrong 
     */
    public List<CustomerSpender> getHighestSpender(){
        String sql = "SELECT all_total, rightTable.customer_id, rightTable.first_name, rightTable.last_name, rightTable.country, rightTable.postal_code, rightTable.phone, rightTable.email " +
                "FROM " +
                "(SELECT SUM(total) as all_total,I.customer_id AS c_id FROM invoice AS I GROUP BY I.customer_id HAVING SUM(total) " +
                "=(SELECT MAX(all_total) FROM " +
                "(SELECT SUM(total) AS all_total,customer_id FROM invoice GROUP BY customer_id) AS spendersTable) " +
                ") AS leftTable, customer AS rightTable WHERE leftTable.c_id=rightTable.customer_id";

        List<CustomerSpender> customerSpenders = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url,username,password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                CustomerSpender customerSpender = new CustomerSpender(
                        result.getDouble("all_total"),
                        result.getInt("customer_id"),
                        result.getString("first_name"),
                        result.getString("last_name"),
                        result.getString("country"),
                        result.getString("postal_code"),
                        result.getString("phone"),
                        result.getString("email")
                );
                customerSpenders.add(customerSpender);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customerSpenders;
    }
    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }


}
