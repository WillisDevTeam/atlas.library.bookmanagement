INSERT INTO BOOK (BOOK_ID, ISBN, TITLE, AUTHOR, GENRE, AVAILABLE, COST, PUBLISHER_NAME, PUBLISH_DATE, CREATION_DATE, MODIFICATION_DATE)
VALUES
('01c9c860-c895-41b4-b777-d7846e2abb01','0-3494-2905-7', 'title1', 'author1', 'genre1', 1, 12.95, 'pubName1', '1980-12-01', '2020-01-02 12:00:00', '2020-01-02 12:00:00'),
('01c9c860-c895-41b4-b777-d7846e2abb02','0-2669-0606-0', 'title2', 'author2', 'genre2', 1, 15.95, 'pubName2', '1990-03-01', '2020-01-03 12:00:00', '2020-01-03 12:00:00'),
('01c9c860-c895-41b4-b777-d7846e2abb03','0-2046-9976-2', 'title3', 'author3', 'genre3', 1, 8.95, 'pubName3', '1995-08-01', '2020-01-04 12:00:00', '2020-01-04 12:00:00');

INSERT INTO USER (USER_ID, FIRST_NAME, LAST_NAME, ACCOUNT_BALANCE, CREATION_DATE, MODIFICATION_DATE)
VALUES
('01c9c860-c895-41b4-b888-d7846e2abb01','firstName1', 'lastName1', 0, '2020-01-02 12:00:00', '2020-01-02 12:00:00'),
('01c9c860-c895-41b4-b888-d7846e2abb02','firstName2', 'lastName2', 6.45, '2020-01-02 12:00:00', '2020-01-02 12:00:00'),
('01c9c860-c895-41b4-b888-d7846e2abb03','firstName3', 'lastName3', 12, '2020-01-02 12:00:00', '2020-01-02 12:00:00');

INSERT INTO BOOK_CHECKOUT (BOOK_CHECKOUT_ID, BOOK_ID, USER_ID, RENEWABLE, DUE_DATE, CREATION_DATE, MODIFICATION_DATE)
VALUES
('01c9c860-c895-41b4-b999-d7846e2abb01', '5603c75f-34c7-4158-b515-b0cde6f532bf', '20001', 0, '2021-02-02 12:00:00', '2020-01-02 12:00:00', '2020-01-02 12:00:00'),
('01c9c860-c895-41b4-b999-d7846e2abb02', 'ebf92ed3-7c5b-4288-89a9-82fc8098ce47', '20001', 1, '2021-02-04 12:00:00', '2020-01-02 12:00:00', '2020-01-02 12:00:00'),
('01c9c860-c895-41b4-b999-d7846e2abb03', 'ebf92ed3-7c5b-4288-89a9-82fc8098ce47', '20003', 1, '2021-02-06 12:00:00', '2020-01-02 12:00:00', '2020-01-02 12:00:00');


INSERT INTO BOOK_QUANTITY (BOOK_QUANTITY_ID, ISBN, TOTAL_QUANTITY, CURRENT_QUANTITY, CREATION_DATE, MODIFICATION_DATE)
VALUES
('01c9c860-c895-41b4-b666-d7846e2abb01','0-3494-2905-7', 5, 0, '2020-01-04 12:00:00', '2020-01-04 12:00:00'),
('01c9c860-c895-41b4-b666-d7846e2abb02','0-2669-0606-0', 2, 2, '2020-01-04 12:00:00', '2020-01-04 12:00:00'),
('01c9c860-c895-41b4-b666-d7846e2abb03','0-2046-9976-2', 8, 8, '2020-01-04 12:00:00', '2020-01-04 12:00:00');
