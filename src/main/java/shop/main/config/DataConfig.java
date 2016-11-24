package shop.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

//@EnableJpaRepositories(basePackages= {"shop.main.data.dao"})
//@EnableTransactionManagement
@Configuration
public class DataConfig<DatabasePopulator> {
	
	/**
	 * Embeded DB
	 **/
	
	@Bean
	public DataSource dataSourceEmbedded() {
		System.out.println("database");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		EmbeddedDatabase embeddedDatabase = builder
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("dbschema.sql")
				.addScript("test-data.sql")
				.build();
		return embeddedDatabase;
	}
	
//	@Autowired
//	private Environment environment;
	
//	@Bean
//	public DataSource dataSourceMysql() {
//		
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(environment.getProperty("jdbc.driverClass"));
//		dataSource.setUrl(environment.getProperty("jdbc.url"));
//		dataSource.setUsername(environment.getProperty("jdbc.username"));
//		dataSource.setPassword(environment.getProperty("jdbc.password"));
//		return dataSource;
//		
//	}
	
	
	//hibernate related beans
	
//	@Bean
//	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//		
//		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
//		
//		//databasepopulator
//		DatabasePopulatorUtils.execute(databasePopulator(), dataSourceMysql());
//		
//		return jpaTransactionManager;
//	}
	
//	@Bean
//	public JpaVendorAdapter jpaVendorAdapter() {
//		
//		HibernateJpaVendorAdapter jpaVendorAdapter= new HibernateJpaVendorAdapter();
//		jpaVendorAdapter.setDatabase(Database.MYSQL);
//		jpaVendorAdapter.setShowSql(true);
//		return jpaVendorAdapter;
//		
//	}
	
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		
//		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
//		entityManagerFactory.setDataSource(dataSourceMysql());
//		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter());
//		entityManagerFactory.setPackagesToScan("springmvc.java.domain"); //hera are models of user and log_post
//		
//		Properties jpaProperties = new Properties();
//		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//		entityManagerFactory.setJpaProperties(jpaProperties);
//		return entityManagerFactory;
//	}
	
//	@Bean
//	public BlogPostService blogPostService() {
//		return new BlogPostServiceImpl();
//	}
	
	//database populator
	
//	private ResourceDatabasePopulator databasePopulator() {
//		
//		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
//		databasePopulator.setContinueOnError(true);
//		databasePopulator.addScript(new ClassPathResource("test-data.sql"));
//		return databasePopulator;
//		
//	}
}
