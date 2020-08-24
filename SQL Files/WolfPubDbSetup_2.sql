INSERT INTO Publications VALUES 
(12345, "Introduction to Databases", "Technology"),
(1002, "Healthy Diet", "Health"),
(1003, "Animal Science", "Science");


INSERT INTO Employees(emp_id, emp_name, email, salary, pay_date, emp_type, age, gender, addr, phone) VALUES
(3001, "John", "3001@gmail.com", 1000, "2020-04-01", "staff", 36, "male", "21 ABC St, NC 27", "9391234567");
INSERT INTO Employees(emp_name, email, salary, pay_date, emp_type, age, gender, addr, phone) VALUES
("Ethen", "3002@gmail.com", 1000, "2020-04-01", "staff", 30, "male", "21 ABC St, NC 27606", "9491234567"),
("Cathy", "3003@gmail.com", 1200, "2020-04-01", "invited", 28, "female", "3300 AAA St, NC 27606", "9591234567"),
("Manny Kae", "manny@abc.com", 1000, "2020-02-29", "staff", 29, "male", "78 Millpitas, NC, 27683", "9499044567"),
("Adi Brave", "adi@abc.com", 1000, "2020-02-29", "staff", 29, "male", "123 Cary, NC", "9927234567"),
("Patricia Suttle", "patricia@abc.com", 1000, "2020-02-29", "admin", 45, "female", "5 Garner St Raleigh", "9499754567");


INSERT INTO Transactions(trans_day, trans_month, trans_year, client_id, amount, description) VALUES 
(01, 04, 2020, 3001, -1000, "Salary Paid"),
(01, 04, 2020, 3002, -1000, "Salary Paid"),
(01, 04, 2020, 3003, -1200, "Salary Paid"),
(10, 01, 2020, 2001, 630, "Payment Received for 4001"),
(10, 02, 2020, 2002, 115, "Payment Received for 5001");


INSERT INTO Distributors(dist_id, dist_name, dist_type, location, city, street_addr, phone, person_of_contact, balance_due) VALUES 
(2001, "BookSell", "bookstore", "Charlotte", "Charlotte", "2200, A Street, NC", "9191234567", "Jason",  215);
INSERT INTO Distributors(dist_name, dist_type, location, city, street_addr, phone, person_of_contact, balance_due) VALUES 
("BookDist", "wholesaler", "Raleigh" , "Raleigh", "2200, B Street, NC", "9291234567", "Alex", 0);
