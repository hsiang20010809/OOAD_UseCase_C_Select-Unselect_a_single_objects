import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

class Canvas extends JPanel {
    private List<Shape> shapes = new ArrayList<>();
    private List<Link> links = new ArrayList<>();
    private Shape startShape = null;
    private Point startPort = null;
    private Shape selectedShape = null; // 用於記錄目前選取的物件
    private LinkType currentLinkType = LinkType.ASSOCIATION; // 預設連結類型
    private Rectangle selectionRect = null; // 用於框選的矩形區域

    public void addShape(Shape shape) {
        shapes.add(shape);
        shapes.sort((s1, s2) -> Integer.compare(s2.getDepth(), s1.getDepth()));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        // 繪製所有圖形
        for (Shape shape : shapes) {
            shape.draw(g, shape.isSelected()); // 傳遞物件的選取狀態
        }
    
        // 繪製所有連接線
        for (Link link : links) {
            link.draw(g);
    
            // 如果起點物件被選取，繪製黑色小正方形
            if (link.getStartShape() != null && link.getStartShape().isSelected()) {
                Point startPort = link.getStartPort();
                g.setColor(Color.BLACK);
                g.fillRect(startPort.x - 5, startPort.y - 5, 10, 10); // 繪製小正方形
            }
        }
    
        // 如果框選矩形存在，繪製框選區域
        if (selectionRect != null) {
            g.setColor(Color.BLUE);
            ((Graphics2D) g).setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f}, 0.0f));
            g.drawRect(selectionRect.x, selectionRect.y, selectionRect.width, selectionRect.height);
        }
    }

    public Canvas() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                boolean shapeClicked = false;

                // 檢查是否點擊到某個物件
                for (Shape shape : shapes) {
                    if (shape.contains(e.getX(), e.getY())) {
                        shapeClicked = true;
                        selectedShape = shape; // 設定選取的物件
                        shape.setSelected(true); // 更新物件的選取狀態

                        // 如果是第一次點擊，設定為起始物件
                        if (startShape == null) {
                            startShape = shape;
                            startPort = shape.getClosestPort(e.getX(), e.getY());
                        }
                    } else {
                        shape.setSelected(false); // 未被點擊的物件取消選取
                    }
                }

                // 如果未點擊到任何物件，清除選取狀態並準備框選
                if (!shapeClicked) {
                    selectedShape = null;
                    startShape = null; // 清除起始物件
                    startPort = null;  // 清除起始連接點
                    selectionRect = new Rectangle(e.getX(), e.getY(), 0, 0); // 啟動框選模式
                }

                repaint(); // 觸發重新繪製
            }


           
            @Override
            public void mouseReleased(MouseEvent e) {
                // 如果框選矩形存在，處理框選邏輯
                if (selectionRect != null) {
                    for (Shape shape : shapes) {
                        if (selectionRect.intersects(shape.getBounds())) {
                            shape.setSelected(true);
                        } else {
                            shape.setSelected(false);
                        }
                    }
                    selectionRect = null; // 清除框選矩形
                } else if (startShape != null) {
                    // 檢查是否點擊到終點物件
                    for (Shape shape : shapes) {
                        if (shape.contains(e.getX(), e.getY()) && shape != startShape) {
                            Point endPort = shape.getClosestPort(e.getX(), e.getY());
                            Link link = new Link(startShape, startPort, shape, endPort, currentLinkType);
                            links.add(link); // 加入連接線
                            break;
                        }
                    }
                    // 清除起始物件和起始連接點
                    startShape = null;
                    startPort = null;
                }

                repaint(); // 觸發重新繪製
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // 更新框選矩形大小
                if (selectionRect != null) {
                    int x = Math.min(selectionRect.x, e.getX());
                    int y = Math.min(selectionRect.y, e.getY());
                    int width = Math.abs(selectionRect.x - e.getX());
                    int height = Math.abs(selectionRect.y - e.getY());
                    selectionRect.setBounds(x, y, width, height);
                }

                repaint();
            }
        });
    }

    public void selectShapeAt(int x, int y) {
        selectedShape = null; // 初始化為未選擇任何物件
        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.contains(x, y)) {
                selectedShape = shape; // 選取最上層的物件
                break;
            }
        }
        repaint(); // 更新畫布，顯示選取狀態
    }

    public void setCurrentLinkType(LinkType type) {
        this.currentLinkType = type;
    }
}
