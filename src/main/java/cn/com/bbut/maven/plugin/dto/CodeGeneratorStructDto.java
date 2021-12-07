package cn.com.bbut.maven.plugin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author guok
 * @date: 2021/11/3
 */
@Data
@Accessors(chain = true)
public class CodeGeneratorStructDto {
    // 数据库url
    private String dbUrl;
    // 数据库用户名
    private String username;
    // 数据库密码
    private String password;
    // 数据库表名
    private String[] tableNames;

    // 输出路径
    private String outPutDir;
    // 作者
    private String author;
    // 包名
    private String packageName;
    // 模块名
    private String moduleName;
    // 启动Swagger模式
    private boolean enableSwagger;
    // 文件覆盖
    private boolean fileOverride;
    // 启动lombok模式
    private boolean enableLombok;
    // 启动debug模式
    private boolean isDebug;
    // 禁用controller模板
    private boolean disControllerTemp;
    // 设置mapperXml生成路径
    private String mapperXmlPath;
    // 转换 service 接口文件名称
    private String convertServiceFileName;
    // 转换 service 实现类文件名称
    private String convertServiceImplFileName;
    // 转换 mapper 类文件名称
    private String convertMapperFileName;
    // 转换 xml 文件名称
    private String convertXmlFileName;
    // Entity 包名
    private String packageEntity;
    // Service 包名
    private String packageService;
    // Service Impl 包名
    private String packageServiceImpl;
    // Mapper 包名
    private String packageMapper;
}
