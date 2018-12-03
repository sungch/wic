----
TODO
-----



------
DEBUG:
mvn clean install spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

------
Learned
------

@PreAuthorize("hasAnyRole('ADMIN')") is to provide a method level authorizaation
<- access to this annotated method is possible only for those who have ADMIN role.

Use Database login: See https://www.youtube.com/watch?v=egXtoL5Kg08

Many-to-many relationship can be done in 2 steps via one-to-many and many-to-one via an intermediary table.
But this method create a user but requires another step to add roles.

User:
    @JsonManagedReference("user_ref")
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

Role:
    @JsonManagedReference("role_ref")
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();

UserRole:
    @JsonBackReference(value = "user_ref")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @JsonBackReference(value = "role_ref")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private Role role;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

Payload to add a user:
{
    "username": "user3",
    "password": "user3"
}

3. To update the user with a role:
INSERT INTO `user_role` VALUES (4,22,2);
Because @JsonBackReference(value = "user_ref") does not (de)serealize, it is not passed across the network.
So I need to do it manually.

Timezone setting at server level: JVM or SYSTEM of the JVM.
Timezone setting at session level: JDBC connectionread by Hibernate settings.

SYSTEM TIME ZONE:
set the system time zone for MySQL Server at startup with the --timezone=timezone_name option to mysqld_safe
SELECT @@system_time_zone; showed 'Mountain Standard Time' by default.
You can also set it by setting the TZ environment variable before you start mysqld.

GLOBAL TIME ZONE:
The initial global server time zone value can be specified explicitly at startup with the --default-time-zone=timezone option on the command line
or you can use the following line in an option file: default-time-zone='timezone'

SET time_zone = timezone;

The current session time zone setting affects display and storage of time values that are zone-sensitive.
This affects NOW(), CURTIME(), TIMESTAMP column.
Values for TIMESTAMP columns are converted from the current time zone to UTC for storage,
and from UTC to the current time zone for retrieval.
SELECT @@GLOBAL.time_zone, @@SESSION.time_zone;

At JDBC session level by Java code
String createdAtUtcStr = "2016-04-24 9:54:23";
DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
format.setTimeZone(TimeZone.getTimeZone("UTC"));
Date date = format.parse(createdAtUtcStr);
User user = new User(1, name, date);
session.save(user);

Hibernate Level:
db.url=jdbc:mysql://localhost:3306/wic?useUnicode=true&useLegacyDatetimeCode=false
spring.jpa.properties.hibernate.jdbc.time_zone = UTC

I can delete head or tail in 1:m relationship, but when deleting at middle layer object like wicOrder in relationship,
   @PreRemove method seems needed to cut relationship with parent and grand-parent.

When one-to-many, parents fetch lazy but child fetch eager.

Debug packaging and delivery further: Test customerOrder
Ensure to generate serialver via serialver -classpath . bettercare.wic.model.OrderedProductsModel for instance.

https://www.baeldung.com/spring-boot-actuators

Spring-boot app, ExceptionMapper with @Provider seems more work than spring's way like @ControllerAdvice and @ResponseBody.


When POSTing, ID must be 0. It cannot be empty or NULL.
PUT POST adds an empty entry even though payload is not valid.
@Past and @Future -> Are they work with LONG or must it be Date field?

/**
 * @Controller + @ResponseBody (converter of string to json) === @RestController
 * <p>
 * Somehow, client has to assume that POST Content-Type is application/json via @RequestBody
 * GET Accept via @RestController
 * <p>
 * Workflow: Client -> DispatcherServlet -> @RequestBody JSON to Java -> @Controller java format
 * Workflow Controller -> @ResponseBody Java to JSON -> DispatcherServlet -> client
 * <p>
 * mvn spring-boot:run
 * @RequestParameter -- method parameter
 * @RequestBody -- payload
 * @PathParameter -- path
 */

--------------
Tomcat Deploy
--------------
To make it deployable to tomcat, Had to Add "WicApplication extends SpringBootServletInitializer" and delete web.xml before deploy.


Use @Transactional in unit test to roll back at the end of the test.
Use @WebMvcTest, MockMvc, @MockBean
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")//, timezone = "MST") When not set, UTC is used.

Two Field annotations
@JsonManagedReference (for parent) which must meet its child pair
@JsonBackReference (for child)

Those two annotaion worked only with jackson-databind in com.fastxml.jackson.core.
They did not work with jackson-mapper-asl of org.codehaus.jackson.
<!--<dependency>-->
            <!--<groupId>org.codehaus.jackson</groupId>-->
            <!--<artifactId>jackson-mapper-asl</artifactId>-->
            <!--<version>1.9.13</version>-->
        <!--</dependency>-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.9.6</version>
        </dependency>


Use @GeneratedValue(strategy=GenerationType.IDENTITY.
    AUTO lets hibernate choose strategy based on hibernate dialect.
    Desirable if to support multiple DBs
    Earlier version, it chose IDENTITY.
    In version 5, it chooses TABLE.
    But use @GenericGenerator to still choose IDENTITY.
    SEQUENCE stretagy requires database sequence but mysql does not support so cannot be used with mysql.
    TABLE has performance and scalability issue.
    See https://docs.jboss.org/hibernate/orm/3.6/reference/en-US/html/mapping.html for all other details.

# wic
entity userRoleId must be decalred to long not Long to avoid NullPointException.

Durig testing, collection may show Error due to session being expired for LAZY fetch more.
Fix is to use EAGER fetch mode but it i not desirable for production system.
Also even in testing, multiple Entityty cannot have EAGER fetch more.
In production, use OpenSessionInViewFilter in web.xml to maintain session to handle this session expiring issue.

If possible, always use Unidirectional in entity relationship.
mappedBy and @JoinColumn(name = "") are exclusive to each other.
If mappedBy is used, I had to annotate from many side as well.
But Using @JoinColumn(name = "") I did not need to do anything on many side of the relationship.
It tell JPA that this a foreign key name in many side.
This also prevents cyclic data structure.

Patter of 1-to-many in this project:

One-Side or Parent-SideConfiguration:
@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private List<Product> products;


Many-Side or Child-side configuration:
Do ot do anything.

This project uses 1 entity manager for customized query executions and
one transaction manager used by spring for CRUDJpaRepository. see RepositoryConfiguration.

Chain of ivocation of Sprig beans:
web.xml AnnotationConfigWebApplicationContext
--> WicApplication(scanBasePackages = {"bettercare.wic"})
--> RepositoryConfiguration

Import database from a file. Make sure database already exists.
mysql -u<u> -p<p> wic < sqlfile

Export database structure only:
mysqldump -d -u<u> -p<p> wic > sqlfile

Renamed order table to avoid table name being SQL a key word.


TODO:
Test tinyblob

== BOOKS 

Great at work
Peak performance
The talent code
The culture code
