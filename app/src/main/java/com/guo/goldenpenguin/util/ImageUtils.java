package com.guo.goldenpenguin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.guo.goldenpenguin.util.GlideTransform.GlideCircleTransform;
import com.guo.goldenpenguin.util.GlideTransform.GlideRoundTransform;
import com.guo.goldenpenguin.util.GlideTransform.RoundedCornersTransformation;
import com.guo.goldenpenguin.view.BigImageView;


import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * imageView网络图片加载工具类
 *
 * @Description:
 * @see:
 * @since:
 * @author:ZhongGaoYong
 * @copyright www.elleshop.com
 * @Date:2016/11/6 17 08
 */
public class ImageUtils {

    static int devicesRam = getTotalRam();

    private static ImageUtils imageUtils;

    public static ImageUtils image() {
        if (imageUtils == null) {
            //同步代码块（对象未初始化时，使用同步代码块，保证多线程访问时对象在第一次创建后，不再重复被创建）
            synchronized (ImageUtils.class) {
                //未初始化，则初始instance变量
                if (imageUtils == null) {
                    imageUtils = new ImageUtils();
                }
            }
        }
        return imageUtils;
    }


    ImageOptions imageOptions = new ImageOptions.Builder()
            .setUseMemCache(true)
            .setIgnoreGif(false)
//            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .build();


    /**
     * 显示图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, Context context) {
        if (!StringUtil.isEmpty(iconUrl)) {
//            imageView.setImageDrawable(null);
            iconUrl = toURLString(iconUrl);
//            x.image().bind(imageView, iconUrl, imageOptions);
//            Picasso.with(ActivityManager.current()).load(iconUrl).into(imageView);
            if (iconUrl.contains(".gif")) {
                Glide.with(context).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(context).load(iconUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }

        }
    }
    /**
     * 显示图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, Fragment fragment) {
        if (!StringUtil.isEmpty(iconUrl)) {
//            imageView.setImageDrawable(null);
            iconUrl = toURLString(iconUrl);
//            x.image().bind(imageView, iconUrl, imageOptions);
//            Picasso.with(ActivityManager.current()).load(iconUrl).into(imageView);
            if (iconUrl.contains(".gif")) {
                Glide.with(fragment).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(fragment).load("http://i.ebayimg.com/images/g/vycAAOSwRjNXOS-G/s-l1600.jpg").diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }

        }
    }

    /**
     * 显示图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, int defaultImg, Fragment fragment) {
        if (!StringUtil.isEmpty(iconUrl)) {
//            imageView.setImageDrawable(null);
            iconUrl = toURLString(iconUrl);
//            x.image().bind(imageView, iconUrl, imageOptions);
//            Picasso.with(ActivityManager.current()).load(iconUrl).into(imageView);
            if (iconUrl.contains(".gif")) {
                Glide.with(fragment).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(fragment).load(iconUrl).placeholder(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }

        }
    }
    /**
     * 加载本地资源图片
     *
     * @param imageView
     * @param res
     */
    public void display(ImageView imageView, Object res, Context context) {
        String iconUrl = null;
        int resBg;
        if (res!=null){
            if (res instanceof String) {
                iconUrl = (String) res;
            }else if (res instanceof Integer){
                resBg = (Integer) res;
                    Glide.with(context).load(resBg).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        }
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            if (iconUrl.contains(".gif")) {
                Glide.with(context).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(context).load(iconUrl).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            }
        }
    }

    /**
     * 显示图片
     *
     * @param imageView 图像控件
     * @param iconUrl   图片地址
     */
    public void display(ImageView imageView, String iconUrl, int defaultImg, Context context) {
        if (!StringUtil.isEmpty(iconUrl)) {
            Glide.clear(imageView);
            imageView.setImageDrawable(null);
            iconUrl = toURLString(iconUrl);
            Glide.with(context).load(iconUrl).placeholder(defaultImg).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        }

    }

//    /**
//     * 带回调的默认图片
//     *
//     * @param imageView 图像控件
//     * @param iconUrl   图片地址
//     * @param callBack  请求回调
//     */
//    public void displayCallBack(ImageView imageView, String iconUrl, Callback.CommonCallback callBack) {
//        if (!StringUtil.isEmpty(iconUrl)) {
//            imageView.setImageDrawable(null);
//            iconUrl = toURLString(iconUrl);
//            x.image().bind(imageView, iconUrl, imageOptions, callBack);
//        }
//
//    }


    /**
     * 根据图片比例以及给定的imageview宽度，去加载
     *
     * @param imageView
     * @param iconUrl
     * @param newImageViewWidth
     */
    public void displayScaling(ImageView imageView, String iconUrl, int newImageViewWidth, Context context) {
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            if (iconUrl.indexOf(".gif") != -1) {
                Glide.with(context).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(context).load(iconUrl).asBitmap().into(new EquestWidthTransFormation(imageView, newImageViewWidth));
            }
        }


    }
    /**
     * 根据图片比例以及给定的imageview宽度，去加载
     *
     * @param imageView
     * @param iconUrl
     * @param newImageViewWidth
     */
    public void displayScaling(ImageView imageView, String iconUrl, int newImageViewWidth, Fragment fragment) {
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            if (iconUrl.indexOf(".gif") != -1) {
                Glide.with(fragment).load(iconUrl).asGif().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            } else {
                Glide.with(fragment).load(iconUrl).asBitmap().into(new EquestWidthTransFormation(imageView, newImageViewWidth));
            }
        }


    }

    /**
     * 加载圆角图片
     * @param imageView
     * @param iconUrl
     * @param context
     * @param connerSize
     */
    public void disPlayConnerImage(ImageView imageView, String iconUrl, Context context, int connerSize){
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            Glide.with(context).load(iconUrl).transform(new GlideRoundTransform(context,connerSize)).into(imageView);
        }
    }

    /**
     * 加载指定圆角图片
     * @param imageView
     * @param iconUrl
     * @param context
     * @param connerSize
     */
    public void disPlayConnerRoundImage(ImageView imageView, String iconUrl, Context context, int connerSize){
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            Glide.with(context).load(iconUrl).bitmapTransform(new RoundedCornersTransformation(context, connerSize, 0, RoundedCornersTransformation.CornerType.TOP)).crossFade(1000).into(imageView);
        }
    }
    /**
     * 加载圆形图片
     * @param imageView
     * @param iconUrl
     * @param context
     */
    public void disPlayCircleImage(ImageView imageView, String iconUrl, Context context){
        if (!StringUtil.isEmpty(iconUrl)) {
            iconUrl = toURLString(iconUrl);
            Glide.with(context).load(iconUrl).transform(new GlideCircleTransform(context)).into(imageView);
        }
    }

    /**
     * 清除内存缓存
     */
    public void clearMemroy(Context context) {
        // 必须在UI线程中调用
        Glide.get(context).clearMemory();
    }

    /**
     * 下载图片 分享图片使用
     *
     * @param iconUrl
     * @param callback
     */
    public void loadImg(String iconUrl, ImageOptions options, Callback.CacheCallback<File> callback) {
        x.image().loadFile(iconUrl, options, callback);
    }


////------------------------------------------------------------------------------------------------------------

    /**
     * 处理图片地址异常字符问题
     *空格、回车、换行符、制表符
     * @param s
     * @return
     */
    public static String toURLString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                try {
                    sb.append(URLEncoder.encode(String.valueOf(c), "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(sb.toString());
        return  m.replaceAll("");
//        if (devicesRam == 1) {
//            return sb.toString().replaceAll(" ", "%20").replaceAll("/80/", "/50/");
//        } else if (devicesRam == 2) {
//            return sb.toString().replaceAll(" ", "%20").replaceAll("/80/", "/60/");
//        } else if (devicesRam == 3) {
//            return sb.toString().replaceAll(" ", "%20").replaceAll("/80/", "/70/");
//        } else {
//            return sb.toString().replaceAll(" ", "%20");
//        }
    }

    public static int getTotalRam() {//GB
        String path = "/proc/meminfo";
        String firstLine = null;
        int totalRam = 0;
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader br = new BufferedReader(fileReader, 8192);
            firstLine = br.readLine().split("\\s+")[1];
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstLine != null) {
            totalRam = (int) Math.ceil((new Float(Float.valueOf(firstLine) / (1024 * 1024)).doubleValue()));
        }

        return totalRam;//返回1GB/2GB/3GB/4GB
    }

    /**
     * 更加给定的宽，等比设定imageview的高度
     *
     * @Description:
     * @see:
     * @since:
     * @author:ZhongGaoYong
     * @copyright www.elleshop.com
     * @Date:2016/12/14 下午3:34
     */
    private class LoadImageCallBack implements Callback.CacheCallback<Drawable> {

        ImageView mImageView;

        //是否使用缓存
        private Boolean isUseCaching = false;
        //imageView的新宽
        private int newImageWidth;

        public LoadImageCallBack(ImageView imageView, int newImgWidth) {
            this.mImageView = imageView;
            this.newImageWidth = newImgWidth;
        }

        @Override
        public boolean onCache(Drawable result) {
            if (result != null) {
                isUseCaching = true;
                changeLayoutParams(result);
            }
            return false;
        }

        @Override
        public void onSuccess(Drawable result) {
            //如果没有使用缓存中的drawable那么使用此对象
            if (!isUseCaching) {
                changeLayoutParams(result);
            }
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }

        /**
         * 改变imageView宽高属性
         *
         * @param result
         */
        void changeLayoutParams(Drawable result) {
            if (result != null) {
                //原图宽高
                int imgWidth = result.getIntrinsicWidth();
                //原图高
                int imgHeight = result.getIntrinsicHeight();

                //现图宽
                int newImgWidth = newImageWidth;
                //现图高
                int newImgHeight = (int) (newImgWidth / ((float) imgWidth) * imgHeight);

                ViewGroup.LayoutParams params = mImageView.getLayoutParams();

                if (params.width != newImgWidth || params.height != newImgHeight) {
                    params.width = newImgWidth;

                    params.height = newImgHeight;

                    mImageView.setLayoutParams(params);

                    Log.e("shan", "shan=========");
                }

            }
        }
    }


    class EquestWidthTransFormation extends SimpleTarget<Bitmap> {

        private int newWidth;

        ImageView mImageView;


        public EquestWidthTransFormation(ImageView imageView, int newWidth) {
            this.mImageView = imageView;
            this.newWidth = newWidth;
        }


        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            //原图宽高
            int imgWidth = resource.getWidth();
            //原图高
            int imgHeight = resource.getHeight();
            //现图宽
            int newImgWidth = newWidth;
            //现图高
            int newImgHeight = (int) (newImgWidth / ((float) imgWidth) * imgHeight);
            GlobalTools.setViewWidth2HeightByLayoutParams(mImageView, newImgWidth, newImgHeight);
//            Bitmap result = Bitmap.createScaledBitmap(resource, newImgWidth, newImgHeight, false);
//            if (result != resource) {
//                resource.recycle();
//            }

            if (imgHeight > 4096) {
                if (mImageView instanceof BigImageView) {
                    ((BigImageView) mImageView).showBigImg(resource);
                }
            } else {
                mImageView.setImageBitmap(resource);
            }
        }
    }
}
