package hayashi.userservice.config.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String> customHeaders;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.customHeaders = new HashMap<>();
    }

    public void addHeader(String name, String value) {
        customHeaders.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = customHeaders.get(name);

        if (value != null) return value;
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>();
        Enumeration<String> originalNames = super.getHeaderNames();

        while (originalNames.hasMoreElements()) {
            names.add(originalNames.nextElement());
        }

        names.addAll(customHeaders.keySet());

        return Collections.enumeration(names);
    }
}
