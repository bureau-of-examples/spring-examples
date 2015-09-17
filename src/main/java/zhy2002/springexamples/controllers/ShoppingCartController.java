package zhy2002.springexamples.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.service.ShoppingCartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Shopping cart controller.
 */
@Controller
@RequestMapping()
public class ShoppingCartController {

    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService service){
        shoppingCartService = service;
    }

    @RequestMapping(value = "/countCartItems", method = RequestMethod.GET)
    public @ResponseBody String getCartItemCount(){

        Long cartId = 3L;
        Integer count = shoppingCartService.itemInCart(cartId);
        return count == null ? null : count.toString();
    }

    @RequestMapping(value = "/allCarts", method = RequestMethod.GET)
    public Map<String, ShoppingCart> getAllCarts(){

        ShoppingCart cart1 = new ShoppingCart();
        ShoppingCart cart2 = new ShoppingCart();

        HashMap<String, ShoppingCart> result = new HashMap<>();
        result.put("1", cart1);
        result.put("2", cart2);
        return result;
    }

}
