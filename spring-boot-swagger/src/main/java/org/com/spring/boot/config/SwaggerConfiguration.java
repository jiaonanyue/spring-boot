package org.com.spring.boot.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger配置项
 *
 * @description
 * @author jiaonanyue
 * @date 2017年8月2日
 * @since 1.7
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurerAdapter implements EnvironmentAware {

	private String basePackage;
	private String creatName;
	private String serviceName;
	private RelaxedPropertyResolver propertyResolver;
	private String description;
	private String url;
	private String mail;


	/**
	 * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
	 *  <!--swagger资源配置-->
	 *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
	 *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
	 *
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars*")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


	
	@Bean
	public Docket createRestApi() {
		ParameterBuilder tokenPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		tokenPar.name("x-access-token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
		pars.add(tokenPar.build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage(this.basePackage))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(pars)  ;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title(this.serviceName)
				.description(this.description)
				.contact(new Contact(this.creatName,this.url,this.mail)).version("1.0").build();
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, null);
		this.basePackage = propertyResolver.getProperty("swagger.basepackage");
		this.creatName = propertyResolver.getProperty("swagger.service.developer");
		this.serviceName = propertyResolver.getProperty("swagger.service.name");
		this.description = propertyResolver.getProperty("swagger.service.description");
		this.url = propertyResolver.getProperty("swagger.service.url");
		this.mail = propertyResolver.getProperty("swagger.service.mail");
	}
}
