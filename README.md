# 💰 Personal Finance Management System

Ứng dụng console Java giúp quản lý thu nhập và chi tiêu cá nhân, hỗ trợ đầy đủ các thao tác CRUD và thống kê tài chính, được xây dựng theo mô hình **Layered Architecture**.

---

## 🚀 Tính năng

| Chức năng | Mô tả |
|---|---|
| ➕ Thêm thu nhập | Nhập số tiền, mô tả, ngày, danh mục |
| ➕ Thêm chi tiêu | Nhập số tiền, mô tả, ngày, danh mục |
| 📋 Xem tất cả giao dịch | Hiển thị danh sách dạng bảng |
| 🔍 Tìm giao dịch theo ID | Tra cứu nhanh một giao dịch |
| ✏️ Sửa giao dịch | Cập nhật thông tin giao dịch đã có |
| 🗑️ Xóa giao dịch | Xóa với xác nhận trước khi thực hiện |
| 📊 Thống kê tài chính | Tổng thu nhập, tổng chi tiêu, số dư ví |

---

## 🗂️ Cấu trúc dự án

```
src/
├── Main.java                               # Entry point
├── model/
│   ├── Transaction.java                    # Abstract base class
│   ├── Income.java                         # Giao dịch thu nhập
│   ├── Expense.java                        # Giao dịch chi tiêu
│   ├── Category.java                       # Enum danh mục
│   └── TransactionType.java                # Enum loại giao dịch (INCOME/EXPENSE)
├── repository/
│   ├── TransactionRepository.java          # Interface định nghĩa các thao tác dữ liệu
│   ├── InMemoryTransactionRepository.java  # Lưu trữ trong RAM
│   └── PostgreSQLTransactionRepository.java# Lưu trữ trong PostgreSQL (mở rộng)
├── service/
│   └── TransactionService.java             # Business logic
├── ui/
│   ├── ConsoleUI.java                      # Giao diện người dùng dòng lệnh
│   └── Menu.java                           # Hiển thị menu
├── util/
│   ├── DatabaseConnection.java             # Kết nối PostgreSQL
│   └── InputReader.java                    # Đọc và validate input từ người dùng
└── exception/
    └── TransactionNotFoundException.java   # Custom exception
```

---

## 🏗️ Kiến trúc

Dự án áp dụng **Layered Architecture** (Kiến trúc phân tầng):

```
┌─────────────────────────────┐
│          UI Layer           │  ConsoleUI — nhận input, hiển thị output
├─────────────────────────────┤
│        Service Layer        │  TransactionService — xử lý business logic
├─────────────────────────────┤
│       Repository Layer      │  TransactionRepository — truy cập dữ liệu
├─────────────────────────────┤
│         Model Layer         │  Transaction, Income, Expense, Category
└─────────────────────────────┘
```

**Repository Pattern** được áp dụng: `TransactionRepository` là interface, cho phép dễ dàng đổi giữa `InMemoryTransactionRepository` và `PostgreSQLTransactionRepository` mà không ảnh hưởng đến các tầng trên.

---

## 📦 Danh mục giao dịch

**Thu nhập:**
`SALARY` · `FREELANCE` · `INVESTMENT` · `OTHER_INCOME`

**Chi tiêu:**
`FOOD` · `TRANSPORT` · `HOUSING` · `ENTERTAINMENT` · `HEALTHCARE` · `EDUCATION` · `SHOPPING` · `OTHER_EXPENSE`

---

## ⚙️ Yêu cầu hệ thống

- **Java** JDK 17 trở lên
- **PostgreSQL** 14+

---

## ▶️ Cách chạy

### Chế độ In-Memory *(mặc định, không cần database)*

1. Clone repository:
   ```bash
   git clone https://github.com/your-username/finance-management-system.git
   ```

2. Mở project bằng IntelliJ IDEA hoặc VS Code

3. Biên dịch và chạy file `Main.java`

> Dữ liệu lưu trong RAM — sẽ mất khi tắt chương trình.

---

### Chế độ PostgreSQL *(lưu dữ liệu lâu dài)*

Project hỗ trợ lưu trữ dữ liệu bằng PostgreSQL thông qua `PostgreSQLTransactionRepository`. Để kích hoạt, tạo database `finance_db`, cấu hình thông tin kết nối trong `DatabaseConnection.java`, và đổi repository trong `Main.java`.

---

## 🛠️ Công nghệ sử dụng

- **Java** — ngôn ngữ lập trình chính
- **OOP** — kế thừa, trừu tượng hóa, đa hình
- **Collections Framework** — `ArrayList`, `Optional`, `Stream API`
- **Repository Pattern** — tách biệt logic truy cập dữ liệu
- **Custom Exception** — xử lý lỗi nghiệp vụ rõ ràng
- **PostgreSQL + JDBC** — lưu trữ dữ liệu bền vững với `PreparedStatement`
