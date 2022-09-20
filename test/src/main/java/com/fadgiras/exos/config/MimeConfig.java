package com.fadgiras.exos.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MimeConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("doc", "application/msword");
        mappings.add("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mappings.add("odt","application/vnd.oasis.opendocument.text");
        mappings.add("pdf","application/pdf");
        factory.setMimeMappings(mappings);
    }
}