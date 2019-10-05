package com.chen.k8sgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class K8sgatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(K8sgatewayApplication.class, args);
    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                //增加一个path匹配，以"/customize/hello/"开头的请求都在此路由
                .route(r -> r.path("/download-server/**")
                        //表示将路径中的第一级参数删除，用剩下的路径与webdemo的路径做拼接，
                        //这里就是"lb://webdemo/hello/"，能匹配到webdemo的HelloController的路径
                        .filters(f -> f.stripPrefix(1)
                                //在请求的header中添加一个key&value
                                .addRequestHeader("extendtag", "geteway-" + System.currentTimeMillis()))
                        //指定匹配服务webdemo，lb是load balance的意思
                        .uri("lb://download-server")
                ).build();
    }
    @RequestMapping("/nihao")
    public String nihao(){
        return  "nihao";
    }

}
