package zhy2002.springexamples.service;

import org.springframework.validation.annotation.Validated;
import zhy2002.springexamples.domain.ShoppingCart;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Shopping cart related business logic.
 */
@Validated
public interface ShoppingCartService {

    void save(@Valid @NotNull ShoppingCart cart);
}
