package org.hoey.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class NioServer {


    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(128);
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.socket().bind(new InetSocketAddress(8000), 100);
            Selector selector = Selector.open();
            SelectionKey key = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                int select = selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey next = keyIterator.next();
                    keyIterator.remove();
                    if (next.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ);
                    }else if(next.isReadable()){
                        SocketChannel channel = (SocketChannel) next.channel();
                        buffer.clear();
                        int read = channel.read(buffer);
                        if(read > 0){
                            buffer.flip();
                            String req = StandardCharsets.UTF_8.decode(buffer).toString();
                            System.out.println("received:" + req);
                            channel.write(ByteBuffer.wrap("get response".getBytes(StandardCharsets.UTF_8)));
                        }
                    }else if (key.isWritable()){
                        buffer.clear();
                        SocketChannel channel = (SocketChannel) next.channel();
                        channel.write(ByteBuffer.wrap("write able".getBytes(StandardCharsets.UTF_8)));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
