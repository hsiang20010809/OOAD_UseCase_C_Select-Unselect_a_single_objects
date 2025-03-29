import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

abstract class Shape {
    protected int x, y;
    protected int depth; // 深度屬性

    public Shape(int x, int y, int depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public abstract void draw(Graphics g, boolean isSelected);

    public abstract boolean contains(int mouseX, int mouseY);

    public abstract List<Point> getConnectionPorts();

    // 新增抽象方法 getBounds()
    public abstract Rectangle getBounds();

    private boolean isSelected = false;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public Point getClosestPort(int mouseX, int mouseY) {
        List<Point> ports = getConnectionPorts();
        Point closestPort = null;
        double minDistance = Double.MAX_VALUE;
    
        for (Point port : ports) {
            double distance = port.distance(mouseX, mouseY);
            if (distance < minDistance) {
                minDistance = distance;
                closestPort = port;
            }
        }
    
        return closestPort;
    }
    
}
