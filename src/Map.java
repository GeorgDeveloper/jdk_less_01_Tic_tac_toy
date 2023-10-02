import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;


public class Map extends JPanel {

    private static final Random RANDOM = new Random();
    private static final int DOT_PADDING = 5;

    private int gameOverType;
    private static final int STATE_DRAW = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_WIN_TWO_HUMAN = 3;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_TWO_HUMAN = "Победил игрок 2!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "НИЧЬЯ!";
    private final int HUMAN_DOT = 1;
    private final int AI_DOT = 2;

    private final int HUMAN_TWO_DOT = 3;
    private final int EMPTY_DOT = 0;
    private int fieldSizeY;
    private int fieldSizeX;

    private char[][] field;

    private int panelWidth;
    private int panelHeigth;
    private int cellHeigth;
    private int cellWidth;

    private boolean isGameOver;
    private boolean isInitialized;

    private int wLen;
    private int mode;

    public Map() {
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    update(e);
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    update2(e);
                }
            }
        });

        isInitialized = false;
    }



    private void initMap(int x, int y) {
        fieldSizeY = y;
        fieldSizeX = x;
        field = new char[fieldSizeY][fieldSizeX];
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }


    void startNewGame(int mode, int fSzX, int fSZY, int wLen) {
        fieldSizeX = fSzX;
        fieldSizeY = fSZY;
        this.wLen = wLen;
        this.mode = mode;
        System.out.printf("Mode: %d;\nSize: x=%d, y=%d;\nWin Length: %d", mode, fSzX, fSZY, wLen);
        initMap(fieldSizeX, fieldSizeY);
        isGameOver = false;
        isInitialized = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        render(g);
    }

    private void update(MouseEvent e) {
        if (isGameOver || !isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeigth;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_DOT;
        repaint();
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) return;
        if (mode == 1) {
            if (checkEndGame(HUMAN_TWO_DOT, STATE_WIN_TWO_HUMAN)) return;
        } else if (mode == 0) {
            aiTurn();
            repaint();
        }

        if (checkEndGame(AI_DOT, STATE_WIN_AI)) return;
    }

    private void update2(MouseEvent e) {
        if (isGameOver || !isInitialized) return;
        int cellX = e.getX() / cellWidth;
        int cellY = e.getY() / cellHeigth;
        if (!isValidCell(cellX, cellY) || !isEmptyCell(cellX, cellY)) return;
        field[cellY][cellX] = HUMAN_TWO_DOT;
        repaint();
        if (checkEndGame(HUMAN_TWO_DOT, STATE_WIN_TWO_HUMAN)) return;
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameOverType = gameOverType;
            isGameOver = true;
            repaint();
            return true;
        }

        if (isMapFull()) {
            this.gameOverType = STATE_DRAW;
            isGameOver = true;
            repaint();
            return true;
        }
        return false;
    }

    private void render(Graphics g) {
        if (!isInitialized) return;
        panelWidth = getWidth();
        panelHeigth = getHeight();

        cellWidth = panelWidth / fieldSizeX;
        cellHeigth = panelHeigth / fieldSizeY;

        g.setColor(Color.BLACK);
        for (int h = 0; h < fieldSizeX; h++) {
            int y = h * cellHeigth;
            g.drawLine(0, y, panelWidth, y);
        }

        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, panelHeigth);
        }

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPTY_DOT) {
                    continue;
                }
                if (field[y][x] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeigth + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeigth - DOT_PADDING * 2);
                } else if (field[y][x] == AI_DOT) {
                    g.setColor(new Color(0xff0000));
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeigth + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeigth - DOT_PADDING * 2);
                } else if (field[y][x] == HUMAN_TWO_DOT) {
                    g.setColor(Color.GREEN);
                    g.fillOval(x * cellWidth + DOT_PADDING,
                            y * cellHeigth + DOT_PADDING,
                            cellWidth - DOT_PADDING * 2,
                            cellHeigth - DOT_PADDING * 2);
                } else {
                    throw new RuntimeException("Unexpected value " + field[y][x] + " in cell: x=" + " y=" + y);
                }
            }
        }
        if (isGameOver) showMessageGameOver(g);

    }

    private void showMessageGameOver(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 200, getWidth(), 70);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Times new roman", Font.BOLD, 48));
        switch (gameOverType) {
            case STATE_DRAW -> g.drawString(MSG_DRAW, 180, getHeight() / 2);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, 20, getHeight() / 2);
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, 70, getHeight() / 2);
            case STATE_WIN_TWO_HUMAN -> g.drawString(MSG_WIN_TWO_HUMAN, 70, getHeight() / 2);
            default -> throw new RuntimeException("Unexpected gameOver state: " + gameOverType);
        }
    }

    private boolean checkWin(int c) {
        if (checkDiagonal(c) || checkLanes(c) || checkRandomDiagonal(c)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }


    /**
     * Проверяем диагонали
     */
    boolean checkDiagonal(int state) {
        int tempOne = 0;
        int tempTwo = 0;
        for (int i = 0; i < fieldSizeX; i++) {
            if (field[i][i] == state) {
                tempOne++;
                if (tempOne == wLen) return true;
            } else {
                tempOne = 0;
            }
            if (field[fieldSizeX - i - 1][i] == state) {
                tempTwo++;
                if (tempTwo == wLen) return true;
            } else {
                tempTwo = 0;
            }
        }
        return false;
    }

    boolean checkRandomDiagonal(int state) {
        int tempOne = 0;
        int tempTwo = 0;
        int rows = fieldSizeX;
        int cols = fieldSizeY;

        // Проверяем все побочные диагонали слева направо и справа налево
        for (int k = 0; k < rows + cols - 1; k++) {
            for (int i = 0; i < rows; i++) {
                int j = k - i;
                if (j > 0 && j < cols) {
                    if (field[i][j] == state) {
                        tempOne++;
                        if (tempOne == wLen) return true;
                    } else {
                        tempOne = 0;
                    }
                }
            }
        }

        for (int k = 0; k < rows + cols - 1; k++) {
            for (int i = 0; i < rows; i++) {
                int j = cols - 1 - (k - i);
                if (j > 0 && j < cols) {
                    if (field[i][j] == state) {
                        tempTwo++;
                        if (tempTwo == wLen) return true;
                    } else {
                        tempTwo = 0;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Проверяем горизонтальные и вертикальные линии
     */
    boolean checkLanes(int state) {
        int tempOne = 0;
        int tempTwo = 0;
        for (int col = 0; col < fieldSizeY; col++) {
            for (int row = 0; row < fieldSizeX; row++) {
                if (field[col][row] == state) {
                    tempOne++;
                    if (tempOne == wLen) return true;
                } else {
                    tempOne = 0;
                }
                if (field[row][col] == state) {
                    tempTwo++;
                    if (tempTwo == wLen) return true;
                } else {
                    tempTwo = 0;
                }
            }
        }
        return false;
    }

}
