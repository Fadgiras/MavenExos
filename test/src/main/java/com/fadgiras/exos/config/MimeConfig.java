package com.fadgiras.exos.config;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

public class MimeConfig {
    public static void main(String[] args,ConfigurableServletWebServerFactory factory) {

        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        mappings.add("doc", "application/msword");
        mappings.add("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        factory.setMimeMappings(mappings);
    }
}
