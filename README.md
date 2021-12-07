## 代码自动生成
* 基于mybatis-plus代码生成器插件 3.5.3+
#### pom.xml 配置插件

```xml
<plugin>
    <groupId>cn.com.bbut.maven.plugin</groupId>
    <artifactId>mybatis-plus-generator</artifactId>
    <version>1.0</version>
    <configuration>
        <yamlConfigPath>xxx/xxx/generatorConfig.yaml</yamlConfigPath>
    </configuration>
</plugin>
```

#### 生成命令

```
mvn mybatis-plus-generator:generate
```

#### generatorConfig.yaml配置

* 配置路径 默认：项目根目录/gen/generatorConfig.yaml

```yaml
#必填
dbTypeName: oracle #数据库类型
dbHost: localhost:1521 #数据库host
dbName: dbname #数据库名称
dbUser: dbuser #数据库用户名
dbPassword: dbpassword #数据库密码
tableNames: ['tbl01','tbl02'] #数据库表名
#非必填
outPutDir: xxxx #默认target/generated-sources/mybatis-plus-generator
author: xxx #作者，默认空
packageName: xx.xx.xx.xxx #包名
moduleName: xxxx #模块名
enableSwagger: false  #启用swagger模式，默认false
fileOverride: true #文件覆盖，默认true
enableLombok: false #启用lombok模式，默认false
disControllerTemp: false  #禁用controller模板，默认false
mapperXmlPath: xxx/xx/xx #默认target/generated-sources/mybatis-plus-generator/mapper
convertServiceFileName: Pre #转换 service 接口文件名称  Pre + xxxxService
convertServiceImplFileName: Pre # 转换 service 实现类文件名称 Pre + xxxxServiceImpl
convertMapperFileName: Pre # 转换 mapper 类文件名称 Pre + xxxxMapper
convertXmlFileName: Pre # 转换 xml 文件名称 Pre + xxxxMapper.xml
packageEntity: model # Entity 包名
packageMapper: dao # Mapper 包名
packageService:  service # Service 包名
packageServiceImpl: serviceImpl # Service Impl 包名
```

