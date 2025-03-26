package leads.produtos.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name ="status")
    private String status;

    @Column(name ="name")
    private String name;

    @Column(name ="email")
    private String email;

    @Column(name ="password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_cart",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "prod_id")
    )
    @JsonManagedReference
    private List<Product> cart = new ArrayList<>();

    public User() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Product> getCart() {
        return cart;
    }

    public void setCart(List<Product> cart) {
        this.cart = cart;
    }

    public void addProductToCart(Product product) {
        this.cart.add(product);
    }

    public void removeProductFromCart(Product product) {
        this.cart.remove(product);
    }

    public User(UUID id, String status, String name, String email, String password) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
