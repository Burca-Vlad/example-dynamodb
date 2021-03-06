package com.example;

import static org.hamcrest.CoreMatchers.hasItem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.example.models.User;
import com.example.repositories.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { PropertyPlaceholderAutoConfiguration.class, DynamoDBConfig.class })
public class UserRepositoryIntegrationTest {

	private static final String KEY_NAME = "id";
	private static final Long READ_CAPACITY_UNITS = 5L;
	private static final Long WRITE_CAPACITY_UNITS = 5L;
	private static final String TABLE_NAME = "User";
	
	@Autowired
	UserRepository repository;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Before
	public void init() throws Exception {

		ListTablesResult listTablesResult = amazonDynamoDB.listTables();

		listTablesResult.getTableNames().stream().filter(tableName -> tableName.equals(TABLE_NAME))
				.forEach(tableName -> {
					amazonDynamoDB.deleteTable(tableName);
				});

		List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
		attributeDefinitions.add(new AttributeDefinition().withAttributeName(KEY_NAME).withAttributeType("S"));

		List<KeySchemaElement> keySchemaElements = new ArrayList<KeySchemaElement>();
		keySchemaElements.add(new KeySchemaElement().withAttributeName(KEY_NAME).withKeyType(KeyType.HASH));

		CreateTableRequest request = new CreateTableRequest().withTableName(TABLE_NAME).withKeySchema(keySchemaElements)
				.withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(new ProvisionedThroughput()
						.withReadCapacityUnits(READ_CAPACITY_UNITS).withWriteCapacityUnits(WRITE_CAPACITY_UNITS));

		amazonDynamoDB.createTable(request);

	}

	@Test
	public void sampleTestCase() {
		User dave = new User("Dave", "Matthews");
		repository.save(dave);

		User carter = new User("Carter", "Beauford");
		repository.save(carter);

		Page<User> result = repository.findByLastName("Matthews", new PageRequest(0, 10));
		Assert.assertEquals(1, result.getContent().size());
		Assert.assertThat(result, hasItem(dave));
	}
}