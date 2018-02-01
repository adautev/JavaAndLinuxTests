package com.saorsa.javaandlinux;

import com.saorsa.javaandlinux.jni.CreateNamedPipeJNI;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class NamedPipesChatServer {

    public static final String SERVER_PIPE = "/tmp/serverFIFO";
    public static final String CLIENT_PIPE_PATH = "/tmp/";
    public static final String COMMAND_DELIMITER = ":#:";

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
                    String[] commandParameters = commandLine.split(COMMAND_DELIMITER);
                    String fromUser = commandParameters[0];
                    String toUser = commandParameters[1];
                    String command = commandParameters[2];
                    String sentence = commandParameters[3];

                    String userPipePath = CLIENT_PIPE_PATH + toUser;
                    File userPipeFile = new File(userPipePath);
                    if(!userPipeFile.exists()) {
                        pipe.create(userPipePath);
                    }
                    RandomAccessFile userPipe = new RandomAccessFile(
                            userPipePath, "rw");
                    switch(command) {
                        case "send":
                            userPipe.write(("receive" + COMMAND_DELIMITER + sentence).getBytes());
                            break;
                        default:
                            break;

                    }
                    userPipe.close();

                    if (command == "quit") {
                        break;
                    }

                    Thread.sleep(1000);
                }
            }
            serverPipe.close();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
