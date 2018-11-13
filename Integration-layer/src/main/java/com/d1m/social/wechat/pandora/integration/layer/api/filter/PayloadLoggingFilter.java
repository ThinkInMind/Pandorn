package com.d1m.social.wechat.pandora.integration.layer.api.filter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@Slf4j
public class PayloadLoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
        log.info("PayloadLoggingFilter init!");
        contentTypes = Lists.newArrayList(MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_JSON_UTF8_VALUE,
                MediaType.TEXT_XML_VALUE,
                MediaType.APPLICATION_XML_VALUE);
    }

    private List<String> contentTypes;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        final String contentType = request.getHeader("Content-type");

        if (StringUtils.isEmpty(contentType) || !contentTypes.contains(contentType)) {
            if (!request.getRequestURI().startsWith("/health")) {
                log.info("\n" +
                        "::::::::::::::::::::::::::Request Method: " +
                        request.getMethod() +
                        "\t" +
                        "Request URL: " +
                        request.getRequestURI());

                chain.doFilter(servletRequest, servletResponse);
            }
            return;
        }
        PayloadRequestWrapper wrapper = new PayloadRequestWrapper(request);

        log.info("\n" +
                "::::::::::::::::::::::::::Request Method: " +
                request.getMethod() +
                "\t" +
                "Request URL: " +
                request.getRequestURI() +
                "\n::::::::::::::::::::::::::Params: " +
                Strings.nullToEmpty(getParameters(request.getParameterMap())) +
                "\n::::::::::::::::::::::::::Headers: \n" + Strings.nullToEmpty(getHeaders(request)) +
                "\n::::::::::::::::::::::::::payload: " +
                Strings.nullToEmpty(wrapper.getBody()));


        chain.doFilter(wrapper, servletResponse);

    }

    private String getParameters(Map<String, String[]> paramMap) {

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry entry : paramMap.entrySet()) {
            final String tmp = entry.getKey() + ":" + Arrays.toString((String[]) entry.getValue());
            if (stringBuilder.length() < 1) {
                stringBuilder.append(tmp);
            } else {
                stringBuilder.append(",");
                stringBuilder.append(tmp);
            }
        }
        return stringBuilder.toString();
    }

    private String getHeaders(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        final Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            final String key = headerNames.nextElement();
            final String value = request.getHeader(key);
            if (stringBuilder.length() < 1) {
                stringBuilder.append(key).append(":").append(value).append("\n");
            } else {
                stringBuilder.append(";").append(key).append(":").append(value);
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void destroy() {
        log.info("PayloadLoggingFilter destroy!");
    }
}
