-- ============================================
-- RENTAL MONITORING SYSTEM
-- Database Schema (3 Tables)
-- Owner: Ken (Database Engineer)
-- ============================================

CREATE DATABASE IF NOT EXISTS rental_db;
USE rental_db;

-- Drop in reverse order (child first, then parent)
DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS rentals;
DROP TABLE IF EXISTS customers;

-- ============================================
-- TABLE 1: customers
-- ============================================
CREATE TABLE customers (
    customer_id     INT AUTO_INCREMENT PRIMARY KEY,
    full_name       VARCHAR(100) NOT NULL,
    contact_number  VARCHAR(15)  NOT NULL,
    email           VARCHAR(100),
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================
-- TABLE 2: rentals (linked to customers via FK)
-- ============================================
CREATE TABLE rentals (
    rental_id       INT AUTO_INCREMENT PRIMARY KEY,
    customer_id     INT NOT NULL,
    item_name       VARCHAR(100) NOT NULL,
    rental_date     DATE NOT NULL,
    return_date     DATE NOT NULL,
    status          ENUM('Active', 'Returned', 'Overdue') DEFAULT 'Active',
    amount          DECIMAL(10, 2) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

-- ============================================
-- TABLE 3: payments (linked to rentals via FK)
-- ============================================
CREATE TABLE payments (
    payment_id      INT AUTO_INCREMENT PRIMARY KEY,
    rental_id       INT NOT NULL,
    amount_paid     DECIMAL(10, 2) NOT NULL,
    payment_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_method  ENUM('Cash', 'GCash', 'Bank Transfer') NOT NULL,
    FOREIGN KEY (rental_id) REFERENCES rentals(rental_id)
);

-- ============================================
-- Sample Data
-- ============================================
INSERT INTO customers (full_name, contact_number, email) VALUES
    ('Juan dela Cruz',  '09171234567', 'juan@email.com'),
    ('Maria Santos',    '09281234567', 'maria@email.com'),
    ('Pedro Reyes',     '09391234567', 'pedro@email.com');

INSERT INTO rentals (customer_id, item_name, rental_date, return_date, status, amount) VALUES
    (1, 'Mountain Bike',       '2025-05-01', '2025-05-05', 'Returned', 500.00),
    (2, 'Tent & Camping Gear', '2025-05-10', '2025-05-15', 'Active',   1200.00),
    (3, 'Kayak',               '2025-04-20', '2025-04-25', 'Overdue',  800.00);

INSERT INTO payments (rental_id, amount_paid, payment_method) VALUES
    (1, 500.00,  'Cash'),
    (2, 600.00,  'GCash'),
    (3, 800.00,  'Bank Transfer');
