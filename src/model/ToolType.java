package model;

public class ToolType {
    private String toolType;

    public ToolType(String toolType) {
        this.toolType = toolType;
    }

    public String getToolType() {
        return toolType;
    }

    public void setToolType(String type) {
        this.toolType = toolType;
    }

    @Override
    public String toString() {
        return "model.ToolType{" +
                ", type='" + toolType + '\'' +
                '}';
    }
}
