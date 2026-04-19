package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 应用配置类，包含模型和工具的配置
 * 具体包括：ToolsConfig, ToolConfig, ModelConfig
 * 定义一个应用配置中心，用来接收 JSON 配置，并在程序中安全地使用这些配置
 *
 * 包含知识点：
 * 1. 配置文件解析：使用 Jackson 库解析 JSON 配置文件，并生成对应的对象
 * 2. Lombok 注解：自动生成 getter 和 setter 方法
 * 3. 嵌套类：类中有类的写法，将细分类封装到一个类中
 * 4. 防御性编程：避免 空指针异常 抛出
 */
@JsonIgnoreProperties(ignoreUnknown = true)  // 解析 JSON 文件的注解，多余字段、未知属性等跳过不崩溃报错
@Data  // Lombok 注解，自动生成 getter 和 setter 方法
public class AppConfig {

    @JsonProperty("models")  // 模型信息：模型名称、模型配置
    private Map<String, ModelConfig> models;

    @JsonProperty("defaultModel")  // 默认模型
    private String defaultModel;

    @JsonProperty("tools")  // 工具配置
    private ToolsConfig tools = new ToolsConfig(); // 这里直接用 new 防止 工具为 null 时，会抛出 NullPointerException

    @JsonProperty("ai")  // AI行为配置
    private AIConfig ai = new AIConfig();  // 这里 new 用法一样


    // 不是普通的 Getter 方法
    public String getDefaultModel() {
        // 高优先级
        // 如果配置了defaultModel，使用配置的值
        if (defaultModel != null && !defaultModel.trim().isEmpty()) {
            return defaultModel;
        }

        // 中等优先级
        // 如果没有配置defaultModel，使用第一个模型作为默认
        /**
         * 获取模型列表的第一个模型作为默认模型
         * 但是哈希集合结构是无序的，所以这里的第一个其实不稳定，可能会跳动
         */
        // TODO: 换一种数据结构，使得拥有哈希集合的基础上再有排序性质
        if (models != null && !models.isEmpty()) {
            return models.keySet().iterator().next();
        }

        // 最低优先级
        // 如果连模型都没有配置，返回null或抛出异常
        return null;
    }


    @Data
    public static class ModelConfig {
        @JsonProperty("name")
        private String name;

        @JsonProperty("baseURL")
        private String baseURL;

        @JsonProperty("apiKey")
        private String apiKey;

        @JsonProperty("streaming")
        private boolean streaming = true;

        @JsonProperty("maxTokens")
        private Integer maxTokens = 4096;

        @JsonProperty("temperature")
        private Double temperature = 0.7;

        @JsonProperty("topP")
        private Double topP = 0.9;

        @JsonProperty("timeout")
        private Integer timeout = 60;
    }

    @Data
    public static class ToolsConfig {
        @JsonProperty("fileManager")
        private ToolConfig fileManager = new ToolConfig(); // Ensure fileManager is initialized

        @JsonProperty("commandExec")
        private ToolConfig commandExec = new ToolConfig();

        @JsonProperty("codeExecutor")
        private ToolConfig codeExecutor = new ToolConfig();

        @JsonProperty("search")
        private ToolConfig search = new ToolConfig();

    }

    @Data
    public static class ToolConfig {
        @JsonProperty("enabled")
        private boolean enabled = true;

        @JsonProperty("maxFileSize")
        private Long maxFileSize= 10485760L; // 设置默认值

        @JsonProperty("allowedCommands")
        private String[] allowedCommands;

        @JsonProperty("timeoutSeconds")
        private Integer timeoutSeconds = 30;

        @JsonProperty("allowedLanguages")
        private String[] allowedLanguages = {"java", "python", "javascript"};

    }

    /**
     * AI行为配置
     */
    @Data
    public static class AIConfig {
        @JsonProperty("autoProcessToolResults")
        private boolean autoProcessToolResults = false; // 默认false：工具执行后直接显示结果，不再反馈给AI
    }
}