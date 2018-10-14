package de.dplatz.apt;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic.Kind;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

public class SmartProcessorTest {
	private Messager mockMessager;
	private ProcessorWrapper processor;
	private Compiler compiler;

	@BeforeEach
	public void setup() throws Exception {
		mockMessager = Mockito.mock(Messager.class);
		processor = new ProcessorWrapper(new SmartCommentProcessor(), mockMessager);
		compiler = new Compiler();
	}

	@Test
	public void should_generate_warning() throws Exception {
		SourceFile[] sourceFiles = { new SourceFile(
				"SimpleAnnotated.java",
				"@java.lang.Deprecated",
				"public class SimpleAnnotated {}") };

		compiler.compileWithProcessor(processor, sourceFiles);
		verifyPrintMessage(
				Kind.WARNING,
				"Uhhhh. Deprecated. Better remove it.",
				"SimpleAnnotated");
		Mockito.verifyNoMoreInteractions(mockMessager);
	}

	private void verifyPrintMessage(Kind kind, String message, String elementName) {
		Mockito.verify(mockMessager).printMessage(
				ArgumentMatchers.eq(kind),
				ArgumentMatchers.eq(message),
				Mockito.any());
	}
}
