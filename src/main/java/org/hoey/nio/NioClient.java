package org.hoey.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NioClient {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new ClientThread().start();
        }
    }

    static class ClientThread extends Thread{


        @Override
        public void run() {
            SocketChannel channel = null;
            try {
                channel = SocketChannel.open();
                channel.configureBlocking(false);
                channel.connect(new InetSocketAddress("127.0.0.1", 8000));
                Selector selector = Selector.open();
                channel.register(selector, SelectionKey.OP_CONNECT);
                while (true) {
                    selector.select();
                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey next = keyIterator.next();
                        keyIterator.remove();
                        if (next.isConnectable()) {
                            if (channel.finishConnect()) {
                                next.interestOps(SelectionKey.OP_READ);
                                channel.write(ByteBuffer.wrap("hello world".getBytes(StandardCharsets.UTF_8)));
                            }else{
                                next.cancel();
                            }
                        }else if(next.isReadable()){
                            ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                            int read = channel.read(byteBuffer);
                            if(read > 0){
                                byteBuffer.flip();
                                String resp = StandardCharsets.UTF_8.decode(byteBuffer).toString();
                                System.out.println(Thread.currentThread().getName() + "client get resp:" + resp);
                            }
                            Thread.sleep(1000);
                            channel.write(ByteBuffer.wrap("hello server".getBytes(StandardCharsets.UTF_8)));
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();

            }finally {
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
