package pixelArt;
import java.awt.event.*;
import java.util.regex.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import javax.swing.*;

public class Board extends JPanel{
	private final Color BACKGROUND_COLOR = new Color(0x2c3e50);
	
	private String requestImg, pixelArtName;
	private JFileChooser selectorImg, folderDestiny;
	private int pixelImg[];
	private JButton selectImg, buttonDestiny;
	private ImageIcon icon_img;
	private Dimension size;
	private final int WIDTH = 350, HEIGHT = 350;
	private boolean isImgSelected;
	private Image imageSelected;
	private int imgWidth, imgHeight;
	private String currentDirectory;
	
	public Board() {
		initBoard();
		loadImages();
		initVariables();
	}
	
	public void initBoard() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(new BorderLayout());
	}
	
	public void initVariables() {
		isImgSelected = false;
		requestImg = "Select An Image";
		currentDirectory = System.getProperty("user.dir");
		
		selectImg = new JButton("Select Img", icon_img);
		selectImg.setUI(new ButtonUI());
		selectImg.addActionListener(e -> selectImgManager());
		add(selectImg, BorderLayout.NORTH);
		
		buttonDestiny = new JButton("Select Destiny");
		buttonDestiny.setUI(new ButtonUI());
		buttonDestiny.addActionListener(e -> destinyButtonManager());
		add(buttonDestiny, BorderLayout.SOUTH);
	}
	
	public void destinyButtonManager() {
		if(!isImgSelected) return;
		
		folderDestiny = new JFileChooser(new File(currentDirectory));
		folderDestiny.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int open = folderDestiny.showSaveDialog(this);
		
		if(open == JFileChooser.APPROVE_OPTION) {
			File path = folderDestiny.getSelectedFile();
			PixelHandler.drawPixelArt(pixelImg, path.getAbsolutePath(), pixelArtName, imgWidth);
		}
	}
	
	public boolean isAnImage(File file) {
		Pattern regExp = Pattern.compile(".+\\.(png|jpg)$");
		Matcher extension = regExp.matcher(file.getName());	
		return extension.matches();
	}
	
	public void saveImgInformation(File imagePath) {
		Image image = new ImageIcon(imagePath.getAbsolutePath()).getImage();
		imageSelected = changeSizeImg(image, 300, 200);//this is necessary to loop safely through the image 
													   //in pixelHandler(multiple of 5)
		String imageName = imagePath.getName();
		int extensionIndex = imageName.lastIndexOf(".");	
		pixelArtName = imageName.substring(0, extensionIndex);
		
		imgWidth = imageSelected.getWidth(null);
		imgHeight = imageSelected.getHeight(null);
		pixelImg = new int[imgWidth * imgHeight];
	}
	
	
	public void selectImgManager() {
		selectorImg = new JFileChooser(new File(currentDirectory));
		
		int open = selectorImg.showOpenDialog(this);
		
		if(open == JFileChooser.APPROVE_OPTION) {
			File fileChoosen = selectorImg.getSelectedFile();
			if(isAnImage(fileChoosen)) {			
				saveImgInformation(fileChoosen);
				isImgSelected = true;
				PixelHandler.takePixels(imageSelected, pixelImg);
				repaint();
			} else {
				requestImg = "Select A Valid Format .png or .jpg";
				repaint();
			}
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(isImgSelected) {
			drawImageSelected(g);
		} else {
			requestImgScreen(g);
		}
	}
	
	public void requestImgScreen(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Color.white);
		g2d.fillRect(50, 50, WIDTH - 100, HEIGHT - 100);
		g2d.setColor(Color.BLACK);
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.drawString(requestImg, (WIDTH - metrics.stringWidth(requestImg))/2, HEIGHT/2);
	}
	
	public void drawImageSelected(Graphics g) {
		g.drawImage(imageSelected, 50, 50, WIDTH - 100, HEIGHT - 100, null);
	}
	
	public void loadImages() {
		Image imgIcn = new ImageIcon("src/pixelArt/image-icon.png").getImage();
		icon_img = new ImageIcon(changeSizeImg(imgIcn, 15, 15));
	}
	
	public BufferedImage changeSizeImg(Image img,int desiredWidth, int desiredHeight) {
		
		
		BufferedImage resizedImg = new BufferedImage(desiredWidth, desiredHeight, BufferedImage.TYPE_INT_ARGB);	
		Graphics2D g2d = resizedImg.createGraphics();
		g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		
		g2d.drawImage(img, 0, 0, desiredWidth, desiredHeight, null);
		g2d.dispose();
		
		return resizedImg;
	}
	
	
}
