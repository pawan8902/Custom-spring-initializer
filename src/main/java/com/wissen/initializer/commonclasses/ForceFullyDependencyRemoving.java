package com.wissen.initializer.commonclasses;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.spring.build.BuildCustomizer;

@Component
public class ForceFullyDependencyRemoving implements BuildCustomizer<MavenBuild> {
	private static final Logger logger = LoggerFactory.getLogger(ForceFullyDependencyRemoving.class);

	@Override
	public void customize(MavenBuild mavenBuild) {
		Set<String> dependenciesBeforeRemoval = mavenBuild.dependencies().ids().collect(Collectors.toSet());
		logger.info("Dependencies before removal: {}", dependenciesBeforeRemoval);

		if (mavenBuild.dependencies().has("spring-boot-devtools")) {
			mavenBuild.dependencies().remove("spring-boot-devtools");
		}

		Set<String> dependenciesAfterRemoval = mavenBuild.dependencies().ids().collect(Collectors.toSet());
		logger.info("Dependencies after removal: {}", dependenciesAfterRemoval);
	}
}
