package com.yifeng.img;

/**
 * @ClassName: ImageInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author RockeyCai
 * @date 2015年11月2日 下午3:11:24
 * 
 */
public enum ImageInfo {

	iPhone5_5("5.5", 1242, 2208),
	iPhone4_7("4.7", 750, 1334),
	iPhone4_0("4.0", 640, 1136),
	iPhone3_5("3.5", 640, 960),
	
	iPad("iPad", 1536, 2048),
	iPad_Pro("iPad_Pro", 2048, 2732),
	
	iPhone_icon("iPhone_icon", 1024, 1024),
	iPad_icon("iPad_icon", 1024, 1024),
	
	icon_29("Icon-Small.png", 29, 29),
	icon_29_2x("Icon-Small@2x.png", 58, 58),
	icon_29_3x("Icon-Small@3x.png", 87, 87),
	
	icon_40("Icon-40.png", 40, 40),
	icon_40_2x("Icon-40@2x.png", 80, 80),
	icon_40_3x("Icon-40@3x.png", 120, 120),
	
	icon_50("Icon-50.png", 50, 50),
	icon_50_2x("Icon-50@2x.png", 100, 100),
	icon_50_3x("Icon-50@3x.png", 150, 150),
	
	icon_57("Icon.png", 57, 57),
	icon_57_2x("Icon@2x.png", 114, 114),
	icon_57_3x("Icon@3x.png", 171, 171),
	
	icon_60("Icon-60.png", 60, 60),
	icon_60_2x("Icon-60@2x.png", 120, 120),
	icon_60_3x("Icon-60@3x.png", 180, 180),
	
	icon_72("Icon-72.png", 72, 72),
	icon_72_2x("Icon-72@2x.png", 144, 144),
	icon_72_3x("Icon-72@3x.png", 216, 216),
	
	icon_76("Icon-76.png", 76, 76),
	icon_76_2x("Icon-76@2x.png", 156, 156),
	icon_76_3x("Icon-76@3x.png", 228, 228);
	
	
	public int width;
	public int height;
	public String imgName;

	private ImageInfo(String imgName, int width, int height) {
		this.width = width;
		this.height = height;
		this.imgName = imgName;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
}