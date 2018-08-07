package com.wenda.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import com.wenda.dateroute.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Mybatis数据源配置
 * 参考：https://blog.csdn.net/YHYR_YCY/article/details/78894940
 *
 * @author evilhex.
 * @date 2018/7/27 下午2:48.
 */
@Configuration
public class DataSourceConfig {

    /**
     * 主库配置属性
     */
    @Value("${spring.datasource.master.url}")
    private String masterDBUrl;
    @Value("${spring.datasource.master.username}")
    private String masterDBUser;
    @Value("${spring.datasource.master.password}")
    private String masterDBPassword;

    /**
     * 从库配置属性
     */
    @Value("${spring.datasource.slave.url}")
    private String slaveDBUrl;
    @Value("${spring.datasource.slave.username}")
    private String slaveDBUser;
    @Value("${spring.datasource.slave.password}")
    private String slaveDBPassword;

    @Bean
    public DynamicDataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = DynamicDataSource.getInstance();

        //设置主库的配置
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setUrl(masterDBUrl);
        masterDataSource.setUsername(masterDBUser);
        masterDataSource.setPassword(masterDBPassword);

        //设置从库的配置
        DruidDataSource slaveDataSource = new DruidDataSource();
        slaveDataSource.setUrl(masterDBUrl);
        slaveDataSource.setUsername(masterDBUser);
        slaveDataSource.setPassword(masterDBPassword);

        Map<Object, Object> map = Maps.newHashMap();
        map.put("master", masterDataSource);
        map.put("slave", slaveDataSource);

        dynamicDataSource.setTargetDataSources(map);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);

        return dynamicDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        //设置mybatis配置文件的位置
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
