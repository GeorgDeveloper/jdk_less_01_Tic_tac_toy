import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsWindow extends JFrame {
    int MIN_FIELD_SIZE = 3;
    int MAX_FIELD_SIZE = 10;

    int setFieldSize = MIN_FIELD_SIZE;

    int MIN_WIN_SIZE = 3;
    int MAX_WIN_SIZE = 6;

    int setWinSize = MIN_WIN_SIZE;
    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 480;
    JButton btnStart = new JButton("START");
    JSlider jSliderFieldSize = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE);
    JSlider jSliderWinLength = new JSlider(MIN_WIN_SIZE, setWinSize, MIN_WIN_SIZE);

    ButtonGroup buttonGroup = new ButtonGroup();
    JRadioButton jRadioButtonH = new JRadioButton("Человек против компьютера");
    JRadioButton jRadioButtonA = new JRadioButton("Человек против человека");
    JLabel jLbField;
    JLabel jLbWin;
    int player = 0;


    public SettingsWindow(GameWindow gameWindow) {

        setLayout(new GridLayout(10, 1));
        add(new JLabel("Выберите режим игры"));

        buttonGroup.add(jRadioButtonH);
        buttonGroup.add(jRadioButtonA);
        add(jRadioButtonH);
        add(jRadioButtonA);

        jRadioButtonA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = 1;
            }
        });

        jRadioButtonH.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player = 0;
            }
        });

        add(new JLabel("Выберите размер поля"));
        jLbField = new JLabel("Установите размер поля: " + setFieldSize);
        add(jLbField);
        jSliderFieldSize.setPaintTicks(true);
        jSliderFieldSize.setMajorTickSpacing(10);
        jSliderFieldSize.setMinorTickSpacing(1);
        add(jSliderFieldSize);
        jSliderFieldSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setFieldSize = jSliderFieldSize.getValue();
                jLbField.setText("Установите размер поля: " + setFieldSize);
                jSliderWinLength.setMaximum(getMAX_WIN_SIZE());
            }
        });


        add(new JLabel("Выберите длину для победы"));
        jLbWin = new JLabel("Установите длину: " + setWinSize);
        add(jLbWin);
        jSliderWinLength.setPaintTicks(true);
        jSliderWinLength.setMajorTickSpacing(10);
        jSliderWinLength.setMinorTickSpacing(1);
        add(jSliderWinLength);

        jSliderWinLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                setWinSize = jSliderWinLength.getValue();
                jLbWin.setText("Установите длину: " + setWinSize);
            }
        });

        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        add(btnStart);


        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(player, jSliderFieldSize.getValue(), jSliderFieldSize.getValue(), setWinSize);
                setVisible(false);
            }
        });
    }

    public int getMAX_WIN_SIZE() {
        return Math.min(setFieldSize, MAX_WIN_SIZE);

    }

}
