INSERT INTO tickets (id, title, content, priority, notes, status) VALUES (1, 'Lost baggage', 'I couldn’t find my baggage after the flight from NYC to London.', 5, 'High priority, VIP customer', 'Done'), (2, 'Flight delay', 'Flight AF123 was delayed for 5 hours and I received no updates.', 3, NULL, 'Done'), (3, 'Class upgrade request', 'Request for business class upgrade for flight AF456.', 4, 'Handle with care','Done'), (4, 'Check-in issue', 'The online check-in system is not working for flight AF789.', 1, NULL, 'Done'), (5, 'Refund request', 'Request for a refund for a flight canceled without notice.',2 , NULL, 'Done'), (6, 'Damaged baggage', 'My baggage was returned severely damaged after the flight.',  1, 'Photo of the damage attached', 'Done');
INSERT INTO categories (id, name) VALUES (1, 'Lost baggage'),(2, 'Flight delay or cancellation'),(3, 'Flight change or upgrade'),(4, 'Check-in issues'),(5, 'Refund requests'),(6, 'Baggage damage');
INSERT INTO tickets_categories (ticket_id, category_id) VALUES (1,1),(2,2),(4,4),(3,3),(5,5),(6,6);
INSERT INTO users (id, username, password, first_name, last_name, picture) VALUES (1, 'jdoe1', 'password123', 'John', 'Doe', 'https://example.com/images/jdoe1.jpg'),(2, 'asmith', 'pass456', 'Alice', 'Smith', 'https://example.com/images/asmith.jpg'),(3, 'bjackson', 'pwd789', 'Brad', 'Jackson', 'https://example.com/images/bjackson.jpg'),(4, 'csanchez', 'abc123', 'Carlos', 'Sanchez', 'https://example.com/images/csanchez.jpg'),(5, 'rjohnson', 'qwerty456', 'Rachel', 'Johnson', 'https://example.com/images/rjohnson.jpg'),(6, 'lmartinez', 'zxcvbn789', 'Luis', 'Martinez', 'https://example.com/images/lmartinez.jpg'),(7, 'klee', 'pass001', 'Kevin', 'Lee', 'https://example.com/images/klee.jpg'),(8, 'dwhite', 'password654', 'Diana', 'White', 'https://example.com/images/dwhite.jpg'),(9, 'tadams', 'pass321', 'Thomas', 'Adams', 'https://example.com/images/tadams.jpg'),(10, 'cwilson', 'passwd789', 'Catherine', 'Wilson', 'https://example.com/images/cwilson.jpg'),(11, 'pmiller', 'pass876', 'Paul', 'Miller', 'https://example.com/images/pmiller.jpg'),(12, 'jsmith', 'pass998', 'Jessica', 'Smith', 'https://example.com/images/jsmith.jpg'),(13, 'awilson', 'pwd234', 'Andrew', 'Wilson', 'https://example.com/images/awilson.jpg'),(14, 'mjones', 'mypassword123', 'Megan', 'Jones', 'https://example.com/images/mjones.jpg'),(15, 'bgarcia', 'securepwd', 'Brianna', 'Garcia', 'https://example.com/images/bgarcia.jpg'),(16, 'fmartin', 'mypassword321', 'Francis', 'Martin', 'https://example.com/images/fmartin.jpg'),(17, 'kbell', 'key1234', 'Kara', 'Bell', 'https://example.com/images/kbell.jpg'),(18, 'dgreen', 'passkey001', 'David', 'Green', 'https://example.com/images/dgreen.jpg'),(19, 'nrodriguez', 'alpha789', 'Natalie', 'Rodriguez', 'https://example.com/images/nrodriguez.jpg'),(20, 'jmurphy', 'passalpha123', 'James', 'Murphy', 'https://example.com/images/jmurphy.jpg'),(21, 'abrown', 'secure789', 'Ashley', 'Brown', 'https://example.com/images/abrown.jpg'),(22, 'charris', 'pass789abc', 'Chris', 'Harris', 'https://example.com/images/charris.jpg'),(23, 'lthompson', 'keypass456', 'Laura', 'Thompson', 'https://example.com/images/lthompson.jpg'),(24, 'bscott', 'beta987', 'Brian', 'Scott', 'https://example.com/images/bscott.jpg'),(25, 'rmorgan', 'omega1234', 'Rachel', 'Morgan', 'https://example.com/images/rmorgan.jpg'), (26, 'admin', 'password', 'Michele', 'Gelmini', 'dfdsfdsf');
INSERT INTO roles (id, name) VALUES(0, 'ADMIN'), (1, 'OPERATOR');
INSERT INTO users_roles(roles_id, user_id) VALUES (0, 26),(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10), (1, 11), (1, 12), (1, 13), (1, 14), (1, 15), (1, 16), (1, 17), (1, 18), (1, 19), (1, 20), (1, 21), (1, 22), (1, 23), (1, 24), (1, 25);