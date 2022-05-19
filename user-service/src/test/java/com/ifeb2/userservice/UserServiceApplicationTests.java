package com.ifeb2.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class UserServiceApplicationTests {

    private PathMatcher delegate = new AntPathMatcher();
    private final Set<String> paramIgnores = new HashSet<>();
    @Test
    void contextLoads() {
        paramIgnores.add("/user/by/{name}");
        paramIgnores.add("/user/add");

        System.out.println(delegate.match("/user/by/{name}","/user/by/admin"));

        boolean b =  paramIgnores.stream().anyMatch(uri->delegate.match(uri,"/user/by/admin"));

        System.out.println(b);
    }

}
