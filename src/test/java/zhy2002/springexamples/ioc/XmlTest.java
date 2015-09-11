package zhy2002.springexamples.ioc;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;
import zhy2002.springexamples.common.InitCounterBean;
import zhy2002.springexamples.common.PropertyTestObject;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.User;
import zhy2002.springexamples.ioc.xml.AutowiredTestObject;
import zhy2002.springexamples.ioc.xml.SetterInjectionTestObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test the behaviour of xml config.
 */
public class XmlTest {

    @Test
    public void importIsAlwaysRelative(){ //so do not use a leading '/' as this confuses IntelliJ.

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/xmltest/importIsAlwaysRelative.xml");

        //assertion
        assertThat(applicationContext.containsBean("shoppingCart2"), equalTo(true));
    }

    @Test
    public void multipleConfigAreCombinedIntoOne(){

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/xmltest/multipleConfigAreCombinedIntoOneB.xml", "/xmltest/multipleConfigAreCombinedIntoOneA.xml");
        ShoppingCart cartA = applicationContext.getBean("shoppingCartA", ShoppingCart.class);
        ShoppingCart cartB = applicationContext.getBean("shoppingCartB", ShoppingCart.class);

        //assertion
        assertThat(cartA.getCustomer().getLastName(), equalTo("CustomerB"));
        assertThat(cartB.getCustomer().getLastName(), equalTo("CustomerA"));
    }

    @Test
    public void crossImportResultInDoubleInitialization(){

        //arrange
        InitCounterBean.reset(); //assume no concurrent access to this counter.

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/importIsAlwaysRelative.xml", "xmltest/overlappingImport.xml");

        //assertion
        assertThat(InitCounterBean.getInstanceCount(), equalTo(2));

        /*
        NOTE: THIS ONLY HAPPENS IF YOU THE BEAN HAS NO ID
* 22:37:12.103 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'zhy2002.springexamples.common.InitCounterBean#0'
* 22:37:12.103 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating instance of bean 'zhy2002.springexamples.common.InitCounterBean#0'
* 22:37:12.103 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Eagerly caching bean 'zhy2002.springexamples.common.InitCounterBean#0' to allow for resolving potential circular references
* 22:37:12.104 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Finished creating instance of bean 'zhy2002.springexamples.common.InitCounterBean#0'
* 22:37:12.104 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating shared instance of singleton bean 'zhy2002.springexamples.common.InitCounterBean#1'
* 22:37:12.104 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Creating instance of bean 'zhy2002.springexamples.common.InitCounterBean#1'
* 22:37:12.104 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Eagerly caching bean 'zhy2002.springexamples.common.InitCounterBean#1' to allow for resolving potential circular references
* 22:37:12.104 [main] DEBUG o.s.b.f.s.DefaultListableBeanFactory - Finished creating instance of bean 'zhy2002.springexamples.common.InitCounterBean#1'
        */
    }

    @Test
    public void overriddenBeanIsNotInstantiated(){

        //arrange
        InitCounterBean.reset();

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/overrideBeanA.xml", "xmltest/overrideBeanB.xml");

        //assertion
        assertThat(InitCounterBean.getInstanceCount(), equalTo(1));
    }

    @Test
    public void allInjectionWillUseOverridingBean(){

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/overrideBeanA.xml", "xmltest/overrideBeanB.xml");
        ShoppingCart shoppingCartA = applicationContext.getBean("shoppingCartA", ShoppingCart.class);
        ShoppingCart shoppingCartB = applicationContext.getBean("shoppingCartB", ShoppingCart.class);
        User userA = applicationContext.getBean("userA", User.class);
        User userB = applicationContext.getBean("userA", User.class);

        //assertion
        assertThat(shoppingCartA.getCustomer().getLastName(), equalTo("Overriding")); //property injection
        assertThat(shoppingCartB.getCustomer().getLastName(), equalTo("Overriding"));
        assertThat(userA.getCustomer().getLastName(), equalTo("Overriding")); //constructor injection
        assertThat(userB.getCustomer().getLastName(), equalTo("Overriding"));
    }

    @Test
    public void canRegisterSingleton(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:na.xml");
        Customer customer = new Customer();
        customer.setLastName("Customer1");
        applicationContext.getBeanFactory().registerSingleton("customer1", customer); //thread safe according to source code.

        //action
        Customer bean = applicationContext.getBean("customer1", Customer.class);

        //assertion
        assertThat(bean, sameInstance(customer));
    }

    @Test
    public void overriddenBeanIsNotAccessedViaName(){

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/overrideBeanA.xml", "xmltest/overrideBeanB.xml");
        Customer customer = applicationContext.getBean("overriddenCustomer", Customer.class);

        //assert
        assertThat(customer.getLastName(), equalTo("Overriding"));
    }

    @Test
    public void instantiationByInnerFactoryClass(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/instantiation.xml");

        //action
        Customer customer = (Customer)applicationContext.getBean("customer");

        //assertion
        assertThat(customer.getFirstName(), equalTo("Snow"));
        assertThat(customer.getLastName(), equalTo("Bros"));
    }

    @Test
    public void canUseInstanceFactory(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/canUseInstanceFactory.xml");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //action
        Date newYear2015 = (Date)applicationContext.getBean("lunarNewYear2015");

        //assertion
        assertThat(dateFormat.format(newYear2015), equalTo("2015-02-19"));
    }

    @Test
    public void autowiredCanBeOptional(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/compoentScanIocXmlPackage.xml");

        //action
        AutowiredTestObject testObject = applicationContext.getBean("testObject1", AutowiredTestObject.class);

        //assertion
        assertThat(testObject, notNullValue());
        assertThat(testObject.getOptionalCustomer(), nullValue());
        assertThat(testObject.getRequiredCustomer(), notNullValue()); //dependency must be present otherwise: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'autowiredTestObject': Injection of autowired dependencies failed; nested exception is org.springframework.beans.factory.BeanCreationException: Could not autowire field: private zhy2002.springexamples.domain.Customer zhy2002.springexamples.ioc.xml.AutowiredTestObject.requiredCustomer; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type [zhy2002.springexamples.domain.Customer] found for dependency: expected at least 1 bean which qualifies as autowire candidate for this dependency. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

    }

    //org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'zhy2002.springexamples.ioc.xml.SetterInjectionTestObject#0' defined in class path resource [xmltest/setterInjectionIsOptional.xml]: Cannot resolve reference to bean 'nonExistentBean' while setting bean property 'customer'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'nonExistentBean' is defined
    @Test(expectedExceptions = BeanCreationException.class)
    public void setterInjectionIsOptional(){ //while setter injection itself is optional, if it is declared in XML config it must be fulfilled.

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/setterInjectionRefBeanMustExist.xml");

        //action
        SetterInjectionTestObject testObject = applicationContext.getBean(SetterInjectionTestObject.class);

        //assertion
        assertThat(testObject, notNullValue());
        assertThat(testObject.getCustomer(), nullValue());
    }

    @Test
    public void setterInjectionCanHaveCircularReference(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/setterInjectionCanHaveCircularReference.xml");

        //action
        SetterInjectionTestObject testObject1 = applicationContext.getBean("testObject1", SetterInjectionTestObject.class);
        SetterInjectionTestObject testObject2 = applicationContext.getBean("testObject2", SetterInjectionTestObject.class);

        //assertion
        assertThat(testObject1, notNullValue());
        assertThat(testObject1.getFriend(), sameInstance(testObject2));
        assertThat(testObject2, notNullValue());
        assertThat(testObject2.getFriend(), sameInstance(testObject1));
    }

    @Test
    public void canReferenceToBeanOfSameIdInParentContext(){

        //arrange
        ClassPathXmlApplicationContext parentContext = new ClassPathXmlApplicationContext("xmltest/parentContext.xml");
        ClassPathXmlApplicationContext childContext = new ClassPathXmlApplicationContext(new String[]{"xmltest/childContextA.xml"}, parentContext);

        //action
        ShoppingCart parentCart = childContext.getBean("cartOfParentCustomer", ShoppingCart.class);
        ShoppingCart childCart = childContext.getBean("cartOfChildCustomer", ShoppingCart.class);
        ShoppingCart newCart = childContext.getBean("cartOfNewCustomer", ShoppingCart.class);

        //assertion
        assertThat(parentCart.getCustomer().getLastName(), equalTo("Customer In Parent Context"));
        assertThat(childCart.getCustomer().getLastName(), equalTo("Customer In Child Context"));
        assertThat(newCart.getCustomer().getLastName(), equalTo("New Customer In Parent Context")); //if a bean is not present in the child context, it is searched in the parent context.

    }

    @Test
    public void canMergeWithParentCollection(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/mergeWithParent.xml");

        //action
        PropertyTestObject childObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(childObject.getConfigProperties().getProperty("key1"), equalTo("value1+"));
        assertThat(childObject.getConfigProperties().getProperty("key3"), equalTo("value3"));
        assertThat(childObject.getIntegers(), hasSize(4));
        assertThat(childObject.getIntegers().get(3), equalTo(5));

    }

    @Test
    public void pNamespaceCanReferenceBean(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/pNamespaceCanReferenceBean.xml");

        //action
        Customer customer = applicationContext.getBean(Customer.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class);

        //assertion
        assertThat(customer.getLastName(), equalTo("My Customer"));
        assertThat(cart.getCustomer(), sameInstance(customer));
    }

    @Test
    public void cNamespaceCanReferenceBean(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/cNamespaceCanReferenceBean.xml");

        //action
        Customer customer = applicationContext.getBean(Customer.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class);

        //assertion
        assertThat(customer.getLastName(), equalTo("My Customer"));
        assertThat(cart.getCustomer(), sameInstance(customer));
    }

    @Test
    public void useCompoundPropertyName(){

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmltest/useCompoundPropertyName.xml");

        //action
        PropertyTestObject testObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(testObject.getNestedTestObject(), notNullValue());
        assertThat(testObject.getNestedTestObject().getBeanId(), equalTo("testObject"));

    }


    //todo parked here: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-factory-dependson



}
