INSERT INTO department (code, description, head_of_department_code, name) VALUES ('D001', 'Human Resources Department', NULL, 'Human Resources');
INSERT INTO department (code, description, head_of_department_code, name) VALUES ('C001', 'Computer Department', NULL, 'Computer');
INSERT INTO department (code, description, head_of_department_code, name) VALUES ('M001', 'Mechanics Department', NULL, 'Mechanics');
INSERT INTO department (code, description, head_of_department_code, name) VALUES ('E001', 'Enginerring Department', NULL, 'Engineering');



INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (15, 1500000.00, 10000.00, 0, 'D001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (10, 5000000.00, 10000.00, 4, 'D001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (20, 2500000.00, 10000.00, 0, 'C001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (15, 1000000.00, 10000.00, 4, 'C001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (15, 1500000.00, 10000.00, 0, 'M001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (10, 1000000.00, 10000.00, 4, 'M001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (13, 1500000.00, 10000.00, 0, 'E001');
INSERT INTO position (anual_leaves, basic_salary, ot_fees_per_hour, position_code, department_code) VALUES (10, 5000000.00, 10000.00, 4, 'E001');


INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'John', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'john@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'Jack', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'jack@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 2, 'Smith', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'smith@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'Louis', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'louis@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'Chloe', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'chloe@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'Liam', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'liam@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 2, 'Harry', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'harry@gmail.com');
INSERT INTO account (activated, role, name, password, username) VALUES (0, 1, 'Sandy', '$2a$10$cAbPy9AoBTdHxd5nYzktG.Gjs.yjqktNG1iPUK5ml170ptp1fSRky', 'sandy@gmail.com');

INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (1, '2023-01-15', '1990-05-25', 0, 0, 'D001', 'john@gmail.com', '123456789', 'D001', 1, 'D001-0001');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (2, '2022-01-15', '1990-05-25', 0, 4, 'D001', 'jack@gmail.com', '123456789', 'D001', 1, 'D001-0002');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (3, '2024-03-15', '1990-05-25', 0, 0, 'C001', 'smith@gmail.com', '123456789', 'C001', 1, 'C001-0001');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (4, '2023-02-15', '1990-05-25', 0, 4, 'C001', 'louis@gmail.com', '123456789', 'C001', 1, 'C001-0002');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (5, '2023-01-05', '1990-05-25', 1, 0, 'M001', 'chloe@gmail.com', '123456789', 'M001', 1, 'M001-0001');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (6, '2023-07-15', '1990-05-25', 0, 4, 'M001', 'liam@gmail.com', '123456789', 'M001', 1, 'M001-0002');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (7, '2023-09-15', '1990-05-25', 0, 0, 'E001', 'harry@gmail.com', '123456789', 'E001', 1, 'E001-0001');
INSERT INTO employee (account_id, assign_date, date_of_birth, gender, position_position_code, department_code, email, phone, position_department_code, status, code) VALUES (8, '2024-06-15', '1990-05-25', 1, 4, 'E001', 'sandy@gmail.com', '123456789', 'E001', 1, 'E001-0002');


INSERT INTO leave_type (paid_days, name, remark) VALUES (10, 'Sick Leave', 'Paid sick leave');
INSERT INTO leave_type (paid_days, name, remark) VALUES (15, 'Vacation Leave', 'Paid vacation leave');
INSERT INTO leave_type (paid_days, name, remark) VALUES (5, 'Personal Leave', 'Paid personal leave');
INSERT INTO leave_type (paid_days, name, remark) VALUES (12, 'Maternity Leave', 'Paid maternity leave');

INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (1, '2024-07-01', '2024-06-25', 1, '2024-06-15 08:00:00.000000', 'E001-0001','E001', 'Family event', 'PENDING');
INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (2, '2024-07-01', '2024-06-25', 1, '2024-06-15 08:00:00.000000', 'C001-0001','C001', 'Family event', 'APPROVED');
INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (3, '2024-07-01', '2024-06-25', 1, '2024-06-15 08:00:00.000000', 'D001-0001','D001', 'Family event', 'REJECTED');
INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (4, '2024-07-01', '2024-06-25', 2, '2024-06-15 08:00:00.000000', 'E001-0002','E001', 'Family event', 'PENDING');
INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (5, '2024-07-01', '2024-06-25', 3, '2024-06-15 08:00:00.000000', 'E001-0002','E001', 'Family event', 'PENDING');
INSERT INTO leave_application (id, end_date, start_date, type_id, apply_at, employee_code,department_code, remark, status) VALUES (6, '2024-07-01', '2024-06-25', 1, '2024-06-15 08:00:00.000000', 'E001-0002','E001', 'Family event', 'PENDING');

UPDATE department SET head_of_department_code = 'E001-0001' WHERE code = 'E001';
UPDATE department SET head_of_department_code = 'C001-0001' WHERE code = 'C001';


INSERT INTO employee_code_seq (seq_number,department) values (0002,'C001');
INSERT INTO employee_code_seq (seq_number,department) values (0002,'D001');
INSERT INTO employee_code_seq (seq_number,department) values (0002,'M001');
INSERT INTO employee_code_seq (seq_number,department) values (0002,'E001');
