package org.batikan.test.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TestSystemApplication {
    public static void main(String[] args) {
	ApplicationContext context = SpringApplication.run(TestSystemApplication.class, args);

	TestSystemMockDataInitializer testSystemMockDataInitializer = context.getBean(
		TestSystemMockDataInitializer.class);
	testSystemMockDataInitializer.initialize();
    }
}
