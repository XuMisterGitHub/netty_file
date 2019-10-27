package com.netty.common.model;

import java.io.File;
import java.io.Serializable;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUpload implements Serializable {

    private static final long serialVersionUID = 6028114819660493835L;
    private File file;// 文件
    private String fileName;// 文件名
    private int starPos;// 开始位置
    private byte[] bytes;// 文件字节数组
    private int endPos;// 结尾位置

}
