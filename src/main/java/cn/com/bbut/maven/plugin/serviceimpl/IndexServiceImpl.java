package cn.com.bbut.maven.plugin.serviceimpl;

import cn.com.bbut.maven.plugin.dto.CodeGeneratorRequestDto;
import cn.com.bbut.maven.plugin.dto.CodeGeneratorStructDto;
import cn.com.bbut.maven.plugin.enums.DbType;
import cn.com.bbut.maven.plugin.generator.MyAutoGenerator;
import cn.com.bbut.maven.plugin.service.IndexService;
import cn.hutool.core.bean.BeanUtil;
import org.codehaus.plexus.util.StringUtils;

/**
 * @author guok
 * @date: 2021/11/3
 */
public class IndexServiceImpl implements IndexService {

    @Override
    public void codeGenerator(CodeGeneratorRequestDto requestDto) {
        CodeGeneratorStructDto csd = new CodeGeneratorStructDto();
        BeanUtil.copyProperties(requestDto, csd);
        handleDb(csd, requestDto);
        handleTable(csd, requestDto);
        execute(csd);
    }

    private void execute(CodeGeneratorStructDto csd) {
        new MyAutoGenerator(csd).execute();
    }

    private void handleDb(CodeGeneratorStructDto csd, CodeGeneratorRequestDto requestDto) {
        String dbUrl = "";
        String username = requestDto.getDbUser();
        String password = requestDto.getDbPassword();
        if (StringUtils.isNotBlank(requestDto.getDbTypeName())
                && DbType.getDbType(requestDto.getDbTypeName()) == DbType.MYSQL) {
            // mysql
            dbUrl = "jdbc:mysql://"
                    + requestDto.getDbHost()
                    + "/"
                    + requestDto.getDbName()
                    + "?useSSL=false&generateSimpleParameterMetadata=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
        } else if (DbType.getDbType(requestDto.getDbTypeName()) == DbType.ORACLE) {
            dbUrl = "jdbc:oracle:thin:@" + requestDto.getDbHost() + ":" + requestDto.getDbName();
        } else {
            System.out.println("暂不支持的数据库类型");
        }
        csd.setDbUrl(dbUrl).setUsername(username).setPassword(password);
    }

    // 处理表
    private void handleTable(CodeGeneratorStructDto csd, CodeGeneratorRequestDto requestDto) {
        csd.setTableNames(requestDto.getTableNames());
    }
}
