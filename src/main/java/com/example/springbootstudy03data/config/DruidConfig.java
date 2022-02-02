package com.example.springbootstudy03data.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {
    //与application.yml连接起来
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
    //后台监控(只要进入特定网址就会开始监控后台):web.xml,ServletRegistrationBean
    //因为springboot内置了servlet容器，所以没有web.xml。替代方法：ServletRegistrationBean
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");//注意必须是一个*，两个**会报错
        //后台需要有人登陆，账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        //增加配置(两个key都是固定的)
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword", "123456");
        //允许谁可以访问(value为空则所有人都可访问；localhost则本机可以访问；或为特定的人)
        initParameters.put("allow", "");
        //禁止谁能访问
        //initParameters.put("yang", "192.168.11.123");

        bean.setInitParameters(initParameters);//设置初始化参数
        return bean;
    }

    //filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        //设置过滤器
        bean.setFilter(new WebStatFilter());
        //可以过滤那些请求
        HashMap<String, String> initParameters = new HashMap<>();
        //这些不进行统计
        initParameters.put("exclusion","*.js,*.css,/druid/*");
        bean.setInitParameters(initParameters);
        return bean;
    }
}
