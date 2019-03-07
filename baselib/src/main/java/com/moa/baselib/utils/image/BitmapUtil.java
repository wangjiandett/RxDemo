package com.moa.baselib.utils.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Original code by ultrakain
 * from https://github.com/ultrakain/AndroidBitmapUtil@cf88855b82
 * With fix by ben75
 * http://stackoverflow.com/questions/22909429/android-save-a-bitmap-to-bmp-file-format
 */
public class BitmapUtil {

    private static final int BMP_WIDTH_OF_TIMES = 4;
    private static final int BYTE_PER_PIXEL = 3;

    /**
     * Android Bitmap Object to Window's v3 24bit Bmp Format File
     *
     * @param orgBitmap
     * @param filePath
     * @return file saved result
     */
    public static boolean save(Bitmap orgBitmap, String filePath) throws IOException {
        long start = System.currentTimeMillis();
        if (orgBitmap == null) {
            return false;
        }

        if (filePath == null) {
            return false;
        }

        boolean isSaveSuccess = true;

        //image size
        int width = orgBitmap.getWidth();
        int height = orgBitmap.getHeight();

        //image dummy data size
        //reason : the amount of bytes per image row must be a multiple of 4 (requirements of bmp format)
        byte[] dummyBytesPerRow = null;
        boolean hasDummy = false;
        int rowWidthInBytes = BYTE_PER_PIXEL * width; //source image width * number of bytes to encode one pixel.
        if (rowWidthInBytes % BMP_WIDTH_OF_TIMES > 0) {
            hasDummy = true;
            //the number of dummy bytes we need to add on each row
            dummyBytesPerRow = new byte[(BMP_WIDTH_OF_TIMES - (rowWidthInBytes % BMP_WIDTH_OF_TIMES))];
            //just fill an array with the dummy bytes we need to append at the end of each row
            for (int i = 0; i < dummyBytesPerRow.length; i++) {
                dummyBytesPerRow[i] = (byte) 0xFF;
            }
        }

        //an array to receive the pixels from the source image
        int[] pixels = new int[width * height];

        //the number of bytes used in the file to store raw image data (excluding file headers)
        int imageSize = (rowWidthInBytes + (hasDummy ? dummyBytesPerRow.length : 0)) * height;
        //file headers size
        int imageDataOffset = 0x36;

        //final size of the file
        int fileSize = imageSize + imageDataOffset;

        //Android Bitmap Image Data
        orgBitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        //ByteArrayOutputStream baos = new ByteArrayOutputStream(fileSize);
        ByteBuffer buffer = ByteBuffer.allocate(fileSize);

        /**
         * BITMAP FILE HEADER Write Start
         **/
        buffer.put((byte) 0x42);
        buffer.put((byte) 0x4D);

        //size
        buffer.put(writeInt(fileSize));

        //reserved
        buffer.put(writeShort((short) 0));
        buffer.put(writeShort((short) 0));

        //image data start offset
        buffer.put(writeInt(imageDataOffset));

        /** BITMAP FILE HEADER Write End */

        //*******************************************

        /** BITMAP INFO HEADER Write Start */
        //size
        buffer.put(writeInt(0x28));

        //width, height
        //if we add 3 dummy bytes per row : it means we add a pixel (and the image width is modified.
        buffer.put(writeInt(width + (hasDummy ? (dummyBytesPerRow.length == 3 ? 1 : 0) : 0)));
        buffer.put(writeInt(height));

        //planes
        buffer.put(writeShort((short) 1));

        //bit count
        buffer.put(writeShort((short) 24));

        //bit compression
        buffer.put(writeInt(0));

        //image data size
        buffer.put(writeInt(imageSize));

        //horizontal resolution in pixels per meter
        buffer.put(writeInt(0));

        //vertical resolution in pixels per meter (unreliable)
        buffer.put(writeInt(0));

        buffer.put(writeInt(0));

        buffer.put(writeInt(0));

        /** BITMAP INFO HEADER Write End */

        int row = height;
        int col = width;
        int startPosition = (row - 1) * col;
        int endPosition = row * col;
        while (row > 0) {
            for (int i = startPosition; i < endPosition; i++) {
                buffer.put((byte) (pixels[i] & 0x000000FF));
                buffer.put((byte) ((pixels[i] & 0x0000FF00) >> 8));
                buffer.put((byte) ((pixels[i] & 0x00FF0000) >> 16));
            }
            if (hasDummy) {
                buffer.put(dummyBytesPerRow);
            }
            row--;
            endPosition = startPosition;
            startPosition = startPosition - col;
        }

        FileOutputStream fos = new FileOutputStream(filePath);
        fos.write(buffer.array());
        fos.close();
        Log.v("AndroidBmpUtil", System.currentTimeMillis() - start + " ms");

        return isSaveSuccess;
    }

    /**
     * Write integer to little-endian
     *
     * @param value
     * @return
     * @throws IOException
     */
    private static byte[] writeInt(int value) throws IOException {
        byte[] b = new byte[4];

        b[0] = (byte) (value & 0x000000FF);
        b[1] = (byte) ((value & 0x0000FF00) >> 8);
        b[2] = (byte) ((value & 0x00FF0000) >> 16);
        b[3] = (byte) ((value & 0xFF000000) >> 24);

        return b;
    }

    /**
     * Write short to little-endian byte array
     *
     * @param value
     * @return
     * @throws IOException
     */
    private static byte[] writeShort(short value) throws IOException {
        byte[] b = new byte[2];

        b[0] = (byte) (value & 0x00FF);
        b[1] = (byte) ((value & 0xFF00) >> 8);

        return b;
    }

    public static Bitmap fastBlur(Bitmap sentBitmap, int radius) {
        // todo faster!!1
        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filter works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return bitmap;
        }
        try {
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();

            int[] pix = new int[w * h];
            Log.e("pix", w + " " + h + " " + pix.length);
            bitmap.getPixels(pix, 0, w, 0, 0, w, h);

            int wm = w - 1;
            int hm = h - 1;
            int wh = w * h;
            int div = radius + radius + 1;

            int r[] = new int[wh];
            int g[] = new int[wh];
            int b[] = new int[wh];
            int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
            int vmin[] = new int[Math.max(w, h)];

            int divsum = (div + 1) >> 1;
            divsum *= divsum;
            int dv[] = new int[256 * divsum];
            for (i = 0; i < 256 * divsum; i++) {
                dv[i] = (i / divsum);
            }

            yw = yi = 0;

            int[][] stack = new int[div][3];
            int stackpointer;
            int stackstart;
            int[] sir;
            int rbs;
            int r1 = radius + 1;
            int routsum, goutsum, boutsum;
            int rinsum, ginsum, binsum;

            for (y = 0; y < h; y++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                for (i = -radius; i <= radius; i++) {
                    p = pix[yi + Math.min(wm, Math.max(i, 0))];
                    sir = stack[i + radius];
                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);
                    rbs = r1 - Math.abs(i);
                    rsum += sir[0] * rbs;
                    gsum += sir[1] * rbs;
                    bsum += sir[2] * rbs;
                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }
                }
                stackpointer = radius;

                for (x = 0; x < w; x++) {

                    r[yi] = dv[rsum];
                    g[yi] = dv[gsum];
                    b[yi] = dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (y == 0) {
                        vmin[x] = Math.min(x + radius + 1, wm);
                    }
                    p = pix[yw + vmin[x]];

                    sir[0] = (p & 0xff0000) >> 16;
                    sir[1] = (p & 0x00ff00) >> 8;
                    sir[2] = (p & 0x0000ff);

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[(stackpointer) % div];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi++;
                }
                yw += w;
            }
            for (x = 0; x < w; x++) {
                rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
                yp = -radius * w;
                for (i = -radius; i <= radius; i++) {
                    yi = Math.max(0, yp) + x;

                    sir = stack[i + radius];

                    sir[0] = r[yi];
                    sir[1] = g[yi];
                    sir[2] = b[yi];

                    rbs = r1 - Math.abs(i);

                    rsum += r[yi] * rbs;
                    gsum += g[yi] * rbs;
                    bsum += b[yi] * rbs;

                    if (i > 0) {
                        rinsum += sir[0];
                        ginsum += sir[1];
                        binsum += sir[2];
                    } else {
                        routsum += sir[0];
                        goutsum += sir[1];
                        boutsum += sir[2];
                    }

                    if (i < hm) {
                        yp += w;
                    }
                }
                yi = x;
                stackpointer = radius;
                for (y = 0; y < h; y++) {
                    // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                    pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                    rsum -= routsum;
                    gsum -= goutsum;
                    bsum -= boutsum;

                    stackstart = stackpointer - radius + div;
                    sir = stack[stackstart % div];

                    routsum -= sir[0];
                    goutsum -= sir[1];
                    boutsum -= sir[2];

                    if (x == 0) {
                        vmin[y] = Math.min(y + r1, hm) * w;
                    }
                    p = x + vmin[y];

                    sir[0] = r[p];
                    sir[1] = g[p];
                    sir[2] = b[p];

                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];

                    rsum += rinsum;
                    gsum += ginsum;
                    bsum += binsum;

                    stackpointer = (stackpointer + 1) % div;
                    sir = stack[stackpointer];

                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];

                    rinsum -= sir[0];
                    ginsum -= sir[1];
                    binsum -= sir[2];

                    yi += w;
                }
            }

            Log.e("pix", w + " " + h + " " + pix.length);
            bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        } catch (Exception exp) {
        }
        return (bitmap);

    }

    public static Bitmap fromBytes(byte[] bytes) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = null;
        if (null != bitmap) {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            canvas.drawRect(rectF, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        }
        return output;
    }
    
    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        return bitmap;
    }
    
    /**
     * 图片按比例大小压缩方法（根据路径获取图片并压缩）
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getImage(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
        
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }
    
    /**
     * 将压缩的bitmap保存到SDCard卡临时文件夹，用于上传
     *
     * @param filename
     * @param bit
     * @return
     */
    private static String saveBitmap(String filename, Bitmap bit) {
        String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath()+"/laopai/";
        String filePath = baseDir + filename;
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        File f = new File(filePath);
        try {
            f.createNewFile();
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(f);
            bit.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        return filePath;
    }
}