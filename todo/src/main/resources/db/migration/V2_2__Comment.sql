DROP TABLE IF EXISTS comments;
CREATE TABLE comments (
  id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
  comment TEXT NOT NULL,
  todo_id INT(11),
  created_date DATETIME NOT NULL,
  updated_date DATETIME NOT NULL
);