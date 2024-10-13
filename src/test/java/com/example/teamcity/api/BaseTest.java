package com.example.teamcity.api;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    protected SoftAssert softy;

    @BeforeTest(alwaysRun = true)
    public void beforeTest(){
        softy = new SoftAssert();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest(){
        softy.assertAll();
    }
}
