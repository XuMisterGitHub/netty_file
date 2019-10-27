package com.netty.file;

import com.netty.common.model.FileUpload;
import com.netty.client.file.work.FileUploadClient;
import org.junit.Test;


import java.io.File;

/**
 * @author : https://github.com/xbbGithub
 * @date : Created in 2019/10/27
 * @description : 客户端测试
 **/
public class ClientFileTest {
    private static final int FILE_PORT = 9000;

    @Test
    public void testFile() {
        try {
            long start = System.currentTimeMillis();
            FileUpload uploadFile = new FileUpload();
            File file = new File("D:\\test.zip");
            String fileName = file.getName();// 文件名
            uploadFile.setFile(file);
            uploadFile.setFileName(fileName);
            uploadFile.setStarPos(0);// 文件开始位置
            new FileUploadClient().connect(FILE_PORT, "127.0.0.1", uploadFile);
            long end = System.currentTimeMillis();
            System.out.println("运行时间" + (end - start) + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
