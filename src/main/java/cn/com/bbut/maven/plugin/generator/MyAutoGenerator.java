package cn.com.bbut.maven.plugin.generator;

import cn.com.bbut.maven.plugin.constant.Constants;
import cn.com.bbut.maven.plugin.dto.CodeGeneratorStructDto;
import cn.com.bbut.maven.plugin.util.Utils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.function.ConverterFileName;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;

/**
 * @author guok
 * @date: 2021/11/3
 */
public class MyAutoGenerator {

    private final CodeGeneratorStructDto csd;

    public MyAutoGenerator(CodeGeneratorStructDto csd) {
        this.csd = csd;
    }

    public void execute() {
        FastAutoGenerator.create(dataSourceBuilder()).globalConfig(this::globalConfigBuilder)
                .packageConfig(this::packageConfigBuilder)
                .strategyConfig(this::strategyConfigBuilder)
                .templateConfig(this::templateConfigBuilder)
                .templateEngine(new VelocityTemplateEngine()).execute();
    }

    public DataSourceConfig.Builder dataSourceBuilder() {
        return new DataSourceConfig.Builder(csd.getDbUrl(), csd.getUsername(), csd.getPassword());
    }

    public void globalConfigBuilder(GlobalConfig.Builder builder) {
        String outPutDir = csd.getOutPutDir();
        if (StringUtils.isBlank(outPutDir)) {
            outPutDir = Utils.getAbsolutePath(Constants.THIS_GENERATOR_TARGET);
        }
        builder.outputDir(outPutDir); // 指定输出目录

        String author = csd.getAuthor();
        if (StringUtils.isBlank(author)) {
            author = "";
        }
        builder.author(author); // 设置作者

        if (csd.isEnableSwagger()) {
            builder.enableSwagger(); // 开启 swagger 模式
        }
        if (csd.isFileOverride()) {
            builder.fileOverride(); // 覆盖已生成文件
        }
    }

    public void packageConfigBuilder(PackageConfig.Builder builder) {
        builder.parent(csd.getPackageName()) // 设置父包名
                .moduleName(csd.getModuleName()); // 设置父包模块名
        String mapperXmlPath = csd.getMapperXmlPath();
        if (StringUtils.isBlank(mapperXmlPath)) {
            mapperXmlPath = Utils.getAbsolutePath(Constants.THIS_GENERATOR_TARGET_MAPPER);
        }
        builder.pathInfo(Collections.singletonMap(OutputFile.mapperXml, mapperXmlPath)); // 设置mapperXml生成路径
        // 设置 Entity 包名
        String packageEntity = csd.getPackageEntity();
        if (StringUtils.isNotBlank(packageEntity)) {
            builder.entity(packageEntity);
        }
        // 设置 Service 包名
        String packageService = csd.getPackageService();
        if (StringUtils.isNotBlank(packageService)) {
            builder.service(packageService);
        }
        // 设置 Service Impl 包名
        String packageServiceImpl = csd.getPackageServiceImpl();
        if (StringUtils.isNotBlank(packageServiceImpl)) {
            builder.serviceImpl(packageServiceImpl);
        }
        // 设置 Mapper 包名
        String packageMapper = csd.getPackageMapper();
        if (StringUtils.isNotBlank(packageMapper)) {
            builder.mapper(packageMapper);
        }
    }

    public void strategyConfigBuilder(StrategyConfig.Builder builder) {
        String serviceFileName = csd.getConvertServiceFileName();
        String serviceImplFileName = csd.getConvertServiceImplFileName();
        String mapperFileName = csd.getConvertMapperFileName();
        String xmlFileName = csd.getConvertXmlFileName();
        ConverterFileName converterServiceFileName = name -> csd.getConvertServiceFileName() + name
                + "Service";
        ConverterFileName converterServiceImplFileName = name -> csd
                .getConvertServiceImplFileName() + name + "ServiceImpl";
        ConverterFileName converterMapperFileName = name -> csd.getConvertMapperFileName() + name
                + "Mapper";
        ConverterFileName converterXmlFileName = name -> csd.getConvertXmlFileName() + name
                + "Mapper";
        Service.Builder serviceBuilder = builder.addInclude(csd.getTableNames()) // 设置需要生成的表名
                .serviceBuilder();
        if (StringUtils.isNotBlank(serviceFileName)) {
            serviceBuilder.convertServiceFileName(converterServiceFileName); // 转换service文件名称
        }
        if (StringUtils.isNotBlank(serviceImplFileName)) {
            serviceBuilder.convertServiceImplFileName(converterServiceImplFileName); // 转换service实现类文件名称
        }
        Mapper.Builder mapperBuilder = serviceBuilder.mapperBuilder();
        if (StringUtils.isNotBlank(mapperFileName)) {
            mapperBuilder.convertMapperFileName(converterMapperFileName);// 转换
                                                                         // mapper
                                                                         // 类文件名称
        }
        if (StringUtils.isNotBlank(xmlFileName)) {
            mapperBuilder.convertXmlFileName(converterXmlFileName);// 转换 xml
                                                                   // 类文件名称
        }
        if (csd.isEnableLombok()) {
            builder.entityBuilder().enableLombok(); // 启用lombok
        }
    }

    public void templateConfigBuilder(TemplateConfig.Builder builder) {
        builder.entity("templates/entity.java.vm"); // 设置entity模板
        if (csd.isDisControllerTemp()) {
            builder.disable(TemplateType.CONTROLLER); // 禁用controller模板
        }
    }
}
