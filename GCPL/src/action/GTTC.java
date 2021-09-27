package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import generate.AutoCoding;
import util.SelectUtil;

import java.security.MessageDigest;

public class GTTC extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Project project=e.getProject();
        String projectBasePath=project.getBasePath();
        String data = SelectUtil.getSelectString(e);
        Messages.showMessageDialog(project,"开始","Success",Messages.getInformationIcon());
        if (data == null) {
            Messages.showMessageDialog(project,"传入数据为空","Fail",Messages.getErrorIcon());
            return;
        }
        AutoCoding autoCoding=new AutoCoding();
        try{
            autoCoding.paresForGenerate(data,projectBasePath);
            Messages.showMessageDialog(project,"创建成功","Success",Messages.getInformationIcon());
        }catch (Exception exception){
            Messages.showMessageDialog(project,"创建失败","Fail",Messages.getErrorIcon());
        }


    }
}
