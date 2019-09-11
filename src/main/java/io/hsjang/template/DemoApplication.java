package io.hsjang.template;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@SpringBootApplication
@EnableWebFlux
public class DemoApplication implements WebFluxConfigurer{

	@Value("classpath:/static/index.html") Resource index;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public RouterFunction<ServerResponse> resources(@Value("classpath:/static/index.html") Resource index) {
		return RouterFunctions.route(RequestPredicates.GET("/"), req -> ServerResponse.ok().contentType(MediaType.TEXT_HTML).body(index));
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("/", "classpath:/static/").setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS));
    }

}
