package com.idle.kb_i_dle_backend.config;

import com.idle.kb_i_dle_backend.domain.member.util.JwtInterceptor;
import com.idle.kb_i_dle_backend.domain.member.util.JwtProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "com.idle.kb_i_dle_backend"
})
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class WebConfig implements WebMvcConfigurer {

    public WebConfig() {
        System.out.println("WebConfig created");
    }

    @Autowired
    @Lazy
    private JwtInterceptor jwtInterceptor;

    @Bean
    public JwtProcessor jwtProcessor() {
        return new JwtProcessor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // jwtInterceptor() 메서드를 호출하여 빈을 주입
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/*/**"); // 특정 URL 패턴에 대해 인터셉터 적용
    }

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // CORS 정책 오류 해결
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowedOrigins("http://localhost:5173")
                .allowCredentials(true); // 프론트엔드 도메인
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);
        return encodingFilter;
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        // css, javascript, image
        registry.addResourceHandler("/**")
                .addResourceLocations("/resources/");

        // 파일 업로드를 위한 리소스 핸들러
        exposeDirectory("fileupload", registry);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() throws IOException {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setResolveLazily(true);
        resolver.setMaxUploadSize(20 * 1024 * 1024);
        resolver.setMaxUploadSizePerFile(20 * 1024 * 1024);
        return resolver;
    }

    // 파일을 업로드하면 HTTP URL로 접근 가능하도록 설정
    void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get(dirName);
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        if (dirName.startsWith("../")) {
            dirName = dirName.replace("../", "");
        }

        registry
                .addResourceHandler("/" + dirName + "/**")
                .addResourceLocations("file:/" + uploadPath + "/");
    }
}
