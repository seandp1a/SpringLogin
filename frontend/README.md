# Spring Security  + Angular 前後端分離

### Spring Security:
 - 驗證登入資訊(from DB)
 - 未對session做額外設定
 - 登入功能API化
 - 登入成功與錯誤皆再 Filter 層處理
 - 回傳的 ContentType 都是 JSON
 
### application.properties:
- 設定SESSION存活時間

### Interceptor:
沒用到

### Angular:
 - 登入頁面再Angular
 - 登入api : [POST] localhost:8080/api/login
            {
              account:AAA,
              password:123
            }
 - server 底下的靜態資源不需要權限便可拜訪
 - 除了登入以外的 API 皆需要登入後獲得權限才可拜訪
