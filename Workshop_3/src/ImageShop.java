
public class ImageShop {
	private ImageDocument image;
	
	public static void main(String args[]){
		ImageLoader loader = new ImageLoader("someFile");
		ImageDocument doc = loader.loadImage();
		
		/* Create and initialise library */
		EffectLibrary lib = new EffectLibrary();
		lib.initialise();
		Effect effect = lib.getEffect("grayScale");
		
		/* modify document */
		doc.outputFile = "output";
		doc.addTransform(effect);
		doc.renderImage("output");
		
	}
	private Effect[] processTransforms(String args[]){
		Effect effect[] = new Effect[1];
		effect[0] = new GrayScaleEffect();
		return effect;
	}
	
	private void processImage(String image, Effect[] transforms){
		
	}

}
