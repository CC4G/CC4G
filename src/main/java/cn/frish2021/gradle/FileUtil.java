package cn.frish2021.gradle;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.stream.Stream;

public class FileUtil {
    public static String readString(String link)
    {
        StringBuilder stringBuilder = new StringBuilder();
        try
        {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection)
            {
                connection = ((HttpURLConnection) urlConnection);
            }
            if (connection != null)
            {
                IOUtils.readLines(connection.getInputStream(), StandardCharsets.UTF_8).forEach(line -> stringBuilder.append(line).append("\n"));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static byte[] readFile(String link)
    {
        byte[] bytes = null;
        try
        {
            URL url = new URL(link);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection)
            {
                connection = ((HttpURLConnection) urlConnection);
            }
            if (connection != null)
            {
                bytes = IOUtils.toByteArray(connection.getInputStream());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return bytes;
    }

    public static void deleteDirectory(File file) {

        File[] list = file.listFiles();  //无法做到list多层文件夹数据
        if (list != null) {
            for (File temp : list) {     //先去递归删除子文件夹及子文件
                deleteDirectory(temp);   //注意这里是递归调用
            }
        }

        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
