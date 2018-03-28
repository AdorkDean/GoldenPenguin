package com.guo.goldenpenguin.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.guo.goldenpenguin.App;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @ClassName: FileUtil
 * @Description: 文件处理类
 * @author:wangtf
 * @date 2011-9-23 上午11:09:24
 */
@SuppressLint("NewApi")
public class FileUtil {

    // 文件删除间隔时间
    public static int DELETE_EXPIRED_FILE_TIME = 1000 * 2600 * 24 * 7;
    // 缓存文件大小50M
    public static double CACHE_SIZE = 10;
    // 允许缓存到sd卡时,sd卡的最下剩余空间
    public static double FREE_SD_SPACE_NEEDED_TO_CACHE = 30;

    /**
     * 如果存在SD卡，那么缓存到SD卡、否则缓存到data/data/package/cache。下 <br>
     * 有些数据可能有些保存在SD卡上
     *
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static String getCacheRootPath() {
        if (isExitSdCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
        } else {
            return App.getInstance().getCacheDir().getPath() + File.separator;
        }
    }

    /**
     * 获取data/data/package/cache 目录 <br>
     * 用于保存一些必须要保存在手机app保存目录下的数据
     *
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static String getCacheAppPath() {
        return App.getInstance().getCacheDir().getPath() + "/";
    }

    /**
     * 根据媒体文件的uri获得path
     *
     * @param context
     * @param uri
     * @return
     * @Description:
     * @see:
     * @since:
     * @author: zhuanggy
     * @date:2013-9-24
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getMediaFilePathByUri(Activity context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * 获取图片保存路径
     *
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static String getCacheImagePath() {
        String rootPath = getCacheRootPath();
        (new File(rootPath)).setWritable(true);
        if (isExitSdCard()) {
            rootPath = rootPath + "Android/data/" + App.getInstance().getPackageName() + "/cache/files/image/";
        } else {
            rootPath = rootPath + "files/image/";
        }
        createDir(rootPath);
        return rootPath;
    }

    /**
     * 获取视频缓存路径
     * @return
     */
    public static String getCacheVideoPath(){
        String rootPath = getCacheRootPath();
        (new File(rootPath)).setWritable(true);
        if (isExitSdCard()) {
            rootPath = rootPath + "Android/data/" + App.getInstance().getPackageName() + "/cache/files/video/";
        } else {
            rootPath = rootPath + "files/video/";
        }
        createDir(rootPath);
        return rootPath;
    }
    /**
     * 将bitmap保存到文件
     * @param bitmap
     * @throws IOException
     */
    public static String saveBitmap(Bitmap bitmap) {
        String filename= System.currentTimeMillis()+".png";
        File file = new File(getCacheImagePath()+filename);
        if(file.exists()){
            file.delete();
        }
        FileOutputStream out;
        try{
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.PNG, 90, out))
            {
                out.flush();
                out.close();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
    /**
     * 获取普通文本内容保存路径
     *
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static String getCacheTextPath() {
        String rootPath = getCacheAppPath();
        return rootPath;
    }

    /**
     * 判断是否挂起SdCard
     *
     * @return
     */
    public static boolean isExitSdCard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取一张图片地址，用于保存图片
     *
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-14
     */
    public static String getImageChaceDirectory() {
        return getCacheImagePath() + Math.abs(UUID.randomUUID().hashCode()) + ".jpg";
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * 是否是文件夹
     *
     * @param path
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static boolean isDir(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * 是否存在
     *
     * @param dir
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */

    public static boolean isExistDir(String dir) {
        File file = new File(dir);
        return file.exists();
    }

    /**
     * 删除文件夹
     *
     * @param path
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }

    /**
     * 删除文件夹/文件
     *
     * @param file
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static void deleteFile(File file) {

        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param dir
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2015-10-28
     */
    public static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * @return void
     * @throws
     * @Title: updateFileTime
     * @Description: 修改文件的最后修改时间
     * @author wangtf
     * @date 2011-10-18
     * @parameters
     */
    public static void updateFileTime(String dir, String fileName) {
        File file = new File(dir, fileName);
        long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }

    /**
     * 计算存储目录下的文件大小，当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定 那么删除40%最近没有被使用的文件 *
     *
     * @param dirPath * @param filename
     */
    public static void removeCache(String dirPath) {
        List<File> fileList = new ArrayList<File>();
        getFiles(dirPath, fileList);
        getRomTotalSize();
        if (fileList.size() == 0) {
            return;
        }

        // 文件夹总大小
        long dirSize = 0;
        dirSize = getFileDirSize(fileList, dirSize);
//		//XLog.d(XLog.LOG_OTHER, "file:" + dirPath + " size=" + dirSize / 1024 / 1024 + "M");

        // 内存总大小(如果是SD卡路径，那么获取的是SD卡剩余总大小，如果是手机内存那么获取的是手机内存剩余总大小)
        double freeTotal = dirPath.contains(getCacheAppPath()) ? freeRomAvailableSize() : freeSpaceOnSd();

        if (dirSize > CACHE_SIZE * 1024 * 1024 || FREE_SD_SPACE_NEEDED_TO_CACHE > freeTotal) {
            // 需要删除的空间大小
            int removeFactor = (int) ((0.4 * fileList.size()) + 1);
            FileLastModifSort fileLastModifSort = new FileLastModifSort();
            Collections.sort(fileList, fileLastModifSort);
            for (int i = 0; i < removeFactor; i++) {
                // if(files[i].getName().contains(WHOLESALE_CONV)) {
                fileList.get(i).delete();
                // }
            }
        }
    }

    /**
     * 获取此file下的所有文件夹名称
     *
     * @param file
     * @return
     * @Description:
     * @see:
     * @since:
     * @author:www.wozhongla.com
     * @date:2014-12-9
     */
    public static List<String> foldersName(File file) {
        List<String> folders = new ArrayList<String>();
        if (file != null) {
            File[] files = file.listFiles();
            for (File f : files) {
                if (f.isDirectory()) {
                    folders.add(f.getName());
                }
            }
        }
        return folders;
    }

    /**
     * @return
     * @Description:计算文件大小
     * @see:
     * @since:
     * @author:chen zhong
     * @date:2013-8-12
     */
    public static long getFileDirSize(String dirPath) {
        long dirSize = 0;
        List<File> fileList = new ArrayList<File>();
        getFiles(dirPath, fileList);

        if (fileList.size() == 0) {
            return 0;
        }
        for (int i = 0; i < fileList.size(); i++) {
            dirSize += fileList.get(i).length();
        }
        return dirSize;
    }

    /**
     * @return long
     * @throws
     * @Title: findFiles
     * @Description: 递归获取文件夹的大小
     * @author wangtf
     * @date 2011-10-19
     * @parameters
     */
    public static long getFileDirSize(List<File> fileList, long dirSize) {
        for (int i = 0; i < fileList.size(); i++) {
            dirSize += fileList.get(i).length();
        }
        return dirSize;
    }

    /**
     * @return void
     * @throws
     * @Title: getFiles
     * @Description: 获取指定目录下的文件
     * @author wangtf
     * @date 2011-10-19
     * @parameters
     */
    public static void getFiles(String filePath, List<File> fileList) {
        File file = new File(filePath);
        if (file.isFile()) {
            fileList.add(file);
            return;
        }
        // 否则是目录，继续遍历里面的文件
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                getFiles(files[i].getAbsolutePath(), fileList);
            }
        }
    }

    /**
     * @return void
     * @throws
     * @Title: removeExpiredCache
     * @Description: 删除过期文件
     * @author wangtf
     * @date 2011-10-18
     * @parameters
     */
    public static void deleteExpiredFile(String dirPath) {
        List<File> fileList = new ArrayList<File>();
        getFiles(dirPath, fileList);
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (System.currentTimeMillis() - file.lastModified() > DELETE_EXPIRED_FILE_TIME) {
                file.delete();
            }
        }
    }

    /**
     * @return double MB
     * @throws
     * @Title: freeSpaceOnSd
     * @Description: 获取sd卡剩余空间
     * @author wangtf
     * @date 2011-10-18
     * @parameters
     */
    public static double freeSpaceOnSd() {
        double sdSize = 0d;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            sdSize = availCount * blockSize / (1024 * 1024);
        }
        //XLog.d(XLog.LOG_OTHER, "SD size=" + sdSize + "M");
        return sdSize;
    }

    /**
     * 获得机身可用内存
     *
     * @return
     */
    private static double freeRomAvailableSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        double romSize = blockSize * availableBlocks / 1024 / 1024;
        //XLog.d(XLog.LOG_OTHER, "Rom 剩余 size=" + romSize + "M");
        return romSize;
    }

    /**
     * 获得机身内存总大小
     *
     * @return
     */
    private static double getRomTotalSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        double romSize = blockSize * totalBlocks / 1024 / 1024;
        //XLog.d(XLog.LOG_OTHER, "Rom 总 size=" + romSize + "M");
        return romSize;
    }

    /**
     * @return double
     * @throws
     * @Title: freeSpaceOnSystem
     * @Description: 获取系统剩余内存空间
     * @author wangtf
     * @date 2011-10-18
     * @parameters
     */
    public static double freeSpaceOnSystem() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();

        return availCount * blockSize / (1024 * 1024);
    }

    /**
     * @ClassName: FileLastModifSort
     * @Description: 根据文件的最后修改时间进行排序
     * @author:wangtf
     * @date 2011-10-18 下午6:37:03
     */
    static class FileLastModifSort implements Comparator<File> {

        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    //Ps:原本uri返回的是file:///...，android4.4返回的是content://,所以这里要注意一下
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {
        Cursor cursor=null;
        String url = "";
        if (!TextUtils.isEmpty(uri.getAuthority())) {
            try {
                cursor = context.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.Media.DATA}, null, null, null);
                if (null == cursor) {

                    return url;
                }
                cursor.moveToFirst();
                url = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else {
            url = uri.getPath();
        }
        return url;
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     */
    private static String getMIMEType(String file_name) {
        String type = "*/*";
        String fName = file_name.toLowerCase();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    /**
     * 打开文件
     */
    public static Intent getOpenFileIntent(String file_name, Uri uri, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        // 获取文件file的MIME类型
        String type = getMIMEType(file_name);
        intent.setDataAndType(uri, type);
        return intent;
    }

    // 建立一个MIME类型与文件后缀名的匹配表
    private final static String[][] MIME_MapTable = {
            // {后缀名， MIME类型}
            {".3gp", "video/3gpp"}, {".apk", "application/vnd.android.package-archive"}, {".asf", "video/x-ms-asf"}, {".avi", "video/x-msvideo"}, {".bin", "application/octet-stream"}, {".bmp", "image/bmp"}, {".c", "text/plain"}, {".class", "application/octet-stream"}, {".conf", "text/plain"}, {".cpp", "text/plain"}, {".doc", "application/msword"}, {".exe", "application/octet-stream"}, {".gif", "image/gif"}, {".gtar", "application/x-gtar"}, {".gz", "application/x-gzip"}, {".h", "text/plain"}, {".htm", "text/html"}, {".html", "text/html"}, {".jar", "application/java-archive"}, {".java", "text/plain"}, {".jpeg", "image/jpeg"}, {".jpg", "image/jpeg"}, {".js", "application/x-javascript"}, {".log", "text/plain"}, {".m3u", "audio/x-mpegurl"}, {".m4a", "audio/mp4a-latm"}, {".m4b", "audio/mp4a-latm"}, {".m4p", "audio/mp4a-latm"}, {".m4u", "video/vnd.mpegurl"}, {".m4v", "video/x-m4v"}, {".mov", "video/quicktime"}, {".mp2", "audio/x-mpeg"}, {".mp3", "audio/x-mpeg"}, {".mp4", "video/mp4"}, {".mpc", "application/vnd.mpohun.certificate"}, {".mpe", "video/mpeg"}, {".mpeg", "video/mpeg"}, {".mpg", "video/mpeg"}, {".mpg4", "video/mp4"}, {".mpga", "audio/mpeg"}, {".msg", "application/vnd.ms-outlook"}, {".ogg", "audio/ogg"}, {".pdf", "application/pdf"}, {".png", "image/png"}, {".pps", "application/vnd.ms-powerpoint"}, {".ppt", "application/vnd.ms-powerpoint"}, {".prop", "text/plain"}, {".rar", "application/x-rar-compressed"}, {".rc", "text/plain"}, {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"}, {".sh", "text/plain"}, {".tar", "application/x-tar"}, {".tgz", "application/x-compressed"}, {".txt", "text/plain"}, {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"}, {".wmv", "audio/x-ms-wmv"}, {".wps", "application/vnd.ms-works"},
            // {".xml", "text/xml"},
            {".xml", "text/plain"}, {".z", "application/x-compress"}, {".zip", "application/zip"},
            // word 2007
            {"docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"}, {"xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"}, {"pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"}};

    public static long forChannel(File f1, File f2) throws Exception {
        long time = new Date().getTime();
        int length = 2097152;
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        FileChannel inC = in.getChannel();
        FileChannel outC = out.getChannel();
        ByteBuffer b = null;
        while (true) {
            if (inC.position() == inC.size()) {
                inC.close();
                outC.close();
                return new Date().getTime() - time;
            }
            if ((inC.size() - inC.position()) < length) {
                length = (int) (inC.size() - inC.position());
            } else
                length = 2097152;
            b = ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
    }
}
