package com.mysteelsoft.logger.filter;

import org.springframework.util.FileCopyUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * request 封装
 *
 * @author shanyesen
 */
public class RequestLoggingWrapper extends HttpServletRequestWrapper {

    private byte[] body;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public RequestLoggingWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    private void loadBody(HttpServletRequest request) throws IOException {
        body = FileCopyUtils.copyToByteArray(request.getInputStream());
        inputStream = new RequestLoggingInputStream(body);
    }

    public byte[] getOnceBody() {
        try {
            return body;
        } finally {
            body = null;
        }
    }

    public void clearBody() {
        body = null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    @Override
    public String getCharacterEncoding() {
        String enc = super.getCharacterEncoding();
        return (enc != null ? enc : Charset.forName("UTF-8").name());
    }

    private static class RequestLoggingInputStream extends ServletInputStream {

        private final ByteArrayInputStream is;

        public RequestLoggingInputStream(byte[] bytes) {
            is = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        @Override
        public boolean isFinished() {
            return is.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

    }

}
