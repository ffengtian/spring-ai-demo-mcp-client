# Spring AI Demo MCP Client

这是一个基于Spring Boot 3.4.9和Spring AI 1.0.0的演示项目，集成了DeepSeek大模型和MCP客户端功能。

## 功能特性

- 🚀 **Spring Boot 3.4.9** - 最新的Spring Boot框架
- 🤖 **Spring AI 1.0.0** - 官方Spring AI框架
- 🧠 **DeepSeek大模型** - 集成DeepSeek Chat模型
- 🔗 **MCP客户端** - 支持与MCP服务器通信
- 📡 **SSE流式响应** - 支持Server-Sent Events
- 🌐 **REST API** - 提供完整的REST接口

## 技术栈

- **JDK**: 17
- **Spring Boot**: 3.4.9
- **Spring AI**: 1.0.0
- **DeepSeek API**: OpenAI兼容接口
- **MCP**: Model Context Protocol客户端
- **WebFlux**: 响应式Web框架

## 快速开始

### 1. 环境要求

- JDK 17+
- Maven 3.6+
- DeepSeek API Key

### 2. 配置

在 `src/main/resources/application.properties` 中配置：

```properties
# DeepSeek AI Configuration
spring.ai.openai.api-key=your-deepseek-api-key
spring.ai.openai.base-url=https://api.deepseek.com/v1
spring.ai.openai.chat.options.model=deepseek-chat

# MCP Client Configuration
mcp.client.url=http://127.0.0.1:8080
mcp.client.timeout=30000
```

### 3. 运行应用

```bash
# 编译项目
mvn clean compile

# 运行应用
mvn spring-boot:run
```

应用将在 `http://localhost:8081` 启动。

## API接口

### 1. 普通聊天接口

**POST** `/api/ai/chat`

请求体：
```json
{
  "message": "你好，请介绍一下Spring AI"
}
```

响应：
```json
{
  "response": "Spring AI是Spring生态系统中的AI集成框架..."
}
```

### 2. 流式聊天接口（SSE）

**POST** `/api/ai/chat/stream`

请求体：
```json
{
  "message": "请详细解释Spring AI的工作原理"
}
```

响应（SSE流）：
```
data: Spring AI是一个...

data: 它提供了统一的API接口...

data: 支持多种AI模型...
```

### 3. 健康检查

**GET** `/api/ai/health`

响应：
```json
{
  "status": "UP",
  "service": "Spring AI Demo MCP Client",
  "timestamp": "1703123456789"
}
```

### 4. 服务信息

**GET** `/api/ai/info`

响应：
```json
{
  "service": "Spring AI Demo MCP Client",
  "version": "1.0.0",
  "aiModel": "DeepSeek Chat",
  "mcpStatus": {
    "available": false,
    "url": "http://127.0.0.1:8080",
    "lastHealthCheck": 0,
    "lastFailure": 0,
    "inBackoff": false
  },
  "endpoints": {
    "chat": "/api/ai/chat",
    "stream": "/api/ai/chat/stream",
    "health": "/api/ai/health",
    "mcpStatus": "/api/ai/mcp/status"
  }
}
```

### 5. MCP服务状态

**GET** `/api/ai/mcp/status`

响应：
```json
{
  "available": false,
  "url": "http://127.0.0.1:8080",
  "lastHealthCheck": 0,
  "lastFailure": 0,
  "inBackoff": false
}
```

### 6. 刷新MCP服务状态

**POST** `/api/ai/mcp/refresh`

响应：
```json
{
  "status": "success",
  "message": "MCP service status refreshed"
}
```

## MCP集成

项目支持与MCP（Model Context Protocol）服务器通信，具备智能服务发现和自动判定功能：

- **MCP服务器地址**: `http://127.0.0.1:8080`
- **通信方式**: SSE (Server-Sent Events)
- **智能服务发现**: 自动检测MCP服务可用性
- **健康检查**: 定期检查MCP服务状态
- **失败退避**: 服务失败后自动退避，避免频繁重试
- **自动降级**: 当MCP服务器不可用时，自动回退到纯DeepSeek模式

### 智能MCP判定

系统会智能分析用户输入，自动判定是否需要MCP增强：

#### 明确触发条件
- "mcp"、"工具"、"服务"、"调用"、"增强"

#### 智能触发条件
- **实时数据**: "实时"、"最新"、"当前"、"现在"、"今天"
- **外部服务**: "天气"、"股票"、"新闻"、"计算"、"查询"、"搜索"
- **系统操作**: "文件"、"系统"、"网络"、"api"
- **复杂问题**: 长度>100字符、"如何"、"怎么"、"步骤"、"流程"、"详细"、"完整"

#### 服务状态管理
- **缓存机制**: 30秒内重复检查使用缓存结果
- **失败退避**: 失败后1分钟内不再尝试连接
- **自动重连**: 定期检查服务恢复状态

## 项目结构

```
src/main/java/com/learn/peppa/spring/ai/demo/
├── SpringAiDemoApplication.java          # 主应用类
├── config/
│   ├── DeepSeekConfig.java              # DeepSeek配置
│   └── McpClientConfig.java             # MCP客户端配置
├── controller/
│   └── AiController.java                 # REST控制器
└── service/
    ├── AiService.java                    # AI服务
    └── McpClientService.java             # MCP客户端服务
```

## 测试

运行测试：

```bash
mvn test
```

## 开发说明

### 添加新的AI模型

1. 在 `DeepSeekConfig.java` 中添加新的配置
2. 在 `AiService.java` 中实现新的处理逻辑
3. 更新 `application.properties` 配置

### 扩展MCP功能

1. 在 `McpClientService.java` 中添加新的MCP方法
2. 在 `AiService.java` 中集成新的MCP调用
3. 更新触发条件和处理逻辑

## 故障排除

### 常见问题

1. **DeepSeek API连接失败**
   - 检查API Key是否正确
   - 确认网络连接正常
   - 验证API配额是否充足

2. **MCP服务器连接失败**
   - 确认MCP服务器运行在 `http://127.0.0.1:8080`
   - 检查防火墙设置
   - 查看应用日志获取详细错误信息

3. **端口冲突**
   - 默认端口8081被占用时，修改 `server.port` 配置

## 许可证

MIT License

## 贡献

欢迎提交Issue和Pull Request！
