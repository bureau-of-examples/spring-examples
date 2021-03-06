package zhy2002.springexamples.ioccontainer;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;
import zhy2002.springexamples.Person;
import zhy2002.springexamples.beans.package1.Bean1;
import zhy2002.springexamples.beans.package2.Bean2;
import zhy2002.springexamples.common.InitCounterBean;
import zhy2002.springexamples.common.LazyTestObject;
import zhy2002.springexamples.common.MyTestService;
import zhy2002.springexamples.common.PropertyTestObject;
import zhy2002.springexamples.domain.Customer;
import zhy2002.springexamples.domain.ShoppingCart;
import zhy2002.springexamples.domain.User;
import zhy2002.springexamples.ioccontainer.xml.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test the behaviour of xml config.
 */
public class XmlConfigTest {

    @Test
    public void importIsAlwaysRelative() { //so do not use a leading '/' as this confuses IntelliJ.

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/xmlconfigtest/importIsAlwaysRelative.xml");

        //assertion
        assertThat(applicationContext.containsBean("shoppingCart2"), equalTo(true));
    }

    @Test
    public void multipleConfigAreCombinedIntoOne() {

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/xmlconfigtest/multipleConfigAreCombinedIntoOneB.xml", "/xmlconfigtest/multipleConfigAreCombinedIntoOneA.xml");
        ShoppingCart cartA = applicationContext.getBean("shoppingCartA", ShoppingCart.class);
        ShoppingCart cartB = applicationContext.getBean("shoppingCartB", ShoppingCart.class);

        //assertion
        assertThat(cartA.getCustomer().getLastName(), equalTo("CustomerB"));
        assertThat(cartB.getCustomer().getLastName(), equalTo("CustomerA"));
    }

    @Test
    public void crossImportResultInDoubleInitialization() {

        //arrange
        InitCounterBean.reset(); //assume no concurrent access to this counter.

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/importIsAlwaysRelative.xml", "xmlconfigtest/overlappingImport.xml");

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
    public void overriddenBeanIsNotInstantiated() {

        //arrange
        InitCounterBean.reset();

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/overrideBeanA.xml", "xmlconfigtest/overrideBeanB.xml");

        //assertion
        assertThat(InitCounterBean.getInstanceCount(), equalTo(1));
    }

    @Test
    public void allInjectionWillUseOverridingBean() {

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/overrideBeanA.xml", "xmlconfigtest/overrideBeanB.xml");
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
    public void canRegisterSingleton() {

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
    public void overriddenBeanIsNotAccessedViaName() {

        //arrange

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/overrideBeanA.xml", "xmlconfigtest/overrideBeanB.xml");
        Customer customer = applicationContext.getBean("overriddenCustomer", Customer.class);

        //assert
        assertThat(customer.getLastName(), equalTo("Overriding"));
    }

    @Test
    public void instantiationByInnerFactoryClass() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/instantiation.xml");

        //action
        Customer customer = (Customer) applicationContext.getBean("customer");

        //assertion
        assertThat(customer.getFirstName(), equalTo("Snow"));
        assertThat(customer.getLastName(), equalTo("Bros"));
    }

    @Test
    public void canUseInstanceFactory() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/canUseInstanceFactory.xml");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //action
        Date newYear2015 = (Date) applicationContext.getBean("lunarNewYear2015");

        //assertion
        assertThat(dateFormat.format(newYear2015), equalTo("2015-02-19"));
    }

    @Test
    public void autowiredCanBeOptional() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/compoentScanIocXmlPackage.xml");

        //action
        AutowiredTestObject testObject = applicationContext.getBean("testObject1", AutowiredTestObject.class);

        //assertion
        assertThat(testObject, notNullValue());
        assertThat(testObject.getOptionalCustomer(), nullValue());
        assertThat(testObject.getRequiredCustomer(), notNullValue()); //dependency must be present otherwise: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'autowiredTestObject': Injection of autowired dependencies failed; nested exception is org.springframework.beans.factory.BeanCreationException: Could not autowire field: private zhy2002.springexamples.domain.Customer zhy2002.springexamples.ioccontainer.xml.AutowiredTestObject.requiredCustomer; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No qualifying bean of type [zhy2002.springexamples.domain.Customer] found for dependency: expected at least 1 bean which qualifies as autowire candidate for this dependency. Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}

    }

    //org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'zhy2002.springexamples.ioccontainer.xml.SetterInjectionTestObject#0' defined in class path resource [xmlconfigtest/setterInjectionIsOptional.xml]: Cannot resolve reference to bean 'nonExistentBean' while setting bean property 'customer'; nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'nonExistentBean' is defined
    @Test(expectedExceptions = BeanCreationException.class)
    public void setterInjectionIsOptional() { //while setter injection itself is optional, if it is declared in XML config it must be fulfilled.

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/setterInjectionRefBeanMustExist.xml");

        //action
        SetterInjectionTestObject testObject = applicationContext.getBean(SetterInjectionTestObject.class);

        //assertion
        assertThat(testObject, notNullValue());
        assertThat(testObject.getCustomer(), nullValue());
    }

    @Test
    public void setterInjectionCanHaveCircularReference() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/setterInjectionCanHaveCircularReference.xml");

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
    public void canReferenceToBeanOfSameIdInParentContext() {

        //arrange
        ClassPathXmlApplicationContext parentContext = new ClassPathXmlApplicationContext("xmlconfigtest/parentContext.xml");
        ClassPathXmlApplicationContext childContext = new ClassPathXmlApplicationContext(new String[]{"xmlconfigtest/childContextA.xml"}, parentContext);

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
    public void beanPostProcessorInParentContextIsNotRegisteredInChildContext() {
        ClassPathXmlApplicationContext parentContext = new ClassPathXmlApplicationContext("xmlconfigtest/beanPostProcessorParentContext.xml");
        ClassPathXmlApplicationContext childContext = new ClassPathXmlApplicationContext(new String[]{"xmlconfigtest/beanPostProcessorChildContext.xml"}, parentContext);
        int childPostProcessCount = childContext.getBeanFactory().getBeanPostProcessorCount();
        int parentPostProcessorCount = parentContext.getBeanFactory().getBeanPostProcessorCount();
        assertThat(parentPostProcessorCount, greaterThan(childPostProcessCount));
    }


    @Test
    public void canMergeWithParentCollection() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/mergeWithParent.xml");

        //action
        PropertyTestObject childObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(childObject.getConfigProperties().getProperty("key1"), equalTo("value1+"));
        assertThat(childObject.getConfigProperties().getProperty("key3"), equalTo("value3"));
        assertThat(childObject.getIntegers(), hasSize(4));
        assertThat(childObject.getIntegers().get(3), equalTo(5));

    }

    @Test
    public void pNamespaceCanReferenceBean() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/pNamespaceCanReferenceBean.xml");

        //action
        Customer customer = applicationContext.getBean(Customer.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class);

        //assertion
        assertThat(customer.getLastName(), equalTo("My Customer"));
        assertThat(cart.getCustomer(), sameInstance(customer));
    }

    @Test
    public void cNamespaceCanReferenceBean() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/cNamespaceCanReferenceBean.xml");

        //action
        Customer customer = applicationContext.getBean(Customer.class);
        ShoppingCart cart = applicationContext.getBean(ShoppingCart.class);

        //assertion
        assertThat(customer.getLastName(), equalTo("My Customer"));
        assertThat(cart.getCustomer(), sameInstance(customer));
    }

    @Test
    public void useCompoundPropertyName() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/useCompoundPropertyName.xml");

        //action
        PropertyTestObject testObject = applicationContext.getBean(PropertyTestObject.class);

        //assertion
        assertThat(testObject.getNestedTestObject(), notNullValue());
        assertThat(testObject.getNestedTestObject().getBeanId(), equalTo("testObject"));

    }

    @Test
    public void lazyInitBeanIsInitiatedWhenFirstNeeded() {

        //arrange
        LazyTestObject.setLogs(new ArrayList<>());

        //action
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/lazyInit.xml");

        //assertion
        assertThat(LazyTestObject.getLogs(), hasSize(2));

        //action
        boolean hasLazy2 = applicationContext.containsBean("lazy2");  //check containsBean will not load the bean

        //assertion
        assertThat(hasLazy2, equalTo(true));
        assertThat(LazyTestObject.getLogs(), hasSize(2));

        //action
        LazyTestObject lazy2 = (LazyTestObject) applicationContext.getBean("lazy2");

        //assertion
        assertThat(lazy2, notNullValue());
        assertThat(LazyTestObject.getLogs(), hasSize(3));
    }

    /**
     * Ok to autowire when there is only one viable Constructor.
     * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-factory-autowire
     */
    @Test
    public void autowireByConstructorWillPickTheOnlyViableConstructor() {
        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/onlyOneViableAutowireConstructor.xml");

        //action
        AutowireTestObject testObject = applicationContext.getBean(AutowireTestObject.class);

        //assertion
        assertThat(testObject.getCustomer(), notNullValue());

    }

    /**
     * Ok to autowire when there are two viable Constructors when one is more specific than the other.
     */
    @Test
    public void autowireByConstructorTwoViableConstructorsWillPickTheOneWithMoreParameters() {
        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/twoViableAutowireConstructor.xml");

        //action
        AutowireTestObject testObject = applicationContext.getBean(AutowireTestObject.class);

        //assertion
        assertThat(testObject.getCustomer(), notNullValue());
        assertThat(testObject.getShoppingCart(), notNullValue());
    }

    /**
     * Ok to autowire when there are two viable Constructors but one has more parameters.
     */
    @Test
    public void autowireByConstructorTwoViableConstructorsWillPickTheOneWithMoreParameters2() {
        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/twoViableAutowireConstructor2.xml");

        //action
        AutowireTestObject testObject = applicationContext.getBean(AutowireTestObject.class);

        //assertion
        assertThat(testObject.getCustomer(), nullValue());
        assertThat(testObject.getProduct(), notNullValue());
        assertThat(testObject.getShoppingCart(), notNullValue());
    }


    /**
     * It seems if two viable constructor has the same number of parameters, the one defined latter wins.
     */
    @Test
    public void autowireByConstructorTwoViableConstructorsWithSameNumberOfParametersWillUseTheOneDefinedLater() {
        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/twoViableAutowireConstructor3.xml");

        //action
        AutowireTestObject testObject = applicationContext.getBean(AutowireTestObject.class);

        //assertion
        assertThat(testObject.getCustomer(), notNullValue());
        assertThat(testObject.getProduct(), nullValue());
    }

    @Test
    public void autowireByNameIsOptional() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/autowireByName.xml");

        //action
        AutowireTestObject testObject = applicationContext.getBean(AutowireTestObject.class);

        //assertion
        assertThat(testObject.getCustomer(), notNullValue());
        assertThat(testObject.getShoppingCart(), notNullValue());
        assertThat(testObject.getProduct(), nullValue());

    }

    @Test
    public void canInjectLookupMethod() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/lookupMethod.xml");

        //action
        LookupMethodTestObject testObject = applicationContext.getBean(LookupMethodTestObject.class);
        ShoppingCart cart1 = testObject.createCart();
        ShoppingCart cart2 = testObject.createCart();

        //assertion
        assertThat(cart1.getCustomer().getLastName(), equalTo("Prototype"));
        assertThat(cart2.getCustomer().getLastName(), equalTo("Prototype"));
        assertThat(cart1.getCustomer(), not(sameInstance(cart2.getCustomer())));
    }

    /**
     * Can replace any method in Spring but prefer AspectJ.
     */
    @Test
    public void canReplaceMethod() {

        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/replaceMethod.xml");

        //action
        LookupMethodTestObject testObject1 = applicationContext.getBean("originalTestObject", LookupMethodTestObject.class);
        LookupMethodTestObject testObject2 = applicationContext.getBean("replacedTestObject", LookupMethodTestObject.class);

        //assertion
        ShoppingCart cart1 = testObject1.createCart("Test1");
        ShoppingCart cart2 = testObject2.createCart("Test2");
        ShoppingCart cart2a = testObject2.createCart("Test2");
        assertThat(cart1.getDateCreated(), nullValue());
        assertThat(cart1.getCustomer().getLastName(), equalTo("Prototype"));
        assertThat(cart2.getDateCreated(), notNullValue());
        assertThat(cart2.getCustomer().getLastName(), nullValue());
        assertThat(cart2.getCustomer(), not(sameInstance(cart2a.getCustomer())));

    }

    @SuppressWarnings("unchecked")
    @Test
    public void beanPropertyMightNotBeSetWhenInjected() {
        //arrange
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/postConstructTest.xml");
        ArrayList<String> list = (ArrayList<String>) applicationContext.getBean("list");

        assertThat(list.contains("other: null"), equalTo(true));
    }


    @Test
    public void requiredPropertyInjectionCheckIsNotEnabledByDefault() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/requiredPropertyInjectionTest.xml");
        RequiredTestObject testObject = (RequiredTestObject) applicationContext.getBean("testObject");
        assertThat(testObject.getRequiredProperty(), nullValue());
    }

    @Test(expectedExceptions = BeanCreationException.class)
    public void requiredPropertyInjectionCheckIsEnforcedByBPP() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/requiredPropertyInjectionFailTest.xml");
        RequiredTestObject testObject = (RequiredTestObject) applicationContext.getBean("testObject");
        assertThat(testObject.getRequiredProperty(), nullValue());
    }

    @Test
    public void requiredPropertyInjectionCanStillBeNull() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/requiredPropertyInjectionPassTest.xml");
        RequiredTestObject testObject = (RequiredTestObject) applicationContext.getBean("testObject");
        assertThat(testObject.getRequiredProperty(), nullValue());
    }

    @Test(expectedExceptions = BeanCreationException.class)
    public void beanValidationPostProcessorValidatesBeanAfterPropertiesSet() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/beanValidationPostProcessorTest.xml");
        RequiredTestObject testObject = (RequiredTestObject) applicationContext.getBean("testObject");
        assertThat(testObject.getRequiredProperty(), nullValue());
    }

    @Test
    public void multipleComponentScansAreCombined() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/multipleComponentScans.xml");
        Bean1 bean1 = applicationContext.getBean(Bean1.class);
        assertThat(bean1, notNullValue());

        Bean2 bean2 = applicationContext.getBean(Bean2.class);
        assertThat(bean2, notNullValue());
    }

    @Test
    public void canRegisterThreadScopeBean() throws Exception {

        final int[] hashCodes = new int[4];
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/simpleThreadScope.xml");

        Thread thread1 = new Thread(() -> {
            MyTestService testService = applicationContext.getBean(MyTestService.class);
            hashCodes[0] = testService.getIdentityHashCode();
            testService = applicationContext.getBean(MyTestService.class);
            hashCodes[1] = testService.getIdentityHashCode();
        });
        Thread thread2 = new Thread(() -> {
            MyTestService testService = applicationContext.getBean(MyTestService.class);
            hashCodes[2] = testService.getIdentityHashCode();
            testService = applicationContext.getBean(MyTestService.class);
            hashCodes[3] = testService.getIdentityHashCode();
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(hashCodes[0], equalTo(hashCodes[1]));
        assertThat(hashCodes[2], equalTo(hashCodes[3]));
        assertThat(hashCodes[0], not(equalTo(hashCodes[2])));
    }

    @Test
    public void canRegisterThreadScopeFactory() throws Exception {
        final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/threadScopeFactory.xml");
        final Object signal = new Object();

        Thread thread1 = new Thread(() -> {
            final MyConfig myConfig1 = applicationContext.getBean("myConfig", MyConfig.class);
            assertThat(myConfig1.getTheValue(), equalTo("initial"));

            Thread thread2 = new Thread(() -> {
                MyConfig myConfig2 = applicationContext.getBean("myConfig", MyConfig.class);
                assertThat(myConfig2.getTheValue(), equalTo("initial"));

                synchronized (signal) {
                    signal.notifyAll();
                    try {
                        signal.wait();
                    }catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }

                myConfig2 = applicationContext.getBean("myConfig", MyConfig.class);
                assertThat(myConfig2.getTheValue(), equalTo("initial"));
            });
            thread2.start();

            synchronized (signal) {
                try {
                    signal.wait();
                }catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            myConfig1.setTheValue("initial2");
            MyConfigFactory factory = applicationContext.getBean("myConfigFactory", MyConfigFactory.class);
            factory.updateMyConfig(myConfig1);

            synchronized (signal) {
                signal.notifyAll();
            }

            try {
                thread2.join();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            Thread thread3 = new Thread(() -> {
                MyConfig myConfig3 = applicationContext.getBean("myConfig", MyConfig.class);
                assertThat(myConfig3.getTheValue(), equalTo("initial2"));

            });
            thread3.start();
            try {
                thread3.join();
            }catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        thread1.start();
        thread1.join();
    }

    @Test
    public void canUseSpel() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("xmlconfigtest/canUseSpel.xml");
        Person person2 = applicationContext.getBean("person2", Person.class);

        assertThat(person2.getFirstName(), equalTo("Adam"));
        assertThat(person2.getLastName(), equalTo("#{test}"));
    }


    //todo parked here: http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#beans-factory-scopes


}
