import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

class Ships {
    private final int CELL_SIZE;
    private ArrayList<Ship> ships = new ArrayList<Ship>(); // Список кораблей
    private final int[] PATTERN = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1}; // Шаблон для создание кораблей
    private Random random;
    private boolean hide;
/* Конструктор парка кораблей
@fieldSize размер поля в ячейках
@cellSize размер ячейки в пикселях
@hide видимость кораблей
 */
    Ships(int fieldSize, int cellSize, boolean hide) {
        random = new Random();
        for (int i = 0; i < PATTERN.length; i++) {
            Ship ship;
            do {// цикл поиска подходящего места для коробля
                int x = random.nextInt(fieldSize);
                int y = random.nextInt(fieldSize);
                int position = random.nextInt(2);
                ship = new Ship(x, y, PATTERN[i], position);
            } while (ship.isOutOfField(0, fieldSize - 1) || isOverlayOrTouch(ship));
            ships.add(ship);
        }
        CELL_SIZE = cellSize;
        this.hide = hide;
    }
//Метод проверки наложение на другое судно или соприкосновение с ним
    boolean isOverlayOrTouch(Ship ctrlShip) {
        for (Ship ship : ships) if (ship.isOverlayOrTouch(ctrlShip)) return true;
        return false;
    }
// Метод проверки попадания в корабль
    boolean checkHit(int x, int y) {
        for (Ship ship : ships) if (ship.checkHit(x, y)) return true;
        return false;
    }
// Метод проверки наличие не потопленных кораблей
    boolean checkSurvivors() {
        for (Ship ship : ships) if (ship.isAlive()) return true;
        return false;
    }
//Количество живых кораблей
    int parkShip() {
        int countShipAlive = ships.size();
        for (Ship ship : ships) if (!ship.isAlive()) countShipAlive--;
        return countShipAlive;
    }

    void paint(Graphics g) {
        for (Ship ship : ships) ship.paint(g, CELL_SIZE, hide);
    }
}