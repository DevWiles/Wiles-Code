package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

/**
 * 各种子配置（ModelConfig、ToolsConfig等）
 * 整合所有配置项，包括引用 MCPConfig
 */

@Data
public class AppConfig {

    @JsonProperty("models")
    private Map<String, ModelConfig> models;

    @JsonProperty("defaultModel")
    private String defaultModel;

    @JsonProperty("tools")
    private ToolsConfig tools;

    @JsonProperty("session")
    private SessionConfig session;

    @JsonProperty("ui")
    private UIConfig ui;

    @JsonProperty("performance")
    private PerformanceConfig performance;

    @JsonProperty("mcp")
    private MCPConfig mcp;  //TODO:这个操作是什么？什么时候创建？

    @Data
    public static class ModelConfig {
        private String name;
        private String baseURL;
        private String apiKey;
        private boolean streaming;
        private int maxTokens;
        private double temperature;
    }

    @Data
    public static class ToolsConfig {
        private ToolConfig fileManager;
        private ToolConfig commandExec;
        private ToolConfig codeExecutor;
        private ToolConfig search;
    }

    @Data
    public static class ToolConfig {
        private boolean enabled;
        private long maxFileSize;
        private int timeoutSeconds;
    }

    @Data
    public static class SessionConfig {
        private boolean autoSave;
        private int maxSessions;
        private long sessionTimeout;
    }

    @Data
    public static class UIConfig {
        private String theme;
        private boolean showTimestamps;
        private boolean colorfulOutput;
        private boolean progressAnimation;
    }

    @Data
    public static class PerformanceConfig {
        private boolean enableMonitoring;
        private String logLevel;
        private int cacheSize;
    }
}
