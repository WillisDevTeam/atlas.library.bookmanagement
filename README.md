# Atlas Library Book Management Service

This service will be used to manage the following: 

    - Books within the Atlas Library
    - Users of the Atlas Library
    - The Book Checkout process 

This service is to be used as an example 
## Assumptions
As this is a simple project, some assumptions had to be made and will be outlined below.

### Data Updates
I have made some restrictions on what can be updated on each of the following:
- Book: Only the book cost can be updated.
- BookCheckout: Only the book due date can be updated. invoking this update will extend the due date by 7 days.
- user: only the users account balance can be updated.

### Validations
Currently, no validations are being made on incoming request objects. This leaves the service venerable to attack/misuse/user-error
<br /> In a real world application validations would be made on each incoming request to makes sure each object is as expected.
<br /> For Example:
- When creating a new Library book, the CreateBookModel object would need to be validated for the expected fields
- when making an update to the user accountBalance the updateLibraryUserAccBal endpoint excepts a double. This could be validated and restrained to only except values with up to 2 places after the decimal place.

### Unit Tests
Currently, only happy path unit tests have been written for all Controllers and Service layer classes.
<br /> In a real world application, Unit tests will have the following attributes:
- Line Coverage: 80% or greater (measures how many statements you took)
- Mutation Coverage: 80% or greater ()
- Branch Coverage: 80% or greater. (Branch coverages checks if you took the true and false branch for each conditional)

### API Doc
In a real world application, an API document would be developed and shared with users of the service.
<br /> This document would contain all the information necessary to successfully hit the endpoints with this service.
<br /> It would contain the following information:
- Endpoint descriptions
- Required fields for each endpoint
- Optional fields for each endpoint
- Request body structure
- Successful Response body structure
- Failure Response structures (Example: 400, 401, 403, 409, etc)

### Authorization
In a real world application, each endpoint would contain the proper token authentication such as a JSON Web Token (JWT)
<br /> For Example:
- GET endpoints may be available for anyone and would not contain any sensitive information
- POST/PUT/DELETE endpoints would be made secure to protect against attacks