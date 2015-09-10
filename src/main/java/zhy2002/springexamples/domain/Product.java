package zhy2002.springexamples.domain;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;

/**
 * An example domain class for testing.
 */
public class Product {

    private Long id;
    private String name;

    @DecimalMin(value = "0", inclusive = false, message = "Price cannot be lower than {min}.")
    private BigDecimal price;
    private String unitType;
    private BigDecimal stock;

    public Product(){
    }

    public Product(String name){
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }
}
