// Button.java
enum Mode {
    SELECT, RECT, OVAL
}

class Button {
    private Mode mode;

    public Button() {
        this.mode = Mode.SELECT; // 預設為 SELECT 模式
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        System.out.println("Mode switched to: " + mode);
    }

    public Mode getMode() {
        return this.mode;
    }
}
