package com.study.shenxing.caesar.utils;

import java.io.File;

/**
 * 文件操作类
 * Created by shenxing on 16/1/28.
 */
public class FileUtils {

    /**
     * 递归计算文件大小
     * @param file
     * @return
     */
    private static long getFileSize(File file) {
        long fileSize = 0 ;
        if (file.isDirectory()) {
            File subFiles[] = file.listFiles() ;
            int subFileCount = subFiles.length ;
            for (int i = 0; i < subFileCount; i++) {
                if (subFiles[i].isDirectory()) {
                    fileSize += getFileSize(subFiles[i]) ;
                } else {
                    fileSize += subFiles[i].length();
                }
            }
        } else {
            fileSize += file.length();
        }
        return fileSize ;
    }

    /**
     * 删除文件夹或者文件
     * @param folderPath String 文件夹路径或者文件的绝对路径
     */
    public static boolean deleteDirectory(String folderPath) {
        boolean isCompleted = false;
        try {
            // 删除文件夹里所有的文件及文件夹
            deleteAllFile(folderPath);
            File lastFile = new File(folderPath);
            if (lastFile.exists()) {
                // 最后删除空文件夹
                lastFile.delete();
                if (!lastFile.exists()) {
                    isCompleted = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isCompleted;
    }

    /**
     * 删除文件夹里面的所有文件
     * @param path String 文件夹路径或者文件的绝对路径
     */
    private static void deleteAllFile(String path) {
        // 在内存开辟一个文件空间，但是没有创建
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            String[] tempList = file.list();
            File temp ;
            if (tempList != null) {
                for (int i = 0; i < tempList.length; i++) {
                    if (path.endsWith(File.separator)) {
                        temp = new File(path + tempList[i]);
                    } else {
                        temp = new File(path + File.separator + tempList[i]);
                    }
                    if (temp.isFile()) {
                        temp.delete();
                    }
                    if (temp.isDirectory()) {
                        // 先删除文件夹里面的文件
                        deleteAllFile(path + "/" + tempList[i]);
                        // 再删除空文件夹
                        deleteDirectory(path + "/" + tempList[i]);
                    }
                }
            }

        }
    }



}
