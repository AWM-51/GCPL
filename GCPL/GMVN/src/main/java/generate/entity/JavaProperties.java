package generate.entity;

import java.util.*;

/**
 * @author wj
 * @date 2021-09-16
 */
public class JavaProperties {
    // 包名
    private final String pkg;
    // 类名
    private final String className;
    // 属性集合  需要改写 equals hash 保证名字可不重复 类型可重复
    private final Set<Field> fields = new LinkedHashSet<>();
    // 导入类的不重复集合
    private final Set<String> imports = new LinkedHashSet<>();



    //url
    private final String[] url = new String[2];




    //额外属性map
    private final Map<Object,Object> extProperty=new HashMap<>();



    // 导入类的不重复集合
    private final Set<Method> methods = new LinkedHashSet<>();


    public JavaProperties(String className, String pkg) {
        this.className = className;
        this.pkg = pkg;
    }

    public String getClassName() {
        return className;
    }

    public void setUrl(String url,String type){
        this.url[0]=url;
        this.url[1]=type;
    }

    public String[] getUrl() {
        return this.url;
    }


    public void setExtProperty(Object key,Object value) {
        this.extProperty.put(key,value);
    }

    public Map<Object, Object> getExtProperty() {
        return this.extProperty;
    }




    public void addImport(String importStr){
        this.imports.add(importStr);
    }

    public void addField(Class<?> type, String fieldName) {
        // 处理 java.lang
        final String pattern = "java.lang";
        String fieldType = type.getName();
        if (!fieldType.startsWith(pattern)) {
            // 处理导包
            imports.add(fieldType);
        }
        Field field = new Field();
        // 处理成员属性的格式
        int i = fieldType.lastIndexOf(".");
        field.setFieldType(fieldType.substring(i + 1));
        field.setFieldName(fieldName);
        fields.add(field);
    }

    public void addMethod(Method method){
        if(null!=method){
            methods.add(method);
        }
    }



    public String getPkg() {
        return pkg;
    }


    public String getclassName() {
        return className;
    }


    public Set<Field> getFields() {
        return fields;
    }

    public Set<String> getImports() {
        return imports;
    }

    public Set<Method> getMethods() {
        return methods;
    }



    /**
     * 成员属性封装对象.
     */
    public static class Field {
        // 成员属性类型
        private String fieldType;
        // 成员属性名称
        private String fieldName;

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        /**
         * 一个类的成员属性 一个名称只能出现一次
         * 我们可以通过覆写equals hash 方法 然后放入Set
         *
         * @param o 另一个成员属性
         * @return 比较结果
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Field field = (Field) o;
            return Objects.equals(fieldName, field.fieldName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(fieldType, fieldName);
        }
    }

    /**
     * 成员方法封装对象
     * */
    public static class Method {

        // 方法类型
        private String methodType;
        // 方法名
        private String methodName;
        // 方法作用域
        private Map<String,String> param;

        public String getMethodType() {
            return methodType;
        }

        public void setMethodType(String methodType) {
            this.methodType = methodType;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Map<String, String> getParam() {
            return param;
        }

        public void setParam(Map<String, String> param) {
            this.param = param;
        }

        /**
         * 一个类的成员属性 一个名称只能出现一次
         * 我们可以通过覆写equals hash 方法 然后放入Set
         *
         * @param o 另一个成员属性
         * @return 比较结果
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Method method = (Method) o;
            return Objects.equals(methodName, method.methodName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(methodType, methodName,param);
        }
    }

}
