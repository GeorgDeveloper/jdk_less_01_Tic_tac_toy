import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 555;
    private static final int WINDOW_WIDTH = 507;
    private static final int WINDOW_POSX = 800;
    private static final int WINDOW_POSY = 300;
    Map map;
    SettingsWindow settingsWindow;

    JButton btnStart = new JButton("New Game");
    JButton btnExit = new JButton("Exit");

    public GameWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("TicTacToe");
        setResizable(false);
        map = new Map();
        settingsWindow = new SettingsWindow(this);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });
        JPanel panBotton = new JPanel(new GridLayout(1, 2));
        add(panBotton, BorderLayout.SOUTH);
        add(map);
        panBotton.add(btnStart);
        panBotton.add(btnExit);
        setVisible(true);

    }

    public void startNewGame(int mode, int fSzX, int fSZY, int wLen) {

        map.startNewGame(mode, fSzX, fSZY, wLen);
    }
}
