INSERT INTO PeriodicPublications VALUES
(1002, 02, 2020, "monthly", "magazine", "2020-02-24", 75),
(1003, 03, 2020, "monthly", "journal", "2020-03-01", 10);


INSERT INTO Articles VALUES
(1002, 02, 2020, 1, "2020-02-25", "Keto Diet", "Diet", "ABC"),
(1003, 03, 2020, 1, "2020-03-03", "Animal Science title", "Animal Science topic", "AAA");


INSERT INTO Books VALUES
(12345, 2, "2018-10-10", 20);


INSERT INTO Chapters VALUES
(12345, 2, 1, "2018-10-12", "Introduction", "topic1", "Databases are essential to business.");

INSERT INTO Admins(emp_id) values
(3006);


INSERT INTO Authors(emp_id) values
(3003),
(3005);


INSERT INTO Editors(emp_id) values
(3001),
(3002);


INSERT INTO Journalists(emp_id) values
(3004);



INSERT INTO Maintains values
(1, 3006),
(2, 3006),
(3, 3006),
(4, 3006),
(5, 3006);


INSERT INTO WritesBooks values
(3005, 12345, 2);


INSERT INTO WritesPerPub values
(3004, 1002, 02, 2020),
(3004, 1003, 03, 2020);


INSERT INTO EditsChapters values
(3001, 12345, 2, 1);



INSERT INTO EditsArticles values
(3002, 1002, 02, 2020, 1);



INSERT INTO OrdersBooks(isbn, edition, title, dist_id, order_day, order_month, order_year, exp_del_date, status, num_ordered_copies, price_per_copy, shipping_cost, total_amount) values
(12345, 2, "Introduction to Databases", 2001, 02, 01, 2020, "2020-01-15", "completed paid", 30, 20, 30,630),
(12345, 2, "Introduction to Databases", 2001, 05, 02, 2020, "2020-02-15", "accepted", 10, 20, 15,215); 



INSERT INTO OrdersPerPub (isbn, issue_wm, issue_year, title, dist_id, order_day, order_month, order_year, exp_del_date, status, num_ordered_copies, price_per_copy, shipping_cost, total_amount) values 
(1003, 3, 2020, "Animal Science", 2002, 10, 02, 2020, "2020-02-25", "completed paid", 10, 10, 15, 115);


INSERT INTO Pays values
(2001,3006,4001),
(2002,3006,5001);



