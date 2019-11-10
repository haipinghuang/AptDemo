# AptDemo
AptDemo used for apt study，very simple;

实现：
1. 定义annotation
2. 使用annotation，如注解类、方法、字段、参数等
3. 继承AbstractProcessor实现自己的注解处理逻辑（根据得到的注解信息动态生成类、方法等）
4. 写个第三方类调用动态生成的类的方法、字段等

原理：动态生成类、方法；动态加载类并调用类的方法
