package com.netty.server.file.handler;

import com.netty.common.model.FileUpload;
import com.netty.common.model.FileUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;
/**
 * @author : https://github.com/xbbGithub
 * @date : Created in 2019/10/27
 * @description : 文件处理
 **/
public class FileUploadServerHandler extends ChannelInboundHandlerAdapter {
	private int byteRead;
    private volatile int start = 0;
    private String file_dir = "D:\\data";
    private final static Logger LOGGER = LoggerFactory.getLogger(FileUploadServerHandler.class);
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.channelActive(ctx);
        LOGGER.info("服务端：channelActive()");
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    	// TODO Auto-generated method stub
    	super.channelInactive(ctx);
        LOGGER.info("服务端：channelInactive()");
    	ctx.flush();
    	ctx.close();
    }
    
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("收到客户端发来的文件,正在处理....");
        if (msg instanceof FileUpload) {
            FileUpload ef = (FileUpload) msg;
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();
            String md5 = ef.getFileName();//文件名
            String path = file_dir + File.separator + md5;
            File file = new File(path);
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");//r: 只读模式 rw:读写模式
            randomAccessFile.seek(start);//移动文件记录指针的位置,
            randomAccessFile.write(bytes);//调用了seek（start）方法，是指把文件的记录指针定位到start字节的位置。也就是说程序将从start字节开始写数据
            start = start + byteRead;
            if (byteRead > 0) {
                ctx.writeAndFlush(start);//向客户端发送消息
                randomAccessFile.close();
                if(byteRead!=1024 * 10){
                	Thread.sleep(1000);
                	channelInactive(ctx);
                    FileUtil.decompressZip(path,"D:\\data\\");
                    FileUtil.deleteDir(path);
                }
            } else {
                ctx.close();
            }
            LOGGER.info("处理完毕,文件路径:"+path+","+byteRead);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
        LOGGER.info("FileUploadServerHandler--exceptionCaught()");
    }
}
