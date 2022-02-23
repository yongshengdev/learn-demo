package com.sign.lib_processor;


import com.sign.lib_interface.BindView;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

public class BindingProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Filer mFiler;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        // 主要是输出一些重要的日志使用
        mMessager = processingEnv.getMessager();
        // 你就理解成最终我们写java文件 要用到的重要 输出参数即可
        mFiler = processingEnv.getFiler();
        // 一些方便的utils方法
        mElementUtils = processingEnv.getElementUtils();
        // 这里要注意的是Diagnostic.Kind.ERROR 是可以让编译失败的 一些重要的参数校验可以用这个来提示用户你哪里写的不对
        mMessager.printMessage(Diagnostic.Kind.NOTE, " BindingProcessor init");
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // key 就是使用注解的class的类名 element就是使用注解本身的元素 一个class 可以有多个使用注解的field
        Map<String, List<Element>> fieldMap = new HashMap<>();
        // 这里 获取到 所有使用了 BindView 注解的 element
        for (Element element : roundEnvironment.getElementsAnnotatedWith(BindView.class)) {
            //取到 这个注解所属的class的Name
            String className = element.getEnclosingElement().getSimpleName().toString();
            //取到值以后 判断map中 有没有 如果没有就直接put 有的话 就直接在这个value中增加一个element
            if (fieldMap.get(className) != null) {
                List<Element> elementList = fieldMap.get(className);
                elementList.add(element);
            } else {
                List<Element> elements = new ArrayList<>();
                elements.add(element);
                fieldMap.put(className, elements);
            }
        }

        //遍历map，开始生成辅助类
        for (Map.Entry<String, List<Element>> entry : fieldMap.entrySet()) {
            try {
//                generateCodeByStringBuffer(entry.getKey(), entry.getValue());
                generateCodeByJavapoet(entry.getKey(), entry.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void generateCodeByJavapoet(String className, List<Element> elements) throws IOException {
        //声明构造方法
        MethodSpec.Builder constructMethodBuilder =
                MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).addParameter(ClassName.bestGuess(className), "activity");
        //构造方法里面 增加语句
        for (Element e : elements) {
            constructMethodBuilder.addStatement("activity." + e.getSimpleName() + "=activity.findViewById(" + e.getAnnotation(BindView.class).value() + ");");
        }

        //声明类
        TypeSpec viewBindingClass =
                TypeSpec.classBuilder(className + "ViewBinding").addModifiers(Modifier.PUBLIC).addMethod(constructMethodBuilder.build()).build();
        String packageName = mElementUtils.getPackageOf(elements.get(0)).getQualifiedName().toString();

        JavaFile build = JavaFile.builder(packageName, viewBindingClass).build();
        build.writeTo(mFiler);
    }

    private void generateCodeByStringBuffer(String className, List<Element> elements) throws IOException {
        //你要生成的类 要和 注解的类 同属一个package 所以还要取 package的名称
        String packageName = mElementUtils.getPackageOf(elements.get(0)).getQualifiedName().toString();
        StringBuffer sb = new StringBuffer();
        // 每个java类 的开头都是package sth...
        sb.append("package ");
        sb.append(packageName);
        sb.append(";\n");

        // public class XXXActivityViewBinding {
        final String classDefine = "public class " + className + "ViewBinding { \n";
        sb.append(classDefine);

        //定义构造函数的开头
        String constructorName = "public " + className + "ViewBinding(" + className + " activity){ \n";
        sb.append(constructorName);

        //遍历所有element 生成诸如 activity.tv=activity.findViewById(R.id.xxx) 之类的语句
        for (Element e : elements) {
            sb.append("activity." + e.getSimpleName() + "=activity.findViewById(" + e.getAnnotation(BindView.class).value() + ");\n");
        }

        sb.append("\n}");
        sb.append("\n }");

        //文件内容确定以后 直接生成即可
        JavaFileObject sourceFile = mFiler.createSourceFile(className + "ViewBinding");
        Writer writer = sourceFile.openWriter();
        writer.write(sb.toString());
        writer.close();
    }

    // 要支持哪些注解
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }
}