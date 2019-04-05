package com.datasource.provider.cboardprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {


	/*@Autowired
    private TableMapper tableMapper;*/

    @Test
    @Rollback
    public void testQueryTableListinfo() throws Exception {

    }

}
