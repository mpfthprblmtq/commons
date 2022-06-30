package com.mpfthprblmtq.commons.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class RequestURL {

    private String base;
    private String url;
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> urlParams = new HashMap<>();

    public RequestURL(String base) {
        setBase(base);
    }

    public RequestURL withBaseUrl(String base) {
        setBase(base);
        return this;
    }

    public RequestURL withQueryParam(String parameter, String value) {
        getQueryParams().put(parameter, value);
        return this;
    }

    public RequestURL withUrlParam(String parameter, String value) {
        getUrlParams().put(parameter, value);
        return this;
    }

    @SuppressWarnings("all")    // for the "StringBuilder can be replaced with String" warning
    public String build() throws MalformedURLException {

        if (getBase() == null) {
            throw new MalformedURLException("No base url given!");
        }

        if (getUrl() == null) {
            setUrl(getBase());
        }

        // build url parameters
        for (String parameter : getUrlParams().keySet()) {
            if (getUrl().contains("{" + parameter + "}")) {
                setUrl(getUrl().replace("{" + parameter + "}", getUrlParams().get(parameter)));
            } else {
                throw new MalformedURLException("Could not find {" + parameter + "} in given url: " + getBase());
            }
        }

        // build query parameters
        if (!queryParams.isEmpty()) {
            setUrl(getUrl() + "?");
            for (String parameter : getQueryParams().keySet()) {
                setUrl(new StringBuilder()
                        .append(getUrl())
                        .append(parameter)
                        .append("=")
                        .append(getQueryParams().get(parameter))
                        .append("&")
                        .toString());
            }

            // remove last &
            setUrl(getUrl().substring(0, getUrl().length() - 1));
        }

        return getUrl();
    }

    public URL buildUrl() throws MalformedURLException {
        return new URL(build());
    }
}
