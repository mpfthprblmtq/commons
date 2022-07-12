package com.mpfthprblmtq.commons.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class RequestProperties {

    private Map<String, String> properties = new HashMap<>();

    public RequestProperties withProperty(String property, String value) {
        getProperties().put(property, value);
        return this;
    }

    public RequestProperties withoutProperty(String property) {
        getProperties().remove(property);
        return this;
    }

    public RequestProperties build() {
        return this;
    }
}
