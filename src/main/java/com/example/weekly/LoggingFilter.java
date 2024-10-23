package com.example.weekly;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Sử dụng CachedBodyHttpServletRequest để lưu trữ request body
        CachedBodyHttpServletRequest cachedBodyRequest = new CachedBodyHttpServletRequest(request);

        // Sử dụng CachedBodyHttpServletResponse để ghi nhận response body
        CachedBodyHttpServletResponse cachedBodyResponse = new CachedBodyHttpServletResponse(response);

        // Log thông tin request
        logger.info("Request URI: {}", cachedBodyRequest.getRequestURI());
        logger.info("Request Method: {}", cachedBodyRequest.getMethod());

        // Log các header
        Enumeration<String> headerNames = cachedBodyRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            logger.info("Header {}: {}", headerName, cachedBodyRequest.getHeader(headerName));
        }

        // Log body của request
        logger.info("Request Body: {}", cachedBodyRequest.getBody());

        // Tiếp tục chuỗi filter với request và response đã được wrapped
        filterChain.doFilter(cachedBodyRequest, cachedBodyResponse);

        // Sau khi xử lý request, log thông tin response
        logger.info("Response Status: {}", cachedBodyResponse.getStatus());
        logger.info("Response Body: {}", cachedBodyResponse.getContentAsString());

        // Ghi dữ liệu response thực tế từ cachedBodyResponse ra output stream
        response.getOutputStream().write(cachedBodyResponse.getContentAsString().getBytes());
    }
}