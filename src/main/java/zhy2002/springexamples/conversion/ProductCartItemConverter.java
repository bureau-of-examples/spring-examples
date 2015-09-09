package zhy2002.springexamples.conversion;

import org.springframework.core.convert.converter.Converter;
import zhy2002.springexamples.domain.Product;
import zhy2002.springexamples.domain.ShoppingCartItem;

import java.math.BigDecimal;

/**
 * Convert a product instance into a shopping cart item.
 * - An IllegalArgumentException should be thrown to report an invalid source value.
 * - Take care to ensure that your Converter implementation is thread-safe.
 */
public class ProductCartItemConverter implements Converter<Product, ShoppingCartItem> {

    @Override
    public ShoppingCartItem convert(Product source) {
        ShoppingCartItem item = new ShoppingCartItem();
        item.setProduct(source);
        item.setQuantity(BigDecimal.ONE);
        return item;
    }
}
