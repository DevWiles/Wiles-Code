package agent.wilescode.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * 聊天消息模型
 */
@Getter
@ToString
public class ChatMessage {

    private final String id;
    private final String role;
    private final String content;
    private final String timestamp;
    private final String sessionId;

    @JsonIgnore
    private final boolean userMessage;

    @JsonIgnore
    private final boolean systemMessage;

    @JsonIgnore
    private final boolean assistantMessage;

    private ChatMessage(String role, String content, String sessionId) {
        this.id = UUID.randomUUID().toString();
        this.role = role;
        this.content = content;
        this.sessionId = sessionId;
        this.timestamp = Instant.now().toString();

        this.userMessage = "user".equals(role);
        this.systemMessage = "system".equals(role);
        this.assistantMessage = "assistant".equals(role);
    }

    // 给 ChatMessage 对象提供多种角色的构造方法和多种模式的构造方法
    public static ChatMessage user(String content) {
        return new ChatMessage("user", content, null);
    }

    public static ChatMessage assistant(String content) {
        return new ChatMessage("assistant", content, null);
    }

    public static ChatMessage system(String content) {
        return new ChatMessage("system", content, null);
    }

    // 静态工厂方法
    public static ChatMessage from(String content) {
        return assistant(content);
    }

    // 深拷贝构造
    public static ChatMessage copy(ChatMessage other) {
        return new ChatMessage(other.role, other.content, other.sessionId);
    }
}