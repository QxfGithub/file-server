package com.qxf.fileserver.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 文件读取工具类
 * 
 * @author GUANLIANGYOU515
 * @date   2018年4月18日
 * @since  1.0.0
 */
public class FileUtil {
    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 读取文件内容，作为字符串返回
     */
    public static String readFileAsString(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        if (file.length() > 1024 * 1024 * 1024) {
            throw new IOException("File is too large");
        }

        StringBuilder sb = new StringBuilder((int) (file.length()));
        // 创建字节输入流  
        FileInputStream fis = new FileInputStream(filePath);
        // 创建一个长度为10240的Buffer
        byte[] bbuf = new byte[10240];
        // 用于保存实际读取的字节数  
        int hasRead = 0;
        while ((hasRead = fis.read(bbuf)) > 0) {
            sb.append(new String(bbuf, 0, hasRead));
        }
        fis.close();
        return sb.toString();
    }

    /**
     * 根据文件路径读取byte[] 数组
     */
    public static byte[] readFileByBytes(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
            BufferedInputStream in = null;

            try {
                in = new BufferedInputStream(new FileInputStream(file));
                short bufSize = 1024;
                byte[] buffer = new byte[bufSize];
                int len1;
                while (-1 != (len1 = in.read(buffer, 0, bufSize))) {
                    bos.write(buffer, 0, len1);
                }

                byte[] var7 = bos.toByteArray();
                return var7;
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

                bos.close();
            }
        }
    }

    /**
     * 根据byte数组，生成文件
     *
     * @throws Exception
     */
    public static void writeFile(byte[] byteArr, String filePath, String fileName) throws Exception {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;

        File dir = new File(filePath);
        if (!dir.exists()) {// 如果文件目录不存在，则创建
            LOG.info("FileUtil mkdirs : {}", dir.getAbsolutePath());
            dir.mkdirs();
        }
        file = new File(filePath + File.separator + fileName);
        LOG.info("FileUtil write file : {}", file.getAbsolutePath());
        fos = new FileOutputStream(file);
        bos = new BufferedOutputStream(fos);
        bos.write(byteArr);
        bos.close();
        fos.close();
    }
    /**
     * 获得指定文件的byte数组
     *
     * @param filePath
     * @return
     * @throws Exception 
     */
    public static byte[] getBytes(String filePath) throws Exception {
        byte[] buffer = null;
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        
        return buffer;
    }
    
}
