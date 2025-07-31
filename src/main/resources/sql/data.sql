INSERT INTO users (username, password, roles, full_name, balance) VALUES
                                                                      ('alice', '$2a$12$jbqce7693JI0soYQRuoC1OsVJmzHWKIasQYYTILkfvUOwltTAo8/i', 'ROLE_USER', 'Alice Johnson', 1000.00),
                                                                      ('bob', '$2a$12$jbqce7693JI0soYQRuoC1OsVJmzHWKIasQYYTILkfvUOwltTAo8/i', 'ROLE_USER', 'Bob Smith', 500.50),
                                                                      ('boss', '$2a$10$XlZRjOz7WI1iKmLjqvTSOeC82ZfnFwwdzjlg5cGVr7.Y.iJ2VB4hC', 'ROLE_ADMIN', 'Adam Mac', 200.50);

INSERT INTO transactions (user_id, amount, type, description) VALUES
                                                                  (1, 200.00, 'CREDIT', 'Initial top-up'),
                                                                  (1, 150.00, 'DEBIT', 'Purchase A'),
                                                                  (2, 300.00, 'CREDIT', 'Initial top-up'),
                                                                  (3, 400.00, 'CREDIT', 'Initial top-up');