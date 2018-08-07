package com.wenda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 一个简易的问答系统：Spring boot,redis，mybatis,主从分离，异步队列，字典树过滤敏感词
 * 1、为实现动态数据源配置，需要把Spring自带的数据库配置禁止；
 *
 * @author evilhex
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class WendaApplication {

    public static void main(String[] args) {

        SpringApplication.run(WendaApplication.class, args);
    }
}
