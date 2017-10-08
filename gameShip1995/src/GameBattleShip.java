import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class GameBattleShip extends JFrame {

    final String TITLE_OF_PROGRAM = "Морской бой";
    final int FIELD_SIZE = 10;//Размер поля в ячейках
    final int PANEL_SIZE = 400;//Размер поля в пикселях
    final int CELL_SIZE = PANEL_SIZE / FIELD_SIZE;//Размер ячейки
    final String BTN_INIT = "Новая Игра!";
    final String BTN_EXIT = "Выход";
    final String YOU_WON = "Победа";
    final int MOUSE_BUTTON_LEFT = 1;
    final int MOUSE_BUTTON_RIGHT = 3;

    JTextArea board;
    Canvas leftPanel;
    Ships aiShips;
    Shots humanShots;
    Random random;
    boolean gameOver;
    int comCount =0 ;

    public static void main(String[] args) {
        new GameBattleShip();
    }

    GameBattleShip() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        leftPanel = new Canvas();
        leftPanel.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
        leftPanel.setBackground(Color.white);
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.blue));
        leftPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX()/CELL_SIZE; // преобразование в координаты
                int y = e.getY()/CELL_SIZE;
                if (e.getButton() == MOUSE_BUTTON_LEFT && !gameOver) {// Левая кнопка мыши
                    if (!humanShots.hitSamePlace(x, y)) {
                        comCount++;
                        humanShots.add(x, y, true);
                        if (aiShips.checkHit(x, y)) {
                            if (!aiShips.checkSurvivors()) {
                                board.append("\n" + YOU_WON + "\n" + " Колличество попыток : " + comCount);
                                gameOver = true;
                            }
                        }
                        leftPanel.repaint();
                        board.setCaretPosition(board.getText().length());

                    }
                }
                if (e.getButton() == MOUSE_BUTTON_RIGHT) { // Правая кнопка мыши

                    Shot label = humanShots.getLabel(x, y);
                    if (label != null)
                        humanShots.removeLabel(label);
                    else
                        humanShots.add(x, y, false);
                    leftPanel.repaint();
                }
            }
        });

        JButton init = new JButton(BTN_INIT); // Кнопка "Новая игра"
        init.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                init();
                leftPanel.repaint();
                comCount=0;
            }
        });
        JButton exit = new JButton(BTN_EXIT); // Кнопка "Выход"
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        board = new JTextArea(); // панель для вывода системного текста
        board.setEditable(false);
        JScrollPane scroll = new JScrollPane(board);

        JPanel buttonPanel = new JPanel(); // панель для копок
        buttonPanel.setLayout(new GridLayout());
        buttonPanel.add(init);
        buttonPanel.add(exit);

        JPanel rightPanel = new JPanel();         //Правая панель
        rightPanel.setLayout(new BorderLayout());

        rightPanel.add(scroll, BorderLayout.CENTER);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        add(leftPanel);
        add(rightPanel);
        pack();
        setLocationRelativeTo(null); // отцентровка окна
        setVisible(true);
        init();
    }

    void init() { // init all game object
        aiShips = new Ships(FIELD_SIZE, CELL_SIZE, false);
        humanShots = new Shots(CELL_SIZE);
        board.setText(BTN_INIT);
        gameOver = false;
        random = new Random();
    }

    class Canvas extends JPanel { // for painting
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            int cellSize = (int) getSize().getWidth() / FIELD_SIZE;
            g.setColor(Color.lightGray);
            for (int i = 1; i < FIELD_SIZE; i++) {
                g.drawLine(0, i*cellSize, FIELD_SIZE*cellSize, i*cellSize);
                g.drawLine(i*cellSize, 0, i*cellSize, FIELD_SIZE*cellSize);
            }
            humanShots.paint(g);
            aiShips.paint(g);

        }
    }
}