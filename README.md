# Minecraft Server ATuser Plugins

Minecraft Server ATuser Plugins 是一个基于 Bukkit API 的 Minecraft 服务端插件，允许在游戏中使用 "@" 符号提醒特定玩家。这个插件为 Minecraft 服务器提供了方便的方式提醒其他玩家注意消息

## 安装说明

1. 下载最新版本的 Minecraft Server ATuser Plugins 插件 JAR 文件。
2. 将 JAR 文件放置在 Bukkit 服务器的插件文件夹中 (`plugins` 文件夹)。
3. 重新启动服务器以加载插件。

理论上，Minecraft Server ATuser Plugins 插件应该与任何使用 Bukkit 服务端核心的 Minecraft 游戏版本兼容。

## 使用说明

1. 在游戏内，输入指令 `/at` 来获取帮助菜单。
2. 若要提醒特定玩家，只需在聊天框内输入 `@<玩家名>`，或使用 `/at <玩家名>` 命令。
3. 玩家需要相应的权限节点才能使用 Minecraft Server ATuser Plugins 插件功能。

## 权限节点

- `at.user`：普通玩家权限，允许使用 Minecraft Server ATuser Plugins 插件提醒其他玩家。
- `at.admin`：管理员权限，允许管理 Minecraft Server ATuser Plugins 插件的设置和功能。
- `at.all`：允许使用 `@所有人` 功能提醒所有在线玩家的权限。
- `at.shield`：允许屏蔽其他玩家对自己的提醒消息。

## 源代码

源代码可在 [GitHub 仓库](https://github.com/Azizi030/atuser) 获取。

## 贡献

欢迎对 Minecraft Server ATuser Plugins 插件进行贡献！请查阅 CONTRIBUTING.md 文件以获取更多信息。

## 许可证

Minecraft Server ATuser Plugins 插件采用 [MIT 许可证](LICENSE)。
