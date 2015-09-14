package zhy2002.springexamples.ioccontainer.xml;

import org.springframework.beans.factory.support.MethodReplacer;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.ShoppingCart;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Stamp date if it is null
 */
public class ShoppingCartDateStamper implements MethodReplacer {

    @Override
    public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {

        //cannot call method.invoke here as obj is the proxy and it will redirect the call back here.
        ShoppingCart shoppingCart = new ShoppingCart(new Customer());
        shoppingCart.getCustomer().setFirstName(args[0].toString());
        shoppingCart.setDateCreated(new Date());
        return shoppingCart;
    }
}
