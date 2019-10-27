package com.netty.file;

import com.netty.server.file.work.FileUploadServer;
import lombok.Getter;
import org.junit.Test;

/**
 * @author : https://github.com/xbbGithub
 * @date : Created in 2019/10/27
 * @description : 服务端测试
 **/
@Getter
public class ServerFileTest {

    private static final int FILE_PORT = 9000;

    @Test
    public void testServerFile() {
        try {
            new FileUploadServer().bind(FILE_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
