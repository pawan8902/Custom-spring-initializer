package com.wissen.initializer.versionvalidation;

import io.spring.initializr.generator.project.contributor.ProjectContributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CustomPomContributor implements ProjectContributor {

	private final JavaVersionDependencyCustomizer javaVersionDependencyCustomizer;

	@Autowired
	public CustomPomContributor(JavaVersionDependencyCustomizer javaVersionDependencyCustomizer) {
		this.javaVersionDependencyCustomizer = javaVersionDependencyCustomizer;
	}

	@Override
	public void contribute(Path projectRoot) throws IOException {
		List<String> unsupportedDependencies = javaVersionDependencyCustomizer.getUnsupportedDependencies();

		if (!unsupportedDependencies.isEmpty()) {
			Path pomFile = projectRoot.resolve("pom.xml");
			String pomContent = new String(Files.readAllBytes(pomFile));

			StringBuilder messageBuilder = new StringBuilder();
			for (String message : unsupportedDependencies) {
				messageBuilder.append(message).append("\n");
			}
			if (pomContent.contains("<properties>")) {
				String updatedPomContent = pomContent.replace("</properties>",
						String.format("<unsupportedDependencies>\n%s</unsupportedDependencies>\n</properties>",
								messageBuilder.toString()));
				Files.write(pomFile, updatedPomContent.getBytes());
			} else {
				String updatedPomContent = pomContent.replace("</project>", String.format(
						"<properties>\n<unsupportedDependencies>\n%s</unsupportedDependencies>\n</properties>\n</project>",
						messageBuilder.toString()));
				Files.write(pomFile, updatedPomContent.getBytes());
			}
		}
	}
}
