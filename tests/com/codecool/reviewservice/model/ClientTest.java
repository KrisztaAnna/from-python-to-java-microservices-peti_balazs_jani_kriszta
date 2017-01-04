package com.codecool.reviewservice.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ClientTest {

    private Client testClient1;
    private Client testClient2;

    @Before
    public void setUp() throws Exception {
        testClient1 = new Client("testName1", "test@email.1");
        testClient2 = new Client("testName2", "test@email.2", "testAPIKey");
    }

    @Test
    public void getName1() throws Exception {
        assertEquals("testName1", testClient1.getName());
    }

    @Test
    public void getName2() throws Exception {
        assertEquals("testName2", testClient2.getName());
    }

    @Test
    public void getId1() throws Exception {
        testClient1.setId(1);
        assertEquals(1, testClient1.getId());
    }

    @Test
    public void getId2() throws Exception {
        testClient2.setId(2);
        assertEquals(2, testClient2.getId());
    }

    @Test
    public void noId() throws Exception {
        assertEquals(0, testClient1.getId());
    }

    @Test
    public void getAPIKeyAsHashString() throws Exception {
        String testKey = testClient1.getAPIKey();
        // APIKey is based on timestamp so it's quite impossible to test it with assertEquals()
        // but I can test whether the string is empty or not...
        assertFalse(testKey.isEmpty());
    }


    @Test
    public void getAPIKey2() throws Exception {
        assertEquals("testAPIKey", testClient2.getAPIKey());
    }

    @Test
    public void getEmail1() throws Exception {
        assertEquals("test@email.1", testClient1.getEmail());
    }

    @Test
    public void getEmail2() throws Exception {
        assertEquals("test@email.2", testClient2.getEmail());
    }

}