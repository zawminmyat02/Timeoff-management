INSERT INTO department (code, description, head_of_department_code, name) VALUES ('D001', 'Human Resources Department', NULL, 'Human Resources');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (15, 50000.00, 100.00, 1, 'D001');
INSERT INTO account (activated, role, name, password, username) VALUES (1, 0, 'John Doe', 'password123', 'johndoe123');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (1, '2023-01-15', '1990-05-25', 0, 1, 'D001', 'johndoe@example.com', '123456789', 'D001', 1, 'EMP001');
