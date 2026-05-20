-- ============================================
-- RENTAL MONITORING SYSTEM
-- Database Schema
-- Database Engineer: Ken (bebancokenjoseph)
-- ============================================

CREATE DATABASE IF NOT EXISTS rental_db;
USE rental_db;

DROP TABLE IF EXISTS rentals;

CREATE TABLE rentals (
    rental_id       INT AUTO_INCREMENT PRIMARY KEY,
    customer_name   VARCHAR(100) NOT NULL,
    item_name       VARCHAR(100) NOT NULL,
    rental_date     DATE NOT NULL,
    return_date     DATE NOT NULL,
    status          ENUM('Active', 'Returned', 'Overdue') DEFAULT 'Active',
    amount          DECIMAL(10, 2) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Sample Data
INSERT INTO rentals (customer_name, item_name, rental_date, return_date, status, amount)
VALUES
    ('Juan dela Cruz', 'Mountain Bike', '2025-05-01', '2025-05-05', 'Returned', 500.00),
    ('Maria Santos', 'Tent & Camping Gear', '2025-05-10', '2025-05-15', 'Active', 1200.00),
    ('Pedro Reyes', 'Kayak', '2025-04-20', '2025-04-25', 'Overdue', 800.00);
