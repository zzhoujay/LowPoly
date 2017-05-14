package com.zzhoujay.lowpoly;

import java.io.*;
import java.util.Properties;

/**
 * Created by zhou on 16-5-12.
 */
public class Simple {

    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            return;
        }
        if (args.length == 1) {
            FileOutputStream fileOutputStream = new FileOutputStream(args[0]);
            createConfiguration(fileOutputStream);
            System.out.println("默认配置文件已创建");
            return;
        }
        String in = args[0];
        String out = args[1];
        File inFile = new File(in);
        if (inFile.exists()) {
            File outFile = new File(out);
            FileInputStream inputStream = new FileInputStream(inFile);
            FileOutputStream outputStream = new FileOutputStream(outFile);
            long lastTime = System.currentTimeMillis();
            Configuration configuration;
            if (args.length >= 3) {
                FileInputStream fileInputStream = new FileInputStream(args[2]);
                configuration = loadConfiguration(fileInputStream);
            } else {
                configuration = new Configuration();
            }

            LowPoly.generate(inputStream, outputStream, configuration);
            System.out.println("目标已保存至:" + outFile.getAbsolutePath());
            System.out.println("用时:" + (System.currentTimeMillis() - lastTime));
        } else {
            System.out.println("源文件不存在");
        }
    }

    private static void createConfiguration(OutputStream outputStream) throws IOException {
        Properties properties = new Properties();
        properties.setProperty(Configuration.ACCURACY, String.valueOf(Configuration.DEFAULT_ACCURACY));
        properties.setProperty(Configuration.SCALE, String.valueOf(Configuration.DEFAULT_SCALE));
        properties.setProperty(Configuration.FILL, String.valueOf(Configuration.DEFAULT_FILL));
        properties.setProperty(Configuration.FORMAT, Configuration.DEFAULT_FORMAT);
        properties.setProperty(Configuration.ANTI_ALIASING, String.valueOf(Configuration.DEFAULT_ANTI_ALIASING));
        properties.setProperty(Configuration.POINT_COUNT, String.valueOf(Configuration.DEFAULT_POINT_COUNT));
        properties.store(outputStream, "default");
    }

    private static Configuration loadConfiguration(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);

        String accuracy = properties.getProperty(Configuration.ACCURACY, String.valueOf(Configuration.DEFAULT_ACCURACY));
        String scale = properties.getProperty(Configuration.SCALE, String.valueOf(Configuration.DEFAULT_SCALE));
        String fill = properties.getProperty(Configuration.FILL, String.valueOf(Configuration.DEFAULT_FILL));
        String format = properties.getProperty(Configuration.FORMAT, Configuration.DEFAULT_FORMAT);
        String antiAliasing = properties.getProperty(Configuration.ANTI_ALIASING, String.valueOf(Configuration.DEFAULT_ANTI_ALIASING));
        String pointCount = properties.getProperty(Configuration.POINT_COUNT, String.valueOf(Configuration.DEFAULT_POINT_COUNT));

        return new Configuration(Integer.valueOf(accuracy), Float.valueOf(scale), Boolean.valueOf(fill), format, Boolean.valueOf(antiAliasing), Integer.valueOf(pointCount));
    }
}
