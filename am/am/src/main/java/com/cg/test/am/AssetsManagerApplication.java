package com.cg.test.am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class AssetsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetsManagerApplication.class, args);
    }

}
