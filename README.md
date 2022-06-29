# gerimedica-project

##å Table of contents

- [Tech stack](#tech-stack)
- [Usage](#usage)
    * [Upload a CSV file to save the records in the database](#upload-a-csv-file-to-save-the-records-in-the-database)
    * [Get all records saved in database](#get-all-records-saved-in-database)
    * [Get one record from database by a requested code](#get-one-record-from-database-by-a-requested-code)
    * [Delete all records in the datavase](#delete-all-records-in-the-datavase)
- [H2 dashboard](#h2-dashboard)
- [Development process](#development-process)
    * [List of implemented system-level tests:](#list-of-implemented-system-level-tests-)
    * [Unit level tests](#unit-level-tests)
  
## Tech stack
- Java 11
- Spring Boot 2.7.1
- H2 in-memory database
- Maven
## Usage
four types of requests can be sent to this API.

### Upload a CSV file to save the records in the database
send a POST request with a file in the body to `http://localhost:8080/api/grecord`.

As an example, you can check the postman JSon file saved in the root directory (upload csv).

### Get all records saved in database
send a GET request to `http://localhost:8080/api/grecord`.

As an example, you can check the postman JSon file saved in the root directory (get all).

### Get one record from database by a requested code
send a GET request to `http://localhost:8080/api/grecord/[code]`.

As an example, you can check the postman JSon file saved in the root directory (get by code (exists) and get by code (does not exist)).

### Delete all records in the datavase
send a DELETE request to `http://localhost:8080/api/grecord`.

As an example, you can check the postman JSon file saved in the root directory (delete all).


## H2 dashboard
You can also access the H2 dashboard with the following address:

```
http://localhost:8080/h2/
```
username: admin

password: 123


## Development process
I have tried to perform TDD for this assignment. 
Although, due to the shortage of time I only did high-level testing (i.e., endpoint testing). 
Hence, before implementing each feature, first, I have implemented one or two tests, and then, I have  implemented the feature itself.
I continued this process iteratively for each requirement.
I tried to commit my changes as atomic as possible. I hope that the development process is also visible in the code history.

__Update:__ Since I had a bit more time, I wrote another system-level test to check a corner case, where the uploaded CSV file does not have the right template (i.e., different headers)

### List of implemented system-level tests:

- testEmptyGetAll: This test make sure that the API returns an empty list when we dont upload the CSV.
- testUploadAndFullGetAll: This test upload a fake CSV file with 4 records. Then, it get all records saved in the database in order to check if they stored properly.
- testUniqueCode: This test uploads the same csv file twice. Then, it checks if the second request was successful.
- testGettingExistingRecordByCode: This test case tests the "Get one record by a given code" feature. It uploads a CSV file. Then, it gets an existing record. 
- testGettingNonExistingRecordByCode: This test case tests the "Get one record by a given code" feature. It uploads a CSV file. Then, it requests for a non-existing record. Naturally, it asserts the response is empty.
- testUploadAndFullGetAllWithWrongCSVTemplate: This test case checks a situation in which the uploaded CSV file does not have the right template (i.e., different headers).
### Unit level tests
Unfortunately, I had no time remained for unit testing, As the next step, this is necessary to add unit tests as well. The number of unit tests should be more than system-level tests as suggested by __test automation pyramid__.