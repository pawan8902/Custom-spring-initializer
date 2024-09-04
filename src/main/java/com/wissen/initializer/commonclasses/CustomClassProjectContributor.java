package com.wissen.initializer.commonclasses;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.spring.initializr.generator.project.contributor.ProjectContributor;

@Component
public class CustomClassProjectContributor implements ProjectContributor {
    private final Map<String, String> classFilePaths;
    private final Map<String, String> targetFolders;
    private final CustomProjectDescriptionCustomizer customizer;

    public CustomClassProjectContributor(Map<String, String> classFilePaths, Map<String, String> targetFolders, CustomProjectDescriptionCustomizer customizer) {
        this.classFilePaths = classFilePaths;
        this.targetFolders = targetFolders;
        this.customizer = customizer;
    }

    @Override
    public void contribute(Path projectRoot) throws IOException {
        String packageName = customizer.getPackageName();
        if (packageName == null) {
            throw new IllegalStateException("Package name must not be null");
        }

        for (Map.Entry<String, String> entry : classFilePaths.entrySet()) {
            String className = entry.getKey();
            String classFilePath = entry.getValue();

            // Get the target folder from properties
            String targetFolder = targetFolders.getOrDefault(className, "util");

            Path targetPath = projectRoot.resolve(
                    "src/main/java/" + packageName.replace('.', '/') + "/" + targetFolder + "/" + className + ".java");
            Files.createDirectories(targetPath.getParent());

            Path sourcePath = Paths.get(classFilePath);
            List<String> lines = Files.readAllLines(sourcePath);
            String packageDeclaration = "package " + packageName + "." + targetFolder + ";";

            if (!lines.isEmpty() && lines.get(0).startsWith("package ")) {
                lines.set(0, packageDeclaration);
            } else {
                lines.add(0, packageDeclaration);
            }

            Files.write(targetPath, lines);
        }
    }
}
