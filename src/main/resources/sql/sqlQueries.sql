CREATE TABLE book (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  genre VARCHAR(250) DEFAULT NULL
);

INSERT INTO book (title, author, genre) VALUES
  ('book1', 'author1', 'genre1'),
  ('book2', 'author2', 'genre2'),
  ('book3', 'author3', 'genre3');