package com;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.mapper")
@SpringBootApplication
public class Sub
{
    public static void main(String[] args)
    {
        SpringApplication.run(Sub.class, args);

    }


}
