package org.demo02.utils;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("实训项目API文档") //文档的标题
                        .description("实训项目后台接口说明文档") //文档的描述
                        .version("v1.0.0") //文档的版本
                        .contact(new Contact()
                                .name("哈基米"))) //文档的作者
                .externalDocs(new ExternalDocumentation() //文档的扩展信息
                        .url("http://localhost:8089/demo02/doc.html"));//文档的访问地址
    }
}
