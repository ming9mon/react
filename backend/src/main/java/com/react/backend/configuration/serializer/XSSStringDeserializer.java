package com.react.backend.configuration.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import java.io.IOException;

public class XSSStringDeserializer extends JsonDeserializer<String> {
    // OWASP Sanitizers를 사용하여 서식과 링크만 허용하는 정책을 생성
    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {
        String value = p.getText();
        if (value != null) {
            value = POLICY.sanitize(value);
        }
        return value;
    }
}