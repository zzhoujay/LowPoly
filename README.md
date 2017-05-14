# Low Poly 图片生成器

> 能够生成low poly风格的图片,[Android版本的实现](https://github.com/zzhoujay/LowPolyAndroid)

### 效果图

![](img/image.png)

### 使用方法

```
LowPoly.generate(inputStream,outputStream);
```
或者
```
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
LowPoly.generate(inputStream, outputStream, accuracy, scale, fill,format, antiAliasing, pointCount);
```

### jar包使用方法

[下载](https://github.com/zzhoujay/LowPoly/releases/download/1.1/LowPoly-1.1.jar)

```
java -jar LowPoly-1.0.jar input_filename output_filename
```

或者

生成配置文件：

```
java -jar LowPoly-1.1.jar configuration.properties
```
然后

```
java -jar LowPoly-1.0.jar input_filename output_filename configuration.properties
```

### 原理介绍

Low Poly即低多边形，和提高图片精度相反，我们需要降低图片精度来达到low poly的效果

整个算法最主要的就是两步

1. 降低精度
2. 提取图像信息

使用提取到的图像边缘点和一些随机点生成三角形，并着色，即可完成图片的low poly化

#### 降低精度

降低精度采用的是使用三角形粗化像素点的方法，具体实现使用Delaunay算法，具体实现参见`Delaunay.java`

如果单纯只是降低精度的效果图如下：

![](img/1.png)

其中如果去掉填充的颜色（由于点是随机生成的，所以两次生成的点不一样）

![](img/2.png)

取的点越多，生成的图片就会越接近原图片


#### 提取图像信息

采用Sobel算法进行边缘检测，提取图像关键信息，具体参见`Sobel.java`

采集到的点

![](img/3.png)

由于源图片信息量较大采集到的点也就很多，最终效果是由随机的点和采集到的点叠加产生的。


_by zzhoujay_