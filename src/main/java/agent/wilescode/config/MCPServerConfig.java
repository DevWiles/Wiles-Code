package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * MCP服务器配置，这个项目中最细粒度的服务器配置
 */

@Data  // 这里用了lombok的Data注解，不需要手动写getter和setter
public class MCPServerConfig {

    @JsonProperty("name")
    private String name;

    @JsonProperty("command")
    private String command;

    @JsonProperty("enabled")
    private boolean enabled;

    @JsonProperty("args")
    private List<String> args;
}

