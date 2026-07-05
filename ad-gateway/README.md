# ad-gateway

广告平台网关服务，基于 Spring Cloud Gateway，负责请求路由、转发和过滤。

## 前置依赖

### 启动 Nacos

网关和各微服务通过 Nacos 进行服务注册与发现，启动前需先运行 Nacos Server。

**单机模式启动（开发环境）：**

```bash
# Linux/Mac
sh startup.sh -m standalone

# Windows
cmd startup.cmd -m standalone
```
