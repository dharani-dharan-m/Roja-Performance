# Roja Performance - Phase 1: Tyre Shop Backend

A production-level Spring Boot backend for tyre shop management. This Phase 1 implementation focuses exclusively on tyre management with a clean layered architecture.

## Overview

This is an enterprise-grade REST API built with:
- **Spring Boot 4.0.3** - Latest Spring Boot framework
- **Java 17** - Modern Java with records and sealed classes
- **MySQL** - Relational database
- **Lombok** - Reducing boilerplate code
- **Maven** - Build and dependency management

## Architecture

### Layered Architecture Pattern

```
┌─────────────────────────────────────────┐
│     Controller Layer (REST API)         │
│   @RestController - Handles HTTP        │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│     Service Layer (Business Logic)      │
│   TyreService - Interface               │
│   TyreServiceImpl - Implementation       │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│   Repository Layer (Data Access)        │
│   TyreRepository - JPA Repository       │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│     Database Layer (MySQL)              │
│   tyres table - Entity storage          │
└─────────────────────────────────────────┘
```

### Package Structure

```
com.tyreshop.Roja.Performance/
├── controller/              # REST API endpoints
│   └── TyreController
├── service/                 # Business logic interfaces
│   ├── TyreService
│   └── impl/                # Implementation classes
│       └── TyreServiceImpl
├── repository/              # Data access layer
│   └── TyreRepository
├── entity/                  # JPA entities
│   └── Tyre
├── dto/                     # Data Transfer Objects
│   ├── TyreRequestDTO
│   ├── TyreResponseDTO
│   └── ApiResponse
├── exception/               # Exception handling
│   ├── ResourceNotFoundException
│   └── GlobalExceptionHandler
└── config/                  # Application configuration
    └── [Configuration classes]
```

## Data Model

### Tyre Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| id | Long | PK, Auto-increment | Unique identifier |
| brand | String | NOT NULL, Length 100 | Tyre brand name |
| size | String | NOT NULL, Length 50 | Tyre size (e.g., 195/65R15) |
| price | Double | NOT NULL, Positive | Tyre price |
| stockQuantity | Integer | NOT NULL, Min 0 | Available stock |
| description | String | Optional, Length 500 | Product description |
| createdAt | Long | NOT NULL, Immutable | Creation timestamp |
| updatedAt | Long | NOT NULL | Last update timestamp |

### Database Schema

```sql
CREATE TABLE tyres (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    brand VARCHAR(100) NOT NULL,
    size VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    stock_quantity INT NOT NULL,
    description VARCHAR(500),
    created_at BIGINT NOT NULL,
    updated_at BIGINT NOT NULL,
    UNIQUE KEY unique_brand_size (brand, size)
);
```

## REST API Endpoints

### Base URL
```
http://localhost:8080/api/v1/tyres
```

### 1. Create Tyre
```
POST /api/v1/tyres
Content-Type: application/json

{
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 5000.00,
    "stockQuantity": 50,
    "description": "High performance all-season tyre"
}

Response: 201 Created
{
    "success": true,
    "message": "Tyre created successfully",
    "data": {
        "id": 1,
        "brand": "Michelin",
        "size": "195/65R15",
        "price": 5000.00,
        "stockQuantity": 50,
        "description": "High performance all-season tyre",
        "createdAt": 1704067200000,
        "updatedAt": 1704067200000
    },
    "timestamp": 1704067200000
}
```

### 2. Get Tyre by ID
```
GET /api/v1/tyres/{id}

Response: 200 OK
{
    "success": true,
    "message": "Tyre retrieved successfully",
    "data": {
        "id": 1,
        "brand": "Michelin",
        "size": "195/65R15",
        "price": 5000.00,
        "stockQuantity": 50,
        "description": "High performance all-season tyre",
        "createdAt": 1704067200000,
        "updatedAt": 1704067200000
    },
    "timestamp": 1704067200000
}
```

### 3. Get All Tyres
```
GET /api/v1/tyres

Response: 200 OK
{
    "success": true,
    "message": "Tyres retrieved successfully, count: 5",
    "data": [
        { ...tyre1... },
        { ...tyre2... }
    ],
    "timestamp": 1704067200000
}
```

### 4. Get Available Tyres (Stock > 0)
```
GET /api/v1/tyres/available/list

Response: 200 OK
{
    "success": true,
    "message": "Available tyres retrieved successfully, count: 4",
    "data": [ ...tyres with stock > 0... ],
    "timestamp": 1704067200000
}
```

### 5. Get Tyres by Brand
```
GET /api/v1/tyres/brand/Michelin

Response: 200 OK
{
    "success": true,
    "message": "Tyres with brand 'Michelin' retrieved successfully, count: 2",
    "data": [ ...michelin tyres... ],
    "timestamp": 1704067200000
}
```

### 6. Get Tyres by Size
```
GET /api/v1/tyres/size/195/65R15

Response: 200 OK
{
    "success": true,
    "message": "Tyres with size '195/65R15' retrieved successfully, count: 3",
    "data": [ ...tyres with size... ],
    "timestamp": 1704067200000
}
```

### 7. Get Tyres by Price Range
```
GET /api/v1/tyres/price?minPrice=3000&maxPrice=7000

Response: 200 OK
{
    "success": true,
    "message": "Tyres with price range 3000.0 - 7000.0 retrieved successfully, count: 2",
    "data": [ ...tyres in price range... ],
    "timestamp": 1704067200000
}
```

### 8. Update Tyre
```
PUT /api/v1/tyres/{id}
Content-Type: application/json

{
    "brand": "Michelin",
    "size": "195/65R15",
    "price": 5500.00,
    "stockQuantity": 45,
    "description": "Updated description"
}

Response: 200 OK
{
    "success": true,
    "message": "Tyre updated successfully",
    "data": { ...updated tyre... },
    "timestamp": 1704067200000
}
```

### 9. Delete Tyre
```
DELETE /api/v1/tyres/{id}

Response: 204 No Content
{
    "success": true,
    "message": "Tyre deleted successfully",
    "timestamp": 1704067200000
}
```

### 10. Update Stock Quantity
```
PUT /api/v1/tyres/{id}/stock?quantity=10

Response: 200 OK
{
    "success": true,
    "message": "Stock quantity updated successfully",
    "data": { ...updated tyre... },
    "timestamp": 1704067200000
}
```

### 11. Reduce Stock (for Orders)
```
POST /api/v1/tyres/{id}/reduce-stock?quantity=5

Response: 200 OK
{
    "success": true,
    "message": "Stock quantity reduced successfully",
    "data": { ...updated tyre... },
    "timestamp": 1704067200000
}
```

### 12. Health Check
```
GET /api/v1/tyres/health/status

Response: 200 OK
{
    "success": true,
    "message": "Tyre API is running",
    "data": "OK",
    "timestamp": 1704067200000
}
```

## Setup Instructions

### Prerequisites
- Java 17+
- MySQL 8.0+
- Maven 3.6+

### Installation Steps

1. **Clone/Download the project**
   ```bash
   cd Roja-Performance
   ```

2. **Configure Database**
   - Create MySQL database (optional - will auto-create):
     ```sql
     CREATE DATABASE tyreshop;
     ```
   - Update `application.properties` with your credentials:
     ```properties
     spring.datasource.username=root
     spring.datasource.password=root
     ```

3. **Build the Project**
   ```bash
   ./mvnw clean install
   ```

4. **Run the Application**
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Or run the JAR:
   ```bash
   java -jar target/Roja-Performance-0.0.1-SNAPSHOT.jar
   ```

5. **Verify Application**
   ```bash
   curl http://localhost:8080/api/v1/tyres/health/status
   ```

## Exception Handling

### Custom Exceptions

1. **ResourceNotFoundException**
   - Thrown when a tyre is not found
   - HTTP Status: 404 Not Found
   ```json
   {
       "success": false,
       "message": "Tyre not found with ID: 999",
       "data": null,
       "timestamp": 1704067200000
   }
   ```

2. **IllegalArgumentException**
   - Thrown for invalid business logic
   - HTTP Status: 400 Bad Request
   ```json
   {
       "success": false,
       "message": "Insufficient stock. Available: 5, Requested: 10",
       "data": null,
       "timestamp": 1704067200000
   }
   ```

3. **Validation Errors**
   - Thrown for invalid request body
   - HTTP Status: 400 Bad Request
   ```json
   {
       "success": false,
       "message": "Validation failed",
       "data": {
           "brand": "Brand name is required",
           "price": "Price must be greater than zero"
       },
       "timestamp": 1704067200000
   }
   ```

## Validation Rules

| Field | Rules |
|-------|-------|
| brand | NOT NULL, NOT BLANK |
| size | NOT NULL, NOT BLANK |
| price | NOT NULL, POSITIVE (> 0) |
| stockQuantity | NOT NULL, MIN 0 |
| description | Optional |

## Key Features

### 1. Clean Architecture
- Separation of concerns
- Dependency injection
- Service layer abstraction

### 2. Production-Level Code
- Input validation
- Proper exception handling
- Comprehensive logging
- Transaction management

### 3. REST API Best Practices
- Proper HTTP status codes
- Consistent response format
- CORS enabled
- API versioning (/v1)

### 4. Data Integrity
- Unique constraint on brand + size
- Timestamp tracking (createdAt, updatedAt)
- Transaction boundaries

### 5. Security Features (For Future)
- CORS configuration
- Input validation
- SQL injection prevention (via JPA)

## Logging

The application uses SLF4J with Logback:

```properties
# Application logs
com.tyreshop.Roja.Performance=DEBUG

# ORM logs
org.hibernate.SQL=DEBUG
org.hibernate.type.descriptor.sql=TRACE
```

## Testing with cURL

### Create Tyre
```bash
curl -X POST http://localhost:8080/api/v1/tyres \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Bridgestone",
    "size": "215/60R16",
    "price": 4500.00,
    "stockQuantity": 30,
    "description": "Comfortable all-season tyre"
  }'
```

### Get All Tyres
```bash
curl http://localhost:8080/api/v1/tyres
```

### Get Tyre by ID
```bash
curl http://localhost:8080/api/v1/tyres/1
```

### Update Tyre
```bash
curl -X PUT http://localhost:8080/api/v1/tyres/1 \
  -H "Content-Type: application/json" \
  -d '{
    "brand": "Bridgestone",
    "size": "215/60R16",
    "price": 4800.00,
    "stockQuantity": 28,
    "description": "Updated description"
  }'
```

### Delete Tyre
```bash
curl -X DELETE http://localhost:8080/api/v1/tyres/1
```

## Phase 2 Roadmap

- Customer management
- Order management
- Authentication & Authorization
- Payment processing
- Inventory notifications
- Advanced search & filtering

## Performance Considerations

- Connection pooling (HikariCP)
- Batch processing for updates
- Efficient queries with JPQL
- Index on brand and size columns
- Timestamp fields for tracking

## Troubleshooting

### Database Connection Error
- Ensure MySQL is running
- Verify credentials in application.properties
- Check database exists or allow auto-creation

### Port Already in Use
- Change port in application.properties: `server.port=8081`

### Build Issues
- Clean Maven cache: `./mvnw clean install -U`
- Verify Java 17+ installed: `java -version`

## Code Quality

- All classes documented with JavaDoc
- Consistent naming conventions
- Proper use of annotations
- Transaction management
- Null-safety checks

## Dependencies

- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-validation
- lombok
- mysql-connector-j
- spring-boot-starter-test

## Contact & Support

For issues or improvements, refer to the codebase documentation and inline comments.

---

**Built with ❤️ by Roja Performance Team**
**Version 1.0.0 - Phase 1**
