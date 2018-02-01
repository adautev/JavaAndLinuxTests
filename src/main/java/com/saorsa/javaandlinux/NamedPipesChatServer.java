package com.saorsa.javaandlinux;

import com.saorsa.javaandlinux.jni.CreateNamedPipeJNI;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class NamedPipesChatServer {

    public static final String SERVER_PIPE = "/tmp/serverFIFO";
    public static final String CLIENT_PIPE_PATH = "/tmp/";
    //I know this isn't the best use case! :D
    public static void main(String[] args ) throws IOException {
        CreateNamedPipeJNI pipe = new CreateNamedPipeJNI();
        try {
            File serverPipeFile = new File(SERVER_PIPE);
            if(!serverPipeFile.exists()) {
                pipe.create(SERVER_PIPE);
            }

            RandomAccessFile serverPipe = new RandomAccessFile(
                    SERVER_PIPE, "r");

            while(true) {
                String commandLine = serverPipe.readLine();
                if(commandLine != null) {
                    String[] commandParameters = commandLine.split(":#:");
                    String fromUser = commandParameters[0];
                    String toUser = commandParameters[1];
                    String command = commandParameters[2];
                    String sentence = commandParameters[3];
                    Thread.sleep(1000);
                    if (command == "quit") {
                        break;
                    }
                }
            }
            serverPipe.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
