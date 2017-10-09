import java.awt.Color;
import java.awt.Graphics;

/*Реализация объкта ячейка
@x,y  координаты ячейки
@color цвет   ячейки по умолчанию
RED  цветовая константа означающая смерть ячейки
*/
class Cell {
    private final Color RED = Color.red;
    private int x, y;
    private Color color;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        color = Color.gray;// Цвет ячейки по умолчанию
    }
//Геттеры для доступа к переменным X и Y
    int getX() { return x; }
    int getY() { return y; }

//Метод проверки на поподание в ячейку
    boolean checkHit(int x, int y) {
        if (this.x == x && this.y == y) {
            color = RED; // при поподании изменяем цвет на RED
            return true;
        }
        return false;
    }
//Состояние ячейки живая или нет, по цвету
    boolean isAlive() {
        return color != RED;
    }

//Метод прорисовки  ячейки
    void paint(Graphics g, int cellSize, boolean hide) {
        if (!hide || (hide && color == RED)) {
            g.setColor(color);
            g.fill3DRect(x*cellSize + 1, y*cellSize + 1, cellSize - 2, cellSize - 2, true);
        }
    }
}