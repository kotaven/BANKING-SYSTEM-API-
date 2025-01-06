Banking Transaction Management System
# Banking Application

A Spring Boot RESTful application for managing bank accounts and transactions, including features like account creation, balance updates, and transaction tracking.

---

## Features
- **Bank Account Management**:
  - Create, view, update, and delete bank accounts.
- **Transaction Management**:
  - Perform credit and debit operations.
  - Retrieve transaction history.

---

## Technologies Used
- **Backend**: Spring Boot, Java
- **Database**: Oracle SQL
- **API Documentation**: REST APIs
- **Build Tool**: Maven

---

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven
- Oracle Database 10g or later
- Postman (optional, for API testing)

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/kotaven/Banking-Transaction-Management-System.git
properties
Copy code
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
Build and run the application:
bash
Copy code
mvn clean install
mvn spring-boot:run
API Endpoints
Bank Account Endpoints
Create Account
POST /api/accounts
Request:

json
Copy code
{
    "accountHolderName": "John Doe",
    "balance": 10000,
    "accountType": "Savings"
}
Response:

json
Copy code
{
    "accountId": 12345,
    "accountHolderName": "John Doe",
    "balance": 10000,
    "accountType": "Savings"
}
Get All Accounts
GET /api/accounts

Get Account by ID
GET /api/accounts/{id}
Response:

Success: Account details.
Failure: "Bank account not found with ID: {id}".
Update Account
PUT /api/accounts/{id}
Request:

json
Copy code
{
    "accountHolderName": "Jane Doe",
    "balance": 12000,
    "accountType": "Current"
}
Delete Account
DELETE /api/accounts/{id}
Response:

Success: "Bank account deleted with ID: {id}".
Failure: "Bank account not found with ID: {id}".
Transaction Endpoints
Create Transaction
POST /api/transactions
Request:

json
Copy code
{
    "transactionType": "DEBIT",
    "amount": 500,
    "accountId": 12345
}
Response:

json
Copy code
{
    "transactionId": 67890,
    "transactionType": "DEBIT",
    "amount": 500,
    "accountId": 12345
}
Get All Transactions
GET /api/transactions

Get Transaction by ID
GET /api/transactions/{id}

Delete Transaction
DELETE /api/transactions/{id}
Response:

Success: "Transaction deleted with ID: {id}".
Failure: "Transaction not found with ID: {id}".

Contributing
Fork the repository.
Create a new branch: git checkout -b feature/your-feature-name.
Commit your changes: git commit -m 'Add some feature'.
Push to the branch: git push origin feature/your-feature-name.
Open a pull request.
License
This project is licensed under the MIT License.

Contact
For questions or support, please contact:

Email: kotavenkatesh2618@gmail.com
