
/*
 *  Copyright (C) 2016-2017 浙江海宁山顶动力网络科技有限公司
 * 文件说明：
 *  <p>
 *  更新说明:
 *
 *  @author wangjian@xiaokebang.com
 *  @version 1.0.0
 *  @create 17-6-9 下午3:52
 *  @see <a href="http://www.top4s.net/">http://www.top4s.net/</a>
 */

package com.moa.baselib.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 进行文件加密
 *
 * @author wangjian
 */
public class AESHelper {
    public static final String VIPARA = "0102030405060708";
    
    /**
     * 对文件进行AES加密
     *
     * @param key 秘钥
     * @param sourceFilePath 原文件
     * @param destFilePath 加密后文件
     * @return 加密后文件
     */
    public static File encryptFile(String key, String sourceFilePath, String destFilePath) {
        System.out.printf(sourceFilePath);
        FileInputStream in = null;
        FileOutputStream out = null;
        File destFile = null;
        File sourceFile = null;
        try {
            sourceFile = new File(sourceFilePath);
            
            System.out.printf(sourceFilePath + "---" + sourceFile.getAbsolutePath());
            destFile = new File(destFilePath);
            if (sourceFile.exists() && sourceFile.isFile()) {
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                destFile.createNewFile();
                in = new FileInputStream(sourceFile);
                out = new FileOutputStream(destFile);
                Cipher cipher = initAESCipher(key, Cipher.ENCRYPT_MODE);
                //以加密流写入文件
                CipherInputStream cipherInputStream = new CipherInputStream(in, cipher);
                byte[] cache = new byte[1024];
                int nRead = 0;
                while ((nRead = cipherInputStream.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                cipherInputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return destFile;
    }
    
    
    /**
     * AES方式解密文件
     *
     * @param key 秘钥
     * @param sourceFilePath 需要解密的文件
     * @param destFilePath 解密后的文件
     * @return
     */
    public static File decryptFile(String key, String sourceFilePath, String destFilePath) {
        FileInputStream in = null;
        FileOutputStream out = null;
        File destFile = null;
        File sourceFile = null;
        try {
            sourceFile = new File(sourceFilePath);
            destFile = new File(destFilePath);
            if (sourceFile.exists() && sourceFile.isFile()) {
                if (!destFile.getParentFile().exists()) {
                    destFile.getParentFile().mkdirs();
                }
                destFile.createNewFile();
                in = new FileInputStream(sourceFile);
                out = new FileOutputStream(destFile);
                Cipher cipher = initAESCipher(key, Cipher.DECRYPT_MODE);
                CipherOutputStream cipherOutputStream = new CipherOutputStream(out, cipher);
                byte[] buffer = new byte[1024];
                int r;
                while ((r = in.read(buffer)) >= 0) {
                    cipherOutputStream.write(buffer, 0, r);
                }
                cipherOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return destFile;
    }
    
    /**
     * 初始化 AES Cipher
     *
     * @param sKey
     * @param cipherMode
     * @return
     */
    private static Cipher initAESCipher(String sKey, int cipherMode) {
        //创建Key gen
        KeyGenerator keyGenerator = null;
        Cipher cipher = null;
        try {
            IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
            SecretKeySpec key = new SecretKeySpec(sKey.getBytes(), "AES");
            // 算法/工作模式/填充模式
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(cipherMode, key, zeroIv);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return cipher;
    }
    
}