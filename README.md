# Introduction

Exploring of Microservice Technology Stack with Spring boot, Spring-JPA, Apache Kafka, ReactJS

## API Documentation


### ACCOUNT API
1. Create Account 
    - Request
        ```http
        POST /accounts
        ```
        Passing data with Json Format
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `ic_number` | `string` | **Required**. Ic Number |
        
    - Response
        ```javascript
        {
            "acct_number": integer,
            "ic_number": String
        }
        ```

2. Get Account Detail by Account Number
    - Request
        ```http
        GET /accounts/{acct_number}
        ```
        Passing data via url path
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `acct_number` | `integer` | **Required**. Account Number |
        
    - Response
        ```javascript
        {
            "acct_number": integer,
            "ic_number": String
        }
        ```
    
3. Get All Account Detail
    - Request
    
        ```http
        GET /accounts
        ```
    - Response
        ```javascript
        {
            "acct_number": integer,
            "ic_number": String
        }, ...
        ```
4. Update Account Detail by Account Number
    - Request
        ```http
        PUT /accounts
        ```
        Passing data with Json Format
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `ic_number` | `string` | **Required**. Ic Number |
        
    - Response
        ```javascript
        {
            "acct_number": integer,
            "ic_number": String
        }
        ```
      
### Transfer API

1. Transfer Request
    - Request
        ```http
        POST /transfer"
        ```
        Passing data with Json Format
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `debitAcct` | `integer` | **Required**. Transfer From Account number |
        | `debitIC` | `String` | **Required**. Transfer From Account's IC number |
        | `creditAcct` | `integer` | **Required**. Transfer To Account number |
        | `transferIC` | `String` | **Required**. Transfer To Account's IC number |
        | `amount` | `double` | **Required**. Transfer amount |
        
    - Response
        ```javascript
        {
            "id": integer,
            "amount": double,
            "status": string,
            "reason": null,
            "txUid": string,
            "debitAcct": integer,
            "debitIC": string,
            "creditAcct": integer,
            "creditIC": string,
        }
        ```

2. Get Transactions By Ic Number
    - Request
        ```http
        Get /listAllTransferRequestByIcNo/{ic}
        ```
        Passing data via url path
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `ic` | `string` | **Required**. Ic Number |
        
    - Response
        ```javascript
        {
            "id": integer,
            "amount": double,
            "status": string,
            "reason": string,
            "txUid": string,
            "debitAcct": integer,
            "debitIC": string,
            "creditAcct": integer,
            "creditIC": string,
        }, ...
        ```
      
3. Get Transfer Status by TXUID
    - Request
        ```http
        Get /checkTransferStatusForTXUID/{txuid}
        ```
        Passing data via url path
        
        | Parameter | Type | Description |
        | :--- | :--- | :--- |
        | `txuid` | `string` | **Required**. Transaction Uid |
        
    - Response
        ```javascript
        {
            "id": integer,
            "amount": double,
            "status": String,
            "reason": String,
            "txUid": String,
            "debitAcct": integer,
            "debitIC": String,
            "creditAcct": integer,
            "creditIC": String,
        }
        ```