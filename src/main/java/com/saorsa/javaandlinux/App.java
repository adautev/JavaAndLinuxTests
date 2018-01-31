package com.saorsa.javaandlinux;

import org.json.JSONArray;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class App 
{
    public static void main( String[] args ) throws IOException {
        PipedInputStream inputStream = new PipedInputStream();
        PipedOutputStream outputStream = new PipedOutputStream();
        outputStream.connect(inputStream);
        Runnable producer = () -> {
            try {
                produceData(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Runnable consumer = () -> {
            try {
                consumeData(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread producerThread = new Thread(producer);
        producerThread.start();
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        while(true) {
            try {
                producerThread.join();
                consumerThread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void produceData(PipedOutputStream outputStream) throws IOException {
        produceString("Producer: Getting lorem ipsum...", outputStream);
        String loremIpsum = getLoremIpsum();
        JSONArray loremIpsumSentences = new JSONArray(loremIpsum);
        for(Object o: loremIpsumSentences){
            produceString(o.toString(), outputStream);
        }
        produceString("#end",outputStream);
        outputStream.close();
    }

    private static void consumeData(PipedInputStream inputStream) throws IOException, InterruptedException {
        byte character;
        while (true) {
            StringBuilder sentence = new StringBuilder();
            while ((character = (byte) inputStream.read()) != (byte)'\n') {
                if(character == -1) {
                    Thread.sleep(1000);
                    continue;
                }
                System.out.print((char)character);
                sentence.append((char)character);
            }
            System.out.print("\r\n");
            if(sentence.toString().equals("#end")) {
                inputStream.close();
                break;
            }
        }

        inputStream.close();
    }
    private static void produceString(String inputString, PipedOutputStream outputStream) {
        try {
            for (char character:inputString.toCharArray()) {
                outputStream.write((byte)character);
            }
            outputStream.write((byte)'\n');
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getLoremIpsum() throws IOException {
        URL url = new URL("https://baconipsum.com/api/?type=all-meat&paras=2&start-with-lorem=1");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setReadTimeout(5000);
        urlConnection.setRequestMethod("GET");
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}
