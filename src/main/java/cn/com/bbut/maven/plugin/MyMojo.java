package cn.com.bbut.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import cn.com.bbut.maven.plugin.constant.Constants;
import cn.com.bbut.maven.plugin.dto.CodeGeneratorRequestDto;
import cn.com.bbut.maven.plugin.service.IndexService;
import cn.com.bbut.maven.plugin.serviceimpl.IndexServiceImpl;
import cn.com.bbut.maven.plugin.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;

/**
 * Goal which touches a timestamp file.
 *
 * @goal
 * @phase process-sources
 */
@Mojo(name = "generate")
public class MyMojo extends AbstractMojo {

    @Parameter(property = "yamlConfigPath")
    private String yamlConfigPath;

    public void execute()
            throws MojoExecutionException {
        CodeGeneratorRequestDto requestDto = null;
        String configPath = Utils.getAbsolutePath(Constants.THIS_GENERATOR_YAML);
        if(StringUtils.isNotBlank(yamlConfigPath)){
            configPath = yamlConfigPath;
        }
        try (InputStream input = new FileInputStream(configPath);
        ) {
            Yaml yaml = new Yaml();
            requestDto = yaml.loadAs(input, CodeGeneratorRequestDto.class);
            IndexService indexService = new IndexServiceImpl();
            indexService.codeGenerator(requestDto);
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("File not found", e);
        } catch (Exception e) {
            throw new MojoExecutionException("Unknown abnormal", e);
        }
    }
}
