package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * MCP 配置类
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MCPConfig {

    @JsonProperty("enabled")
    private boolean enabled = false;

    @JsonProperty("autoDiscover")
    private boolean autoDiscover = true;

    @JsonProperty("connectionTimeout")
    private int connectionTimeout = 30;

    @JsonProperty("servers")
    // TODO: 这里 server 的可以被修改，要修改 Data 注解改变权限
    private List<MCPServerConfig> servers = new ArrayList<>();

    /**
     * 根据服务器名称查找服务器配置
     */
    public MCPServerConfig getServerConfig(String serverName) {
        return servers.stream()
                // TODO: 这里的 ServerName 可能是空字段，可能返回 null ，导致 NPE
                .filter(server -> serverName.equalsIgnoreCase(server.getName()))
                .findFirst()
                // TODO: 优化写法，这里让 optional 失去了意义
                .orElse(null);
    }

    /**
     * 获取所有启用的服务器配置
     */
    public List<MCPServerConfig> getEnabledServers() {
        return servers.stream()
                .filter(MCPServerConfig::isEnabled)
                .toList();
    }
}

