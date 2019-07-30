package pink.coursework.csvparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${avatar.path}")
    private String avatarPath;
    @Value("${file.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/icon/**").
                addResourceLocations("file:"+avatarPath);
        //registry.addResourceHandler("/file/**").
        //        addResourceLocations("file:"+filePath);
        registry.addResourceHandler("/images/**").
                addResourceLocations("classpath:/static/images/");
    }
}
