INSERT INTO Persons (person_id, type, name, gender, age, email, phone_num, address) VALUES
	(1, 'admin', 'Alice', NULL, NULL, NULL, NULL, NULL),
	(3001, 'editor', 'John', 'M', 36, '3001@gmail.com', '9391234567', '21 ABC St, NC 27'),
	(3002, 'editor', 'Ethen', 'M', 30, '3002@gmail.com', '9491234567', '21 ABC St, NC 27606'),
	(3003, 'author', 'Cathy', 'F', 28, '3003@gmail.com', '9591234567', '3300 AAA St, NC 27606'),
	(2001, 'distributor', 'BookSell', NULL, NULL, NULL, '9191234567', '2200, A Street, NC'),
	(2002, 'distributor', 'BookDist', NULL, NULL, NULL, '9291234567', '2200, B Street, NC');

INSERT INTO Distributors (person_id, balance, contact_person, type, city) VALUES
	(2001, 215.00, 'Jason', 'bookstore', 'charlotte'),
	(2002, 0.00, 'Alex', 'wholesaler', 'Raleigh');

INSERT INTO Publications (pub_id, title, date) VALUES
	(1001, 'introduction to database', '2018-10-10'),
	(1002, 'Healthy Diet', '2020-02-24'),
	(1003, 'Animal Science', '2020-03-01');

INSERT INTO Books (pub_id, edition, ISBN, topic) VALUES
	(1001, 2, '12345', 'technology');

INSERT INTO Periodicals (period_id, type, periodicity, topic) VALUES
	(1, 'magazine', 'monthly', 'health'),
	(2, 'journal', 'monthly', 'science');

INSERT INTO Issues (pub_id, period_id) VALUES
	(1002, 1),
	(1003, 2);

INSERT INTO Articles_Chapters (art_id, date, text, title, topic) VALUES
	(1, '2020-02-24', 'ABC', 'ABC', 'health'),
	(2, '2020-03-01', 'AAA', 'AAA', 'science');

INSERT INTO Articles_or_Chapters_in_Publications (pub_id, art_id) VALUES
	(1002, 1),
	(1003, 2);

INSERT INTO Authors (person_id, type) VALUES
	(3003, 'invited');

INSERT INTO Author_write_Articles_or_Chapters (person_id, art_id) VALUES
	(3003, 1),
	(3003, 2);

INSERT INTO Author_write_Books (person_id, pub_id) VALUES
	(3003, 1001);

INSERT INTO Editors (person_id, type) VALUES
	(3001, 'staff'),
	(3002, 'staff');

INSERT INTO Editor_edit_Publications (person_id, pub_id) VALUES
	(3001, 1001),
	(3002, 1002);

INSERT INTO Orders (order_id, num_of_copy, order_date, delivery_date, price, shipping_cost, person_id, pub_id) VALUES
	(4001, 30, '2020-01-02', '2020-01-15', 20.00, 30.00, 2001, 1001),
	(4002, 10, '2020-02-05', '2020-02-15', 20.00, 15.00, 2001, 1001),
	(4003, 10, '2020-02-10', '2020-02-25', 10.00, 15.00, 2002, 1003);

INSERT INTO Payments (pay_id, date, type, amount, person_id) VALUES
	(NULL, '2020-04-01', 'salary', -1000.00, 3001),
	(NULL, '2020-04-01', 'salary', -1000.00, 3002),
	(NULL, '2020-04-01', 'salary', -1200.00, 3003),
	(NULL, '2020-04-01', 'income', 630.00, 2001),
	(NULL, '2020-04-01', 'income', 115.00, 2002),
	(NULL, '2020-01-02', 'shipping', -30.00, 1001),
	(NULL, '2020-02-05', 'shipping', -15.00, 1001),
	(NULL, '2020-02-10', 'shipping', -15.00, 1003);