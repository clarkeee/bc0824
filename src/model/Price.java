package model;

import java.math.BigDecimal;

public class Price {
    private ToolType toolType;
    private BigDecimal dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public Price(ToolType toolType, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }

    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    public void getDailyCharge(BigDecimal dailyCharge) {
        this.dailyCharge = dailyCharge;
    }

    public boolean getWeekdayCharge() {
        return weekdayCharge;
    }

    public void setWeekdayCharge(boolean weekdayCharge) {
        this.weekdayCharge = weekdayCharge;
    }

    public boolean getWeekendCharge() {
        return weekendCharge;
    }

    public void setWeekendCharge(boolean weekendCharge) {
        this.weekendCharge = weekendCharge;
    }

    public boolean getHolidayCharge() {
        return holidayCharge;
    }

    public void setHolidayCharge(boolean holidayCharge) {
        this.holidayCharge = holidayCharge;
    }

    @Override
    public String toString() {
        return "model.Price{" +
                ", toolType='" + toolType + '\'' +
                ", dailyCharge='" + dailyCharge + '\'' +
                ", weekdayCharge='" + weekdayCharge + '\'' +
                ", weekendCharge='" + weekendCharge + '\'' +
                ", holidayCharge='" + holidayCharge + '\'' +
                '}';
    }
}
