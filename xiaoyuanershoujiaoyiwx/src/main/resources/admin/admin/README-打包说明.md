# 管理端（Vue）打包说明

## 用 GitHub 在线打包并下载 dist（推荐）

工作流文件在 **Git 仓库根目录 `biyesheji`** 下：  
**`biyesheji/.github/workflows/build-admin-dist.yml`**  
（`ADMIN_DIR` 已设为 `xiaoyuanershoujiaoyiwx/src/main/resources/admin/admin`。）

1. 把整个 **`biyesheji`** 推到 GitHub。  
2. 打开 GitHub 仓库 → **Actions** → 左侧选 **Build admin dist** → **Run workflow** → 运行。  
3. 等待绿色完成 → 打开该次运行 → 底部 **Artifacts** → 下载 **admin-dist.zip**。  
4. 解压后把里面的 **`dist` 整个文件夹** 放到本地：  
   `xiaoyuanershoujiaoyiwx\src\main\resources\admin\admin\dist`  
5. 本地执行 **Maven `package`** 启动后端即可。

说明：云端使用 `ADMIN_NO_MINIFY=1` + 约 6GB 堆，与本地「不压缩打包」类似。若仍失败，可在 GitHub 仓库 **Settings → Actions → Larger runners** 了解付费大内存机（一般不必）。

**国内类似能力**：Gitee Go、阿里云效、腾讯云 CODING 等均可配置「Node 构建 + 上传产物」，思路与 GitHub Actions 相同。

---

## 借同学电脑打包：请对方按此清单操作

1. 安装 **Node.js 14.x LTS**（与 `node-sass` 老项目更兼容；若本项目已改用 `sass` 包，14 或 16 一般均可）。
2. 拷贝整个工程（或至少包含 `xiaoyuanershoujiaoyiwx/src/main/resources/admin/admin` 及其中 `package.json`）。
3. 在 **`admin/admin`** 目录执行：
   ```bat
   npm install --no-optional --no-audit --progress=false
   npm run build
   ```
   若仍 OOM，再试：`npm run build:nominify`（Windows CMD）。
4. 打包成功后，将本机生成的 **`admin/admin/dist`** 整个文件夹拷回你的电脑，覆盖到项目里同路径。
5. 你在本机执行 **Maven `package`**，会把 `dist` 复制到 `target/classes/static/`（见 `pom.xml` 中 `copy-admin-dist`）。

**不改 context-path 时**，静态咨询页地址固定为：

- 全量咨询：`http://服务器:端口/xiaoyuanershoujiaoyiwx/admin-shangjia-chat.html`
- 商品管理场景：`http://服务器:端口/xiaoyuanershoujiaoyiwx/shangpin-guanli-chat.html`

---

## 若 `npm run build` 仍报 JavaScript heap out of memory

### 方案 A：关闭压缩再打包（推荐先试）

在 **CMD** 中（当前目录 `admin/admin`）：

```bat
npm run build:nominify
```

- 会设置 `ADMIN_NO_MINIFY=1`，**不做 Terser 压缩**，内存占用通常明显下降。
- 已把 Node 堆上限调到约 **12GB**（`--max-old-space-size=12288`）。若本机内存不足，可把 `package.json` 里该数字改小或先关其它程序。
- 生成的 `dist` 里 **JS 未压缩、体积较大**，功能与正式压缩版一致；若需更小体积，可在内存更大的电脑或 CI 上再执行普通 `npm run build`。

### 方案 B：换机器 / 云打包

在内存 ≥16GB 的电脑，或使用 GitHub Actions 等在线构建，执行 `npm run build`。

---

## 不打包 Vue，也能管理「用户咨询」

项目已提供**独立静态页**（不依赖 `admin/admin/dist`）：

1. 启动 Spring Boot（默认端口 **8080**，上下文 **`/xiaoyuanershoujiaoyiwx`**）。
2. 浏览器打开：

   **`http://localhost:8080/xiaoyuanershoujiaoyiwx/admin-shangjia-chat.html`**

3. 使用与原 Vue 后台相同的管理员账号先登录一次（同域名同端口时页面会读 `localStorage` 里的 `Token`）；若读不到，在页面上手动粘贴 Token。
4. 列表来自接口 `GET /shangjiaChat/page`，保存回复为 `POST /shangjiaChat/update`。

静态文件路径：`src/main/resources/static/admin-shangjia-chat.html`（Maven 打包后会进 jar，无需 `npm run build`）。

---

## PowerShell 用户

`build:nominify` 使用 `set VAR=1&&`，适用于 **CMD**。若在 **PowerShell** 中可先执行：

```powershell
$env:ADMIN_NO_MINIFY='1'
node --max-old-space-size=12288 node_modules/@vue/cli-service/bin/vue-cli-service.js build
```
