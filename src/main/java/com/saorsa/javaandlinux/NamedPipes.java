package com.saorsa.javaandlinux;

import com.saorsa.javaandlinux.jni.CreateNamedPipeJNI;

import java.io.IOException;

public class NamedPipes {
    public static void main( String[] args ) throws IOException {
        CreateNamedPipeJNI pipe = new CreateNamedPipeJNI();
        try {
            pipe.create("/tmp/testFIFO");
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
