package zombie;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Escape {

    Toolkit toolkit = Toolkit.getDefaultToolkit();
    int xscreensize = (int) toolkit.getScreenSize().getWidth();
    int yscreensize = (int) toolkit.getScreenSize().getHeight();
    public BufferedImage Minus, Plus, Minus1, Plus1;
    boolean MouseClicked = false;
    public boolean EscapeIsActive = true;
    boolean OptionsIsActive = false;
    boolean GraphicsActive = false;
    boolean AudioActive = false;
    boolean SettingsActive = false;
    boolean CreditsActive = false;
    public int Counter0 = 0;
    public int Counter1 = 0;
    double fontheight = yscreensize * 0.03425925925;
    int Fontheight = (int) Math.round(fontheight);
    Font stringFont = new Font("Comic Sans MS", Font.PLAIN, Fontheight);

    public Escape() {
    }

    public void drawEsc(Zombie z, Graphics g, int xscreensize, int yscreensize, int scale) throws IOException {
        //System.out.println(fontheight + " + " + Fontheight);
        if (EscapeIsActive == true) {
            EscapeMenu(g, scale, xscreensize, yscreensize);
            Resume(z, g, scale, xscreensize, yscreensize);
            Options(z, g, scale, xscreensize, yscreensize);
            Save(z, g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }
        if (z.OptionsIsActive == true) {
            OptionsMenu(g, scale, xscreensize, yscreensize);
            Graphics(z, g, scale, xscreensize, yscreensize);
            Audio(z, g, scale, xscreensize, yscreensize);
            Settings(z, g, scale, xscreensize, yscreensize);
            Credits(z, g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }
        if (z.GraphicsIsActive == true) {
            GraphicsMenu(g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }
        if (z.AudioIsActive == true) {
            AudioMenu(g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }
        if (z.SettingsIsActive == true) {
            SettingsMenu(g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }
        if (z.CreditsIsActive == true) {
            CreditsMenu(g, scale, xscreensize, yscreensize);
            z.mousepressed = false;
        }

    }

    public void EscapeMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }

    void Resume(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button = Color.LIGHT_GRAY;
        String strg = "Resume";
        int stringb = g.getFontMetrics().stringWidth(strg);
        double height = 450;
        double Height = height / yscreensize;
        double stringh = yscreensize * Height;
        int Stringh = (int) Math.round(stringh);
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                z.GameIsActive = true;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, Stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {

                Button = Color.LIGHT_GRAY;
            }
        }
    }

    void Options(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Options";
        stringb = g.getFontMetrics().stringWidth(strg);
        double height = 550;
        double Height = height / yscreensize;
        double stringh = yscreensize * Height;
        int Stringh = (int) Math.round(stringh);
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                EscapeIsActive = false;
                z.OptionsIsActive = true;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, Stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }
    }

    void Save(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Close";
        stringb = g.getFontMetrics().stringWidth(strg);
        double height = 650;
        double Height = height / yscreensize;
        double stringh = yscreensize * Height;
        int Stringh = (int) Math.round(stringh);
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                System.exit(0);
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, Stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }
    }

    void OptionsMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }

    void Graphics(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Options";
        stringb = g.getFontMetrics().stringWidth(strg);
        
        int stringh = 375;
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                EscapeIsActive = false;
                z.OptionsIsActive = false;
                z.GraphicsIsActive = true;
                z.AudioIsActive = false;
                z.SettingsIsActive = false;
                z.CreditsIsActive = false;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }
        String strgMouseX = "" + MouseX;
        String strgMouseY = "" + MouseY;
        String strgMouse = strgMouseX + " + " + strgMouseY;
        g.drawString(strgMouse, MouseX, MouseY);
        g.drawLine(xscreensize / 2, 0, xscreensize / 2, yscreensize);
    }

    void GraphicsMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }

    void Audio(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Options";
        stringb = g.getFontMetrics().stringWidth(strg);
        int stringh = 450;
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                EscapeIsActive = false;
                z.OptionsIsActive = false;
                z.AudioIsActive = true;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }

    }

    void AudioMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }

    void Settings(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Options";
        stringb = g.getFontMetrics().stringWidth(strg);
        int stringh = 550;
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                EscapeIsActive = false;
                z.OptionsIsActive = false;
                z.GraphicsIsActive = false;
                z.AudioIsActive = false;
                z.SettingsIsActive = true;
                z.CreditsIsActive = false;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }
    }

    void SettingsMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }

    void Credits(Zombie z, Graphics g, int scale, int xscreensize, int yscreensize) {
        int MouseX = MouseInfo.getPointerInfo().getLocation().x;
        int MouseY = MouseInfo.getPointerInfo().getLocation().y;
        Color Button;
        Button = Color.LIGHT_GRAY;
        int stringb;
        String strg = "Options";
        stringb = g.getFontMetrics().stringWidth(strg);
        int stringh = 650;
        Button = Color.LIGHT_GRAY;
        g.setFont(stringFont);
        if (MouseX > xscreensize / 2 - stringb / 2 && MouseY > stringh - fontheight - 10 && MouseX < xscreensize / 2 + stringb / 2 && MouseY < stringh + 10) {
            Button = Color.WHITE;
            if (z.mousepressed == true) {
                EscapeIsActive = false;
                z.OptionsIsActive = false;
                z.GraphicsIsActive = false;
                z.AudioIsActive = false;
                z.SettingsIsActive = false;
                z.CreditsIsActive = true;
            }
        }
        g.setColor(Button);
        g.drawString(strg, xscreensize / 2 - stringb / 2, stringh);
        if (Button == Color.WHITE) {
            if (z.mousepressed == false) {
                Button = Color.LIGHT_GRAY;
            }
        }
    }

    void CreditsMenu(Graphics g, int scale, int xscreensize, int yscreensize) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, xscreensize, yscreensize);
    }


}
