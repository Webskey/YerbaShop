package org.yerbashop;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <h1>Hibernate configuration class.</h1>
 * Sets database connection. 
 *
 * @author  <a href="https://github.com/Webskey">Webskey</a>
 * @since   2018-03-25
 */

@Configuration
@EnableTransactionManagement
public class HibernateConfig {

	@Autowired
	Credentials credentials;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {

		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		props.setProperty("hibernate.show_sql", "false");
		props.setProperty("hibernate.hbm2ddl.auto", "validate");

		factoryBean.setDataSource(credentials.dataSource());
		factoryBean.setHibernateProperties(props);
		factoryBean.setPackagesToScan(new String[] { "org.yerbashop.model" });

		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {

		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());

		return transactionManager;
	}
}

