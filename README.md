# Spring Security 原生登入 + Angular 前端

### Spring Security:
 - 登入
 - 驗證登入資訊(from DB)
 - 驗證SESSION是否合法
 - 驗證拜訪者權限
 - 設定最高上線數
 - 設定已達最高上線處裡
 
### application.properties:
- 設定SESSION存活時間

### Interceptor:
沒用到

### Angular:
 - 從Spring登入後才可拜訪Angular靜態資源，故沒有登入頁面