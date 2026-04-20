package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * MCP 服务器配置类
 */
@JsonIgnoreProperties
@Data
public class MCPServerConfig {

    @JsonProperty("name")
    private String name;

    @JsonProperty("command")
    private String command;

    @JsonProperty("enabled")
    private boolean enabled = false;

    @JsonProperty("args")
    private List<String> args = new ArrayList<>();

}

