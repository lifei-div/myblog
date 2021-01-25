package com.fly.myblog;

import com.fly.myblog.util.MD5Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyblogApplicationTests {

    @Test
    void contextLoads() {

        String a = "123456";
        String code = MD5Utils.code(a);
        System.out.println(code);
    }

}
