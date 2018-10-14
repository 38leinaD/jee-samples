package de.dplatz.apt;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SmartCommentProcessor extends AbstractProcessor {

	private Filer filer;
	private int round = 0;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);

		filer = processingEnv.getFiler();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		for (TypeElement annotation : annotations) {
			if (annotation.getQualifiedName().toString().equals("java.lang.Deprecated")) {
				Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
				annotatedElements.forEach(element -> processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
						"Uhhhh. Deprecated. Better remove it.",
						element));
			}
		}

		try {
			if (round == 0)
				writeFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		round++;
		return false;
	}

	private void writeFile() throws IOException {
		MethodSpec main = MethodSpec.methodBuilder("main")
				.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
				.returns(void.class)
				.addParameter(String[].class, "args")
				.addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
				.build();

		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL)
				.addMethod(main)
				.build();

		JavaFile javaFile = JavaFile.builder("de.dplatz.apt", helloWorld)
				.build();

		JavaFileObject srcFile = filer.createSourceFile("de.dplatz.apt." + helloWorld.name);
		Writer writer = srcFile.openWriter();
		writer.write(javaFile.toString());
		writer.close();

	}
}