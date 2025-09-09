package com.learn.peppa.spring.ai.demo.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// 移除@Tool注解，使用FunctionToolCallback方式
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 交通耗时工具类
 * 提供飞机、火车、汽车的耗时预估
 */
@Component
public class TrafficHourTool {

    private static final Logger logger = LoggerFactory.getLogger(TrafficHourTool.class);

    // 核心城市交通耗时预估值（小时）
    private final Map<String, Map<String, Map<String, Integer>>> trafficData = new ConcurrentHashMap<>();

    public TrafficHourTool() {
        initializeTrafficData();
    }

    /**
     * 初始化核心城市交通耗时数据
     */
    private void initializeTrafficData() {
        // 北京到其他城市
        Map<String, Map<String, Integer>> beijingData = new ConcurrentHashMap<>();
        
        // 北京 -> 上海
        Map<String, Integer> beijingToShanghai = new ConcurrentHashMap<>();
        beijingToShanghai.put("飞机", 2);  // 2小时
        beijingToShanghai.put("火车", 12); // 12小时
        beijingToShanghai.put("汽车", 14); // 14小时
        beijingData.put("上海", beijingToShanghai);
        
        // 北京 -> 广州
        Map<String, Integer> beijingToGuangzhou = new ConcurrentHashMap<>();
        beijingToGuangzhou.put("飞机", 3);  // 3小时
        beijingToGuangzhou.put("火车", 22); // 22小时
        beijingToGuangzhou.put("汽车", 24); // 24小时
        beijingData.put("广州", beijingToGuangzhou);
        
        // 北京 -> 深圳
        Map<String, Integer> beijingToShenzhen = new ConcurrentHashMap<>();
        beijingToShenzhen.put("飞机", 3);  // 3小时
        beijingToShenzhen.put("火车", 24); // 24小时
        beijingToShenzhen.put("汽车", 26); // 26小时
        beijingData.put("深圳", beijingToShenzhen);
        
        // 北京 -> 成都
        Map<String, Integer> beijingToChengdu = new ConcurrentHashMap<>();
        beijingToChengdu.put("飞机", 2);  // 2.5小时
        beijingToChengdu.put("火车", 28); // 28小时
        beijingToChengdu.put("汽车", 30); // 30小时
        beijingData.put("成都", beijingToChengdu);
        
        trafficData.put("北京", beijingData);

        // 上海到其他城市
        Map<String, Map<String, Integer>> shanghaiData = new ConcurrentHashMap<>();
        
        // 上海 -> 北京
        Map<String, Integer> shanghaiToBeijing = new ConcurrentHashMap<>();
        shanghaiToBeijing.put("飞机", 2);  // 2小时
        shanghaiToBeijing.put("火车", 12); // 12小时
        shanghaiToBeijing.put("汽车", 14); // 14小时
        shanghaiData.put("北京", shanghaiToBeijing);
        
        // 上海 -> 广州
        Map<String, Integer> shanghaiToGuangzhou = new ConcurrentHashMap<>();
        shanghaiToGuangzhou.put("飞机", 2);  // 2小时
        shanghaiToGuangzhou.put("火车", 18); // 18小时
        shanghaiToGuangzhou.put("汽车", 20); // 20小时
        shanghaiData.put("广州", shanghaiToGuangzhou);
        
        // 上海 -> 深圳
        Map<String, Integer> shanghaiToShenzhen = new ConcurrentHashMap<>();
        shanghaiToShenzhen.put("飞机", 2);  // 2小时
        shanghaiToShenzhen.put("火车", 20); // 20小时
        shanghaiToShenzhen.put("汽车", 22); // 22小时
        shanghaiData.put("深圳", shanghaiToShenzhen);
        
        trafficData.put("上海", shanghaiData);

        // 广州到其他城市
        Map<String, Map<String, Integer>> guangzhouData = new ConcurrentHashMap<>();
        
        // 广州 -> 北京
        Map<String, Integer> guangzhouToBeijing = new ConcurrentHashMap<>();
        guangzhouToBeijing.put("飞机", 3);  // 3小时
        guangzhouToBeijing.put("火车", 22); // 22小时
        guangzhouToBeijing.put("汽车", 24); // 24小时
        guangzhouData.put("北京", guangzhouToBeijing);
        
        // 广州 -> 上海
        Map<String, Integer> guangzhouToShanghai = new ConcurrentHashMap<>();
        guangzhouToShanghai.put("飞机", 2);  // 2小时
        guangzhouToShanghai.put("火车", 18); // 18小时
        guangzhouToShanghai.put("汽车", 20); // 20小时
        guangzhouData.put("上海", guangzhouToShanghai);
        
        // 广州 -> 深圳
        Map<String, Integer> guangzhouToShenzhen = new ConcurrentHashMap<>();
        guangzhouToShenzhen.put("飞机", 1);  // 1小时
        guangzhouToShenzhen.put("火车", 1);  // 1小时
        guangzhouToShenzhen.put("汽车", 2);  // 2小时
        guangzhouData.put("深圳", guangzhouToShenzhen);
        
        trafficData.put("广州", guangzhouData);

        logger.info("交通耗时数据初始化完成，支持城市：北京、上海、广州、深圳、成都");
    }

    /**
     * 获取交通耗时信息
     * @param departureCity 出发城市
     * @param destinationCity 目的地城市
     * @return 交通耗时信息
     */
    @Tool(name = "getTrafficDuration", description = "根据起始城市返回不同交通工具耗时", returnDirect = true)
    public String getTrafficDuration(String departureCity, String destinationCity) {
        logger.info("查询交通耗时：{} -> {}", departureCity, destinationCity);
        
        if (departureCity == null || destinationCity == null || 
            departureCity.trim().isEmpty() || destinationCity.trim().isEmpty()) {
            return "错误：出发城市和目的地城市不能为空";
        }
        
        String normalizedDeparture = normalizeCityName(departureCity);
        String normalizedDestination = normalizeCityName(destinationCity);
        
        if (normalizedDeparture.equals(normalizedDestination)) {
            return "出发城市和目的地城市相同，无需交通";
        }
        
        Map<String, Map<String, Integer>> cityData = trafficData.get(normalizedDeparture);
        if (cityData == null) {
            return String.format("抱歉，暂不支持从 %s 出发的交通查询", departureCity);
        }
        
        Map<String, Integer> routeData = cityData.get(normalizedDestination);
        if (routeData == null) {
            return String.format("抱歉，暂不支持从 %s 到 %s 的交通查询", departureCity, destinationCity);
        }
        
        StringBuilder result = new StringBuilder();
        result.append(String.format("从 %s 到 %s 的交通耗时预估：\n\n", departureCity, destinationCity));
        
        routeData.forEach((transport, hours) -> {
            result.append(String.format("✈️ %s：约 %d 小时\n", transport, hours));
        });
        
        result.append("\n💡 提示：以上时间为预估时间，实际耗时可能因天气、交通状况等因素有所变化 哈哈哈哈");
        
        return result.toString();
    }

    /**
     * 标准化城市名称
     */
    private String normalizeCityName(String cityName) {
        if (cityName == null) return "";
        
        String normalized = cityName.trim();
        // 处理常见的城市名称变体
        switch (normalized) {
            case "北京":
            case "北京市":
            case "beijing":
            case "Beijing":
                return "北京";
            case "上海":
            case "上海市":
            case "shanghai":
            case "Shanghai":
                return "上海";
            case "广州":
            case "广州市":
            case "guangzhou":
            case "Guangzhou":
                return "广州";
            case "深圳":
            case "深圳市":
            case "shenzhen":
            case "Shenzhen":
                return "深圳";
            case "成都":
            case "成都市":
            case "chengdu":
            case "Chengdu":
                return "成都";
            default:
                return normalized;
        }
    }

    /**
     * 获取支持的城市列表
     */
    @Tool(name = "getSupportedCities", description = "返回支持起始城市查询交通耗时的城市列表")
    public String getSupportedCities() {
        StringBuilder result = new StringBuilder();
        result.append("支持的城市列表：\n\n");
        
        trafficData.keySet().forEach(city -> {
            result.append(String.format("🏙️ %s\n", city));
        });
        
        result.append("\n💡 提示：目前支持查询这些城市之间的交通耗时");
        
        return result.toString();
    }

    // 工具方法已经通过@Component注册，Spring AI会自动发现这些方法
}
