-- ============================================
-- RENTAL MONITORING SYSTEM
-- SQL Views for Reporting
-- Database Engineer: Ken (bebancokenjoseph)
-- ============================================

USE rental_db;

-- View 1: All active rentals
CREATE VIEW view_active_rentals AS
SELECT rental_id, customer_name, item_name, rental_date, return_date, amount
FROM rentals
WHERE status = 'Active';

-- View 2: Overdue rentals with days overdue
CREATE VIEW view_overdue_rentals AS
SELECT rental_id, customer_name, item_name, return_date,
       DATEDIFF(CURDATE(), return_date) AS days_overdue,
       amount
FROM rentals
WHERE status = 'Overdue';

-- View 3: Revenue summary
CREATE VIEW view_revenue_summary AS
SELECT 
    COUNT(*) AS total_rentals,
    SUM(amount) AS total_revenue
FROM rentals
WHERE status = 'Returned';
