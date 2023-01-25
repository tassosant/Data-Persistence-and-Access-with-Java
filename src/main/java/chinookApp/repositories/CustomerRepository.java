package chinookApp.repositories;


import chinookApp.models.Customer;
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
        String sql = "SELECT * FROM customer";
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
    public Customer findByName(String name) {
        return null;
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

    @Override
    public int delete(Customer object) {
        return 0;
    }

    @Override
    public int deleteById(Integer id) {
        return 0;
    }

}
