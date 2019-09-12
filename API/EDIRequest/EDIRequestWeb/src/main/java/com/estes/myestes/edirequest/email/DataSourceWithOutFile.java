package com.estes.myestes.edirequest.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class is to prevent saving to temporary file in File System and use the in-memory data instead.
 * @author Lakshman K
 *
 */
public class DataSourceWithOutFile implements javax.activation.DataSource {

    private String name;
    private String contentType;
    private ByteArrayOutputStream baos;
    
    DataSourceWithOutFile(String name, String contentType, InputStream inputStream) throws IOException {
        this.name = name;
        this.contentType = contentType;        
        baos = new ByteArrayOutputStream();        
        int read;
        byte[] buff = new byte[256];
        while((read = inputStream.read(buff)) != -1) {
            baos.write(buff, 0, read);
        }
    }
    
    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(baos.toByteArray());
    }

    public String getName() {
        return name;
    }

    public OutputStream getOutputStream() throws IOException {
        throw new IOException("Read-only resource");
    }

}