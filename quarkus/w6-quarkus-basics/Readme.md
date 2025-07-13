# Quarkus : Getting to know the framework

The first section is to understand how to build REST services with Quarkus

## Section : Request, Response

### Request Data

`@PathParam`

```java
@Path("")
public class Index {
    @GET
    @Path("/customer/{id}")
    public String a(@PathParam("id") String id) {
        System.out.println(id);
        return "Ok";
    }
}
```

`@QueryParam`

```java
@Path("")
public class Index {
    @GET
    @Path("/customer/{id}")
    public String a(@PathParam("id") String id, @QueryParam("age") int age) {
        System.out.println(id);
        System.out.println(age);
        return "Ok";
    }
}
```

The following are jakarta.ws.rs annotations :-

- PathParam
- QueryParam
- HeaderParam
- CookieParam
- FormParam
- MatrixParam

However you can also use `org.jboss.resteasy.reactive` annotations e.g.

```java
@Path("")
public class Index {
    @GET
    @Path("/customer/{id}")
    public String a(@RestPath("id") String id, @RestQuery("age") int age) {
        System.out.println(id);
        System.out.println(age);
        return "RestQuery";
    }
}
```

`@HeaderParam`

```java
@Path("")
public class Index {
    @GET
    @Path("/customer/{id}")
    public String a(@HeaderParam("User-Agent") String userAgent, @HeaderParam("nothere") String notThere) {
        System.out.println(userAgent);
        System.out.println(notThere);
        return "Ok";
    }
}
```

`Cookie`

```java
@Path("")
public class Index {
    @GET
    @Path("/customer/{id}")
    public String a(@CookieParam("sampleCookie") Cookie sampleCookie) {
        System.out.println(sampleCookie);
        System.out.println(sampleCookie.getValue());
        System.out.println(sampleCookie.getName());
        return "Ok";
    }
}
```

You can also group all of these into a custom class

```java
public static class Parameters {
        @RestPath
        String type;

        @RestMatrix
        String variant;

        @RestQuery
        String age;

        @RestCookie
        String level;

        @RestHeader("X-Cheese-Secret-Handshake")
        String secretHandshake;

        @RestForm
        String smell;
    }

    @POST
    public String allParams(@BeanParam Parameters parameters) {
        return parameters.type + "/" + parameters.variant + "/" + parameters.age
            + "/" + parameters.level + "/" + parameters.secretHandshake
            + "/" + parameters.smell;
    }
}
```

`FormParam` gives you a form field, if you need all fields of a form in a single pojo, record
then use `BeanParam` as shown below.

```java
@Path("")
public class Index {
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/customer")
    public String a(@FormParam("name") String name) {
        System.out.println(name);
        return "Ok";
    }
}
```

```java

@Path("")
public class Index {

    public static class FormData{

        @FormParam("name")
        String name;
        @FormParam("age")
        int age;

        @Override
        public String toString() {
            return "FormData{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/customer")
    public String a(@BeanParam FormData data) {
        System.out.println(data);
        return "Ok";
    }
}
```

It does not work with records just yet - see [discussion](https://github.com/jakartaee/rest/issues/913).

### Response

Returning a simple `String`

```java
@Path("")
public class Index {
    @GET
    public String a() {
        return "Ok";
    }
}
```

Returning JSON

```java
@Path("")
public class Index {

    record Person(String name, int age, Address address){}
    record Address(String line1, String line2, Country country){}
    enum Country{Canada, US}

    record A(String a, int b){}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Person> a() {
        return RestResponse.ok( new Person("sam nelson", 18, new Address("line1", "line2", Country.Canada)));
    }
}
```

The above produces

```json
{
  "name": "sam nelson",
  "age": 18,
  "address": { "line1": "line1", "line2": "line2", "country": "Canada" }
}
```

Note :- You can just return `Person` or `RestResponse<Person>`. The latter gives you tbe ability to customize response even further e.g. customizing status

```java
return RestResponse.status(Response.Status.BAD_REQUEST, new Person("sam nelson", 18, new Address("line1", "line2", Country.Canada)));
```

Also checkout the whole manual way of building the response

```java
@Path("")
public class Index {

    record Person(String name, int age, Address address){}
    record Address(String line1, String line2, Country country){}
    enum Country{Canada, US}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RestResponse<Person> a() {
        var data = new Person("sam nelson", 18, new Address("line1", "line2", Country.Canada));
        NewCookie cookie = new NewCookie.Builder("cookie-name")
                .value("cookie-value")
                .path("/")
                .domain("localhost.com")
                .sameSite(NewCookie.SameSite.LAX)
                .maxAge(100)
                .secure(true)
                .build();
        return ResponseBuilder.ok(data).cookie(cookie).header("header-a", "header-b").build();
    }
}
```

## Section : Panache persistence

Creating a simple entity

```java
@Entity
public class User extends PanacheEntity {
    public String name;
    public String birthDate;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", id=" + id +
                '}';
    }
}
```

Running simple queries on the above entity.

```java
User.listAll().forEach(System.out::println);
```

```java
System.out.println(User.find("SELECT name from User where id=?1", 2).stream().findAny().get());
```

You can also stream all entries. `List` methods have `stream` equivalents.

```java
List<String> names;
try(Stream<User> users = User.streamAll()){
    names = users.map(u -> u.name)
                            .filter(n -> n.contains("ca"))
                            .collect(Collectors.toList());
}
```

The above code generates the below sql query, notice there is no `where` clause in it. This will definitely create a performance impact

```SQL
Hibernate:
    select
        u1_0.id,
        u1_0.birthDate,
        u1_0.name
    from
        User u1_0
```

You can log all queries genered by hibernate with below added to `application.properties`

```properties
quarkus.hibernate-orm.log.sql=true
```

**Creating relationships in hibernate**

```java
@Entity
public class Hobby extends PanacheEntity {
    public String name;
    @ManyToOne
    public User user;
}
```

The above will generate SQL

```SQL
 create table Hobby (
        id bigint not null,
        user_id bigint,
        name varchar(255),
        primary key (id)
    )
```

```java
@Entity
public class User extends PanacheEntity {
    public String name;
    public String birthDate;
    /**
     * This tells hibernate that a new table which maintains relationship is not needed
     * And the column user in table/class Hobby manages this relationship else hibernate
     * will create a new table HOBBY_USER which maintains the relationship.
     *
     * By having List<Hobby> in this class we now have bidirectional relationship available
     * for us to easily query on.
     */
    @OneToMany(mappedBy = "user")
    List<Hobby> hobbies;
}
```

**Cache Issue**

Consider a scenario

- Fetch entity from db
- Update something on entity
- Fetch again `this will fetch from cache`
- Return back

As in step 3 it will fetch from cache it may result in unexpected behaviour. If you wish to force db fetch you need to `clear()` the entity manager as shown below.

```java
User u1 = User.findById(1);

Hobby h1 = new Hobby();
h1.name = "Music";
h1.user = u1;
h1.persist();

Hobby h2 = new Hobby();
h2.name = "Football";
h2.user=u1;
h2.persistAndFlush();

/**
 * Clear the entity manager of User entity.
 */
User.getEntityManager().clear(); // Clears any cache. So when we do a `.findById` again it fetches from the db.

User u = User.findById(1);
System.out.println("User 1's hobbies are ==> " + u);
```

Note :- Hibernate is a pain though, sometime things work and sometimes they don't and its very difficult to debug what the hell is going on.

Might need to look at jdbi integration with Quarkus. There is an extention which does that which I might explore and leave panache entity and others out of it. ==> Lets see, its still a todo.

## Section : Templating

- Qute Reference [link](https://quarkus.io/guides/qute-reference)
- Qute Guide [link](https://quarkus.io/guides/qute)

Lets say we have a template `index.html` which is stored in `resources/templates/index.html`. To return that template the method needs to return `io.quarkus.qute.TemplateInstance` with some data which the template needs or `null`.

```java
@Path("")
public class Index {

    @Inject
    Template index; // looks for index.html

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance g(){
        return index.data(null); // if you have no data put in null.
    }
}
```

In case you have many subdirs or wish to specify the name of name of the template manually you can use `@Location` i.e. `io.quarkus.qute.Location`

```java
@Location("index.html")
Template template;
```

**TypeSafe Templates**

- Use `CheckedTempalte`
- Add resource in the `.html` file as shown below

```html
{@com.rs.User user}

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Title</title>
  </head>
  <body>
    <p>Hello {user.name}</p>
    <p>Hello {user.age}</p>
    <p>Hello {user.email}</p>
    <!-- Email property does not exists in User so it will fail at build time-->
  </body>
</html>
```

You can also add multiple resources in the template to check for type safety as shown below.

```html
{@com.rs.User user} {@com.rs.Company company}
<!-- 2 resources-->

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Title</title>
  </head>
  <body>
    <p>Hello {user.name}</p>
    <p>Hello {user.age}</p>
    <p>Hello {company.email}</p>
  </body>
</html>
```

Comments

```
{! This is a comment !}
```

Expressions

```
{item.get(name)}
```

Sections such as if, for ...

```
{# if true}
{/if} or {/}
```

```html
<body>
  {#if user.age > 18}
  <p>adult</p>
  {#else}
  <p>child</p>
  {/if}
</body>
```

Different types of sections available

- for
- if
- when
- let
- with
- include
- user defined tags
- fragment
- cached

```html
{@java.util.List<com.rs.User>
  users}

  <!DOCTYPE html>
  <html lang="en">
    <head>
      <meta charset="UTF-8" />
      <title>Title</title>
    </head>
    <body>
      {#for user in users}
      <p>
        {user.name} {user.age} {#if user.age > 18} adult {#else} child {/if}
      </p>
      {/for}
    </body>
  </html></com.rs.User
>
```

**`when`**

Almost equal to switch.

```html
{#when items.size} {#is 1} There is exactly one item! {#is > 10} There are more
than 10 items! {#else} There are 2 -10 items! {/when}
```

Fragments might be very useful in conjuction with `htmx`. // TODO

**Template Include**

```html
<!--index.html-->
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Simple Include</title>
  </head>
  <body>
    {#include foo limit=10 /} // include foo template and pass limit to 10 in
    side foo template
  </body>
</html>
```

**Template Inheritance**

```html
<!--base.html-->
<html>
  <head>
    <meta charset="UTF-8" />
    <title>{#insert title}Default Title{/}</title>
    // Define some default title
  </head>
  <body>
    {#insert}No body!{/}
    <!-- Notice its just #insert and not #insert name which means other will be inserted by default -->
  </body>
</html>
```

```html
{#include base}
<!-- inherit base -->
{#title}My Title{/title}
<!-- start title -->
<div>
  <!-- Will be added to #insert because its default -->
  My body.
</div>
{/include}
```

## Section : JDBI

Creating a jdbi provider - I think we will have to add @ApplcationScoped - TODO is it by default - will have to read more about CDI light 4.0

```java
package com.rs;

import io.agroal.api.AgroalDataSource;
import jakarta.ejb.Singleton;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;

public class JdbiProvider {
    @Inject
    AgroalDataSource ds;

    @Produces
    public Jdbi jdbi(){
        Jdbi jdbi = Jdbi.create(ds).installPlugin(new SQLitePlugin());
        return jdbi;
    }

}
```

`Agroal` is a connection pooling library similar to `hikari` but fits better with quarkus.
Its picking up the data source from `application.properties` file


```properties
quarkus.datasource.db-kind=sqlite
quarkus.datasource.jdbc.url=jdbc:sqlite:database.db
```

Now in controller or service 

```java
package com.rs;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

@Path("")
public class Index {

    @Inject
    Jdbi jdbi;


    @GET
    public void g(){
        System.out.println(jdbi);
        var r = jdbi.withHandle(handle -> {
            return handle.createQuery("Select * from User where id=1")
                    .registerRowMapper(ConstructorMapper.factory(User.class))
                    .mapTo(User.class)
                    .findOne();});

        System.out.println(r);
    }
}
```

Below is the record which is getting mapped with the data in the table.


```java
package com.rs;

public record User(int id, String birthDate, String name) {}

```
