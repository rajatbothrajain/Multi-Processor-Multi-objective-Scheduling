/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphics.icon;

import File.Functions;
import Path.System;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Uday Kandpal
 */
public class implement {

    public final int BUTTON = 0;
    public final int TEXTFIELD = 1;
    public final int LABEL = 2;
    public final int RADIO = 3;
    public final int CHECK = 0;
    public final int CIRCLE = 0;
    public final int TRIANGLE = 0;
    public final int POLYGON = 0;
    Component cd;
    String unparsed;
    String parsed;
    String message = "Message :";
    Functions p = new Functions();

    void printScreen(String source) {

        try {
            Robot robot = new Robot();


            BufferedImage bufferedImage = robot.createScreenCapture(
                    new Rectangle(new Dimension(1280, 1024)));

            File imageFile = new File(source);
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    void drawShape(int Type, int shape, Event e, Rectangle r, Component c) {
        switch (Type) {
            case BUTTON:
                c.prepareImage(null, c);
        }

    }

    void parseGraphicFile(String src, String dest, int pass) {
        unparsed = Functions.File_Output(src);
        unparsed.replace("null", "");
        message.concat("------------------------------------------------------------\nPASS " + pass + "\n");
        p.print(message);
        char c;
        int i;
        for (i = 0; i < unparsed.length() - 1; i++) {
            c = unparsed.charAt(i);
            if (c > 127) {
                message.concat("\n the value of character " + c + "(" + Integer.parseInt(c + "") + ")" + "at index" + i + "was converted to " + (c - 127));
                c = (char) (c - (char) 127);
                parsed.concat(c + "");
                // Functions.System_Output(message, dest.replace(".","MESSAGE."), cd);
                p.print(message);
                p.print("value of c" + c + "");
            } else {
                continue;
            }
        }
        Functions.File_Input(parsed, dest, cd);
        Functions.File_Input(message, dest.replace(".", "MESSAGE."), cd);
        for (int j = 0; j < parsed.length(); j++) {
            c = parsed.charAt(i);
            if (c > 127) {
                parseGraphicFile(src, dest, pass + 1);
                break;
            } else {
                continue;
            }
        }

    }
    public String icon = "default";
    private String root = "C:\\OS\\graphics\\";

    public void load() {
        String path = root + icon + getExt();
    }

    public String getExt() {
        String ext = "uPIC";
        String path = root + icon;
        return ext;
    }
}
