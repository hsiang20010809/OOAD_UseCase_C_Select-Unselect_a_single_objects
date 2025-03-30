import javax.swing.*;
import java.awt.*;

public class WorkflowEditor {
    private Canvas canvas;
    private JButton rectButton;
    private JButton ovalButton;
    private JButton selectButton;
    private JComboBox<String> linkTypeComboBox; // 下拉選單

    public WorkflowEditor() {
        // 初始化畫布
        canvas = new Canvas();

        // 建立視窗
        JFrame frame = new JFrame("Workflow Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // 加入畫布
        frame.add(canvas, BorderLayout.CENTER);

        // 加入工具列
        JPanel toolbar = new JPanel();
        rectButton = new JButton("Rect");
        ovalButton = new JButton("Oval");
        selectButton = new JButton("Select");
        linkTypeComboBox = new JComboBox<>(new String[]{"Association", "Generalization", "Composition"}); // 新增選擇連結類型的下拉選單

        toolbar.add(rectButton);
        toolbar.add(ovalButton);
        toolbar.add(selectButton);
        toolbar.add(linkTypeComboBox); // 加入工具列
        frame.add(toolbar, BorderLayout.NORTH);

        // 設定按鈕事件
        rectButton.addActionListener(e -> {
            canvas.setMode(Canvas.Mode.RECT); // 切換到 RECT 模式
            updateButtonColors();
            System.out.println("Mode switched to: RECT");
        });

        ovalButton.addActionListener(e -> {
            canvas.setMode(Canvas.Mode.OVAL); // 切換到 OVAL 模式
            updateButtonColors();
            System.out.println("Mode switched to: OVAL");
        });

        selectButton.addActionListener(e -> {
            canvas.setMode(Canvas.Mode.SELECT); // 切換到 SELECT 模式
            updateButtonColors();
            System.out.println("Mode switched to: SELECT");
        });

        // 設定下拉選單事件
        linkTypeComboBox.addActionListener(e -> {
            String selectedType = (String) linkTypeComboBox.getSelectedItem();
            if (selectedType != null) {
                switch (selectedType) {
                    case "Association":
                        canvas.setCurrentLinkType(LinkType.ASSOCIATION);
                        break;
                    case "Generalization":
                        canvas.setCurrentLinkType(LinkType.GENERALIZATION);
                        break;
                    case "Composition":
                        canvas.setCurrentLinkType(LinkType.COMPOSITION);
                        break;
                }
                System.out.println("Link type switched to: " + selectedType);
            }
        });

        // 顯示視窗
        frame.setVisible(true);
    }

    // 更新按鈕顏色，顯示當前模式
    private void updateButtonColors() {
        rectButton.setBackground(canvas.getMode() == Canvas.Mode.RECT ? Color.BLACK : null);
        rectButton.setForeground(canvas.getMode() == Canvas.Mode.RECT ? Color.WHITE : Color.BLACK);

        ovalButton.setBackground(canvas.getMode() == Canvas.Mode.OVAL ? Color.BLACK : null);
        ovalButton.setForeground(canvas.getMode() == Canvas.Mode.OVAL ? Color.WHITE : Color.BLACK);

        selectButton.setBackground(canvas.getMode() == Canvas.Mode.SELECT ? Color.BLACK : null);
        selectButton.setForeground(canvas.getMode() == Canvas.Mode.SELECT ? Color.WHITE : Color.BLACK);
    }

    public static void main(String[] args) {
        new WorkflowEditor();
    }
}
