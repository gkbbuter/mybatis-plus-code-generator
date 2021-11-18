package cn.com.bbut.maven.plugin.service;


import cn.com.bbut.maven.plugin.dto.CodeGeneratorRequestDto;

/**
 * @author guok
 * @date: 2021/11/3
 */
public interface IndexService {
    void codeGenerator(CodeGeneratorRequestDto dto);
}
