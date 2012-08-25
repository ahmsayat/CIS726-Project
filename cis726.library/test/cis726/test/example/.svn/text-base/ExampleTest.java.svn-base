package cis726.test.example;


import java.util.ArrayList;
import java.util.Collection;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

public class ExampleTest extends TestCase{
	private Collection collection;
	@Before
	public void setUp() throws Exception {
		collection = new ArrayList();
	}

	@After
	public void tearDown() throws Exception {
		collection.clear();
	}

    public void testEmptyCollection(){	 
    	assertEquals(true, collection.isEmpty());    
    }
}
