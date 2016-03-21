import java.awt.image.BufferedImage;

public class ImageDocument {
	public BufferedImage image;
	public Effect[] transforms;
	public String outputFile;
	
	public ImageDocument(BufferedImage img){
		
	}
	
	public boolean addTransform(Effect t){
		return true;
	}
	
	public boolean addTransformations(Effect[] trans){
		return true;
	}
	
	public void renderImage(String outFile){
		transforms[0].applyEffect(image);
		
	}
}
