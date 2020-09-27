package com.udacity.examples.Testing;

import junit.framework.TestCase;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DynamicTest;

import java.util.Arrays;
import java.util.IntSummaryStatistics;

import static org.junit.Assert.assertEquals;

public class HelperTest {

    @Test
    public void getCountEmptyItem(){
        assertEquals(Helper.getCount(Arrays.asList("sareeta", "snj", "", "john","")), 2);
    }
    @Test
    public void testGetStats(){
        IntSummaryStatistics intSummaryStatistics = Helper.getStats(Arrays.asList(1,2,3,5));
        System.out.println(intSummaryStatistics);
        assertEquals(5,intSummaryStatistics.getMax());
        assertEquals(11,intSummaryStatistics.getSum());
    }

    @Before
    public void init(){
        System.out.println("init!");
    }
    @BeforeClass
    public static void initClass(){
        System.out.println("init class");
    }

    @After
    public void cleanup(){
        System.out.println("after");
    }

    @AfterClass
    public static void tearDown(){
        System.out.println("after alll");
    }

	
}
