# Personal Finance Management System

Ứng dụng quản lý thu chi và thống kê tài chính cá nhân trên giao diện dòng lệnh (CLI), được phát triển bằng **Java 21** và áp dụng kiến trúc phân tầng (**Layered Architecture**) kết hợp **Repository Pattern**.

---

## Mục lục

- [Tính năng chính](#tính-năng-chính)
- [Kiến trúc hệ thống](#kiến-trúc-hệ-thống)
- [Công nghệ sử dụng](#công-nghệ-sử-dụng)
- [Cấu trúc thư mục](#cấu-trúc-thư-mục)
- [Yêu cầu hệ thống](#yêu-cầu-hệ-thống)
- [Hướng dẫn cài đặt & Khởi chạy](#hướng-dẫn-cài-đặt--khởi-chạy)
  - [Chế độ In-Memory (Mặc định)](#1-chế-độ-in-memory-không-cần-database)
  - [Chế độ PostgreSQL](#2-chế-độ-postgresql-lưu-trữ-bền-vững)

---

## Tính năng chính

- **Quản lý giao dịch (CRUD):** Hỗ trợ thêm mới, xem danh sách dạng bảng, tra cứu theo ID, cập nhật và xóa các khoản thu/chi.
- **Phân loại giao dịch:** Phân loại minh bạch giữa thu nhập (`INCOME`) và chi tiêu (`EXPENSE`) với hệ thống danh mục định sẵn (`Salary`, `Freelance`, `Food`, `Transport`, `Housing`,...).
- **Thống kê tài chính:** Tự động tổng hợp tổng thu nhập, tổng chi tiêu và số dư ví. Đảm bảo tính toán tài chính chính xác tuyệt đối bằng `BigDecimal`.
- **Lưu trữ linh hoạt:** Dễ dàng hoán đổi giữa lưu trữ tạm thời trên RAM hoặc lưu trữ lâu dài trên PostgreSQL mà không ảnh hưởng đến logic nghiệp vụ.

---

## Kiến trúc hệ thống

Hệ thống được thiết kế theo **Layered Architecture** nhằm đảm bảo tính độc lập, dễ bảo trì và mở rộng:

```
┌─────────────────────────────┐
│          UI Layer           │  ConsoleUI, Menu — Điều hướng và tương tác người dùng
├─────────────────────────────┤
│        Service Layer        │  TransactionService — Xử lý business logic, tính toán số dư
├─────────────────────────────┤
│       Repository Layer      │  TransactionRepository (Interface)
│                             │  ├── InMemoryTransactionRepository (Bộ nhớ RAM)
│                             │  └── PostgreSQLTransactionRepository (Cơ sở dữ liệu)
├─────────────────────────────┤
│         Model Layer         │  Transaction, Income, Expense, Category, TransactionType
└─────────────────────────────┘
```

### Nguyên lý thiết kế áp dụng
- **Repository Pattern:** Tách biệt hoàn toàn tầng truy xuất dữ liệu khỏi tầng logic nghiệp vụ.
- **Object-Oriented Programming (OOP):** Kế thừa và đa hình được thể hiện qua lớp trừu tượng `Transaction` cùng các lớp kế thừa `Income` và `Expense`.
- **Defensive Programming & Exception Handling:** Quản lý luồng xử lý lỗi tập trung thông qua `TransactionNotFoundException`.

---

## Công nghệ sử dụng

- **Ngôn ngữ:** Java 21 (Implicitly Declared Classes, Stream API, `BigDecimal`)
- **Cơ sở dữ liệu:** PostgreSQL 14+
- **Thư viện kết nối:** JDBC thuần với `PreparedStatement` (phòng chống SQL Injection)
- **Driver:** PostgreSQL JDBC Driver (`postgresql-42.7.13.jar`)

---

## Cấu trúc thư mục

```
src/
├── Main.java                               # Điểm khởi chạy ứng dụng (Entry point)
├── model/
│   ├── Transaction.java                    # Abstract class định nghĩa cấu trúc giao dịch
│   ├── Income.java                         # Model giao dịch thu nhập
│   ├── Expense.java                        # Model giao dịch chi tiêu
│   ├── Category.java                       # Enum danh mục giao dịch
│   └── TransactionType.java                # Enum phân loại loại giao dịch
├── repository/
│   ├── TransactionRepository.java          # Interface định nghĩa các thao tác dữ liệu
│   ├── InMemoryTransactionRepository.java  # Triển khai lưu trữ tạm thời trên RAM
│   └── PostgreSQLTransactionRepository.java# Triển khai lưu trữ trên PostgreSQL
├── service/
│   └── TransactionService.java             # Xử lý business logic và tính toán số dư
├── ui/
│   ├── ConsoleUI.java                      # Điều hướng và hiển thị CLI
│   └── Menu.java                           # Menu tương tác người dùng
├── util/
│   ├── DatabaseConnection.java             # Quản lý kết nối cơ sở dữ liệu
│   └── InputReader.java                    # Đọc và xác thực dữ liệu đầu vào
└── exception/
    └── TransactionNotFoundException.java   # Custom Exception xử lý ngoại lệ nghiệp vụ
```

---

## Yêu cầu hệ thống

- **JDK:** Java 21
- **PostgreSQL:** Phiên bản 14 trở lên (nếu sử dụng chế độ cơ sở dữ liệu)
- **IDE:** IntelliJ IDEA hoặc VS Code hỗ trợ JDK 25

---

## Hướng dẫn cài đặt & Khởi chạy

### 1. Chế độ In-Memory (Không cần Database)

Phù hợp để thử nghiệm nhanh mà không cần cài đặt PostgreSQL. Dữ liệu được lưu trong RAM và giải phóng khi ứng dụng dừng.

1. Clone repository:
   ```bash
   git clone https://github.com/mnhquan/finance-management-system.git
   ```
2. Mở dự án trong IDE và chọn Project SDK là **Java 25**.
3. Trong file `src/Main.java`, cấu hình repository:
   ```java
   TransactionRepository repository = new InMemoryTransactionRepository();
   ```
4. Thực thi `src/Main.java`.

---

### 2. Chế độ PostgreSQL (Lưu trữ bền vững)

1. **Khởi tạo cơ sở dữ liệu:**
   Đăng nhập vào PostgreSQL và chạy đoạn mã DDL sau để tạo cơ sở dữ liệu và bảng:

   ```sql
   CREATE DATABASE finance_db;

   \c finance_db;

   CREATE TABLE IF NOT EXISTS transactions (
       id BIGSERIAL PRIMARY KEY,
       type VARCHAR(10) NOT NULL,
       amount NUMERIC(15, 2) NOT NULL,
       description TEXT,
       date DATE NOT NULL,
       category VARCHAR(50) NOT NULL
   );
   ```

2. **Cấu hình kết nối:**
   Cập nhật thông tin tài khoản cơ sở dữ liệu trong [src/util/DatabaseConnection.java](src/util/DatabaseConnection.java):
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/finance_db";
   private static final String USER = "postgres";
   private static final String PASSWORD = "your_password";
   ```

3. **Kích hoạt Repository:**
   Trong file `src/Main.java`, chuyển sang sử dụng `PostgreSQLTransactionRepository`:
   ```java
   TransactionRepository repository = new PostgreSQLTransactionRepository();
   ```

4. Chạy `src/Main.java` để bắt đầu sử dụng.
