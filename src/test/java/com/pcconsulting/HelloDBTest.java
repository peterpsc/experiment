package com.pcconsulting;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

public class HelloDBTest {

	@Test
	public void testMain_default() throws ClassNotFoundException, SQLException {
	   HelloDB.main();
	}

	@Test
	public void testMain_dbName() throws ClassNotFoundException, SQLException {
		HelloDB.main("db/myDB");
	}

	@Test
	public void testMain_missingDbName() throws ClassNotFoundException {
		try {
			HelloDB.main("db/missingDB");
			fail("should not reach here");
		} catch (SQLException se) {
			assertEquals("user lacks privilege or object not found: CUSTOMER", se.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
