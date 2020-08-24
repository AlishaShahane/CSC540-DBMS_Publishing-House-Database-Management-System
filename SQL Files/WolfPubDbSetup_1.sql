use asshahan;

DROP TABLE IF EXISTS WritesBooks;
DROP TABLE IF EXISTS WritesPerPub;
DROP TABLE IF EXISTS EditsChapters;
DROP TABLE IF EXISTS EditsArticles;
DROP TABLE IF EXISTS OrdersBooks;
DROP TABLE IF EXISTS OrdersPerPub;
DROP TABLE IF EXISTS Pays;
DROP TABLE IF EXISTS Maintains;
DROP TABLE IF EXISTS Chapters;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS Books;
DROP TABLE IF EXISTS PeriodicPublications;
DROP TABLE IF EXISTS Publications;
DROP TABLE IF EXISTS Editors;
DROP TABLE IF EXISTS Authors;
DROP TABLE IF EXISTS Journalists;
DROP TABLE IF EXISTS Admins;
DROP TABLE IF EXISTS Transactions;
DROP TABLE IF EXISTS Distributors;
DROP TABLE IF EXISTS Employees;


CREATE TABLE Publications(
isbn INT, 
title VARCHAR(100) NOT NULL, 
genre VARCHAR(30), 
PRIMARY KEY(isbn) 
);


CREATE TABLE PeriodicPublications(
isbn INT, 
issue_wm INT, 
issue_year INT, 
periodicity VARCHAR(30) NOT NULL, 
pub_type VARCHAR(30) NOT NULL, 
issue_date DATE NOT NULL, 
price FLOAT NOT NULL, 
PRIMARY KEY (isbn, issue_wm, issue_year), 
FOREIGN KEY(isbn) REFERENCES Publications(isbn) ON DELETE CASCADE
);


CREATE TABLE Articles(
isbn INT, 
issue_wm INT, 
issue_year INT, 
article_id INT, 
article_date DATE NOT NULL, 
title VARCHAR(100) NOT NULL, 
topic VARCHAR(100) NOT NULL, 
text VARCHAR(500), 
PRIMARY KEY(isbn, issue_wm, issue_year, article_id), 
FOREIGN KEY(isbn, issue_wm, issue_year) REFERENCES PeriodicPublications(isbn, issue_wm, issue_year) ON DELETE CASCADE
);


CREATE TABLE Books(
isbn INT, 
edition INT,
edition_date DATE NOT NULL, 
price FLOAT NOT NULL, 
PRIMARY KEY (isbn, edition), 
FOREIGN KEY(isbn) REFERENCES Publications(isbn) ON DELETE CASCADE
);


CREATE TABLE Chapters(
isbn INT, 
edition INT, 
chapter_id INT, 
chapter_date DATE NOT NULL, 
title VARCHAR(100) NOT NULL, 
topic VARCHAR(100) NOT NULL, 
text VARCHAR(500), 
PRIMARY KEY(isbn, edition, chapter_id), 
FOREIGN KEY(isbn, edition) REFERENCES Books(isbn, edition) ON DELETE CASCADE
);


CREATE TABLE Employees(
emp_id INT AUTO_INCREMENT, 
emp_name VARCHAR(50) NOT NULL, 
email VARCHAR(30), 
salary FLOAT NOT NULL, 
pay_date DATE NOT NULL, 
emp_type VARCHAR(30) NOT NULL, 
age INT NOT NULL,
gender VARCHAR(30),
addr VARCHAR(30) NOT NULL,
phone VARCHAR(30) NOT NULL,
PRIMARY KEY(emp_id)
);


CREATE TABLE Admins(
emp_id INT, 
PRIMARY KEY(emp_id), 
FOREIGN KEY(emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);


CREATE TABLE Authors(
emp_id INT, 
PRIMARY KEY(emp_id), 
FOREIGN KEY(emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);


CREATE TABLE Editors(
emp_id INT, 
PRIMARY KEY(emp_id), 
FOREIGN KEY(emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);


CREATE TABLE Journalists(
emp_id INT, 
PRIMARY KEY(emp_id), 
FOREIGN KEY(emp_id) REFERENCES Employees(emp_id) ON DELETE CASCADE
);


CREATE TABLE Transactions(
trans_id INT AUTO_INCREMENT, 
trans_day INT NOT NULL, 
trans_month INT NOT NULL, 
trans_year INT NOT NULL, 
client_id INT NOT NULL, 
amount FLOAT NOT NULL, 
description VARCHAR(30) NOT NULL, 
PRIMARY KEY(trans_id)
);


CREATE TABLE Maintains(
trans_id INT, 
emp_id INT, 
PRIMARY KEY(trans_id, emp_id), 
FOREIGN KEY(emp_id) REFERENCES Admins(emp_id) ON DELETE CASCADE, 
FOREIGN KEY(trans_id) REFERENCES Transactions(trans_id) ON DELETE CASCADE
);


CREATE TABLE Distributors(
dist_id INT AUTO_INCREMENT, 
dist_name VARCHAR(30) NOT NULL, 
dist_type VARCHAR(30) NOT NULL, 
location VARCHAR(30) NOT NULL, 
city VARCHAR(30) NOT NULL, 
street_addr VARCHAR(30) NOT NULL, 
phone VARCHAR(30) NOT NULL, 
person_of_contact VARCHAR(30), 
balance_due FLOAT NOT NULL, 
PRIMARY KEY(dist_id)
);

CREATE TABLE Pays(
dist_id INT, 
emp_id INT, 
order_id INT, 
PRIMARY KEY(dist_id, emp_id, order_id), 
FOREIGN KEY(emp_id) REFERENCES Admins(emp_id) ON DELETE CASCADE, 
FOREIGN KEY(dist_id) REFERENCES Distributors(dist_id) ON DELETE CASCADE
);


CREATE TABLE WritesBooks(
emp_id INT, 
isbn INT, 
edition INT, 
PRIMARY KEY(emp_id, edition, isbn), 
FOREIGN KEY(emp_id) REFERENCES Authors(emp_id) ON DELETE CASCADE, 
FOREIGN KEY(isbn, edition) REFERENCES Books(isbn, edition) ON DELETE CASCADE
 );


CREATE TABLE WritesPerPub(
emp_id INT, 
isbn INT, 
issue_wm INT, 
issue_year INT, 
PRIMARY KEY(emp_id, issue_wm, issue_year, isbn), 
FOREIGN KEY(isbn, issue_wm, issue_year) REFERENCES PeriodicPublications(isbn, issue_wm, issue_year) ON DELETE CASCADE, 
FOREIGN KEY(emp_id) REFERENCES Journalists(emp_id) ON DELETE CASCADE
);


CREATE TABLE EditsChapters(
emp_id INT, 
isbn INT, 
edition INT, 
chapter_id INT, 
PRIMARY KEY(emp_id, edition, isbn, chapter_id), 
FOREIGN KEY(isbn, edition, chapter_id) REFERENCES Chapters(isbn, edition, chapter_id) ON DELETE CASCADE, 
FOREIGN KEY(emp_id) REFERENCES Editors(emp_id) ON DELETE CASCADE
);


CREATE TABLE EditsArticles(
emp_id INT, 
isbn INT, 
issue_wm INT, 
issue_year INT, 
article_id INT, 
PRIMARY KEY(emp_id, issue_wm, issue_year, isbn, article_id), 
FOREIGN KEY(isbn, issue_wm, issue_year, article_id) REFERENCES Articles(isbn, issue_wm, issue_year, article_id) ON DELETE CASCADE, 
FOREIGN KEY(emp_id) REFERENCES Editors(emp_id) ON DELETE CASCADE
);


CREATE TABLE OrdersBooks(
order_id INT AUTO_INCREMENT, 
isbn INT, 
edition INT, 
title VARCHAR(100) NOT NULL,
dist_id INT, 
order_day INT NOT NULL, 
order_month INT NOT NULL, 
order_year INT NOT NULL, 
exp_del_date DATE NOT NULL, 
status VARCHAR(30) NOT NULL, 
num_ordered_copies INT NOT NULL, 
price_per_copy FLOAT NOT NULL, 
shipping_cost FLOAT NOT NULL,
total_amount FLOAT NOT NULL,
PRIMARY KEY(order_id, isbn, edition, dist_id)
)AUTO_INCREMENT=4001;



CREATE TABLE OrdersPerPub(
order_id INT AUTO_INCREMENT, 
isbn INT, 
issue_wm INT, 
issue_year INT, 
title VARCHAR(100) NOT NULL,
dist_id INT, 
order_day INT NOT NULL, 
order_month INT NOT NULL, 
order_year INT NOT NULL, 
exp_del_date DATE NOT NULL, 
status VARCHAR(30) NOT NULL, 
num_ordered_copies INT NOT NULL, 
price_per_copy FLOAT NOT NULL, 
shipping_cost FLOAT NOT NULL,
total_amount FLOAT NOT NULL,
PRIMARY KEY(order_id, isbn, issue_wm, issue_year, dist_id)
)AUTO_INCREMENT=5001;

















