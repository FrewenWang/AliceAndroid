package com.frewen.android.demo.business.samples.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @filename: GsonHelperTest
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/1 13:18
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class GsonHelperTest {

    User mUser;

    @Before
    public void setUp() throws Exception {
//        userInfo =new UserInfo("Frewen.Wong", 18);

    }

    @Test
    public void index() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(mUser));
        System.out.println("---------------");
        Gson gson1 = new GsonBuilder().create();
        System.out.println(gson1.toJson(mUser));
    }


    @After
    public void tearDown() throws Exception {
    }
}