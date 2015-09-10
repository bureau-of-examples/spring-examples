package zhy2002.springexamples.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.service.ShoppingCartService;


/**
 * Implementation class.
 */
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    public void save(ShoppingCart cart) {

        logger.info("Saving a ShoppingCart object...");

    }
}
