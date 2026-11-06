本文件是给 Agent 使用的仓库工程规则，只记录项目技术约束、修改边界和验证要求，不维护项目当前状态、规则清单、版本清单或发布信息。

## 信息来源

- 项目事实以当前源码、`gradle.properties`、`fabric.mod.json`、语言文件和 `README*.md` 为准，`AGENTS.md` 只提供工作规则。
- 修改前先阅读本次任务相关的源码、配置和文档，不要只根据文件名猜测实现。
- `README.md` 是玩家与开发者可见的项目说明；若后续新增 `README_en.md` 等英文版文档，中英文内容需要保持同步。涉及功能、命令、版本或安装方式的变更必须同步更新文档。
- 如果 `AGENTS.md` 与源码或 `gradle.properties` 冲突，以源码和 `gradle.properties` 为准，并在回复中说明冲突。
- 仓库根目录同级的 `RuneLegend-Core`、`Carpet-Ice-Addition` 等工程不属于本仓库，默认只读，不得修改、提交或作为运行时依赖。

## 基本原则

- 优先做最小必要修改，不做与任务无关的重构、格式化、依赖升级或发布配置调整。
- 不能验证的内容要明确说明，不能伪装为已测试或已确认。
- 如果发现与任务无关的问题，不要马上修复，先列入“待人工确认项”。
- 对实现范围、第三方参考、兼容性或验证结果不确定时，写入回复的“待人工确认项”。

## 项目定位与硬性边界

本项目是**独立的** Carpet 扩展模组，通过 Carpet 规则系统调整 Minecraft 原版机制，目标是让原版行为尽可能接近 EaseCation 官方版圣符传说，并方便地图开发者配置规则。

- **不依赖 RuneLegend Core**：不得添加其 Gradle / Fabric 依赖，不得 `import` 或调用其任何类或 API，不得要求其安装或运行。仅允许依赖 Fabric Loader、Fabric API、Fabric Carpet。
- **单版本策略**：只支持 Minecraft 当前最新稳定正式版，禁止 Snapshot、预览版和 Carpet 开发版；禁止多版本兼容架构、旧版本兼容层、反射兼容代码、多版本源码目录或版本分支。如果最新正式版没有兼容的稳定 Carpet 版本，应停止选型并报告，不要自行降级 Minecraft。
- **使用 Carpet 原生机制**：规则注册、规则命令、参数校验、规则监听统一使用 Carpet 的 `CarpetExtension` / `SettingsManager` / `@Rule` / `Validator` / `registerRuleObserver` 等原生能力；不得自建 RuleRegistry、RuleService、命令系统或通用规则引擎。
- **默认不引入 Mixin**：只有任务明确要求实现某个游戏机制时才允许新增 Mixin/事件接入，并在同一任务中同步 `fabric.mod.json`、语言文件、README 和必要的第三方来源说明。纯规则注册任务不得修改原版战斗、伤害、格挡、生物 AI 等行为。

## 目录边界

- `src/main/java/com/ice2974/carpetrunelegendaddition/`：Java 主源码，只放 Fabric 入口、Carpet 扩展入口、规则定义和必要的规则实现逻辑；规则定义类不得夹带游戏行为。
- `src/main/resources/fabric.mod.json`：Fabric 元数据（Mod ID、入口点、依赖声明）；`version` 是唯一允许的 `${version}` 占位符，由 `processResources` 展开。
- `src/main/resources/assets/carpet-runelegend-addition/lang/`：玩家可见翻译的**唯一来源**，至少包含 `zh_cn.json` 与 `en_us.json`；不要额外维护重复的 Java 翻译 Map。
- `gradle.properties`：依赖版本与模组标识的单一来源；`build.gradle` / `settings.gradle`：构建逻辑与插件仓库。
- `README.md`（以及后续可能新增的英文版）：项目说明与验证记录；新增多语言文档时需保持内容同步。
- `.verify/`（本地、已 gitignore）：可选的端到端验证临时目录，不得作为仓库交付物，不得提交其中的服务器、日志、下载文件或世界存档。
- `build/`、`.gradle/`、`run/`、`out/`：构建与运行产物，禁止提交。
- 禁止新增 `versions/`、`common.gradle`、preprocess 版本图、多平台 Gradle 子项目等任何多版本结构。

## 固定项目标识

以下为项目长期固定命名，除非任务明确要求重命名，否则不要修改：

| 标识类型 | 固定值 |
| - | - |
| Display Name / 项目名称 | `Carpet RuneLegend Addition` |
| Mod ID | `carpet-runelegend-addition` |
| Java Package | `com.ice2974.carpetrunelegendaddition` |
| Maven Group | `com.ice2974` |
| 主入口类 | `com.ice2974.carpetrunelegendaddition.CarpetRuneLegendAdditionMod` |
| 规则定义类 | `com.ice2974.carpetrunelegendaddition.settings.CarpetRuneLegendSettings` |
| 规则分类内部名 | `RuneLegend`（本模组专属分类内部名，翻译键 `carpet.category.RuneLegend`，显示值固定为 `RL`）+ Carpet 内置 `feature`（`carpet.api.settings.RuleCategory.FEATURE`，显示值由 Carpet 自带语言文件提供：英文 `feature`、中文 `特性`） |
| 资源 namespace | `carpet-runelegend-addition`（assets 路径 `assets/carpet-runelegend-addition/`） |
| Mixin package（仅在引入 Mixin 后使用） | `com.ice2974.carpetrunelegendaddition.mixins` |
| GitHub 仓库 | `Ice2974/Carpet-RuneLegend-Addition` |

约束：

- `gradle.properties` 中的 `mod_id`、`mod_name`、`maven_group`、`archives_base_name` 是上述标识的单一来源；修改时必须同步 `fabric.mod.json`、Java 常量、语言文件与 README。
- `CarpetRuneLegendAdditionMod` 中的 `MOD_ID` / `MOD_NAME` 常量必须与 `gradle.properties` 保持一致。
- 规则分类按两个相互独立的分类登记：本模组专属分类内部名 `RuneLegend`（语言文件键 `carpet.category.RuneLegend`，显示值固定为 `RL`，不要改成内部名以为的显示串）与 Carpet 内置分类 `feature`（`carpet.api.settings.RuleCategory.FEATURE`，显示值由 Carpet 自带语言文件提供，不要自建或覆盖其翻译键）；不要把两者拼成 `RL FEATURE` 之类的单一分类字符串。分类内部名不得包含空格，因为 Carpet 的 `/carpet list <tag>` 使用单个 word 参数；也不得使用 `RL` 这类过短的缩写，因为 Carpet 会按 tag 对规则内部名做子串匹配，过短分类名会把其他规则一并匹配进来。
- 规则内部名（如 `bedrockBlocking`）是长期稳定标识，不得为了“更好听”而重命名；新增规则使用 lowerCamelCase。

## 构建配置与依赖

- 目标 Minecraft 版本为 26.x 的**免混淆（unobfuscated）发行形态**：不配置 mappings，Fabric Loader / Fabric API / Carpet 依赖使用 `implementation` 引入，产物为普通 `jar` / `sourcesJar`（无 remap 任务）。不得添加 `mappings`、`remapJar`、`modImplementation` 或旧的 Yarn/Mojmap 配置。
- 插件使用 `net.fabricmc.fabric-loom`，版本固定在 `gradle.properties`；不要改用 snapshot/alpha 版 Loom 或其它构建插件。
- 依赖版本（Minecraft、Loader、Fabric API、Carpet、Loom、Java release）全部固定在 `gradle.properties`，不得自行升级、降级或引入版本范围；升级依赖必须作为明确任务并同步 README 版本表。
- Java 版本固定为目标版本要求（当前为 Java 25，见 `build.gradle` 的 toolchain / `options.release`）。Java 版本由 Gradle Java Toolchain 自动选择，不要在提交文件中写死本机 JDK 绝对路径；若构建启动器 JVM 与 toolchain 不一致，可通过环境变量 `JAVA_HOME` 指向对应版本 JDK 后再运行 Wrapper。
- 默认使用仓库内 Gradle Wrapper（`.\gradlew` / `.\gradlew.bat`），不要用系统 Gradle 替换 Wrapper，也不要修改 `gradle/wrapper/gradle-wrapper.properties` 的版本与校验和。
- `fabric.mod.json` 的 `depends` 必须包含 `fabricloader`、`minecraft`、`java`、`fabric-api`、`carpet`；不得删除 Carpet 依赖，也不得添加 RuneLegend Core 依赖。

## Carpet 扩展与规则规范

- 扩展注册：`CarpetRuneLegendAdditionMod` 实现 `ModInitializer` + `CarpetExtension`；`onInitialize()` 中调用 `CarpetServer.manageExtension(...)`，`onGameStarted()` 中调用 `CarpetServer.settingsManager.parseSettingsClass(...)` 将规则注册进 Carpet 原生 SettingsManager。不要改成静态代码块注册、Mixin 注册或自建管理器。
- 规则定义使用 `carpet.api.settings.Rule`；规则字段为 `public static`，默认值即为初始值。Boolean 规则由 Carpet 自动提供 `true` / `false` 选项；需要枚举/字符串选项时使用 `options`，需要严格校验时使用 `strict`。
- 参数校验优先使用 Carpet 原生 `carpet.api.settings.Validator` / `Validators`；规则变更监听优先使用 `CarpetServer.settingsManager.registerRuleObserver(...)`。不要为单条规则另写框架。
- 规则分类使用本模组常量 `RUNELEGEND`（值 `RuneLegend`）与 `carpet.api.settings.RuleCategory.FEATURE` 组合；不要为了方便直接写魔法字符串，也不要把两个独立分类合并成一个字符串。
- 只有任务明确要求时才新增规则；新增规则不得实现未声明的行为。占位规则只允许“注册但不读取”，不得在其他代码中读取并执行行为。
- 不要在 `onGameStarted()` 之外的时机解析规则类，也不要重复解析同一规则类。

## 规则 / 命令 修改

新增或调整功能时，按类型同步检查：

| 类型 | 至少检查 | 必须同步文档 |
| - | - | - |
| 规则 | 规则定义类、翻译提供类、中英文语言文件、相关 Mixin / Helper / 配置类、入口类（根 src 注册宏边界）、canonical registry 的 membership / predicate / precedence、受影响平台 override | `docs/rules.md`、`docs/rules_en.md` |
| 命令 | 命令注册入口、命令实现类、权限判断与对应规则类（COMMAND 分类）、命令树刷新逻辑、翻译提供类、中英文语言文件、受影响平台 override 入口类（各平台 `CarpetIceAdditionMod`）、反馈/错误文本、配置持久化逻辑 | `docs/commands.md`、`docs/commands_en.md` |

约定：

- 中文规则名使用 `.name`；中英文规则介绍使用 `.desc`；英文规则名默认直接显示内部名，不额外新增英文 `.name`。
- 游戏内规则介绍（`.desc`）句末不带句号；`docs/` 文档中的规则介绍句末需要句号。
- 规则文档中的规则介绍需要与游戏内规则介绍保持一致（文档内的注解不需要同步）。
- 规则文档（`docs/rules.md` 与 `docs/rules_en.md`）中的规则条目顺序统一按照规则内部名（英文 camelCase 标识符，如 `beaconIgnoresObstruction`）的字母升序排列，不要按中文拼音或汉字笔画排序。
- 第三方工具、许可证、设计灵感与致谢集中在 `THIRD_PARTY_NOTICES.md`（及 `README*.md` 致谢）统一维护；`docs/` 规则与命令文档保持纯粹的功能说明，不混杂第三方来源声明。新增或调整带有第三方来源的功能时，直接维护 `THIRD_PARTY_NOTICES.md`。
- 命令文档顶部应包含 `命令列表` / `Command List`，新增或调整命令时同时更新列表和对应命令详情。

## 语言与玩家可见文本

- 规则注册在 Carpet 自带 SettingsManager 中，因此翻译键前缀为 `carpet.`：
  - 规则显示名：`carpet.rule.<ruleName>.name`（中文建议提供；英文可省略，Carpet 会回退显示内部名）。
  - 规则描述：`carpet.rule.<ruleName>.desc`（**必需**：非 legacy 规则缺少描述翻译键会导致服务端启动时报错）。
  - 分类显示名：本模组分类 `carpet.category.RuneLegend`；Carpet 内置 `feature` 分类的显示值（en `feature` / zh `特性`）由 Carpet 自带语言文件提供，不要重复定义或覆盖。
- 语言文件是翻译的单一来源：`canHasTranslations(lang)` 直接通过 `carpet.utils.Translations.getTranslationFromResourcePath("assets/" + MOD_ID + "/lang/" + lang + ".json")` 返回；不要额外维护重复的 Java 翻译 Map。
- 游戏内规则描述（`.desc`）句末不带句号；README / 文档中的描述句末需要句号。
- JSON 文件必须为 UTF-8、无 BOM、可被解析；中文可以使用字面 UTF-8 或 `\uXXXX` 转义，但解析结果必须与需求文本完全一致。
- 新增或修改玩家可见文本时，同步检查 `zh_cn.json`、`en_us.json` 与 `README*.md`；不要把长文本硬编码进 Java 类。
- 服务端规则输出由 Carpet 的翻译机制在服务端解析；不要依赖客户端一定安装本模组或一定能加载本模组的语言文件。

## 规则持久化约定

- 使用 Carpet 原生持久化语义，不要自行实现另一套配置系统：
  - 普通 `/carpet <rule> <value>` 只改内存、**不跨重启保留**；
  - `/carpet setDefault <rule> <value>` 写入 `<世界目录>/carpet.conf`，服务端启动时回读，可跨正常重启保留；
  - `/carpet removeDefault <rule>` 删除持久化覆盖。
- 如果任务要求“普通规则修改也自动跨重启保留”，必须先说明这是对 Carpet 原生语义的偏离，并按任务要求实现最小化持久化适配（例如规则观察者 + 独立配置文件），不得扩建成通用配置框架。
- 文档（README）中的持久化说明必须与实际行为一致；若行为变更，同步更新 README 及对应多语言文档。

## 修改类型与同步检查

| 修改类型 | 至少检查 | 必须同步 |
| - | - | - |
| 规则定义 / 规则行为 | 规则字段、`@Rule` 参数、语言键、是否有读取者、是否需要 Mixin/事件、是否影响原版行为 | `src/main/resources/assets/.../lang/*.json`、`README*.md` |
| 新增玩家可见文本 | 中英文语言文件、键名与 Carpet 解析规则、是否需要描述翻译 | `zh_cn.json`、`en_us.json`、`README*.md` |
| `fabric.mod.json` / 入口点 | ID、版本占位符、入口点类名、`depends`、`mixins`（如新增）、JSON 合法性 | `gradle.properties` 标识、README |
| 构建配置 / 依赖版本 | `gradle.properties` 单一来源、Loom 形态、Java 25、无 remap、无多版本结构 | `README*.md` 版本表、本文件相关约束 |
| Mixin / 事件接入（仅在任务要求时） | 注入目标与移植风险、是否只影响声明行为、`fabric.mod.json` 的 `mixins`、构建产物含配置、无多余 Mixin | `README*.md`、必要时的第三方来源说明 |

## 构建与验证

- 分级验证指引：
  - **仅修改 Markdown 文档**：至少自查文档链接、命令、版本与源码是否一致；如仓库已跟踪该文件，运行 `git diff --check`。
  - **修改 Java 源码**：先运行编译，再执行完整构建，确认无错误：
    ```powershell
    $env:JAVA_HOME = '<JDK 25 路径>'
    .\gradlew clean build --console=plain
    ```
  - **修改 `fabric.mod.json`、语言文件或 `gradle.properties`**：执行完整构建，并检查产物 jar 内的 `fabric.mod.json` 已展开（无未替换占位符）、语言文件存在且可解析、无多余 Mixin 配置。
  - **涉及 Carpet 扩展注册、规则链路或入口点**：在完整构建基础上，建议使用独立 Fabric 专用服务器做端到端验证（与目标版本一致的 Minecraft 专用服务器 + Fabric Loader + Fabric API + Carpet + 本模组 jar，`mods/` 内不安装 RuneLegend Core），确认模组加载、规则查询、规则修改与持久化行为；验证临时文件只能放在 `.verify/`。
- 无法运行验证命令时，必须在回复中明确说明未验证内容、原因和风险；不得把未运行的构建、启动或游戏内测试写成已通过。
- 不得为了让验证通过而临时修改依赖版本、加入兼容代码或放宽断言；发现问题应报告并说明影响。

## 日志与测试边界

- 服务端日志不要刷屏，高频路径不要每 tick 输出。
- 用户可见的失败路径（规则设置失败、配置读写失败、兼容性阻断等）可以记录 warn / error，正常操作不要大量输出 info。
- 不要把玩家隐私、服务器敏感路径或无关环境信息写入日志。
- Agent 负责自动化验证与日志排查；需要人工游戏内验收的内容，只概述待检查的功能范围与关注点，具体操作由用户完成。

## 默认不要修改

除非任务明确要求，默认不要修改：

- `README.md`（及后续可能新增的英文版）
- `.gitignore`
- `LICENSE`
- `AGENTS.md`
- `gradle.properties` 中固定的依赖版本与标识
- `gradle/wrapper/*`
- 第三方来源与致谢说明（如后续新增 `THIRD_PARTY_NOTICES.md`，按其维护约定同步）

## 回复要求

完成任务后，回复中说明：

- 修改了哪些文件
- 为什么这样改
- 运行了哪些验证及结果
- 哪些内容未验证，以及原因
- 待人工确认项（如果没有，就写“无”）
