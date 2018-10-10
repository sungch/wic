Use @Transactional in unit test to roll back at the end of the test.

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

