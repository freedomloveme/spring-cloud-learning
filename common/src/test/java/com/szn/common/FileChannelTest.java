package com.szn.common;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("D:\\test.txt", "rw");
        FileChannel fileChannel = raf.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {
            System.out.println("read:" + bytesRead);
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.compact();
            bytesRead = fileChannel.read(byteBuffer);
            System.out.println();
        }
        raf.close();
    }

}
