package com.yifeng.img;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 需求：原始图片缩放为目标尺寸后，要求缩放后的图片保持原图片的样式不改变，但是缩放后图片的四边可以裁减。
 * 原理：将待裁剪图片宽高相除值和目标尺寸宽高相除值进行比较 ，
 * 如果前者较大，说明待裁剪图片相对于目标尺寸来说要宽出一块，现在已高为基准进行裁剪，即将原始图片的高度缩放到目标尺寸的高度。
 * 计算出原始图片高度和目标尺寸高度的比例，和原始图片宽度相乘，得到缩放后的宽度。
 * 此时的缩缩放后的高度就是目标尺寸的高度，但是宽度*肯定*会比目标尺寸宽度宽。 将缩放后的多余的宽度分为两份右边和左边各裁去一份。 以宽为基准原理类似。
 * 
 * @ClassName: ImgUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author RockeyCai
 * @date 2015年11月2日 上午10:25:21
 * 
 */
public class ImgUtil {

	/**
	 * 第一步，等比例压缩图片，第二部，截取图片（根据计算，平均截取）
	 * 
	 * @param srcImgPath
	 *            待切割图片路径
	 * @param destImgPath
	 *            切割后图片路径
	 * @param destImgW
	 *            所需宽度
	 * @param destImgH
	 *            所需高度
	 */
	public static void createThumb(String srcImgPath, String destImgPath, int destImgW, int destImgH) {
		// 原图片等比例缩小或放大之后的图片
		int narrowImgW;
		int narrowImgH;
		// 原图片大小
		int srcImgW;
		int srcImgH;
		try {
			BufferedImage bi = ImageIO.read(new File(srcImgPath));
			srcImgW = bi.getWidth();
			srcImgH = bi.getHeight();
			// 转换图片尺寸与目标尺寸比较 ， 如果转换图片较小，说明转换图片相对于目标图片来说高较小，需要以高为基准进行缩放。
			if ((float) srcImgW / srcImgH > (float) destImgW / destImgH) {
				narrowImgW = (int) (((float) destImgH / (float) srcImgH) * srcImgW);
				narrowImgH = destImgH;
				// 按照原图以高为基准等比例缩放、或放大。这一步高为所需图片的高度，宽度肯定会比目标宽度宽。
				int cutNarrowImgSize = (narrowImgW - destImgW) / 2;
				BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH, BufferedImage.TYPE_INT_RGB);
				narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH), 0, 0, null);
				// 等比例缩放完成后宽度与目标尺寸宽度相比较 ， 将多余宽的部分分为两份 ，左边删除一部分
				Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT);
				CropImageFilter cropFilter = new CropImageFilter(cutNarrowImgSize, 0, narrowImgW - cutNarrowImgSize, narrowImgH);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage cutLiftNarrowImg = new BufferedImage(narrowImgW - cutNarrowImgSize, narrowImgH, BufferedImage.TYPE_INT_RGB);
				cutLiftNarrowImg.getGraphics().drawImage(img, 0, 0, null);
				// 右边删除一部分
				image = cutLiftNarrowImg.getScaledInstance(narrowImgW - cutNarrowImgSize, narrowImgH, Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(0, 0, narrowImgW - cutNarrowImgSize * 2, narrowImgH);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage cutRightNarrowImg = new BufferedImage(narrowImgW - cutNarrowImgSize * 2, narrowImgH, BufferedImage.TYPE_INT_RGB);
				Graphics g = cutRightNarrowImg.getGraphics();
				g.drawImage(img, 0, 0, null); // 绘制截取后的图
				g.dispose();
				// 输出为文件 最终为所需要的格式
				ImageIO.write(cutRightNarrowImg, "JPEG", new File(destImgPath));
			} else { // 以宽度为基准
				narrowImgW = destImgW;
				narrowImgH = (int) (((float) destImgW / (float) srcImgW) * srcImgH);
				int cutNarrowImgSize = (narrowImgH - destImgH) / 2;

				BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH, BufferedImage.TYPE_INT_RGB);
				narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH), 0, 0, null);

				Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT);
				CropImageFilter cropFilter = new CropImageFilter(0, cutNarrowImgSize, narrowImgW, narrowImgH - cutNarrowImgSize);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage cutTopNarrowImg = new BufferedImage(narrowImgW, narrowImgH - cutNarrowImgSize, BufferedImage.TYPE_INT_RGB);
				cutTopNarrowImg.getGraphics().drawImage(img, 0, 0, null);

				image = cutTopNarrowImg.getScaledInstance(narrowImgW, narrowImgH - cutNarrowImgSize, Image.SCALE_DEFAULT);
				cropFilter = new CropImageFilter(0, 0, narrowImgW, narrowImgH - cutNarrowImgSize * 2);
				img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage cutBottomNarrowImg = new BufferedImage(narrowImgW, narrowImgH - cutNarrowImgSize * 2, BufferedImage.TYPE_INT_RGB);
				Graphics g = cutBottomNarrowImg.getGraphics();
				g.drawImage(img, 0, 0, null);
				g.dispose();
				ImageIO.write(cutBottomNarrowImg, "JPEG", new File(destImgPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 第一步，等比例压缩图片，第二部，截取图片（以左上角x=0 y=0,截取）
	 * 
	 * @param srcImgPath
	 *            待切割图片路径
	 * @param destImgPath
	 *            切割后图片路径
	 * @param destImgW
	 *            所需宽度
	 * @param destImgH
	 *            所需高度
	 * @throws IOException
	 */
	public static void createThumb2(String srcImgPath, String destImgPath, int destImgW, int destImgH) throws IOException {

		// 原图片等比例缩小或放大之后的图片
		int narrowImgW;
		int narrowImgH;

		// 原图片大小
		int srcImgW;
		int srcImgH;

		// try {
		BufferedImage bi = ImageIO.read(new File(srcImgPath));
		srcImgW = bi.getWidth();
		srcImgH = bi.getHeight();

		// 转换图片尺寸与目标尺寸比较 ， 如果转换图片较小，说明转换图片相对于目标图片来说高较小，需要以高为基准进行缩放。
		if ((float) srcImgW / srcImgH > (float) destImgW / destImgH) {
//			narrowImgW = (int) (((float) destImgH / (float) srcImgH) * srcImgW);
			narrowImgW = destImgW;
			narrowImgH = destImgH;
			// 按照原图以高为基准等比例缩放、或放大。这一步高为所需图片的高度，宽度肯定会比目标宽度宽。
			BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH, BufferedImage.TYPE_INT_RGB);
			narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH), 0, 0, null);

			// 等比例缩放完成后宽度与目标尺寸宽度相比较 ， 将多余宽的部分分为两份 ，左边删除一部分
			Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT);

			// 获取需要部分
			CropImageFilter cropFilter = new CropImageFilter(0, 0, narrowImgW, destImgH);
			Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage cutRightNarrowImg = new BufferedImage(narrowImgW, destImgH, BufferedImage.TYPE_INT_RGB);

			Graphics g = cutRightNarrowImg.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制截取后的图
			g.dispose();

			// 输出为文件 最终为所需要的格式
			ImageIO.write(cutRightNarrowImg, "JPEG", new File(destImgPath));

		} else { // 以宽度为基准
			narrowImgW = destImgW;
			narrowImgH = (int) (((float) destImgW / (float) srcImgW) * srcImgH);

			BufferedImage narrowImg = new BufferedImage(narrowImgW, narrowImgH, BufferedImage.TYPE_INT_RGB);
			narrowImg.getGraphics().drawImage(bi.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_SMOOTH), 0, 0, null);

			Image image = narrowImg.getScaledInstance(narrowImgW, narrowImgH, Image.SCALE_DEFAULT);// 等比例压缩

			// 获取需要部分
			CropImageFilter cropFilter1 = new CropImageFilter(0, 0, narrowImgW, destImgH);
			Image img1 = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter1));
			BufferedImage cutBottomNarrowImg1 = new BufferedImage(narrowImgW, destImgH, BufferedImage.TYPE_INT_RGB);

			Graphics g = cutBottomNarrowImg1.getGraphics();
			g.drawImage(img1, 0, 0, null);
			g.dispose();
			ImageIO.write(cutBottomNarrowImg1, "JPEG", new File(destImgPath));
		}
		// } catch (IOException e) {
		// //e.printStackTrace();
		// }
	}

	/**
	 * @Title: createImg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param inputPath 源图片地址
	 * @param @param outputRootPath 保存地址
	 * @param @param imageInfos 生成图片集合
	 * @return void 返回类型
	 * @throws
	 */
	public static void createImg(String inputPath, String outputRootPath, List<ImageInfo> imageInfos) {

		// 要生成的图片列表
		List<File> srcImageList = new ArrayList<File>();

		// 获取要生成的图片列表
		File inputFilePath = new File(inputPath);
		if (inputFilePath.isDirectory()) {
			for (File temp : inputFilePath.listFiles()) {
				if (temp.isFile()) {
					if (temp.getName().toUpperCase().lastIndexOf(".JPG") > -1 ||  temp.getName().toUpperCase().lastIndexOf(".JPEG") > -1 || temp.getName().toUpperCase().lastIndexOf(".PNG") > -1) {
						srcImageList.add(temp);
					}
				}
			}
		}

		File file = null;
		// 遍历生成文件
		for (ImageInfo imageInfo : imageInfos) {
			// 生成图片存放的文件夹 E:/mytest/iPhone/4.7
			String format = null;
			//判断有没有以后杠结尾
			if(outputRootPath.lastIndexOf("/") == outputRootPath.length() - 1 || outputRootPath.lastIndexOf("\\") == outputRootPath.length() - 1){
				format = "%s%s";
			}else{
				format = "%s/%s";				
			}
			
			String createImgDicPath = String.format(format, outputRootPath, imageInfo.imgName);
			// 创建文件夹
			file = new File(createImgDicPath);
			if (!file.exists()) {
				file.mkdirs();
			}

			// 遍历图片集合,生成图片
			for (File srcImgeFile : srcImageList) {
				// 源图片路径 E:/mytest/iPhone/5.5/p1.jpg
				String srcImgPath = srcImgeFile.getPath();
				// 生成图片路径 E:/mytest/iPhone/4.7/p1.jpg
				String createImgName = String.format("%s/%s", createImgDicPath, srcImgeFile.getName());
				try {
					// 生成文件
					createThumb2(srcImgPath, createImgName, imageInfo.width, imageInfo.height);
					System.out.println(String.format("%s 生成完成", createImgName));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(String.format("%s 生成失败 原因:%s", createImgName, e.getMessage()));
				}

			}
		}
		srcImageList.clear();
		srcImageList = null;
	}
	
	
	/**
	 * @Title: createImg
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param inputPath 源图片地址
	 * @param @param outputRootPath 保存地址
	 * @param @param imageInfos 生成图片集合
	 * @return void 返回类型
	 * @throws
	 */
	public static void createIcon(String inputPath, String outputRootPath, List<ImageInfo> imageInfos) {
		
		// 要生成的图片列表
		List<File> srcImageList = new ArrayList<File>();
		File temp = new File(inputPath);
		if (temp.isFile() && ( temp.getName().toUpperCase().lastIndexOf(".JPG") > -1 ||  temp.getName().toUpperCase().lastIndexOf(".JPEG") > -1 || temp.getName().toUpperCase().lastIndexOf(".PNG") > -1 )) {
			srcImageList.add(temp);
		}else{
			System.out.println(String.format("图片不符合生产规格:%s", inputPath));
			return;
		}
		
		File file = null;
		// 遍历生成文件
		for (ImageInfo imageInfo : imageInfos) {
			// 生成图片存放的文件夹
			String createImgDicPath = outputRootPath;;
			// 创建文件夹
			file = new File(createImgDicPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			// 遍历图片集合,生成图片
			for (File srcImgeFile : srcImageList) {
				// 源图片路径
				String srcImgPath = srcImgeFile.getPath();
				// 生成图片路径
				String createImgName = String.format("%s%s", outputRootPath, imageInfo.imgName);
				try {
					// 生成文件
					createThumb2(srcImgPath, createImgName, imageInfo.width, imageInfo.height);
					System.out.println(String.format("%s 生成完成", createImgName));
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(String.format("%s 生成失败 原因:%s", createImgName, e.getMessage()));
				}
				
			}
		}
		srcImageList.clear();
		srcImageList = null;
	}
}