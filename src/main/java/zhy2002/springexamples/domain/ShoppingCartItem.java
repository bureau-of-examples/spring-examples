package zhy2002.springexamples.domain;

import java.math.BigDecimal;

/**
 * Created by JOHNZ on 11/05/2015.
 */
public class ShoppingCartItem {

    private Product product;
    private BigDecimal quantity;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
