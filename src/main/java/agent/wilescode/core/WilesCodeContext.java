package agent.wilescode.core;

import agent.wilescode.config.AppConfig;

/**
 * 上下文初始化过程，手写 SpringBoot Mini 来进行上下文管理
 *
 * 依赖注入：确保各个组件都能获取到它们需要的依赖
 *
 * 生命周期管理：控制初始化程序，避免循环依赖
 *
 * 资源配置：建立数据库连接、网络连接、文件句柄等。
 */
public class WilesCodeContext {
    private final AppConfig appConfig;

}
