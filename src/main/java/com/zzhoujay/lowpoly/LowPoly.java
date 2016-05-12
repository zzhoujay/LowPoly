package com.zzhoujay.lowpoly;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhou on 16-5-11.
 * <p>
 * LowPoly图片生成器
 */
public final class LowPoly {

    public static void generate(InputStream inputStream, OutputStream outputStream) throws IOException {
        generate(inputStream, outputStream, 50, 1, true, "png", false, 300);
    }

    /**
     * 生成low poly风格的图片
     *
     * @param inputStream  源图片
     * @param outputStream 输出图片流
     * @param accuracy     精度值，越小精度越高
     * @param scale        缩放，源图片和目标图片的尺寸比例
     * @param fill         是否填充颜色，为false时只绘制线条
     * @param format       输出图片格式
     * @param antiAliasing 是否抗锯齿
     * @param pointCount   随机点的数量
     * @throws IOException
     */
    public static void generate(InputStream inputStream, OutputStream outputStream, int accuracy, float scale, boolean fill, String format, boolean antiAliasing, int pointCount) throws IOException {
        if (inputStream == null || outputStream == null) {
            return;
        }
        BufferedImage image = ImageIO.read(inputStream);

        int width = image.getWidth();
        int height = image.getHeight();

        ArrayList<int[]> collectors = new ArrayList<>();
        ArrayList<int[]> particles = new ArrayList<>();

        Sobel.sobel(image, (magnitude, x, y) -> {
            if (magnitude > 40) {
                collectors.add(new int[]{x, y});
            }
        });

        for (int i = 0; i < pointCount; i++) {
            particles.add(new int[]{(int) (Math.random() * width), (int) (Math.random() * height)});
        }

        int len = collectors.size() / accuracy;
        for (int i = 0; i < len; i++) {
            int random = (int) (Math.random() * collectors.size());
            particles.add(collectors.get(random));
            collectors.remove(random);
        }

        particles.add(new int[]{0, 0});
        particles.add(new int[]{0, height});
        particles.add(new int[]{width, 0});
        particles.add(new int[]{width, height});

        List<Integer> triangles = Delaunay.triangulate(particles);

        float x1, x2, x3, y1, y2, y3, cx, cy;

        BufferedImage out = new BufferedImage((int) (width * scale), (int) (height * scale), BufferedImage.TYPE_INT_ARGB);

        Graphics g = out.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAliasing ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_DEFAULT);

        for (int i = 0; i < triangles.size(); i += 3) {
            x1 = particles.get(triangles.get(i))[0];
            x2 = particles.get(triangles.get(i + 1))[0];
            x3 = particles.get(triangles.get(i + 2))[0];
            y1 = particles.get(triangles.get(i))[1];
            y2 = particles.get(triangles.get(i + 1))[1];
            y3 = particles.get(triangles.get(i + 2))[1];

            cx = (x1 + x2 + x3) / 3;
            cy = (y1 + y2 + y3) / 3;

            Color color = new Color(image.getRGB((int) cx, (int) cy));

            g.setColor(color);
            if (fill) {
                g.fillPolygon(new int[]{(int) (x1 * scale), (int) (x2 * scale), (int) (x3 * scale)}, new int[]{(int) (y1 * scale), (int) (y2 * scale), (int) (y3 * scale)}, 3);
            } else {
                g.drawPolygon(new int[]{(int) (x1 * scale), (int) (x2 * scale), (int) (x3 * scale)}, new int[]{(int) (y1 * scale), (int) (y2 * scale), (int) (y3 * scale)}, 3);
            }
        }

        ImageIO.write(out, format, outputStream);

    }
}
