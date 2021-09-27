package com.wj.plugins.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import generate.AutoCoding;
import generate.entity.GenerateParam;



import java.util.HashMap;
import java.util.Map;

public class GCAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
//        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(e.getDataContext());
//        String children=file.getName();
        Project project =e.getData(PlatformDataKeys.PROJECT);
//        Messages.showMessageDialog(project,children,"0",Messages.getInformationIcon());
        String javaRootPath="src/test/java/so/dian/qa/ControllerTest";
        String jsonRootPath="src/test/resources/testData";
        String controllerName="TagRuleController";
        Map<Object,String> InterfaceNameList=new HashMap<>();
        InterfaceNameList.put("/v1/tag/rule/add","POST");
        InterfaceNameList.put("/v1/tag/rule/detail","GET");
        InterfaceNameList.put("/v1/tag/rule/page","GET");
        InterfaceNameList.put("/v1/tag/rule/edit","POST");
        InterfaceNameList.put("/v1/tag/rule/test","POST");
        InterfaceNameList.put("/v1/tag/rule/remove","POST");
        String ApplicationClassImport="so.dian.qa.riskcontrolTest.RiskControlTestApplication";
        GenerateParam generateParam=GenerateParam.builder()
                .JavaRootPath(javaRootPath)
                .JsonRootPath(jsonRootPath)
                .ControllerName(controllerName)
                .ApplicationClassImport(ApplicationClassImport)
                .InterfacePathMap(InterfaceNameList)
                .build();
        AutoCoding autoCoding=new AutoCoding();
        autoCoding.generate(generateParam);
    }
}
