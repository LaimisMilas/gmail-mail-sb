package lt.gmail.mail.sender;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
//          .apis(RequestHandlerSelectors.basePackage("lt.gmail.mail.sender"))              
          .paths(PathSelectors.ant("/api/*"))                        
          .build()
          .apiInfo(new ApiInfo(
        		  "Gmai-mail", 
        		  "",
        		  "1.0",
        		  "Free to use",
        		  new springfox.documentation.service.Contact("Laimonas", "", "laimis.milasius@gmail.com"),
        		  "",
        		  "",
        		  Collections.emptyList()));
    }

}
