package chinookApp.models;

public record CustomerSpender (
    double all_total,
    int id,
    String first_name,
    String last_name,
    String country,
    String postal_code,
    String phone,
    String email
){}
