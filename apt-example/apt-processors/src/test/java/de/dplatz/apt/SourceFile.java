package de.dplatz.apt;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class SourceFile {
	private final String fileName;
	private final Collection<String> content;

	public SourceFile(String fileName, String... content) {
		this.fileName = fileName;
		this.content = Arrays.stream(content).collect(Collectors.toList());
	}

	public String getFileName() {
		return fileName;
	}

	public Collection<String> getContent() {
		return content;
	}
}