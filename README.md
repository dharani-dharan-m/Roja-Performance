# Roja Performance - Tire Shop Management System

A complete, fully functional web application built with Spring Boot for managing a tire shop business.

## Features

- **Product Management**: Add, update, delete, and browse tire inventory
- **Customer Management**: Manage customer information and relationships
- **Order Management**: Create, track, and manage customer orders
- **User Management**: Manage system users with role-based access control
- **REST API**: Complete RESTful API for all operations
- **Web Dashboard**: Interactive web interface for easy management
- **Database**: MySQL integration with automatic schema creation

## Technology Stack

- **Backend**: Java 17, Spring Boot 4.0.3
- **Database**: MySQL
- **ORM**: Hibernate (Spring Data JPA)
- **Security**: Spring Security with BCrypt password hashing
- **Build Tool**: Maven
- **Frontend**: HTML5, CSS3, JavaScript (Vanilla)

## Project Structure

```
Roja-Performance/
├── src/
│   ├── main/
│   │   ├── java/com/tyreshop/Roja/Performance/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── AppConfig.java
│   │   │   │   └── DataInitializer.java
│   │   │   ├── controller/          # REST Controllers
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── CustomerController.java
│   │   │   │   ├── OrderController.java
│   │   │   │   └── UserController.java
│   │   │   ├── model/               # Entity Models
│   │   │   │   ├── Product.java
│   │   │   │   ├── Customer.java
│   │   │   │   ├── Order.java
│   │   │   │   └── User.java
│   │   │   ├── repository/          # Data Access Layer
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── CustomerRepository.java
│   │   │   │   ├── OrderRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/             # Business Logic Layer
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── CustomerService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   └── UserService.java
│   │   │   └── RojaPerformanceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/
│   │       │   └── index.html       # Web Dashboard
│   │       └── templates/
│   └── test/
│       └── java/...                 # Test Classes
└── pom.xml                          # Maven Configuration
```

## Installation & Setup

### Prerequisites
- Java 17 or higher
- MySQL Server 8.0 or higher
- Maven 3.6 or higher
- Git (optional)

### Step 1: Database Setup

1. Start your MySQL server
2. Create a database (optional - it will auto-create):
   ```sql
   CREATE DATABASE roja_performance;
   ```
3. Update `application.properties` with your MySQL credentials if different from default

### Step 2: Update Configuration

Edit `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/roja_performance?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root          # Your MySQL username
spring.datasource.password=root          # Your MySQL password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

### Step 3: Build the Application

Navigate to the project directory and run:

```bash
# Windows
mvnw clean install

# Linux/Mac
./mvnw clean install
```

### Step 4: Run the Application

```bash
# Windows
mvnw spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

## Usage

### Web Dashboard
Access the interactive dashboard at:
```
http://localhost:8080/
```

The dashboard provides:
- Dashboard Overview
- Product Management
- Customer Management
- Order Management
- User Management

### REST API Endpoints

#### Health Check
```
GET http://localhost:8080/api/health
GET http://localhost:8080/api
```

#### Products
```
GET    /api/products                 # Get all products
GET    /api/products/{id}            # Get product by ID
GET    /api/products/available/list  # Get available products
GET    /api/products/brand/{brand}   # Get products by brand
GET    /api/products/type/{type}     # Get products by type
POST   /api/products                 # Create new product
PUT    /api/products/{id}            # Update product
DELETE /api/products/{id}            # Delete product
POST   /api/products/{id}/update-stock # Update product stock
```

#### Customers
```
GET    /api/customers                # Get all customers
GET    /api/customers/{id}           # Get customer by ID
GET    /api/customers/active/list    # Get active customers
GET    /api/customers/email/{email}  # Get customer by email
GET    /api/customers/city/{city}    # Get customers by city
POST   /api/customers                # Create new customer
PUT    /api/customers/{id}           # Update customer
DELETE /api/customers/{id}           # Delete customer
```

#### Orders
```
GET    /api/orders                        # Get all orders
GET    /api/orders/{id}                   # Get order by ID
GET    /api/orders/customer/{customerId}  # Get orders by customer
GET    /api/orders/status/{status}        # Get orders by status
GET    /api/orders/product/{productId}    # Get orders by product
POST   /api/orders                        # Create new order
PUT    /api/orders/{id}                   # Update order
PATCH  /api/orders/{id}/status            # Update order status
DELETE /api/orders/{id}                   # Delete order
```

#### Users
```
GET    /api/users                    # Get all users
GET    /api/users/{id}               # Get user by ID
GET    /api/users/username/{username}# Get user by username
GET    /api/users/email/{email}      # Get user by email
POST   /api/users                    # Create new user
PUT    /api/users/{id}               # Update user
DELETE /api/users/{id}               # Delete user
```

## Sample Data

The application automatically initializes with sample data:

### Default Users
- **Admin**: username=`admin`, password=`admin123`
- **Manager**: username=`manager`, password=`manager123`
- **User**: username=`user`, password=`user123`

### Sample Products
- Michelin Pilot Sport 4
- Bridgestone Turanza
- Continental EcoContact
- Goodyear Assurance
- Yokohama Advan

### Sample Customers
- Arjun Kumar (Bangalore)
- Priya Sharma (Delhi)
- Rajesh Patel (Mumbai)

## API Examples

### Create a Product
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "New Tire",
    "description": "Premium tire",
    "brand": "Michelin",
    "type": "Performance",
    "price": 5000.00,
    "quantity": 25,
    "size": "215/60R16",
    "available": true
  }'
```

### Create an Order
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customer": {"id": 1},
    "product": {"id": 1},
    "quantity": 4,
    "totalPrice": 60000.00,
    "shippingAddress": "123 Main Street",
    "status": "PENDING"
  }'
```

### Update Order Status
```bash
curl -X PATCH "http://localhost:8080/api/orders/1/status?status=SHIPPED"
```

## User Roles

- **ADMIN**: Full access to all operations
- **MANAGER**: Can manage products, customers, and orders
- **USER**: Limited access to view information

## Database Schema

### Products Table
- id (Primary Key)
- name
- description
- price
- quantity
- brand
- size
- type
- available
- createdAt
- updatedAt

### Customers Table
- id (Primary Key)
- firstName
- lastName
- email (Unique)
- phone
- address
- city
- state
- zipCode
- active
- createdAt
- updatedAt

### Orders Table
- id (Primary Key)
- customer_id (Foreign Key)
- product_id (Foreign Key)
- quantity
- totalPrice
- status (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- shippingAddress
- createdAt
- updatedAt
- deliveryDate

### Users Table
- id (Primary Key)
- username (Unique)
- password (Encrypted)
- email (Unique)
- fullName
- role (ADMIN, MANAGER, USER)
- enabled
- createdAt
- updatedAt

## Troubleshooting

### Database Connection Error
- Ensure MySQL is running
- Check database credentials in `application.properties`
- Verify the database URL is correct

### Port Already in Use
- Change the port in `application.properties`:
  ```properties
  server.port=8081
  ```

### Build Fails
- Clean and rebuild:
  ```bash
  mvnw clean install -U
  ```

## Development Tips

1. **Hot Reload**: Use Maven or IDE hot reload features
2. **Logging**: Check console for detailed application logs
3. **API Testing**: Use Postman or curl to test REST endpoints
4. **CORS**: Application allows CORS from all origins for development

## Future Enhancements

- JWT Authentication
- Advanced Search & Filtering
- Report Generation
- Payment Integration
- Email Notifications
- Inventory Alerts
- Analytics Dashboard

## Support

For issues or questions, check the application logs and ensure:
1. MySQL is running
2. All dependencies are properly installed
3. Application properties are correctly configured
4. Java 17+ is installed

## License

This project is provided as-is for educational and business use.

## Author

Roja Performance Management System

---

**Enjoy managing your tire shop with Roja Performance!** 🏎️
