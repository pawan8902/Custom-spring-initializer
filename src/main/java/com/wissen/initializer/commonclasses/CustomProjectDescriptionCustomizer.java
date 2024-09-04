package com.wissen.initializer.commonclasses;

import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionCustomizer;
import org.springframework.stereotype.Component;

@Component
public class CustomProjectDescriptionCustomizer implements ProjectDescriptionCustomizer {

    private String packageName;

    @Override
    public void customize(MutableProjectDescription description) {
        this.packageName = description.getPackageName();
    }

    public String getPackageName() {
        return packageName;
    }
}
