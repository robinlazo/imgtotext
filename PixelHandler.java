package pixelArt;

import java.awt.Image;

import java.awt.image.PixelGrabber;
import java.io.*;

public final class PixelHandler {
	private static CharGrayScale[] charRepresentation = CharGrayScale.values();
	
	public static void takePixels(Image image, int pixels[]) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		PixelGrabber grabber = new PixelGrabber(image, 0, 0, width, height, pixels, 0, width);
		try {
			boolean pixelsGrabbed = grabber.grabPixels();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static char grayscaleChar(int avg) {
		//i thought to use remaining of 30 to avoid if else ladder but i think this will be more readable
		if(avg > 228) {
			return CharGrayScale.White.getRepresentation();
		}else if(avg > 200) {
			return CharGrayScale.Lightgray.getRepresentation();
		} else if(avg > 174) {
			return CharGrayScale.Slategray.getRepresentation();
		} else if(avg > 145) {
			return CharGrayScale.Gray.getRepresentation();
		} else if(avg > 117) {
			return CharGrayScale.Medium.getRepresentation();
		} else if(avg > 90) {
			return CharGrayScale.MediumGray.getRepresentation();
		} else if(avg > 61) {
			return CharGrayScale.MediumDarkGray.getRepresentation();
		} else if(avg > 31) {
			return CharGrayScale.Darkgray.getRepresentation();
		} else if(avg > 5) {
			return CharGrayScale.Charcoal.getRepresentation();
		}
		
		return CharGrayScale.Black.getRepresentation();
	}
	
	public static void drawPixelArt(int pixels[], String filePath, String fileName, int scanLine) {
		
		String path = filePath + "/" + fileName + ".txt";
		
		try(FileWriter fileW = new FileWriter(path)) {
			
			for(int y = 0; y < pixels.length/scanLine; y++) {
				for(int x = 0; x < scanLine; x++) {
					
					int rgb = pixels[(y * scanLine) + x];
					int r = (rgb >> 16) & 0xff;
					int g = (rgb >> 8) & 0xff;
					int b = rgb & 0xff;
					int avg =(int) (.33 * r + .56 * g + .11 * b);
					char scale = grayscaleChar(avg);
					fileW.write(scale);
			
				}
				fileW.write('\n');
			}
			
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}
