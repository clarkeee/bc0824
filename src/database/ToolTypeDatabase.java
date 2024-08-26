package database;

import model.ToolType;

import java.util.HashMap;
import java.util.Map;

public class ToolTypeDatabase {
    private Map<String, ToolType> toolTypeDatabase;

    public ToolTypeDatabase() {
        toolTypeDatabase = new HashMap<>();
    }

    public void saveToolType(ToolType toolType) {
        toolTypeDatabase.put(toolType.getToolType(), toolType);
    }

    public ToolType getToolType(String toolType) {
        return toolTypeDatabase.get(toolType);
    }

    public void listTools() {
        for (ToolType toolType : toolTypeDatabase.values()) {
            System.out.println(toolType);
        }
    }
}
