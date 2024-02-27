package com.gwj.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SModelEnum {
    CAMERA("摄像机", "photo"),
    BATTERY("电量", "t0888"),
    WIND_SPEED("风速", "t0005"),
    SOIL_TEMPERATURE_30CM("土壤温度(30cm)", "t0006_2"),
    SOIL_TEMPERATURE_10CM("土壤温度(10cm)", "t0002_2"),
    AIR_TEMPERATURE("空气温度", "t0001_1"),
    SOIL_TEMPERATURE_50CM("土壤温度(50cm)", "t0007_2"),
    RAIN_GAUGE("雨量计", "t0003"),
    SOIL_MOISTURE_50CM("土壤湿度(50cm)", "t0007_1"),
    SOIL_MOISTURE_30CM("土壤湿度(30cm)", "t0006_1"),
    SOIL_MOISTURE_10CM("土壤湿度(10cm)", "t0002_1"),
    AIR_HUMIDITY("空气湿度", "t0001_2"),
    WIND_DIRECTION("风向", "t0004"),
    ELECTRICAL_CONDUCTIVITY_30CM("电导率(30cm)", "t0006_3"),
    ELECTRICAL_CONDUCTIVITY_10CM("电导率(10cm)", "t0002_3"),
    ELECTRICAL_CONDUCTIVITY_50CM("电导率(50cm)", "t0007_3");
    String stName;
    String sModel;
}
