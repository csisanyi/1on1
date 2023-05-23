package com.getbridge.homework;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HomeworkApplicationTests {


	@Autowired
	private MongoClient mongoClient;

	@Test
	void testMongoDBConnection() {
		assert mongoClient != null;

		// Test the connection by performing a simple operation
		String databaseName = mongoClient.listDatabaseNames().first();
		assert databaseName != null;
		System.out.println("Connected to MongoDB: " + databaseName);
	}

}
