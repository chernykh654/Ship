import java.awt.Graphics;
import java.util.ArrayList;

//Класс реализаци корабля
class Ship {
    private ArrayList<Cell> cells = new ArrayList<Cell>();

    Ship(int x, int y, int length, int position) {
        for (int i = 0; i < length; i++)
            cells.add(
                    new Cell(x + i * ((position == 1)? 0 : 1),
                            y + i * ((position == 1)?1:0)));
    }

 //Метод проверки вышел ли корабль за границы поля
    boolean isOutOfField(int bottom, int top) {
        for (Cell cell : cells)
            if (cell.getX() < bottom || cell.getX() > top ||
                    cell.getY() < bottom || cell.getY() > top)
                return true;
        return false;
    }
//Метод проверки наложение на другое судно или соприкосновение с ним
    boolean isOverlayOrTouch(Ship ctrlShip) {
        for (Cell cell : cells)
            if (ctrlShip.isOverlayOrTouchCell(cell))
                return true;
        return false;
    }

    boolean isOverlayOrTouchCell(Cell ctrlCell) {
        for (Cell cell : cells)
            for (int dx = -1; dx < 2; dx++)
                for (int dy = -1; dy < 2; dy++)
                    if (ctrlCell.getX() == cell.getX() + dx &&
                            ctrlCell.getY() == cell.getY() + dy)
                        return true;
        return false;
    }
    //Метод проверки на поподание в корабль
    boolean checkHit(int x, int y) {
        for (Cell cell : cells)
            if (cell.checkHit(x, y))
                return true;
        return false;
    }

    boolean isAlive() {
        for (Cell cell : cells)
            if (cell.isAlive())
                return true;
        return false;
    }

    void paint(Graphics g, int cellSize, boolean hide) {
        for (Cell cell : cells)
            cell.paint(g, cellSize, hide);
    }
}