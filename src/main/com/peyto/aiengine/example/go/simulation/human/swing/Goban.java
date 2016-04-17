package com.peyto.aiengine.example.go.simulation.human.swing;

import com.peyto.aiengine.example.go.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

public class Goban extends JPanel {

    public static final int STONE_WIDTH = 24;
    public static final int STONE_HEIGHT = 24;
    public static final int CELL_SIZE = 24;
    public static final int OFFSET_X = 18;
    public static final int OFFSET_Y = 18;

    private static final int NUMBER_OF_WHITE_STONES = 10;

    private Image gobanImage;
    private Image blackImage;
    private Image[] whiteImages = new Image[NUMBER_OF_WHITE_STONES];

    private Map<Stone, Integer> whiteStonesTypes = new HashMap<>();
    private Board board;
    private Stone lastHumanStoneToDisplay;
    private final Random random = new Random();

    private Image resizeStone(BufferedImage original) {
        BufferedImage resizedImage = new BufferedImage(STONE_WIDTH, STONE_HEIGHT, original.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(original, 0, 0, STONE_WIDTH, STONE_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    private Integer getWhiteStoneImageType(Stone stone) {
        Integer type = whiteStonesTypes.computeIfAbsent(stone,
                stone1 -> random.nextInt(NUMBER_OF_WHITE_STONES));
        return type;
    }


    public Goban() {
        try {
            String pathname = "/home/peyto/Downloads/go/Goban_19x19_vide.png";
            String whitePathnameTemplate = "/home/peyto/Downloads/go/w";
            String blackPathname = "/home/peyto/Downloads/go/b.png";
            //gobanImage=Image.createImage("/123.png");
            //gobanImage = new ImageIcon(pathname).getImage();
            gobanImage = ImageIO.read(new File(pathname));
            blackImage = resizeStone(ImageIO.read(new File(blackPathname)));
            for (int i=1; i<=NUMBER_OF_WHITE_STONES; i++) {
                whiteImages[i-1] = resizeStone(ImageIO.read(new File(whitePathnameTemplate+i+".png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentPosition(Board board) {
        this.board = board;
    }

    private void drawStoneAtPosition(Graphics g, Stone stone) {
        int x = stone.getX();
        int y = stone.getY();
        Image stoneImage = (stone.getColor()== com.peyto.aiengine.example.go.model.Color.BLACK)?
                blackImage : whiteImages[getWhiteStoneImageType(stone)];
        g.drawImage(stoneImage, OFFSET_X+CELL_SIZE*x, OFFSET_Y+CELL_SIZE*y, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(gobanImage, 20, 20, this);

        if (board!=null) {
            for (Stone stone : board.getAllStones()) {
                drawStoneAtPosition(g, stone);
            }
            if (lastHumanStoneToDisplay!=null) {
                drawStoneAtPosition(g, lastHumanStoneToDisplay);
            }
        }
    }

    /**
     * Adds a stone to board to repaint goban
     */
    public void addNewStoneLocally(Move newMove) {
        lastHumanStoneToDisplay = newMove.getNewStone();
    }

    public void forgetManualStone() {
        lastHumanStoneToDisplay = null;
    }

    public void forceDrawGoban() {
        this.revalidate();
        this.repaint();
    }

    public List<Stone> getAllStones() {
        return board.getAllStones();
    }
}
