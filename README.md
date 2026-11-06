# Carpet RuneLegend Addition

面向 RuneLegend 游戏玩法的**独立** Carpet 扩展模组。它主要通过 Carpet 规则系统调整 Minecraft 原版机制，目标是让原版 Minecraft 的游戏行为尽可能接近 EaseCation 官方版圣符传说，并为地图开发者提供方便的规则配置能力。

> **本模组不依赖 RuneLegend Core。** 工程中没有 RuneLegend Core 的 Gradle 依赖或 Fabric 模组依赖，不导入、不调用 RuneLegend Core 的任何类或 API，也不要求安装 RuneLegend Core。本模组只依赖 Fabric Loader、Fabric API 与 Fabric Carpet 等必要基础组件，可以独立安装、启动和运行。

## 当前阶段

本轮只搭框架，不实现任何实际玩法：

- Fabric Mod 主入口（`ModInitializer`）。
- Carpet Extension 注册与生命周期接入。
- 使用 **Carpet 原生** `SettingsManager` / `@Rule` 注册规则，不重复实现规则系统、命令系统或规则引擎。
- 一条占位规则 `bedrockBlocking`，仅用于验证规则注册、显示、查询与修改链路。
- 中英文语言文件（中文规则名与描述、英文描述回退）。

**本轮明确不包含**：`bedrockBlocking` 的实际功能、任何其他规则、任何 Mixin、事件监听、战斗/伤害/格挡/击退/生物 AI 修改。

## 目标版本（单版本策略）

只支持 Minecraft 当前最新稳定正式版，不做多版本兼容架构、旧版本兼容层、反射兼容代码或多版本源码目录。

| 组件 | 版本 | 说明 |
| --- | --- | --- |
| Minecraft | **26.3** | Mojang 最新稳定正式版（非 Snapshot/预览版） |
| Fabric Loader | **0.20.1** | 最新稳定版 |
| Fabric API | **0.165.2+26.3** | 对应 26.3 的最新稳定版 |
| Fabric Carpet | **26.3** | 对应 26.3 的最新稳定版（非开发版） |
| Fabric Loom | **1.19.3** | 最新稳定版（不使用 alpha） |
| Gradle | **9.8.2** | 通过 Gradle Wrapper 固定 |
| Java | **25** | Minecraft 26.3 所需 |

依赖版本集中在 `gradle.properties` 中固定，构建可稳定复现。

> Minecraft 26.x 是免混淆（unobfuscated）发行形态：工程**不配置 mappings**，模组依赖以 `implementation` 引入，产物是普通 `jar` / `sourcesJar`，没有 remap 步骤。这与 1.21.x 时代的 `modImplementation` + `remapJar` 架构不同，属于当前版本的官方构建形态。

## 工程结构

```text
Carpet-RuneLegend-Addition/
├── build.gradle                  # Fabric Loom 构建脚本（MC 26.3 免混淆形态）
├── settings.gradle               # 插件仓库、Gradle Toolchain 解析、工程名
├── gradle.properties             # 依赖版本与模组标识（单一来源）
├── gradle/wrapper/              # Gradle 9.8.2 Wrapper
├── gradlew / gradlew.bat
├── LICENSE                       # Apache-2.0
├── README.md                     # 项目说明（中文）
└── src/main/
    ├── java/com/ice2974/carpetrunelegendaddition/
    │   ├── CarpetRuneLegendAdditionMod.java      # Fabric 主入口 + CarpetExtension
    │   └── settings/CarpetRuneLegendSettings.java # 规则定义（@Rule）
    └── resources/
        ├── fabric.mod.json                        # Fabric 元数据与入口点
        └── assets/carpet-runelegend-addition/lang/
            ├── zh_cn.json                         # 中文规则名/描述、分类显示名
            └── en_us.json                         # 英文描述回退
```

命名约定：

| 标识 | 值 |
| --- | --- |
| Mod ID | `carpet-runelegend-addition` |
| 项目名称 | `Carpet RuneLegend Addition` |
| Java 包名 | `com.ice2974.carpetrunelegendaddition` |
| Maven Group | `com.ice2974` |
| 规则分类内部名 | `RL_FEATURE`（显示名 `RL FEATURE`） |
| 语言文件命名空间 | `assets/carpet-runelegend-addition/` |

## 构建

要求：JDK 25（`JAVA_HOME` 指向 JDK 25；`settings.gradle` 中的 foojay-resolver 也可在缺失时按需解析 JDK）。

```powershell
$env:JAVA_HOME = 'C:\path\to\jdk-25'
.\gradlew clean build
```

产物：

- `build/libs/carpet-runelegend-addition-0.1.0.jar`
- `build/libs/carpet-runelegend-addition-0.1.0-sources.jar`

## 安装

1. 为 Minecraft **26.3** 安装 **Fabric Loader 0.20.1**（客户端或服务端）。
2. 将以下文件放入 `mods/`：
   - `fabric-api-0.165.2+26.3.jar`
   - `carpet-26.3.jar`
   - `carpet-runelegend-addition-0.1.0.jar`
3. **不需要**安装 RuneLegend Core。
4. 启动后，游戏日志的 "Loading ... mods:" 列表中应出现 `carpet-runelegend-addition 0.1.0`，且 Carpet 日志显示 `Carpet Mod version: 26.3+...`。

## 规则

| 内部名 | 显示名（中文） | 类型 | 默认值 | 分类 | 状态 |
| --- | --- | --- | --- | --- | --- |
| `bedrockBlocking` | 基岩版格挡 | Boolean | `false` | `RL FEATURE` | 占位规则，仅注册，无实际功能 |

- 规则通过 `CarpetServer.settingsManager.parseSettingsClass(...)` 注册到 **Carpet 原生 SettingsManager**，因此复用 Carpet 原生的 `/carpet` 命令、规则列表、参数校验与规则监听机制，没有自定义命令或规则引擎。
- 分类内部名为 `RL_FEATURE`（Carpet 分类在命令中作为单个 word 参数使用，不能包含空格）；界面显示值来自语言文件键 `carpet.category.RL_FEATURE`，即 `RL FEATURE`。
- 规则翻译键由 Carpet 按 `<settingsManager identifier>.rule.<ruleName>.<name|desc>` 解析，本工程注册进 Carpet 自带管理器，因此键前缀为 `carpet.`。

### 查询与修改（Carpet 原生命令）

```text
/carpet bedrockBlocking                  # 查看规则菜单：名称、描述、分类、当前值、可选值
/carpet list RL_FEATURE                  # 按分类列出规则
/carpet list                             # 列出所有已修改（非默认）规则
/carpet bedrockBlocking true             # 修改规则（仅当前会话生效）
/carpet setDefault bedrockBlocking true  # 修改规则并写入存档，持久化
/carpet removeDefault bedrockBlocking    # 移除持久化覆盖，恢复代码默认值
/carpet language zh_cn                   # 切换 Carpet 规则显示语言（zh_cn / en_us）
```

> 修改 `bedrockBlocking` 不会触发任何游戏行为：本轮没有任何代码读取该规则。

## 规则持久化机制（Carpet 原生行为）

Carpet 的规则值**不是**每次都写盘，机制如下：

- 规则值只是 Java 静态字段，规则变更/服务器启动时由代码默认值初始化。
- 持久化文件为**世界目录下的 `<世界目录>/carpet.conf`**（规则注册在 Carpet 自带 SettingsManager 时文件名即 `carpet.conf`）。
- 只有 `/carpet setDefault <rule> <value>` 会写入该文件（`/carpet removeDefault` 会删除对应条目）；普通的 `/carpet <rule> <value>` 只在内存中生效。
- 服务器加载世界时，Carpet 在 `SettingsManager.attachServer()` → `loadConfigurationFromConf()` 中读取 `carpet.conf` 并应用，因此 `setDefault` 设置的规则**跨正常服务器重启保留**。

结论：

- 如果"规则修改跨重启保留"指 **setDefault 语义**（地图作者显式固定规则默认值），Carpet 原生机制**已经满足**，无需额外实现。
- 如果要求**普通的 `/carpet <rule> <value>` 修改也自动跨重启保留**，Carpet 原生机制**不满足**（这是 Carpet 有意的设计：普通修改是会话级临时值），后续需要增加最小化的持久化适配（例如注册规则监听器，把变更写入独立配置文件，并在启动时回读）。本轮按"使用 Carpet 原生机制、不过度设计"的要求**未实现**该适配。

## 核心类与职责

| 类 / 文件 | 职责 |
| --- | --- |
| `CarpetRuneLegendAdditionMod` | Fabric `ModInitializer` 主入口，同时实现 `CarpetExtension`；`onInitialize()` 读取模组版本并调用 `CarpetServer.manageExtension(...)` 注册扩展；`onGameStarted()` 把规则类交给 Carpet 原生 `SettingsManager` 解析；`canHasTranslations()` 直接从本模组语言文件返回翻译表 |
| `CarpetRuneLegendSettings` | 规则定义类：分类常量 `RL_FEATURE` 与 `@Rule` 标注的静态字段；本轮只有 `bedrockBlocking` 占位规则 |
| `fabric.mod.json` | Fabric 元数据：Mod ID、版本占位符、主入口点、`depends`（fabricloader / minecraft / java / fabric-api / carpet） |
| `lang/zh_cn.json`、`lang/en_us.json` | Carpet 规则翻译：`carpet.rule.bedrockBlocking.name/.desc` 与 `carpet.category.RL_FEATURE`；语言文件是翻译的唯一来源 |

## 后续扩展建议

1. 新增规则：在 `CarpetRuneLegendSettings` 中增加 `@Rule` 字段（支持 Boolean / Integer / Double / String+`options` / Enum 等 Carpet 原生类型），并在中英文语言文件中补 `carpet.rule.<name>.desc`（Carpet 要求非 legacy 规则必须有描述翻译，否则启动报错）及可选 `.name`。
2. 参数校验：使用 Carpet 原生 `carpet.api.settings.Validator` / `Validators`，无需自建校验框架。
3. 规则监听：使用 `CarpetServer.settingsManager.registerRuleObserver(...)`。
4. 游戏行为：只有在实现具体玩法时才增加对应的 Mixin / 事件接入，并保持单一 Minecraft 版本（26.3）。
5. 若确有"普通修改跨重启保留"需求，再按上文结论增加最小持久化适配。

## 验证记录（2026-11-06）

### Gradle 构建

```powershell
$env:JAVA_HOME = 'D:\DevTools\Java\JDK25'
.\gradlew clean build --console=plain --warning-mode all
```

- 结果：`BUILD SUCCESSFUL`（Gradle 9.8.2 / Fabric Loom 1.19.3 / JDK 25.0.5），无错误、无弃用告警。
- 产物：
  - `carpet-runelegend-addition-0.1.0.jar`，4,982 B，SHA256 `17158736E6FB12DC481031AAE91757B19E0C5DE58ECA7E71643481D351D30CB2`
  - `carpet-runelegend-addition-0.1.0-sources.jar`，3,679 B，SHA256 `606DCC35C49BFD82FC651FE548126023C9A4844864A312128FA11E66BAA86FC1`
- 连续多次 `clean build` 产物字节一致（可复现）。
- Jar 内容审计：`fabric.mod.json`（`${version}` 已展开为 `0.1.0`）、2 个模组类、`zh_cn.json` / `en_us.json`；**无任何 Mixin 配置**；无 RuneLegend Core 类或字符串。
- 依赖审计：`compileClasspath` / `runtimeClasspath` 仅包含 Minecraft 26.3、fabric-loader 0.20.1、fabric-api 0.165.2+26.3、carpet 26.3（及 Fabric 子模块与原生库），**无 RuneLegend Core 依赖**。

### 独立专用服务器端到端验证

验证环境（本地 `.verify/`，已 gitignore，不随仓库分发）：Minecraft 26.3 专用服务器 + Fabric Loader 0.20.1 + Fabric API 0.165.2+26.3 + Carpet 26.3 + 本模组 jar，`mods/` 内**没有** RuneLegend Core，使用全新世界。

| 检查项 | 结果 |
| --- | --- |
| 模组加载 | 服务端日志 `Loading 44 mods:` 包含 `- carpet-runelegend-addition 0.1.0`、`- carpet 26.3+v260915`、`- fabric-api 0.165.2+26.3`；无 ERROR/Exception |
| Carpet 识别扩展 | `bedrockBlocking` 出现在 Carpet 规则列表、规则菜单与分类中 |
| 中文显示（`carpet language zh_cn`） | `基岩版格挡 (bedrockBlocking)`；描述 `引入基岩版在格挡近战攻击后将攻击者朝反方向击退约1方块远的机制`；`分类: [RL FEATURE]`；`当前值: false (default value)`；`Options: [ true false ]` |
| 分类查询 | `carpet list RL_FEATURE` → `- 基岩版格挡 (bedrockBlocking) [true] [false]` |
| 规则修改 | `carpet bedrockBlocking true` → 重新查询为 `true (modified value)` |
| 普通修改持久化 | 重启后回到 `false (default value)`；`world/carpet.conf` 不存在 → 普通修改仅当前会话有效 |
| `setDefault` 持久化 | 立即写入 `world/carpet.conf`（内容 `bedrockBlocking true`）；重启日志 `[CM] Loaded 1 settings from carpet.conf`，查询为 `true (modified value)` |
| `removeDefault` | 清空 conf 条目，重启后回到 `false (default value)` |
| 英文回退（`carpet language en_us`） | 显示名回退为 `bedrockBlocking`，英文描述与 `[RL FEATURE]` 正确 |
| RuneLegend Core 独立性 | 服务器从未安装 RuneLegend Core，日志中无其加载记录或错误 |
| 行为安全 | 无 Mixin、无事件监听、无代码读取该规则；静态检查确认 jar 中只有该规则字段定义，没有使用者 |

服务器测试的全部原始日志、命令用例与状态快照保留在本地 `.verify/logs/`（`EVIDENCE.md` 为索引）；验证结束后所有测试服务器均已通过控制台 `stop` 正常关闭。

## 许可证

本项目使用 Apache-2.0 许可证，见 [LICENSE](LICENSE)。依赖的 Fabric Loader、Fabric API、Fabric Carpet 分别遵循其各自许可证。
