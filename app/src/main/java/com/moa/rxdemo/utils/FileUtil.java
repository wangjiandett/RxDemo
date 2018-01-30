package com.moa.rxdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.Formatter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    
    public static String readFile(String path) {
        byte[] databytes = readFileBytes(path);
        if (databytes != null) {
            return new String(databytes);
        }
        return null;
    }
    
    public static byte[] readFileBytes(String path) {
        InputStream is = getFileInputStream(path);
        if (is != null) {
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            //We create an array of bytes
            byte[] data = new byte[1024];
            int current = 0;
            try {
                while ((current = bis.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, current);
                }
                return buffer.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    public static InputStream getFileInputStream(String path) {
        InputStream inputstream = null;
        if (!TextUtils.isEmpty(path)) {
            return null;
        }
        
        try {
            File pfile = new File(path);
            if (pfile.exists() && (!pfile.isDirectory())) {
                inputstream = new FileInputStream(pfile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return inputstream;
    }
    
    /**
     * 创建文件
     *
     * @param path 文件名 以“/”开头表示绝对路径
     * @return 文件File
     */
    public static File createFile(String path, String name) {
        File file = new File(path, name);
        try {
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    
    public static File createDir(String path, String dirName) {
        File file = new File(path, dirName);
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
    
    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);
        
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
    
    public static boolean saveBitmapToFile(Bitmap mBitmap, String filepath, String filename) {
        if (null == mBitmap || TextUtils.isEmpty(filepath)) {
            return false;
        }
        
        boolean result = true;
        File file = createFile(filepath, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            result = mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
    
    public static boolean deleteFileRecursively(String file) {
        return deleteFileRecursively(new File(file));
    }
    
    /**
     * 递归删除文件或者文件夹，
     *
     * @param file
     * @return
     */
    public static boolean deleteFileRecursively(File file) {
        if (null == file || !file.exists()) {
            return true;
        }
        
        boolean result = true;
        if (file.isFile()) {
            result &= file.delete();
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            // 删除空文件夹
            if (childFile == null || childFile.length == 0) {
                result &= file.delete();
            }
            else {
                // 删除子文件夹
                for (File f : childFile) {
                    result &= deleteFileRecursively(f);
                }
                result &= file.delete();
            }
        }
        return result;
    }
    
    
    public static boolean writeFileInputStream(InputStream is, String path) {
        boolean result = true;
        File file = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = new byte[1024];
            int num = 0;
            while ((num = is.read(data, 0, data.length)) != -1) {
                out.write(data, 0, num);
            }
            out.close();
            data = null;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
    
    public static void saveToFile(byte[] bfile, String filePath, String filename) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            file = createFile(filePath, filename);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return;
    }
    
    public static String formatFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }
    
    
    /**
     * 调用手机系统shell打开对应文件
     *
     * @param file
     */
    public static void openFile(File file, Context me) throws Exception {
        if (null != file && file.exists() && null != me) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            String type = MediaFileUtil.getFileType(file.getAbsolutePath()).mimeType;
            intent.setDataAndType(Uri.fromFile(file), type);
            me.startActivity(intent);
        }
        return;
    }
    
    public static File saveImageToGallery(Context context, File saveFile) {
        if (saveFile != null && saveFile.exists()) {
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), saveFile.getAbsolutePath(),
                    saveFile.getName(), null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.parse("file://" + saveFile.getAbsolutePath())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        return saveFile;
    }
    
    
}
