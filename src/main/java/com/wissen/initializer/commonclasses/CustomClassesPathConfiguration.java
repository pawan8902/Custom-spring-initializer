package com.wissen.initializer.commonclasses;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class CustomClassesPathConfiguration {

    @Bean
    public CustomClassProjectContributor customClassProjectContributor(CustomProjectDescriptionCustomizer customizer,
                                                                       CustomProperties customProperties) {
        return new CustomClassProjectContributor(customProperties.getClassFilePaths(), customProperties.getTargetFolders(), customizer);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
