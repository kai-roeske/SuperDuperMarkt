DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20),
    name VARCHAR(30),
    quality INT,
    delivered DATE
);

INSERT INTO products (type, name, quality, delivered) VALUES
('cheese', 'gouda', 100, '2025-06-10'),
('cheese', 'limburger', 80, '2025-06-07'),
('wine', 'beaujolais', 50, '2025-06-05');
