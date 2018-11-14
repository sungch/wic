----
TODO
-----

// TODO detect current time zone, adjust with incoming UTC data, and then trim the dates.
private void normalizeVoucherEffectiveDates(Voucher voucher) {

@PostMapping("/customerOrder")
ResponseEntity<PackagingOrderedProductRepresentation> createCustomerOrder
// TODO response with incoming data when there is an ERROR
      
Debug packaging and delivery further: Test customerOrder
Ensure to generate serialver via serialver -classpath . bettercare.wic.model.OrderedProductsModel for instance.

Add last update date inn wic_order for current state

------
DEBUG: mvn clean install spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
------
INFO
------
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
entity id must be decalred to long not Long to avoid NullPointException.

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
