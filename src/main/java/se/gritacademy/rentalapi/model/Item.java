package se.gritacademy.rentalapi.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 500)
    @Column(length = 500)
    private String description;
    @NotBlank
    private String category;
    @NotNull
    @Positive
    private BigDecimal pricePerDay;

    public Item() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String newCategory) {
        this.category = newCategory;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }
    public void setPricePerDay(BigDecimal newPricePerDay) {
        this.pricePerDay = newPricePerDay;
    }
}