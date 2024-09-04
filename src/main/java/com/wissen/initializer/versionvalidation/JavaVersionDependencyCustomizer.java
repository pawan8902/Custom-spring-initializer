package com.wissen.initializer.versionvalidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.spring.initializr.generator.buildsystem.Dependency;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescriptionCustomizer;

@Component
public class JavaVersionDependencyCustomizer implements ProjectDescriptionCustomizer {

    private final Map<String, Map<String, List<String>>> dependencyMetadata;
    private List<String> unsupportedDependencies;

    public JavaVersionDependencyCustomizer(Map<String, Map<String, List<String>>> dependencyMetadata) {
        this.dependencyMetadata = dependencyMetadata;
        this.unsupportedDependencies = new ArrayList<>();
    }

    @Override
    public void customize(MutableProjectDescription description) {
        String javaVersion = description.getLanguage().jvmVersion();
        String springBootVersion = description.getPlatformVersion().toString();
        
        // Filter dependencies
        filterDependencies(description, javaVersion, springBootVersion);
    }

    private void filterDependencies(MutableProjectDescription description, String javaVersion, String springBootVersion) {
        Map<String, Dependency> requestedDependencies = new HashMap<>(description.getRequestedDependencies());

        for (Map.Entry<String, Dependency> entry : requestedDependencies.entrySet()) {
            Dependency dependency = entry.getValue();
            Map<String, List<String>> versionMetadata = dependencyMetadata.get(dependency.getArtifactId());

            if (versionMetadata != null) {
                List<String> compatibleJavaVersions = versionMetadata.get(springBootVersion);

                if (compatibleJavaVersions == null || !compatibleJavaVersions.contains(javaVersion)) {
                    description.removeDependency(entry.getKey());
                    unsupportedDependencies.add(String.format("Dependency '%s' (version: '%s') is not supported with Java version '%s' and Spring Boot version '%s'.",
                            dependency.getArtifactId(), dependency.getVersion(), javaVersion, springBootVersion));
                }
            }
        }
    }

    public List<String> getUnsupportedDependencies() {
        return unsupportedDependencies;
    }
}
