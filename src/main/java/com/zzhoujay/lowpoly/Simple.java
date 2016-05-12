package com.zzhoujay.lowpoly;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zhou on 16-5-12.
 */
public class Simple {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            throw new RuntimeException("请输入有效的图片");
        }
        String in = args[0];
        String out = args[1];
        File inFile = new File(in);
        if (inFile.exists()) {
            File outFile = new File(out);
            FileInputStream inputStream = new FileInputStream(inFile);
            FileOutputStream outputStream = new FileOutputStream(outFile);
            long lastTime = System.currentTimeMillis();
            if (args.length >= 8) {
                int accuracy = Integer.valueOf(args[2]);
                float scale = Float.valueOf(args[3]);
                boolean fill = Boolean.valueOf(args[4]);
                String format = args[5];
                boolean antiAliasing = Boolean.valueOf(args[6]);
                int pointCount = Integer.valueOf(args[7]);
                LowPoly.generate(inputStream, outputStream, accuracy, scale, !fill, format, antiAliasing, pointCount);
            } else {
                LowPoly.generate(inputStream, outputStream);
            }
            System.out.println("目标已保存至:" + outFile.getAbsolutePath());
            System.out.println("用时:" + (System.currentTimeMillis() - lastTime));
        } else {
            throw new RuntimeException("源文件不存在");
        }
    }
}
