package fr.triedge.file.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class Utils {

	public static String encodeFileToBase64Binary(File file) throws Exception{
		FileInputStream fileInputStreamReader = new FileInputStream(file);
		byte[] bytes = new byte[(int)file.length()];
		fileInputStreamReader.read(bytes);
		String res = new String(Base64.getEncoder().encodeToString(bytes));
		fileInputStreamReader.close();
		return res;
	}

	
	public static boolean compareWithBaseImage(File baseImage, File compareImage)
			throws IOException {
		BufferedImage bImage = ImageIO.read(baseImage);
		BufferedImage cImage = ImageIO.read(compareImage);
		int height = bImage.getHeight();
		int width = bImage.getWidth();
		boolean same = false;
		//BufferedImage rImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				try {
					int pixelC = cImage.getRGB(x, y);
					int pixelB = bImage.getRGB(x, y);
					if (pixelB == pixelC ) {
						same = true;
					} else {
						same = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return same;
	}
}
