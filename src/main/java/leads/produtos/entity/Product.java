package leads.produtos.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_prod")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name ="status")
    private String status;

    @Column(name ="name")
    private String name;

    @Column(name ="value")
    private String value;

    public Product() {

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Product(UUID id, String status, String name, String value) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.value = value;
    }
}
