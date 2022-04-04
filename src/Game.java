import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {

    private Board board;
    private int boardSize = 20;
    private GridUI gridUI;

    private static final int MINE_AMOUNT = 40;
    private int mineFlag = MINE_AMOUNT;
    private boolean isAlive = true;

    private Color[] colors = {Color.blue,Color.green, Color.red, Color.blue, Color.red, Color.cyan, Color.black,Color.gray};




    public Game() {
        board = new Board(boardSize,MINE_AMOUNT);
        gridUI = new GridUI();
        add(gridUI);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        board.getCell(2,3).setFlagged(true);

        Game.this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 82 && e.getKeyChar() == 'r'){
                    board = new Board(boardSize,MINE_AMOUNT);
                    isAlive=true;
                    mineFlag=MINE_AMOUNT;
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public void start(){
        setVisible(true);
    }

    class GridUI extends JPanel {
        public static final int CELL_PIXEL_SIZE = 30;

        private Image imageCell;
        private Image imageFlag;
        private Image imageMine;


        public GridUI(){
            setPreferredSize(new Dimension(boardSize * CELL_PIXEL_SIZE,boardSize * CELL_PIXEL_SIZE));
            imageCell = new ImageIcon("imgs/Cell.png").getImage();
            imageFlag = new ImageIcon("imgs/Flag.png").getImage();
            imageMine = new ImageIcon("imgs/Mine.png").getImage();


            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    int col = e.getX() / CELL_PIXEL_SIZE;
                    int row = e.getY() / CELL_PIXEL_SIZE;

                    Cell cell = board.getCell(row,col);

                    if(!cell.isCovered()){
                        return;
                    }

                    // check right click
                    if (isAlive) {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            if (!cell.isFlagged() && mineFlag>0) {
                                cell.setFlagged(!cell.isFlagged());
                            } else if(cell.isFlagged()) {
                                cell.setFlagged(!cell.isFlagged());
                            }

                        } else if (SwingUtilities.isLeftMouseButton(e)) {
                            if (!cell.isFlagged()) {
                                System.out.println(cell.getAdjacentMines());
                                board.uncover(row, col);
                                if (board.mineUncovered()) {
                                    JOptionPane.showMessageDialog(Game.this, "KaBo0m press R to restart", "You lose", JOptionPane.WARNING_MESSAGE);
                                    isAlive = false;
                                    board.dead();
                                }
                                if (board.isWin(MINE_AMOUNT)){
                                    JOptionPane.showMessageDialog(Game.this, "You win the game", "Congrats", JOptionPane.WARNING_MESSAGE);
                                    isAlive = false;
                                    board.dead();
                                }
                            }
                        }

                    }


                    repaint();
                }
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);

            mineFlag = MINE_AMOUNT;
            for(int row = 0 ; row < boardSize ; row++){
                for(int col = 0 ; col < boardSize ; col++) {
                    paintCell(g, row, col);
                }
            }
            g.drawString("Mine: "+mineFlag,10,10);

        }

        private void paintCell(Graphics g,int row, int col){
            int x = col *CELL_PIXEL_SIZE;
            int y = row * CELL_PIXEL_SIZE;


            Cell cell = board.getCell(row,col);
            if (cell.isCovered()) {
                g.drawImage(imageCell, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                if (cell.isFlagged()) {
                    g.drawImage(imageFlag, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                    mineFlag-=1;
                }
            }else{
                g.setColor(Color.gray);
                g.fillRect(x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE);
                g.setColor(Color.lightGray);
                g.fillRect(x+1, y+1, CELL_PIXEL_SIZE-2, CELL_PIXEL_SIZE);
                if (cell.isMine()) {
                    g.drawImage(imageMine, x, y, CELL_PIXEL_SIZE, CELL_PIXEL_SIZE, null, null);
                }else if (cell.getAdjacentMines()>0){
                    g.setColor(colors[cell.getAdjacentMines()-1]);
                    g.drawString(cell.getAdjacentMines()+"",x+(int)(CELL_PIXEL_SIZE *0.35),y+(int)(CELL_PIXEL_SIZE *0.65));
                }
            }





        }


    }



    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

}
