# Sơ đồ kiến trúc xử lý API eKYC (Spring Boot 3 Lớp)

Dưới đây là sơ đồ Mermaid mô tả luồng đi của dữ liệu từ Client (Mobile App) qua các tầng kiến trúc (Controller -> Service -> Repository) tới Database và trả kết quả về.

```mermaid
sequenceDiagram
    participant C as Client (Mobile App)
    participant Ctrl as AccountController
    participant Svc as AccountService
    participant Repo as AccountRepository
    participant DB as Database (H2/MySQL)

    C->>Ctrl: POST /api/v1/accounts/register (JSON Payload)
    
    note over Ctrl: 1. Validate Data (@Valid)<br/>Check format CCCD, Email, Phone
    
    alt Validation Failed
        Ctrl-->>C: 400 Bad Request (Lỗi định dạng)
    else Validation Passed
        Ctrl->>Svc: registerAccount(AccountRegisterRequest)
        
        note over Svc: 2. Xử lý Business Logic
        Svc->>Repo: existsByCitizenId() / existsByPhone()
        Repo->>DB: Query SELECT
        DB-->>Repo: Result (true/false)
        Repo-->>Svc: Boolean result
        
        alt Dữ liệu đã tồn tại
            Svc-->>Ctrl: throw IllegalArgumentException
            Ctrl-->>C: 400 Bad Request (CCCD/SĐT đã đăng ký)
        else Dữ liệu hợp lệ
            note over Svc: Sinh Random Account Number<br/>Map DTO sang Entity
            Svc->>Repo: save(Account)
            Repo->>DB: Query INSERT INTO accounts
            DB-->>Repo: Saved Entity (kèm ID)
            Repo-->>Svc: Entity Object
            
            note over Svc: Map Entity sang DTO Response
            Svc-->>Ctrl: AccountRegisterResponse
            Ctrl-->>C: 201 Created (Kèm thông tin Account)
        end
    end
```
