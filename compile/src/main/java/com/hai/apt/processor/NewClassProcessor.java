package com.hai.apt.processor;

import com.google.auto.service.AutoService;
import com.hai.annotation.NewClass;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
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
@AutoService(Processor.class)//此处注意，别写成了Process，不然此类不会执行
public class NewClassProcessor extends AbstractProcessor {
    private static final String TAG = "NewClassProcessor";
    private String packageName;
    public static final String PROXY = "Proxy";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        error("process begin init");
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        error("process begin process");
        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(NewClass.class);
        for (Element element : elementsAnnotatedWith) {
            if (element.getKind() == ElementKind.CLASS) {
                error(element.toString());
//                ExecutableElement executableElement = (ExecutableElement) element;
                TypeElement classElement = (TypeElement) element;
                //full class name
                String qfClassName = classElement.getQualifiedName().toString();
                String className = classElement.getSimpleName().toString();
                PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(classElement);
                //package name
                packageName = packageElement.getQualifiedName().toString();
                error("className=" + qfClassName);
                error("packageName=" + packageName);

                try {
                    JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(packageName + "." + className + "$$" + PROXY, classElement);
                    Writer writer = sourceFile.openWriter();
                    writer.write(getJavaCode(className + "$$" + PROXY));
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    error("generate java code error=" + e.getLocalizedMessage());
                }
            }
        }

        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        error("process begin getSupportedAnnotationTypes");
        Set<String> set = new LinkedHashSet<>();
        set.add(NewClass.class.getCanonicalName());
        return set;
    }

    public String getJavaCode(String proxyName) {
        StringBuilder builder = new StringBuilder();
        builder.append("// Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");
//        builder.append("import com.zhy.m.permission.*;\n");
        builder.append('\n');
        builder.append("public class ").append(proxyName).append(" {\n");
        generateMethod(builder);
        builder.append("}\n");

        return builder.toString();
    }

    private void generateMethod(StringBuilder builder) {
        builder.append("public String getString(){\n");
        builder.append("return \"this is zhe msg returned\";");
        builder.append("}\n");
    }


    private void error(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
