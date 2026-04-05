package agent.wilescode.config;

import java.io.IOException;

/**
 * 单例管理器，统一管理配置生命周期
 */

public class ConfigManager {

    private static volatile ConfigManager instance;
    private AppConfig appConfig;
    private final ConfigLoader configLoader;

    private ConfigManager() {
        this.configLoader = new ConfigLoader();
    }

    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }
    // 这里实现了一个懒加载，确保在第一次使用时才加载配置，避免不必要的开销
    public void initialize(String configPath) throws IOException {
        this.appConfig = configLoader.loadConfig(configPath);
    }

    public AppConfig getAppConfig() {
        if (appConfig == null) {
            throw new IllegalStateException("配置未初始化,请先调用 initialize()");
        }
        return appConfig;
    }

    public void reload(String configPath) throws IOException {
        this.appConfig = configLoader.loadConfig(configPath);
    }
}

