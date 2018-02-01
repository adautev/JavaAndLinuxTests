#include <jni.h>
#include <stdio.h>
#include <sys/stat.h>
#include "com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.h"
/*
#1
https://www.ntu.edu.sg/home/ehchua/programming/java/JavaNativeInterface.html
#2
https://stackoverflow.com/questions/3667184/header-files-linked-to-from-header-file-not-found
#3
https://stackoverflow.com/questions/27947194/compiling-errors-in-a-c-program-under-jni
#4
~/IdeaProjects/comsaorsajavaandlinux/src/main/java/com/saorsa/javaandlinux/jni$ gcc -Wl,--add-stdcall-alias -shared -I/usr/lib/jvm
  /java-8-openjdk-amd64/include/linux -I/usr/lib/jvm/java-8-openjdk-amd64/include -o com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.so  -c com_saorsa_javaandlinux_jni_CreateNamedPipeJNI.c
*/
JNIEXPORT void JNICALL Java_com_saorsa_javaandlinux_jni_CreateNamedPipeJNI_create
(JNIEnv* env, jobject object, jstring javaString) {
    const char *nativeString = (*env)->GetStringUTFChars(env, javaString, JNI_FALSE);
    umask(0);
    mknod(nativeString, S_IFIFO|0666, 0);
    (*env)->ReleaseStringUTFChars(env, javaString, nativeString);
}

