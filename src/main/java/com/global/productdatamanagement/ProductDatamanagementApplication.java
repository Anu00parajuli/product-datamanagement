package com.global.productdatamanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.global.productdatamanagement",  // your current app
        "com.eon.springbootdatamanagement"   // external JAR package
})
public class ProductDatamanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductDatamanagementApplication.class, args);
    }

}
