package com.yifeng.img;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author RockeyCai
 * 
 */
public class GenerateScreen {

	public static void main(String[] args) {

		// 生成页面截图
		createScreen();
		
		createiPad();
		
		// 生成icon
		// createIcon();

	}

	/**
	 * 生成宣传页面 4.7 英寸 5.5 英寸 4 英寸 3.5 英寸 iPadi Pad Pro
	 */
	public static void createScreen() {

		// 生成iPhone图片 输出图片地址
		// String outputRootPath = "E:/mytest/iPhone/";
		// String outputRootPath = System.getProperty("user.dir") +
		// "/image/iPhone/";
		String outputRootPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/英文";
		// 源图片文件夹地址
		// String inputPath = "E:/mytest/iPhone/5.5/";
		// String inputPath = System.getProperty("user.dir") +
		// "/image/iPhone/5.5/";
		String inputPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/英文/res";

		// 生成图片集合
		List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
		imageInfos.add(ImageInfo.iPhone5_5); // E:/mytest/iPhone/4.7
		imageInfos.add(ImageInfo.iPhone4_7); // E:/mytest/iPhone/4.7
		imageInfos.add(ImageInfo.iPhone4_0); // E:/mytest/iPhone/4.0
		imageInfos.add(ImageInfo.iPhone3_5); // E:/mytest/iPhone/3.5
		// 生成
		ImgUtil.createImg(inputPath, outputRootPath, imageInfos);

		// 生成iPhone图片 输出图片地址
		// String outputRootPath = "E:/mytest/iPhone/";
		// String outputRootPath = System.getProperty("user.dir") +
		// "/image/iPhone/";
		outputRootPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/中文/";
		// 源图片文件夹地址
		// String inputPath = "E:/mytest/iPhone/5.5/";
		// String inputPath = System.getProperty("user.dir") +
		// "/image/iPhone/5.5/";
		inputPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/中文/res/";

		// 生成
		ImgUtil.createImg(inputPath, outputRootPath, imageInfos);

	}

	public static void createiPad() {

		// 生成iPhone图片 输出图片地址
		String outputRootPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/英文/";
		// 源图片文件夹地址
		String inputPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/英文/res_iPad/";

		// 生成图片集合
		List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
		imageInfos.add(ImageInfo.iPad); // E:/mytest/iPad/
		imageInfos.add(ImageInfo.iPad_Pro); // E:/mytest/iPad/
		// 生成
		ImgUtil.createImg(inputPath, outputRootPath, imageInfos);
		
		// 生成iPhone图片 输出图片地址
		outputRootPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/中文/";
		// 源图片文件夹地址
		inputPath = "/my/已发布软件图片资料/相册Web/Web相册/界面/中文/res_iPad/";
		// 生成
		ImgUtil.createImg(inputPath, outputRootPath, imageInfos);
	}
	
	/**
	 * 生成icon
	 */
	public static void createIcon() {

		// 源图片文件夹地址
		// String inputPath = "E:/mytest/icon/icon1024x1024.jpeg";
		String inputPath = System.getProperty("user.dir")
				+ "/image/icon/icon1024x1024.jpeg";

		// 生成icon图片 输出图片地址
		// String outputRootPath = "E:/mytest/icon/iPhone/";
		String outputRootPath = System.getProperty("user.dir")
				+ "/image/icon/iPhone/";

		// 生成图片集合
		List<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
		imageInfos.add(ImageInfo.icon_29);
		imageInfos.add(ImageInfo.icon_29_2x);
		imageInfos.add(ImageInfo.icon_29_3x);

		imageInfos.add(ImageInfo.icon_40_2x);
		imageInfos.add(ImageInfo.icon_40_3x);

		imageInfos.add(ImageInfo.icon_57);
		imageInfos.add(ImageInfo.icon_57_2x);

		imageInfos.add(ImageInfo.icon_60_2x);
		imageInfos.add(ImageInfo.icon_60_3x);

		ImgUtil.createIcon(inputPath, outputRootPath, imageInfos);

		// 保存图片地址
		// outputRootPath = "E:/mytest/icon/iPad/";
		outputRootPath = System.getProperty("user.dir") + "/image/icon/iPad/";

		// 生成图片集合
		imageInfos.clear();
		imageInfos.add(ImageInfo.icon_29);
		imageInfos.add(ImageInfo.icon_29_2x);

		imageInfos.add(ImageInfo.icon_40);
		imageInfos.add(ImageInfo.icon_40_2x);

		imageInfos.add(ImageInfo.icon_50);
		imageInfos.add(ImageInfo.icon_50_2x);

		imageInfos.add(ImageInfo.icon_72);
		imageInfos.add(ImageInfo.icon_72_2x);

		imageInfos.add(ImageInfo.icon_76);
		imageInfos.add(ImageInfo.icon_76_2x);

		ImgUtil.createIcon(inputPath, outputRootPath, imageInfos);
	}
}
