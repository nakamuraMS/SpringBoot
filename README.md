# Spring Boot ローカル学習環境セットアップ手順

対象フォルダ: `C:\Users\nakamura\KasouWorkV2`

構成: JDK 21 + Gradle(Wrapper) + MySQL(Docker) + VS Code

---

## ステップ0. このフォルダの配置

ダウンロードしたファイル一式（README.md, docker-compose.yml, .vscode フォルダなど）を、
`C:\Users\nakamura\KasouWorkV2` 直下に展開してください。

最終的に以下のような構成になります（プロジェクト本体は後で追加します）。

```
C:\Users\nakamura\KasouWorkV2
├─ README.md
├─ docker-compose.yml
├─ .env
├─ .vscode
│  ├─ extensions.json
│  └─ settings.json
└─ demo\            ← ステップ2でSpring Initializrから生成したプロジェクトをここに置く
```

---

## ステップ1. JDKのインストール（未インストールとのことなので）

Spring Bootの学習用には **Eclipse Temurin JDK 21 (LTS)** がおすすめです。

1. 以下からWindows用インストーラ(.msi)をダウンロード
   https://adoptium.net/ja/temurin/releases/?version=21
   - Operating System: Windows
   - Architecture: x64
   - Package Type: JDK
2. インストーラを実行。途中の「カスタムセットアップ」画面で以下を **有効化**
   - "Set JAVA_HOME variable"
   - "JavaSoft (Oracle) registry keys"
   - "Add to PATH"
3. インストール後、コマンドプロンプトまたはPowerShellで確認

```powershell
java -version
javac -version
```

`openjdk version "21..."` のように表示されればOKです。

---

## ステップ2. Spring Bootプロジェクトの雛形を作成

ブラウザで以下を開いてください。

https://start.spring.io

以下の設定で入力します。

| 項目 | 設定値 |
|---|---|
| Project | Gradle - Groovy |
| Language | Java |
| Spring Boot | 最新の安定版（3.x の中で「SNAPSHOT」や「M」(マイルストーン)が付いていないもの）|
| Project Metadata - Group | com.example |
| Project Metadata - Artifact | demo |
| Project Metadata - Name | demo |
| Packaging | Jar |
| Java | 21 |

右側の「ADD DEPENDENCIES」で以下を追加してください。

- **Spring Web**
- **Spring Data JPA**
- **MySQL Driver**
- **Spring Boot DevTools**（コード変更時の自動リロード用、あると便利）

入力できたら画面下の **GENERATE** ボタンを押すと `demo.zip` がダウンロードされます。

これを展開し、中身（`demo` フォルダごと）を `C:\Users\nakamura\KasouWorkV2\demo` として配置してください。

---

## ステップ3. MySQLをDockerで起動

### 前提: Docker Desktop for Windows のインストール

未インストールの場合は以下からインストールしてください。
https://www.docker.com/products/docker-desktop/

インストール後、Docker Desktopを起動しておきます。

### MySQLコンテナの起動

`C:\Users\nakamura\KasouWorkV2` フォルダで、PowerShellを開いて以下を実行します。

```powershell
docker compose up -d
```

これで MySQL 8.0 がバックグラウンドで起動します（このフォルダの `docker-compose.yml` と `.env` を使用）。

接続情報は以下の通りです（`.env` ファイルに定義済み）。

- ホスト: `localhost`
- ポート: `3306`
- データベース名: `kasoudb`
- ユーザー名: `kasouuser`
- パスワード: `kasoupass`

停止する場合は `docker compose down`、データも含めて完全に削除する場合は `docker compose down -v` です。

---

## ステップ4. Spring Boot側でMySQLに接続する設定

`demo/src/main/resources/application.properties` を、同じく `demo/src/main/resources/application.yml` に置き換えるか、
以下の内容を `application.properties` に追記してください（このフォルダに用意した `application-sample.yml` の内容をコピーして使ってください）。

中身は本パッケージ内の `application-sample.yml` を参照してください。

---

## ステップ5. VS Codeで開いて拡張機能をインストール

1. VS Codeで `C:\Users\nakamura\KasouWorkV2` フォルダを開く
2. 右下に「おすすめの拡張機能をインストールしますか？」という通知が出るので「すべてインストール」を選択
   - 出ない場合は左の拡張機能アイコンから `.vscode/extensions.json` に記載のものを手動インストール
3. インストール後、VS Code左側に象アイコン（Spring Boot Dashboard）が表示されます

---

## ステップ6. 動作確認

VS Codeのターミナルで `demo` フォルダに移動して起動します。

```powershell
cd demo
./gradlew bootRun

-- ソースコードの変更を監視し、ファイルを保存するたびに自動でコンパイルを実行
./gradlew classes --continuous

-- プロジェクトが依存しているライブラリと、その推移的（間接的な）依存関係をツリー構造で確認する
./gradlew dependencies
```

初回はGradleの依存ライブラリダウンロードで数分かかります。
`Started DemoApplication in X seconds` と表示されたら起動成功です。

ブラウザで以下にアクセスして、エラー画面（Whitelabel Error Page、404など）が出ればアプリ自体は正常に起動しています（まだ何も画面を作っていないため）。

http://localhost:8080

---

## よくあるトラブル

- `java -version` が認識されない → PowerShellを再起動、それでもダメならPC再起動（PATH反映のため）
- MySQL接続エラー (`Communications link failure`) → `docker compose ps` でコンテナが起動しているか確認
- ポート3306が使用中 → ローカルに別のMySQLが入っていないか確認。`docker-compose.yml` のポート番号を `3307:3306` などに変更して `application.yml` 側も合わせる

---

## 次に学ぶと良いこと

- Controller / Service / Repository の3層構成
- Spring Data JPAでのEntity作成とCRUD
- Thymeleafでの簡単な画面表示、またはREST APIとしてPostman等で動作確認

ここまでできたら、簡単なCRUD機能（例: メモ帳アプリ）を一緒に作ってみるのもおすすめです。
