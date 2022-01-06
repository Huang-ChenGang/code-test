## environment

- OpenJDK: 11
- Gradle: 6.7.1
- SpringBoot: 2.3.7.RELEASE
- DB: h2

## API displays all the Products

GET /products

eg: http://localhost:8080/products?pageNo=0&pageSize=10

## API add products to the cart

POST /cart/products

eg:
- URL: http://localhost:8080/cart/products
- RequestBody:
```json
{
    "customerId": "123456",
    "productList": [
        {
            "productId": "1641456939948614861",
            "quantity": 5
        },
        {
            "productId": "1641456939948344697",
            "quantity": 30
        }
    ]
}
```

## API edit the products in cart

PUT /cart/product

eg:
- URL: http://localhost:8080/cart/product
- RequestBody:
```json
{
    "customerId": "123456",
    "cartItemId": "1641457096625729729",
    "quantity": 1
}
```

## API remove a product from cart

DELETE /cart/products

eg:
- URL: http://localhost:8080/cart/products
- RequestBody:
```json
{
    "customerId": "123456",
    "cartItemIdList": [
        "1641457003217540879",
        "1641457096613964480"
    ]
}
```

## API display overall bill

GET /cart/overall-bill/{customerId}

eg: http://localhost:8080/cart/overall-bill/123456

## addition

the method init() in ProductServiceImpl.java, is only used for initializing test data.