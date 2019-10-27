package com.netty.client.file.work;

import com.netty.client.file.handler.FileUploadClientHandler;
import com.netty.common.model.FileUpload;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author : https://github.com/xbbGithub
 * @date : Created in 2019/10/27
 * @description : 文件上传客户端
 **/
public class FileUploadClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileUploadClient.class);
    public void connect(int port, String host, final FileUpload fileUploadFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ObjectEncoder());
                            ch.pipeline().addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
                            ch.pipeline().addLast(new FileUploadClientHandler(fileUploadFile));
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
            LOGGER.info("FileUploadClient connect()结束");
        } finally {
            group.shutdownGracefully();
        }
    }

}
