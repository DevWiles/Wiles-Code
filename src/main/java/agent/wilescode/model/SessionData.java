package agent.wilescode.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会话数据模型（Session Aggregate Root）
 *
 * 设计目标：
 * 1. 管理会话生命周期
 * 2. 维护消息列表（内部状态）
 * 3. 保证外部无法随意破坏内部数据结构
 */
@Getter
@ToString
public class SessionData {

    private final String sessionId;
    private final String title;
    private final String modelName;
    private final LocalDateTime createdTime;

    /**
     * lastAccessTime 为状态标记，可变
     * 所以不能用 final
     */
    private LocalDateTime lastAccessTime;

    /**
     * messages 是内部状态容器
     * 不允许外部直接替换，但可以内部修改
     */
    private final List<ChatMessage> messages;

    /**
     * 创建新会话（默认初始化）
     */
    public SessionData(String sessionId, String title, String modelName) {
        this.sessionId = sessionId;
        this.title = title;
        this.modelName = modelName;

        this.createdTime = LocalDateTime.now();
        this.lastAccessTime = LocalDateTime.now();

        /**
         * 初始化内部列表
         * 不用外部传入以避免外部持有引用导致“数据污染”
         */
        this.messages = new ArrayList<>();
    }

    /**
     * 从已有数据恢复 Session（用于持久化恢复）
     */
    public SessionData(String sessionId,
                       String title,
                       String modelName,
                       LocalDateTime createdTime,
                       LocalDateTime lastAccessTime,
                       List<ChatMessage> messages) {

        this.sessionId = sessionId;
        this.title = title;
        this.modelName = modelName;
        this.createdTime = createdTime;
        this.lastAccessTime = lastAccessTime;

        /**
         * 优化点：防御性拷贝
         *
         * 因为 List 是可变对象，如果直接赋值：
         * this.messages = messages;
         * 外部修改 list 会直接影响 Session 内部状态
         */
        this.messages = new ArrayList<>(messages);
    }

    /**
     * 添加消息唯一修改入口
     *不能暴露 setter，防止外部随意替换整个消息列表
     */
    public void addMessage(ChatMessage message) {
        if (message == null) {
            return;
        }

        messages.add(message);

        // 每次有消息更新，就刷新访问时间
        lastAccessTime = LocalDateTime.now();
    }

    /**
     * 更新访问时间
     * 用于 session 活跃度管理
     */
    public void updateLastAccessTime() {
        lastAccessTime = LocalDateTime.now();
    }

    /**
     * 返回消息数量（只读统计）
     */
    public int getMessageCount() {
        return messages.size();
    }

    /**
     * 判断 session 是否为空
     */
    public boolean isEmpty() {
        return messages.isEmpty();
    }

    /**
     * 防止外部修改内部 list
     * 不能直接 return messages，外部可以 messages.clear() / add() → 破坏封装
     */
    public List<ChatMessage> getMessages() {
        return new ArrayList<>(messages);
    }

    /**
     * 调试/日志输出
     */
    @Override
    public String toString() {
        return String.format(
                "Session{id=%s, title=%s, messages=%d, lastAccess=%s}",
                sessionId.substring(0, Math.min(8, sessionId.length())),
                title,
                messages.size(),
                lastAccessTime
        );
    }
}