package database;

import model.Tool;

import java.util.HashMap;
import java.util.Map;

public class ToolDatabase {
    private Map<String, Tool> toolDatabase;

    public ToolDatabase() {
        toolDatabase = new HashMap<>();
    }

    public void saveTool(Tool tool) {
        toolDatabase.put(tool.getCode(), tool);
    }

    public Tool getTool(String code) {
        return toolDatabase.get(code);
    }

    public void listTools() {

        String format = "%-9s%-15s%s%n";

        System.out.println("\n------------------------------------------------------------");
        System.out.printf(format, "Code", "Tool Type", "Tool Brand");
        System.out.println("------------------------------------------------------------");
        for (Tool tool : toolDatabase.values()) {
            System.out.printf(format, tool.getCode(), tool.getType().getToolType(), tool.getBrand());
        }
        System.out.println("------------------------------------------------------------");
    }
}
