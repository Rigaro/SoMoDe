import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageLoader {
	private String fileName;
	
	public ImageLoader(String file){
		this.fileName = file;
	}
	
	public ImageDocument loadImage(){
		BufferedImage img = new BufferedImage(0, 0, 0);
		ImageDocument someImage = new ImageDocument(img);
		return someImage;
	}
	
	public void writeImage(Image img, String outputFile){
		
	}
}
