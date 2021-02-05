# Atlas Library Book Management Service

This service is to be used as an example of a working backend for a public libaray.
This service will be used to manage the following: 
- Books within the Atlas Library
- Users of the Atlas Library
- The Book Checkout process 

## Assumptions
As this is a simple project, some assumptions had to be made and will be outlined below.

### Data Updates
I have made some restrictions on what can be updated on each of the following:
- Book: Only the book cost can be updated.
- BookCheckout: Only the book due date can be updated. Invoking this update will extend the due date by 7 days.
- user: only the users account balance can be updated.

### Validations
Currently, minor validations are being made on incoming request objects. This leaves the service venerable to attack/misuse/user-error
<br /> In a real world application, validations would be made on each incoming request to make sure each object is what is expected.
<br /> For Example:
- When creating a new Library book, the CreateBookModel object fields would need to be validated for the expected contraints and possible enum values.
- when making an update to the user accountBalance the updateLibraryUserAccBal endpoint excepts a double. This could be validated and restrained to only except values with up to 2 places after the decimal point.

### Unit Tests
Currently, only happy path unit tests have been written for all Controllers and Service layer classes.
<br /> In a real world application, Unit tests will have the following attributes:
- Line Coverage: 80% or greater (measures how many statements are tested)
- Mutation Coverage: 80% or greater 
- Branch Coverage: 80% or greater. (Example branch coverage would check true and false branchs for each conditional)

### API Doc
In a real world application, an API document would be developed and shared with users of the service.
<br /> This document would contain all the information necessary to successfully hit the endpoints with this service.
<br /> It could contain the following information:
- Endpoint descriptions
- Required fields for each endpoint
- Optional fields for each endpoint
- URL parameters
- Request body structure
- Successful response body structure
- Failure response structures (Example: 400, 401, 403, 409, etc)

### Authorization
In a real world application, each endpoint would contain the proper token authentication such as a JSON Web Token (JWT)
<br /> For Example:
- GET endpoints may be available for anyone and would not contain any sensitive information
- GET endpoints may also be protected if the response contains sensitive information
- POST/PUT/DELETE endpoints would be made secure to protect against attacks
