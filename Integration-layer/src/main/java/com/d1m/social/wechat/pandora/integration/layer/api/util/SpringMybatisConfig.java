//package com.d1m.social.wechat.pandora.integration.layer.api.util;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.mapper.MapperScannerConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//@Configuration
//public class SpringMybatisConfig {
//
//	@Bean
//	public MapperScannerConfigurer mapperScannerConfigurer() {
//		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//		mapperScannerConfigurer.setBasePackage("com.d1m.social.wechat.card.api.dao");
//		return mapperScannerConfigurer;
//	}
//
//	@Bean
//	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
//		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//
//		sessionFactory.setDataSource(customerRoutingDataSource());
//		sessionFactory.setConfigLocation(
//				new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
//		sessionFactory.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
//
//		return sessionFactory;
//	}
//
//	@Bean
//	public CustomerRoutingDataSource customerRoutingDataSource() {
//
//		String driverClassName = System.getProperty("mysql.driver");
//		String url = System.getProperty("mysql.url");
//		String username = System.getProperty("mysql.username");
//		String password = System.getProperty("mysql.password");
//
//		CustomerRoutingDataSource customerRoutingDataSource = new CustomerRoutingDataSource();
//		customerRoutingDataSource.setDefaultTargetDataSource(getDataSource(driverClassName, url, username, password));
////多数据源支持
//		Map<Object, Object> targetDataSource = new HashMap<Object, Object>();
////		List<Integer> wechatIds = new LinkedList<Integer>();
////		wechatIds.add(11);
////		wechatIds.add(81);
////		wechatIds.add(101);
//
////		for(int i = 11; i < wechatIds.size(); i++) {
////			Integer wechatId = wechatIds.get(i);
////			String driverClassName_ = System.getProperty("mysql.driver" + wechatId);
////			String url_ = System.getProperty("mysql.url" + wechatId);
////			String username_ = System.getProperty("mysql.username" + wechatId);
////			String password_ = System.getProperty("mysql.password" + wechatId);
////			targetDataSource.put(wechatId, getDataSource(driverClassName_, url_, username_, password_));
////		}
//		customerRoutingDataSource.setTargetDataSources(targetDataSource);
//		customerRoutingDataSource.afterPropertiesSet();
//		return customerRoutingDataSource;
//	}
//
//	@Bean
//	public DataSourceTransactionManager dataSourceTransactionManager() {
//		DataSourceTransactionManager manager = new DataSourceTransactionManager();
//		manager.setDataSource(customerRoutingDataSource());
//		return manager;
//	}
//
//	public DriverManagerDataSource getDataSource(String driverClassName, String url, String username, String password) {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(driverClassName);
//		dataSource.setUrl(url);
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		return dataSource;
//	}
//}
