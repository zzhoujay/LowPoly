package com.zzhoujay.lowpoly;

/**
 * Created by zhou on 2017/5/14.
 */
public class Configuration {

    public static final int DEFAULT_ACCURACY = 50;
    public static final float DEFAULT_SCALE = 1;
    public static final boolean DEFAULT_FILL = true;
    public static final String DEFAULT_FORMAT = "png";
    public static final boolean DEFAULT_ANTI_ALIASING = false;
    public static final int DEFAULT_POINT_COUNT = 300;

    public static final String ACCURACY = "accuracy";
    public static final String SCALE = "scale";
    public static final String FILL = "fill";
    public static final String FORMAT = "format";
    public static final String ANTI_ALIASING = "antiAliasing";
    public static final String POINT_COUNT = "pointCount";

    public final int accuracy;
    public final float scale;
    public final boolean fill;
    public final String format;
    public final boolean antiAliasing;
    public final int pointCount;

    public Configuration() {
        this(DEFAULT_ACCURACY, DEFAULT_SCALE, DEFAULT_FILL, DEFAULT_FORMAT, DEFAULT_ANTI_ALIASING, DEFAULT_POINT_COUNT);
    }

    public Configuration(int accuracy, float scale, boolean fill, String format, boolean antiAliasing, int pointCount) {
        this.accuracy = accuracy;
        this.scale = scale;
        this.fill = fill;
        this.format = format;
        this.antiAliasing = antiAliasing;
        this.pointCount = pointCount;
    }
}
