package com.sugeladi.buyunju.config;

import com.sugeladi.buyunju.filter.CostFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean configureFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean<>();
        bean.setName("costFilter");
        CostFilter costFilter = new CostFilter();
        bean.setFilter(costFilter);
        bean.setOrder(1);
        List<String> urlList = new ArrayList<String>();
        urlList.add("/*");
        bean.setUrlPatterns(urlList);
        return bean;
    }
}

