package util;

/**
 * @author wj
 * @date 2021-09-26
 */
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;

/**
 * @author lww
 * @date 2020-05-27 19:59
 */
public class SelectUtil {

    public static String getSelectString(AnActionEvent e) {
        //获取选择的文本
        Editor editor = e.getData(CommonDataKeys.EDITOR);
        if (editor == null) {
            return null;
        }
        String data = editor.getSelectionModel().getSelectedText();
        if (data == null || data.isEmpty()) {
            return null;
        }
        return data;
    }
}
