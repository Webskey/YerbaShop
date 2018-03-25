package org.yerbashop;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.yerbashop.model.Orders;
import org.yerbashop.model.Products;
import org.yerbashop.model.UserRoles;
import org.yerbashop.model.Users;

public class HibernateUtil {

	Credentials credentials = new Credentials();

	private StandardServiceRegistry registry;
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {

				// Create registry builder
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				DriverManagerDataSource data = credentials.dataSource();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Map<String, String> settings = new HashMap<>();
				settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
				settings.put(Environment.URL, data.getUrl());
				settings.put(Environment.USER, data.getUsername());
				settings.put(Environment.PASS, data.getPassword());
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

				// Apply settings
				registryBuilder.applySettings(settings);

				// Create registry
				registry = registryBuilder.build();

				// Create MetadataSources
				MetadataSources sources = new MetadataSources(registry)
						.addAnnotatedClass(Users.class)
						.addAnnotatedClass(Products.class)
						.addAnnotatedClass(Orders.class)
						.addAnnotatedClass(UserRoles.class);

				// Create Metadata
				Metadata metadata = sources.getMetadataBuilder().build();

				// Create SessionFactory
				sessionFactory = metadata.getSessionFactoryBuilder().build();

			} catch (Exception e) {
				e.printStackTrace();
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
			}
		}
		return sessionFactory;
	}

	public void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}