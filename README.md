<h1># 🏠 GJUN社區財務管理系統<br>
🏢 GJUN Apartment Management System</h1>


<h2>📌 專案介紹</h2><br>
<h3>GJUN Apartment Management System 是一個使用 Java Swing + MySQL + MVC + DAO 開發的桌面應用程式，提供 住戶 (Resident) 與 管理員 (Admin) 的登入與操作介面。<br>
<h3>主要功能包含：<br>
使用者登入驗證 (Resident / Admin)<br>
單位 (Unit) 管理<br>
住戶 (Resident) 管理<br>
繳費與支出管理 (Payment / Expense)<br>
財務收支報告 (Report, 圓餅圖 / 表格)<br>
權限控制 (Resident 只能查看，Admin 可新增/編輯/刪除)<br>


<h2>🛠 技術架構</h2><br>
<h3>語言: Java 11+<br>
GUI: Java Swing + WindowBuilder<br>
資料庫: MySQL 8.x<br>
專案管理: Maven<br>
架構模式: MVC + DAO + Service Layer<br>
第三方工具:<br>
JFreeChart (財務圖表)<br>
MySQL Connector/J<br>


<h2>⚙️ 功能模組</h2>
<h3>🔑 使用者登入 (Login)<br>
Resident 使用住戶資料註冊，登入後僅能查看 Panel<br>
Admin 可進行新增、編輯、刪除操作<br>

👤 住戶 (Resident)<br>
查看自己所屬的 Unit<br>
查看 Payment / Expense<br>
查看財務報表<br>

🛠 管理員 (Admin)<br>
新增、編輯、刪除：<br>
Unit<br>
Resident<br>
Payment<br>
Expense<br>
Manager<br>

📊 財務報表 (Report)<br>
支援依月份查詢收入/支出<br>
使用 JFreeChart 顯示圓餅圖<br>
住戶僅能查看，管理員可管理資料<br>


<h2>📂 專案結構</h2><br>
<h3>GJUN Apartment Management System/<br>
│── src/main/java/<br>
│   ├── controller/        # Swing UI Controller<br>
│   ├── dao/               # DAO 介面<br>
│   ├── dao/impl/          # DAO 實作<br>
│   ├── model/             # 資料模型 (User, Resident, Unit...)<br>
│   ├── service/           # Service 介面<br>
│   ├── service/impl/      # Service 實作<br>
│   └── util/              # 工具類別 (DBUtil, Tools)<br>
│   <br>
│── src/main/resources/<br>
│   └── db.properties      # MySQL 設定<br>
│<br>
│── pom.xml                # Maven 設定<br>
│── README.md              # 專案說明文件<br>


<h2>👥 預設帳號</h2>
<h3>| Role     | Username | Password |   備  註   |<br>
| -------- | -------- | -------- | --------- |<br>
| Admin    | admin    | admin123 | 系統管理員 |<br>
| Resident | teacher  | 1234     |  範例住戶  |<br>


<h2>📸 系統介面</h2>

<h3>LoginFrame<br>
SignupFrame<br>
MainFrame<br>
- ManagerPanel<br>
- UnitPPanel<br>
- ResidentPanel<br>
- PaymentPanel<br>
- ExpensePanel<br>
- ReportPanel<br>


<h2>📝 待改進</h2>

<h3>密碼加密 (目前明文，建議使用 BCrypt)<br>
單位與住戶關聯更完整<br>
REST API 版本 (Spring Boot)<br>
Docker 容器化<br>
