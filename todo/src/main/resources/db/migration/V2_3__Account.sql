DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
  id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);