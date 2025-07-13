<!-- TOC -->
* [Spring Framework](#spring-framework)
  * [Overview](#overview)
  * [XML based configuration](#xml-based-configuration)
    * [Application Context](#application-context)
  * [Bean Scopes](#bean-scopes)
    * [Bean lifecycle](#bean-lifecycle)
  * [Java Annotation Configuration](#java-annotation-configuration)
    * [Auto-wiring](#auto-wiring)
    * [`@Qualifier` & `@Primary`](#qualifier--primary)
    * [`@Scope`](#scope)
    * [Init and Destroy](#init-and-destroy)
  * [Java Configuration Class (No XMl requried)](#java-configuration-class--no-xml-requried-)
    * [Way 1 - Create beans manually](#way-1---create-beans-manually)
    * [Way 2 - Use Component Scan](#way-2---use-component-scan)
    * [Reading from properties file](#reading-from-properties-file)
<!-- TOC -->

# Spring Framework

[Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core)

## Overview

Spring framework provides ways to manage your dependencies and provides a framework for inversion of control and specifically dependency injection.

There are 3 ways of configurations which tell spring framework what beans to manage and what dependency to inject into each component/bean.
- XML based (legacy)
- Java Annotation Configuration
- Java Source Code configuration.

Spring development process is basically a 3 step process (at 10000 feet level)
- Configure your beans (3 ways shown above)
- Create container (you will use diff ways to create container based on the type of config you used)
- Retrieve beans from container and use them for what you want.

__What is a Bean ??__

A spring bean is simply a java object. When java objects are created by Spring Container, then spring refers to them as Spring Beans. Spring Beans are created from normal Java Classes just like java objects.

Spring beans are similar to java beans which do not follow all of the rigorous requirements of a Java Bean.



## XML based configuration

You can create a xml file such as `applicationContext.xml` and create beans and add dependencies and values to them. You can also specify which class instance will be created when myCoach bean is referred to. Thus you can change the implementation without changing the source code.

```xml
<beans>
    <bean id = "myCoach" class = "com.rs.classA">
    </bean>
</beans>
```

You can also inject dependency for a bean. (Say class A depends on class B). Below snippet shows constructor injection. 

There is also field injection (discouraged) and setter injection methods apart from constructor injection.

Now when you request for `myCoach` bean spring will give you that bean and will also inject any dependency which the `myCoach` bean has in this case `beanIdB`. Thus you will get fully formed bean.

```xml
<beans>
    <bean id="beanIdB" class="com.rs.classB">
    </bean>
    
    <bean id = "myCoach" class = "com.rs.classA">
        <constructor-arg ref="beanIdB"/> # constructor injection.
    </bean>
</beans>
```

Creating container based on the xml file.

```java
ClassPathXMLApplicationContext context = new ClassPathXMLApplicationContext("applicationContext.xml");
```

Then you can use the context to get the beans

```java
context.getBean("myCoach", Coach.class );
```

__Setter injection__

Setter injection can be used in alternate to constructor injection.

```xml
<beans>
    <bean id="beanIdB" class="com.rs.classB">
    </bean>
    
    <bean id = "myCoach" class = "com.rs.classA">
        <property name="beanB" ref = "beanIdB"/> # setter injection, will look for a public method `setBeanB`.
    </bean>
</beans>
```


__Injecting literal values__

You can inject literal values into the beans. Spring will use these values when spinning up the bean.

```xml
<beans>
    <bean id = "myCricketCoach" class="com.rs.CricketCoach">
        <property name="emailAddress" value="samCoach@gmail.com" />
        <property name="team" value="IndiaUnder19" />
    </bean>
</beans>
```

__Reading values from properties file__

You can also read values from a properties file `applciation.properties` instead of the config file `application.xml`

```properties
# sport.properties
foo.email=samCoach@gmail.com
foo.team=IndiaUnder19
```

```xml
<!--applicationContext.xml-->
<beans>
    <!-- Load the properties file sport.properties   -->
    <context:property-placeholder location="classpath:sport.properties" />
    <bean id = "myCricketCoach" class="com.rs.CricketCoach">
        <property name="emailAddress" value="${foo.email}" />  <!-- use the property value -->
        <property name="team" value="${foo.team}" /> <!-- use the property value -->
    </bean>
</beans>
```

### Application Context

Using `ApplicationContext` you can get beans in the application context. Again based on the type of configuration you used (xml, annotation, java souce code)  the creation class of `ApplicationContext` will differ. There is also projects such as Web, Batch etc... which may have specialized implementation for creating ApplicationContext.


e.g. using xml

```java
ClassPathXMLApplicationContext context = new ClassPathXMLApplicationContext("applicationContext.xml");
```


## Bean Scopes

Scope refers the lifecycle of the bean. Default scope is singleton.

- singleton - single instance - good for stateless
- prototype (new instance everytime) - good for stateful data.
- request - scoped to each request
- session - scoped to http session
- global-session - scoped to global http web session.


```xml
<!--applicationContext.xml-->
<beans>
    <!-- Load the properties file sport.properties   -->
    <context:property-placeholder location="classpath:sport.properties" />
    <bean id = "myCricketCoach" class="com.rs.CricketCoach" scope="singleton"> <!-- scope -->
        <property name="emailAddress" value="${foo.email}" />
        <property name="team" value="${foo.team}" />
    </bean>
</beans>
```

### Bean lifecycle

- Bean is instantiated
- Dependency is injected
- Internal spring processing
- Your custom init method (if you wish to add something). E.g. open file, open connection
- Bean used ...
- Bean used ...
- Your custom destroy method (if you wish to add something). E.g. close file, close connection
- Bean lifecycle is over.



```xml
<!--applicationContext.xml-->
<beans>
    <!-- Load the properties file sport.properties   -->
    <context:property-placeholder location="classpath:sport.properties" />
    <bean id = "myCricketCoach" 
          class="com.rs.CricketCoach" 
          scope="singleton"
          init-method="someMethod"
          destroy-method="someDestroyInitMethod" >
        <property name="emailAddress" value="${foo.email}" />
        <property name="team" value="${foo.team}" />
    </bean>
</beans>
```

The init and destroy methods need to be methods on the class of which you wish to create a bean of.

E.g.

```java
// Wish to create a bean of person
class CricketCoach{
    private String emailAddress;
    private String team;
    
    // init method
    public someInitMethod(){
        
    }
    // destroy method
    public someDestroyMethod(){
        
    }
}
```




## Java Annotation Configuration

[Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-annotation-config)

Code : Package A

XML configuration can become verbose for a large project. Java annotations minimize the XML configuration needed. (Java context configuration method eliminates all XML, this current method eliminates just some xml not all).

- Spring will scan your java class for special annotations.
- Automatically register the beans in the spring container.

**Steps**
- Enable component scanning in the spring config xml file.
- Add the `@Component annotation to java classes`
- Retrieve bean from container.

```java
package A;

import org.springframework.stereotype.Component;

//@Component("thatSillyTennisCoach") // if you wish to define your own bean id you can provide a name
@Component  // Componenet annotation tells Spring to create a bean.
public class TennisCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "I am a tennis coach you should practice for 1 hour";
    }
}
```

### Auto-wiring

Code example : Package B

For dependency injection spring will see what class is required as a dependency. It will look for that class and try to inject the dependency automatically (by class or by interface) thus autowiring your dependency automatically.

```java
package B;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

    /**
     * Field based dependency injection (discouraged)
     */
//    @Autowired
    private FortuneService fortuneService;

    // Constructor based dependency injection.
    public TennisCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "I am a tennis coach you should practice for 1 hour";
    }

    @Override
    public String getDailyFortune(){
        return this.fortuneService.getFortune();
    }
}

```

You can also do setter injection.


```java
package B;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach{

    private FortuneService fortuneService;

    @Override
    public String getDailyWorkout() {
        return "hit 100 balls";
    }

    @Autowired  // setter injection
    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyFortune() {
        return this.fortuneService.getFortune();
    }
}
```

Actually setter injection can be used for any type of method not just a setter method. (Setter method is nothing special) E.g.

```java
@Autowired  // setter injection
public void doSomeCrazyInjectionHere(FortuneService fortuneService) {
    this.fortuneService = fortuneService;
}
```

### `@Qualifier` & `@Primary`

Code : Package B


In case you wish to inject dependency for `FortuneService` which is an interface and there are many classes which implement the `FortuneService` interface then Spring will not know which one to inject. Thus you can use qualifier to tell spring.

```java
@Autowired
@Qualifier("happyFortuneService")
public void setFortuneService(FortuneService fortuneService){
    this.fortuneService=fortuneService;
}
```


You can also label component as a `@Primary` so that it'll be chosen if there is no explict @Qualifer mentioned on the contructor/setter.

@Primary is written on top of component e.g.

```java
package B;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class HappyFortuneService implements FortuneService {
    @Override
    public String getFortune() {
        return "Have a great day.";
    }
}

```

### `@Scope`

Code : Package B

```java
package B;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Primary
@Scope("singleton")
public class HappyFortuneService implements FortuneService {
    @Override
    public String getFortune() {
        return "Have a great day.";
    }
}
```

### Init and Destroy

Code : Package B

**Note** `jakarta` did not work for me had to revert to `javax`

```java
package B;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Primary
@Scope("singleton")
public class HappyFortuneService implements FortuneService {
    @Override
    public String getFortune() {
        return "Have a great day.";
    }

    @PostConstruct
    void init(){
        System.out.println("Running Bean HappyFortune Service - init....");
    }


    @PreDestroy
    void destroy(){
        System.out.println("Running Bean HappyFortune Service - destroy....");
    }
}

```

Note :- For prototype bean spring does not call the @PostDestroy method and does not manage the complete lifecycle of the bean, you have to implement the DisposableBean interface.


## Java Configuration Class (No XMl requried)


In the previous method we used XML component scan along with java Annotations. However we can get rid of xml altogether and use java configuration class/java component scan.

- Create Java class annotate with `@Configuration`
- Add component scanning support `@ComponenetScan` - this is optional

### Way 1 - Create beans manually

Code : Package C

In this case there is no need for component scan. You create beans manually in the configuration class.

__What's the use case for Way 1 (manual bean creation) ?__

In certain situations you will need to use 3rd party code in your application. You will not have the ability to add `@Component` annotation on that 3rd party code.
Thus you can (in your configuration class) create bean manually for those classes.

### Way 2 - Use Component Scan

Code : Package D


### Reading from properties file

Code : Package E