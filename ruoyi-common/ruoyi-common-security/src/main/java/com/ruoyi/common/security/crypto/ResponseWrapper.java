package com.ruoyi.common.security.crypto;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * @author 钟宗兵
 * @since 1.0.0
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;
    private final ServletOutputStream out;
    private final PrintWriter writer;

    public ResponseWrapper(HttpServletResponse resp) throws IOException {
        super(resp);
        // 真正存储数据的流
        buffer = new ByteArrayOutputStream();
        out = new WrappedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer,
                this.getCharacterEncoding()));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (out != null) {
            out.flush();
        }
        if (writer != null) {
            writer.flush();
        }
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    public byte[] getResponseData() throws IOException {
        flushBuffer();
        return buffer.toByteArray();
    }

    private static class WrappedOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream bos;

        public WrappedOutputStream(ByteArrayOutputStream stream) {
            bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            bos.write(b, 0, b.length);
        }

        @Override
        public boolean isReady() {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            // TODO Auto-generated method stub

        }
    }
}
