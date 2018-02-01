package com.saorsa.javaandlinux.jni;

import java.io.File;

//com.saorsa.javaandlinux.jni.CreateNamedPipeJNI
//https://www.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
/*
IN: ~/IdeaProjects/comsaorsajavaandlinux/src/main/java/com/saorsa/javaandlinux
EXECUTE:
javac jni/CreateNamedPipeJNI.java
~/IdeaProjects/comsaorsajavaandlinux/src/main/java$ javah -verbose -d com/saorsa/javaandlinux/jni/  com.saorsa.javaandlinux.jni.CreateNamedPipeJNI

THEN:
1
https://www.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
#2
https://stackoverflow.com/questions/3667184/header-files-linked-to-from-header-file-not-found
#3
https://stackoverflow.com/questions/27947194/compiling-errors-in-a-c-program-under-jni
#4
~/IdeaProjects/comsaorsajavaandlinux/src/main/java/com/saorsa/javaandlinux/jni$ gcc  -shared -I/usr/lib/jvm/java-8-openjdk-amd64/i
nclude/linux -I/usr/lib/jvm/java-8-openjdk-amd64/include -o com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.so  -rdynamic com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.c -fPIC

*/

public class CreateNamedPipeJNI {
    static {
        System.out.println(System.getProperty("user.dir"));
        String libPath = System.getProperty("java.library.path");
        File nativeFile = new File("/home/adautev/IdeaProjects/comsaorsajavaandlinux/src/main/java/com/saorsa/javaandlinux/jni/com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.so");
        if (!nativeFile.exists())
            System.exit(1);
        System.load(String.valueOf(nativeFile));
        System.out.println("java.library.path=" + libPath);

    }
    public native void create(String pipeName);
}
