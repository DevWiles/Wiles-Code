package agent.wilescode.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MCPConfig {

    @JsonProperty("enabled")  //TODO:这个注解是什么？起到什么作用？
    private boolean enabled;

    @JsonProperty("autoDiscover")
    private boolean autoDiscover;

    @JsonProperty("connectionTimeout")
    private int connectionTimeout;

    @JsonProperty("servers")
    private List<MCPServerConfig> servers;
}

