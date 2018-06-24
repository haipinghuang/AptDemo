package com.hai.apt.processor;

import com.google.auto.service.AutoService;
import com.hai.annotation.NewClass;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * ${desc}
 * create by huanghp at 2018/6/20
 * email 1132760021@qq.com
 */
@AutoService(Process.class)
public class NewClassProcessor extends AbstractProcessor {
    private static final String TAG = "NewClassProcessor";
    private String packageName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        System.out.println(TAG + " getSupportedAnnotationTypes() called");
        Set<String> set = new LinkedHashSet<>();
        set.add(NewClass.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return super.getSupportedSourceVersion();
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        error("process begin");
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(NewClass.class);
        for (Element element : elementsAnnotatedWith) {
            if (element.getKind() == ElementKind.CLASS) {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement classElement = (TypeElement) executableElement.getEnclosingElement();
                String qfClassName = classElement.getQualifiedName().toString();
                PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(classElement);
                String packageName = packageName = packageElement.getQualifiedName().toString();
                System.out.println("className=" + qfClassName);
                System.out.println("packageName=" + packageName);

                try {
                    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(packageName + "." + "MyTest", executableElement.getEnclosingElement());
                    Writer writer = sourceFile.openWriter();
                    writer.write(getJavaCode());
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    error("generate java code error="+e.getLocalizedMessage());
                }
            }
        }

        return true;
    }

    public String getJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
//        builder.append("import com.zhy.m.permission.*;\n");
        builder.append('\n');
        builder.append("public class ").append("MyTest");
        builder.append(" {\n");

//        generateMethods(builder);
//        builder.append('\n');

        builder.append("}\n");

        return builder.toString();
    }

    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg);
    }
}
