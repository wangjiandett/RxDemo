package com.moa.baselib.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 实现9张图片组合
 * <p>
 * Created by：wangjian on 2017/11/17 10:28
 */
public class NineImageCombine {
    
    /**
     * 进行图片组合
     *
     * @param bitmaps     图片列表
     * @param parentWidth 图片显示的宽度
     * @param padding     图片间距大小
     * @param borderColor 边框和间隙颜色
     * @return
     */
    public static Bitmap combineImages(List<Bitmap> bitmaps, int parentWidth, int padding, int borderColor) {
        int size = bitmaps.size();
        
        // 根据图片数计算列数
        int maxColumn = getMaxColumn(size);
        // 计算图片行数
        // int maxRow = getMaxRow(size);
        
        // 图片实际占用宽度（去除padding）
        int valiableImageWidth = parentWidth - (maxColumn - 1) * padding;
        
        // 根据列数计算图片宽度和高度
        int imgWidth = valiableImageWidth / maxColumn;
        // int imgHeight = valiableImageWidth / maxColumn;
        
        // padding占据图片宽度的百分比
        float paddingPer = (float) padding / imgWidth;
        
        // 画布,空白大图
        Bitmap newBitmap = Bitmap.createBitmap(parentWidth, parentWidth, Bitmap.Config.ARGB_8888);
        
        // 获得各图片坐标
        List<PointF> points = getPointFlist(size, maxColumn, imgWidth, paddingPer);
        
        for (int i = 0; i < points.size(); i++) {
            // 生成指定大小的缩略图
            Bitmap thumBitmap = getThumbnailBitmap(bitmaps.get(i), imgWidth);
            // 组合成新的图片
            newBitmap = mixtureBitmap(newBitmap, thumBitmap, points.get(i));
        }
        
        if (borderColor != 0) {
            newBitmap = setBgAndBorder(newBitmap, borderColor, padding);
        }
        
        return newBitmap;
    }
    
    /**
     * 计算每一张图片的坐标点
     *
     * @param size       图片的数量
     * @param maxColumn  行数
     * @param imgWidth   每一张图片的宽度
     * @param paddingPer 图片中间的缝隙与图片宽度的百分比
     * @return
     */
    private static List<PointF> getPointFlist(int size, int maxColumn, float imgWidth, float paddingPer) {
        List<PointF> pointFList = new ArrayList<>();
        
        // 1张图片
        if (maxColumn == 1) {
            int xleft = 0;
            int xtop = 0;
            
            addPointF(pointFList, imgWidth, xleft, xtop);
        }
        // 2,3,4张图片
        else if (maxColumn == 2) {
            if (size == 2) {
                // 对应坐标 w == imgWidth
                // 0,w/2  w w/2
                
                for (int i = 0; i < size; i++) {
                    //计算左上角坐标
                    float xleft = 0;
                    float xtop = 0;
                    switch (i) {
                        case 0:
                            xleft = 0;
                            xtop = 1 / 2f;
                            break;
                        case 1:
                            xleft = 1 + paddingPer;
                            xtop = 1 / 2f;
                            break;
                    }
                    
                    addPointF(pointFList, imgWidth, xleft, xtop);
                }
            }
            else if (size == 3) {
                // 对应坐标 w == imgWidth
                //  w/2,0
                // 0,w  w w
                for (int i = 0; i < size; i++) {
                    //计算左上角坐标
                    float xleft = 0;
                    float xtop = 0;
                    
                    switch (i) {
                        case 0:
                            xleft = 1 / 2f;
                            xtop = 0;
                            break;
                        case 1:
                            xleft = 0;
                            xtop = 1 + paddingPer;
                            break;
                        case 2:
                            xleft = 1 + paddingPer;
                            xtop = 1 + paddingPer;
                            break;
                    }
                    
                    addPointF(pointFList, imgWidth, xleft, xtop);
                }
            }
            else {
                // 对应坐标 w == imgWidth
                // 0,0  w 0
                // 0,w  w w
                
                for (int i = 0; i < size; i++) {
                    //计算左上角坐标
                    float xleft = 0;
                    float xtop = 0;
                    
                    switch (i) {
                        case 0:
                        case 2:
                            xleft = 0;
                            xtop = i * (1 / 2f);
                            if (i == 2) {
                                xtop += paddingPer;
                            }
                            break;
                        case 1:
                        case 3:
                            xleft = 1 + paddingPer;
                            xtop = (i - 1) * (1 / 2f);
                            if (i == 3) {
                                xtop += paddingPer;
                            }
                            break;
                    }
                    
                    addPointF(pointFList, imgWidth, xleft, xtop);
                }
            }
        }
        // 5,6,7,8,9张图片
        else if (maxColumn == 3) {
            switch (size) {
                case 5:
                    // 对应坐标 w == imgWidth
                    //   w/2,w/2  3w/2,w/2
                    // 0,3w/2  w,3w/2  2w,3w/2
                    for (int i = 0; i < size; i++) {
                        //计算左上角坐标
                        float xleft = 0;
                        float xtop = 0;
                        switch (i) {
                            case 0:
                                xleft = 1 / 2f;
                                xtop = 1 / 2f;
                                break;
                            case 1:
                                xleft = 3 / 2f + paddingPer;
                                xtop = 1 / 2f;
                                break;
                            case 2:
                            case 3:
                            case 4:
                                xleft = i - 2;
                                xtop = 3 / 2f + paddingPer;
                                if (i != 2) {
                                    xleft += (i - 2) * paddingPer;
                                }
                                break;
                        }
                        
                        addPointF(pointFList, imgWidth, xleft, xtop);
                    }
                    break;
                case 6:
                    // 对应坐标 w == imgWidth
                    // 0,w/2   w,w/2   2w,w/2
                    // 0,3w/2  w,3w/2  2w,3w/2
                    for (int i = 0; i < size; i++) {
                        //计算左上角坐标
                        float xleft = 0;
                        float xtop = 0;
                        switch (i) {
                            case 0:
                            case 1:
                            case 2:
                                xleft = i;
                                xtop = 1 / 2f;
                                if (i != 0) {
                                    xleft += (i) * paddingPer;
                                }
                                break;
                            case 3:
                            case 4:
                            case 5:
                                xleft = i - 3;
                                xtop = 3 / 2f + paddingPer;
                                if (i != 3) {
                                    xleft += (i - 3) * paddingPer;
                                }
                                break;
                        }
                        
                        addPointF(pointFList, imgWidth, xleft, xtop);
                    }
                    break;
                case 7:
                    // 对应坐标 w == imgWidth
                    //       w,0
                    // 0,w   w,w   2w,w
                    // 0,2w  w,2w  2w,2w
                    for (int i = 0; i < size; i++) {
                        //计算左上角坐标
                        float xleft = 0;
                        float xtop = 0;
                        switch (i) {
                            case 0:
                                xleft = 1 + paddingPer;
                                xtop = 0;
                                break;
                            case 1:
                            case 2:
                            case 3:
                                xleft = i - 1;
                                xtop = 1 + paddingPer;
                                if (i != 1) {
                                    xleft += (i - 1) * paddingPer;// 除了第一列，依次递增加padding
                                }
                                break;
                            case 4:
                            case 5:
                            case 6:
                                xleft = i - 4;
                                xtop = 2 + (2) * paddingPer;// 最下面一行要加2倍padding
                                if (i != 4) {
                                    xleft += (i - 4) * paddingPer;// 除了第一列，依次递增加padding
                                }
                                break;
                        }
                        
                        addPointF(pointFList, imgWidth, xleft, xtop);
                    }
                    break;
                case 8:
                    // 对应坐标 w == imgWidth
                    //   w/2,0 3w/2,0
                    // 0,w   w,w   2w,w
                    // 0,2w  w,2w  2w,2w
                    for (int i = 0; i < size; i++) {
                        //计算左上角坐标
                        float xleft = 0;
                        float xtop = 0;
                        switch (i) {
                            case 0:
                                xleft = 1 / 2f;
                                xtop = 0;
                                break;
                            case 1:
                                xleft = 3 / 2f + paddingPer;
                                xtop = 0;
                                break;
                            case 2:
                            case 3:
                            case 4:
                                xleft = i - 2;
                                xtop = 1 + paddingPer;
                                if (i != 2) {
                                    xleft += (i - 2) * paddingPer;// 除了第一列，依次递增加padding
                                }
                                break;
                            case 5:
                            case 6:
                            case 7:
                                xleft = i - 5;
                                xtop = 2 + (2) * paddingPer;// 最下面一行要加2倍padding
                                if (i != 5) {
                                    xleft += (i - 5) * paddingPer;// 除了第一列，依次递增加padding
                                }
                                break;
                        }
                        
                        addPointF(pointFList, imgWidth, xleft, xtop);
                    }
                    break;
                case 9:
                    // 对应坐标 w == imgWidth
                    // 0,0   w,0   2w,0
                    // 0,w   w,w   2w,w
                    // 0,2w  w,2w  2w,2w
                    for (int i = 0; i < size; i++) {
                        //计算左上角坐标
                        float xleft = 0;
                        float xtop = 0;
                        switch (i) {
                            case 0:
                            case 1:
                            case 2:
                                xleft = i;
                                xtop = 0;
                                if (i != 0) {
                                    xleft += (i) * paddingPer;
                                }
                                break;
                            case 3:
                            case 4:
                            case 5:
                                xleft = i - 3;
                                xtop = 1 + paddingPer;
                                if (i != 3) {
                                    xleft += (i - 3) * paddingPer;
                                }
                                break;
                            case 6:
                            case 7:
                            case 8:
                                xleft = i - 6;
                                xtop = 2 + (2) * paddingPer;
                                if (i != 6) {
                                    xleft += (i - 6) * paddingPer;
                                }
                                break;
                        }
                        
                        addPointF(pointFList, imgWidth, xleft, xtop);
                    }
                    break;
            }
            
        }
        
        return pointFList;
    }
    
    /**
     * 计算并保存坐标点
     *
     * @param pointFList
     * @param imgWidth
     * @param xleft
     * @param xtop
     */
    private static void addPointF(List<PointF> pointFList, float imgWidth, float xleft, float xtop) {
        float left = imgWidth * xleft;
        float top = imgWidth * xtop;
        
        PointF pointF = new PointF(left, top);
        pointFList.add(pointF);
    }
    
    /**
     * 根据图片数计算行数
     *
     * @param size
     * @return
     */
    private static int getMaxRow(int size) {
        int maxRow = 1;
        switch (size) {
            case 1:
            case 2:
                maxRow = 1;
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                maxRow = 2;
                break;
            case 7:
            case 8:
            case 9:
                maxRow = 3;
                break;
        }
        
        return maxRow;
    }
    
    /**
     * 根据图片数计算列数
     *
     * @param size 图片数量
     * @return
     */
    private static int getMaxColumn(int size) {
        int maxColumn = 1;
        switch (size) {
            case 1:
                maxColumn = 1;
                break;
            case 2:
            case 3:
            case 4:
                maxColumn = 2;
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                maxColumn = 3;
                break;
        }
        
        return maxColumn;
    }
    
    
    /**
     * 进行图片压缩
     *
     * @param bitmap
     * @param widthDp
     * @return
     */
    public static Bitmap getThumbnailBitmap(Bitmap bitmap, int widthDp) {
        Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, widthDp, widthDp);
        return thumbnail;
    }
    
    /**
     * drawable 转换成bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    
    /**
     * 保存图片到文件中
     *
     * @param mContext
     * @param bitmap
     * @param desName
     * @return
     * @throws IOException
     */
    public static File saveBitmap2File(Context mContext, Bitmap bitmap, String desName) throws IOException {
        FileOutputStream fOut = null;
        File pfile = Environment.getExternalStorageDirectory();
       // File file = mContext.getExternalFilesDir("Pictures");
        File file = new File(pfile,"pics");
        if (!file.exists()) {
            file.mkdirs();
        }
        
        File saveFile = new File(file, desName + ".png");
        file.createNewFile();
        
        fOut = new FileOutputStream(saveFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return saveFile;
    }
    
    
    /**
     * 在padding>0的情况，设置图片间隙和边框，否则是有边框
     *
     * @param orginBitmap
     * @param color       背景颜色（即边框和间隙颜色）
     * @param padding     图片间隙
     * @return
     */
    private static Bitmap setBgAndBorder(Bitmap orginBitmap, int color, int padding) {
        
        Paint paint = new Paint();
        paint.setColor(color);
        
        int addSize = 2 * padding;
        int afwidth = orginBitmap.getWidth() + addSize;
        int afheight = orginBitmap.getHeight() + addSize;
        
        Bitmap bitmap = Bitmap.createBitmap(afwidth, afheight, orginBitmap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        // canvas.drawColor(color);
        canvas.drawRect(0, 0, afwidth, afheight, paint);
        canvas.drawBitmap(orginBitmap, padding, padding, paint);
        return bitmap;
    }
    
    /**
     * 给bitmap设置边框
     *
     * @param bitmap 图片源
     * @param color  边框颜色
     * @param size   宽度
     */
    public static Bitmap setBitmapBorder(Bitmap bitmap, int color, int size) {
        Canvas canvas = new Canvas(bitmap);
        Rect rect = canvas.getClipBounds();
        Paint paint = new Paint();
        //设置边框颜色
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        //设置边框宽度
        paint.setStrokeWidth(size);
        canvas.drawRect(rect, paint);
        return bitmap;
    }
    
    /**
     * 进行图片组合
     *
     * @param first     第一张图片
     * @param second    第二张图片
     * @param fromPoint 第二张图片的坐标
     * @return 组合后的图片
     */
    public static Bitmap mixtureBitmap(Bitmap first, Bitmap second, PointF fromPoint) {
        Bitmap newBitmap = Bitmap.createBitmap(first.getWidth(), first.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBitmap);
        // 第一张图片从（0,0）位置开始绘制
        cv.drawBitmap(first, 0, 0, null);
        // 第二张图片从指定的坐标绘制
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save();
        cv.restore();
        return newBitmap;
    }
    
    public static int dp2px(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + .5f);
    }
}