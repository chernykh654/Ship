import java.awt.Graphics;
import java.util.ArrayList;

class Shots {
    private final int CELL_SIZE; //размер ячейки в пикселях
    private ArrayList<Shot> shots; //Список выстрелов

    Shots(int cellSize) {
        CELL_SIZE = cellSize;
        shots = new ArrayList<Shot>();
    }

    void add(int x, int y, boolean shot) {// добавление выстрела в список
        shots.add(new Shot(x, y, shot));
    }

    boolean hitSamePlace(int x, int y) {// поиск выстрела в списке выстрелов
        for (Shot shot : shots)
            if (shot.getX() == x && shot.getY() == y && shot.isShot())
                return true;
        return false;
    }

    Shot getLabel(int x, int y) { // отображение метки
        for (Shot label : shots)
            if (label.getX() == x && label.getY() == y && (!label.isShot()))
                return label;
        return null;
    }

    void removeLabel(Shot label) {//удаление метки
        shots.remove(label);
    }

    void paint(Graphics g) {
        for (Shot shot : shots) shot.paint(g, CELL_SIZE);
    }
}