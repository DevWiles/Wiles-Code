package agent.wilescode;

import agent.wilescode.core.WilesCodeContext;
import picocli.CommandLine;

/**
 * 创建整个应用的根上下文，作为所有组件的容器
 *
 * 调用 initialize() 方法加载配置、注册工具、连接 MCP 服务器
 *
 * 建立命令解析框架，为后续的命令路由做准备
 */

public class WilesCodeCLI {
    public static void main(String[] args) {
        // 默认异常处理
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("发生未预期错误：" + throwable.getMessage());
            System.exit(1);
        });

        // 创建并初始化应用上下文
        WilesCodeContext context = WilesCodeContext.initialize();

        //
    }
}
