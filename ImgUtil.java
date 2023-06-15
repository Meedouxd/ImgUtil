/*Author: Chase Morgan March 2023
//Java implementation of pyautogui (does not have all the features)
//Original library: https://pyautogui.readthedocs.io/en/latest/
//IMPORTANT: RGB of transparency for images is 9,246,36 and HEX is 09F624
*/
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImgUtil {
    public static Robot robot;
    public static Random rand = new Random();
    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
    //finding the corner of an image on screen
    public static Point getImageCorner(String path, boolean hasTrans) throws AWTException, IOException {
        BufferedImage subimage = ImageIO.read(new File(path));
        BufferedImage image = getScreenshot();
        int smallest = subimage.getWidth();
        if(subimage.getWidth() > subimage.getHeight()){
            smallest = subimage.getHeight();
        }
        for (int i = 0; i <= image.getWidth() - subimage.getWidth(); i++) {
            search:
            for (int j = 0; j <= image.getHeight() - subimage.getHeight(); j++) {
                if(image.getRGB(i,j) == subimage.getRGB(0,0) || subimage.getRGB(0,0) == -16124380){
                    if(image.getRGB(i+subimage.getWidth()-1, j) == subimage.getRGB(subimage.getWidth()-1, 0) ||subimage.getRGB(subimage.getWidth()-1, 0) == -16124380 ){
                        for(int d = 0; d < smallest; d++){
                            if(image.getRGB(i+d,j+d) != subimage.getRGB(d, d) && hasTrans && subimage.getRGB(d,d) != -16124380){
                                continue search;
                            }
                        }
                        return new Point(i, j);
                    }
                }
            }
        }
        return new Point(-1,-1);
    }
    //finding image if you know the min and max coords of location
    public static Point getImageCornerWithBounds(String path, int x1, int y1, int x2, int y2, boolean hasTrans) throws AWTException, IOException {
        BufferedImage subimage = ImageIO.read(new File(path));
        BufferedImage image = getScreenshot();
        int smallest = subimage.getWidth();
        if(subimage.getWidth() > subimage.getHeight()){
            smallest = subimage.getHeight();
        }
        for (int i = x1; i <= x2 - subimage.getWidth(); i++) {
            search:
            for (int j = y1; j <= y2 - subimage.getHeight(); j++) {
                if(image.getRGB(i,j) == subimage.getRGB(0,0) || subimage.getRGB(0,0) == -16124380){
                    if(image.getRGB(i+subimage.getWidth()-1, j) == subimage.getRGB(subimage.getWidth()-1, 0) ||subimage.getRGB(subimage.getWidth()-1, 0) == -16124380 ){
                        for(int d = 0; d < smallest; d++){
                            if(image.getRGB(i+d,j+d) != subimage.getRGB(d, d) && hasTrans && subimage.getRGB(d,d) != -16124380){
                                continue search;
                            }
                        }
                        return new Point(i, j);
                    }
                }
            }
        }
        return new Point(-1,-1);
    }

    //gets the x and y of the middle of an image on screen
    public static Point getImageCenter(String path, boolean hasTrans) throws AWTException, IOException {
        BufferedImage subimage = ImageIO.read(new File(path));
        Point topLeft = getImageCorner(path, hasTrans);
        if (topLeft.x == -1) return new Point(-1,-1);
        //System.out.println((topLeft.x + subimage.getWidth()) /2 +","+ (topLeft.y + subimage.getWidth()) /2);
        return new Point(topLeft.x + (subimage.getWidth() /2), topLeft.y + (subimage.getHeight()/2));
    }
    //gets the x and y of the middle of an image on screen with given bounds
    public static Point getImageCenterWithBounds(String path, int x1, int y1, int x2, int y2, boolean hasTrans) throws AWTException, IOException {
        BufferedImage subimage = ImageIO.read(new File(path));
        Point topLeft = getImageCornerWithBounds(path, x1, y1, x2, y2, hasTrans);
        if (topLeft.x == -1) return new Point(-1,-1);
        //System.out.println((topLeft.x + subimage.getWidth()) /2 +","+ (topLeft.y + subimage.getWidth()) /2);
        return new Point(topLeft.x + (subimage.getWidth() /2), topLeft.y + (subimage.getHeight()/2));
    }
    //returns true/false for an image on screen
    public static boolean isImageOnScreen(String path, boolean hasTrans) throws AWTException, IOException {
        return getImageCorner(path, hasTrans).x != -1;
    }
    //returns true/false for an image on screen in a given area
    public static boolean isImageOnScreenWithBounds(String path,int x1, int y1, int x2, int y2, boolean hasTrans) throws AWTException, IOException {
        return getImageCornerWithBounds(path,x1,y1,x2,y2, hasTrans).x != -1;
    }
    public static void moveMouse(int x, int y){
        if(x < 0 || y < 0) return;
        robot.mouseMove(x,y);
    }
    public static BufferedImage getScreenshot() throws AWTException {
        //Robot r = new Robot();
        // take a full screenshot
        return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
    }
    public static String getCursorInfo() throws AWTException {
        BufferedImage image = getScreenshot();
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        return "MouseX, MouseY, PixelRGB Value: " + mouseX + "," + mouseY + "," + image.getRGB(mouseX,mouseY);
    }
    public static Point getMouseCoords(){
        int mouseX = MouseInfo.getPointerInfo().getLocation().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y;
        return new Point(mouseX, mouseY);
    }
    //some simple shortcuts for the robot class that would typically be used.
    public static void leftPress(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void leftRelease(){
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void rightPress(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
    }
    public static void rightRelease(){
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
    public static void leftClick(){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        delay(rand.nextInt(50)+10);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
     public static void leftClick(int delay){
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(delay);
        delay(rand.nextInt(50)+10);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
    public static void rightClick(){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        delay(rand.nextInt(50)+10);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }

    public static void rightClick(int delay){
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.delay(delay);
        delay(rand.nextInt(50)+10);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
    }
    public static void delay(int time){
        robot.delay(time);
    }
}
