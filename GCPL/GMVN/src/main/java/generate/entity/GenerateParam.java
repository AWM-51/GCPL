package generate.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author wj
 * @date 2021-02-07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateParam {
    private String JavaRootPath;
    private String JsonRootPath;
    private String ControllerName;
    private Map<Object,String> InterfacePathMap;
    private String ApplicationClassImport;
}
