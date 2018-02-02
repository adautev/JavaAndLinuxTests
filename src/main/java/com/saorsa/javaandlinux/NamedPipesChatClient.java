package com.saorsa.javaandlinux;

import com.saorsa.javaandlinux.jni.CreateNamedPipeJNI;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class NamedPipesChatClient {

    public static final String SERVER_PIPE = "/tmp/serverFIFO";
    public static final String CLIENT_PIPE_PATH = "/tmp/";
    public static final String COMMAND_DELIMITER = ":#:";

    //I know this isn't the best use case! :D
    public static void main(String[] args ) throws IOException {
        String userName = "";
        String fifoPath = "";
        if(args.length == 0 || args[0].length() < 2 ) {
            System.out.println("Please specify username!");
            System.exit(0);
        }
        userName = args[0];
        fifoPath = CLIENT_PIPE_PATH + userName;
        try {
            CreateNamedPipeJNI pipe = new CreateNamedPipeJNI();
            File clientPipeFile = new File(fifoPath);
            if(!clientPipeFile.exists()) {
                pipe.create(fifoPath);
            }

            RandomAccessFile clientPipe = new RandomAccessFile(
                    fifoPath, "r");
            //Yes, this will greatly benefit from pub/sub + multi threading. But who cares! :D
            while(true) {
                String commandLine = clientPipe.readLine();
                if(commandLine != null) {
                    String[] commandParameters = commandLine.split(COMMAND_DELIMITER);
                    String fromUser = commandParameters[0];
                    String toUser = commandParameters[1];
                    String command = commandParameters[2];
                    String sentence = commandParameters[3];

                    switch(command) {
                        case "receive":
                            System.out.println("Received message from " + fromUser + ": " + sentence);
                            break;
                        case "break":
                            System.out.println("Exiting...");
                            clientPipe.close();
                        default:
                            break;
                    }

                    Thread.sleep(1000);
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
