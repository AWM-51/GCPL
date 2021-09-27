package generate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import generate.entity.GenerateParam;
import generate.entity.JavaProperties;
import org.elasticsearch.common.collect.CopyOnWriteHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import so.dian.qa.heimdallr.util.doString.DoStringUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AutoCoding {
    private final static Logger logger = LoggerFactory.getLogger(AutoCoding.class);
    /**
     * 简单的代码生成器.
     *
     * @param rootPath       maven 的  java 目录
     * @param templatePath   模板存放的文件夹
     * @param templateName   模板的名称
     * @param javaProperties 需要渲染对象的封装
     * @throws IOException       the io exception
     * @throws TemplateException the template exception
     */
    private static void autoCodingJava(String rootPath,
                                            String templatePath,
                                            String templateName,
                                            JavaProperties javaProperties) throws IOException, TemplateException, IOException, TemplateException {

        System.out.println("------->java生成<-------");
        // freemarker 配置
        Configuration configuration=new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        configuration.setDefaultEncoding("UTF-8");
        // 指定模板的路径
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        // 根据模板名称获取路径下的模板
        Template template=configuration.getTemplate(templateName);
        // 处理路径问题
        String ext=".java";
        String javaName=javaProperties.getclassName().concat(ext);
        String out =rootPath+"/"+javaName;

        // 定义一个输出流来导出代码文件
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(out));
        // freemarker 引擎将动态数据绑定的模板并导出为文件
        template.process(javaProperties,outputStreamWriter);

    }

    /**
     * 简单的json文件生成
     * */
    private static void autoCodingJsonData(String rootPath,
                                             String templatePath,
                                             String templateName,
                                             JavaProperties javaProperties) throws IOException, TemplateException, IOException, TemplateException {

        System.out.println("------->json生成<-------");
        // freemarker 配置
        Configuration configuration=new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        configuration.setDefaultEncoding("UTF-8");
        // 指定模板的路径
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        // 根据模板名称获取路径下的模板
        Template template=configuration.getTemplate(templateName);
        // 处理路径问题
        String ext=".json";
        String jsonName=DoStringUtil.lineToHump(javaProperties.getUrl()[0].replace("/","_")).concat(ext);
        String out =rootPath+"/"+jsonName;

        // 定义一个输出流来导出代码文件
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(out));
        // freemarker 引擎将动态数据绑定的模板并导出为文件
        template.process(javaProperties,outputStreamWriter);

    }

    /**
     * 单controller java/json 生成
     * */
    public static int generate(GenerateParam generateParam){
        System.out.println("------->开始生成<-------");
        String javaRootPath=generateParam.getJavaRootPath();
        String JsonRootPath=generateParam.getJsonRootPath();
        String ControllerName=generateParam.getControllerName();
        Map<Object,String> InterfacePathMap=generateParam.getInterfacePathMap();
        String ApplicationClassImport=generateParam.getApplicationClassImport();


        String path=AutoCoding.class.getClassLoader().getResource("").getPath();
        //String path3=Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String FtlPath=path+"flt";
        logger.info("-----flt路径------"+FtlPath);
        String javaFtlName="ControllerTestJava.ftl";
        String jsonFtlName="JsonData.ftl";
        List<String> JPStrList= Arrays.asList(javaRootPath.split(".java."));
        if(JPStrList.size()!=2){
            return 3;
        }
        String pkg=JPStrList.get(1).replace("/",".");
        String ApplicationClassName=ApplicationClassImport.split("\\.")[ApplicationClassImport.split("\\.").length-1]+".class";
        JavaProperties javaProperties=new JavaProperties(ControllerName+"Test",pkg);
        //方法赋值
        InterfacePathMap.keySet();
        javaProperties.addImport(ApplicationClassImport);
        javaProperties.setExtProperty("SpringBootTest",ApplicationClassName);
        //创建java/json文件夹
        JsonRootPath=JsonRootPath+"/"+ControllerName+"Test";
        File jsonfile = new File(JsonRootPath);
        File javafile = new File(javaRootPath);
        if (!javafile.exists() && !javafile.isDirectory()) {
            javafile.mkdirs();
        }
        if (!jsonfile.exists() && !jsonfile.isDirectory()) {
            jsonfile.mkdirs();
        }
        //循环赋值方法与创建不同方法的json文件
        for(Object key : InterfacePathMap.keySet()){
            JavaProperties.Method method=new JavaProperties.Method();
            String methodName=DoStringUtil.lineToHump(key.toString().replace("/","_"));
            method.setMethodName(methodName);
            method.setMethodType("void");
            javaProperties.setUrl(key.toString(),InterfacePathMap.get(key));
            javaProperties.addMethod(method);
            //创建 json文件
            try {
                autoCodingJsonData(JsonRootPath,FtlPath,jsonFtlName,javaProperties);
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            } catch (TemplateException e) {
                e.printStackTrace();
                return 0;
            }
        }
        //创建jave文件
        try {
            autoCodingJava(javaRootPath,FtlPath,javaFtlName,javaProperties);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        } catch (TemplateException e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * json 格式如下
     * {
     *     "targetsData":[
     *     {
     *         "controllerName":"xxx",
     *         "javaRootPath":"xxx",
     *         "jsonRootPath":"xxx",
     *         "applicationClassPagPath":"xxx",
     *         "Interfaces":[
     *              {
     *                  "url":"xxx",
     *                  "type":"xxx"
     *              }
     *         ]
     *     }
     *     ]
     *
     * }
     * */
    public static void paresForGenerate(String paramJO,String projectBasePath) {
        JSONObject jsonObject= JSON.parseObject(paramJO);
        JSONArray jsonArray=jsonObject.getJSONArray("targetsData");

        jsonArray.stream().forEach(base->{
            JSONObject baseJO=JSON.parseObject(base.toString());
            JSONArray InterfaceList=baseJO.getJSONArray("Interfaces");
            Map<Object,String> InterfaceMap=new ConcurrentHashMap<Object,String>();
            InterfaceList.stream().forEach(a->{
                JSONObject jsonObject1=JSON.parseObject(a.toString());
                InterfaceMap.put(jsonObject1.getString("url"),jsonObject1.getString("type"));
            });
            //构建生成入参
            GenerateParam generateParam= GenerateParam.builder()
                    .JavaRootPath(projectBasePath+"/"+baseJO.getString("javaRootPath"))
                    .JsonRootPath(projectBasePath+"/"+baseJO.getString("jsonRootPath"))
                    .InterfacePathMap(InterfaceMap)
                    .ApplicationClassImport(baseJO.getString("applicationClassPagPath"))
                    .ControllerName(baseJO.getString("controllerName"))
                    .build();
            //生成代码
            generate(generateParam);
        });

    }


}
