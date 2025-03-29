import java.awt.Graphics;
import java.awt.Color;
import java.awt.Point; 

class Link {
    private Shape startShape;
    private Point startPort;
    private Shape endShape;
    private Point endPort;
    private LinkType type;

    public Link(Shape startShape, Point startPort, Shape endShape, Point endPort, LinkType type) {
        this.startShape = startShape;
        this.startPort = startPort;
        this.endShape = endShape;
        this.endPort = endPort;
        this.type = type;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(startPort.x, startPort.y, endPort.x, endPort.y);

        // 繪製箭頭
        switch (type) {
            case ASSOCIATION:
                drawArrow(g, endPort.x, endPort.y, startPort.x, startPort.y);
                break;
            case GENERALIZATION:
                drawTriangleArrow(g, endPort.x, endPort.y, startPort.x, startPort.y);
                break;
            case COMPOSITION:
                drawDiamondArrow(g, endPort.x, endPort.y, startPort.x, startPort.y);
                break;
        }
    }

    public Shape getStartShape() {
        return startShape;
    }
    
    public Point getStartPort() {
        return startPort;
    }

    private void drawArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y1 - y2, x1 - x2);
        int x3 = (int) (x1 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowSize * Math.sin(angle + Math.PI / 6));
        int x4 = (int) (x1 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y4 = (int) (y1 - arrowSize * Math.sin(angle - Math.PI / 6));
        g.drawLine(x1, y1, x3, y3);
        g.drawLine(x1, y1, x4, y4);
    }

    private void drawTriangleArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y1 - y2, x1 - x2);
        int x3 = (int) (x1 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowSize * Math.sin(angle + Math.PI / 6));
        int x4 = (int) (x1 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y4 = (int) (y1 - arrowSize * Math.sin(angle - Math.PI / 6));
        int[] xPoints = {x1, x3, x4};
        int[] yPoints = {y1, y3, y4};
        g.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawDiamondArrow(Graphics g, int x1, int y1, int x2, int y2) {
        int arrowSize = 10;
        double angle = Math.atan2(y1 - y2, x1 - x2);
        int x3 = (int) (x1 - arrowSize * Math.cos(angle + Math.PI / 6));
        int y3 = (int) (y1 - arrowSize * Math.sin(angle + Math.PI / 6));
        int x4 = (int) (x1 - arrowSize * Math.cos(angle - Math.PI / 6));
        int y4 = (int) (y1 - arrowSize * Math.sin(angle - Math.PI / 6));
        int x5 = (int) (x3 - arrowSize * Math.cos(angle));
        int y5 = (int) (y3 - arrowSize * Math.sin(angle));
        int[] xPoints = {x1, x3, x5, x4};
        int[] yPoints = {y1, y3, y5, y4};
        g.fillPolygon(xPoints, yPoints, 4);
    }
}

enum LinkType {
    ASSOCIATION, // 箭頭
    GENERALIZATION, // 大箭頭
    COMPOSITION // 菱形箭頭
}
