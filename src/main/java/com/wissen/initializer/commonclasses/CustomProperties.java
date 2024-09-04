package com.wissen.initializer.commonclasses;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperties {

    private Map<String, String> classFilePaths;
    private Map<String, String> targetFolders;
    private String packageName;

    public Map<String, String> getClassFilePaths() {
        return classFilePaths;
    }

    public void setClassFilePaths(Map<String, String> classFilePaths) {
        this.classFilePaths = classFilePaths;
    }

    public Map<String, String> getTargetFolders() {
        return targetFolders;
    }

    public void setTargetFolders(Map<String, String> targetFolders) {
        this.targetFolders = targetFolders;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
