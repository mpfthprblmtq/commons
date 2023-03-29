package testingUtils.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class PostmanEchoGetResponse {
    Map<String, String> args;
    Map<String, String> headers;
    Map<String, String> data;
    String url;
}
