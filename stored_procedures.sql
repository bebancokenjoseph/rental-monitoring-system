-- ============================================
-- RENTAL MONITORING SYSTEM
-- Stored Procedures & Indexes
-- Database Engineer: Ken (bebancokenjoseph)
-- ============================================

USE rental_db;

-- ============================================
-- INDEXES FOR PERFORMANCE
-- ============================================
CREATE INDEX idx_status ON rentals(status);
CREATE INDEX idx_customer_name ON rentals(customer_name);
CREATE INDEX idx_return_date ON rentals(return_date);

-- ============================================
-- STORED PROCEDURE: Auto-update overdue status
-- ============================================
DELIMITER $$

CREATE PROCEDURE update_overdue_rentals()
BEGIN
    UPDATE rentals
    SET status = 'Overdue'
    WHERE return_date < CURDATE()
      AND status = 'Active';
END$$

DELIMITER ;

-- ============================================
-- EVENT: Run procedure daily automatically
-- ============================================
SET GLOBAL event_scheduler = ON;

CREATE EVENT auto_update_overdue
ON SCHEDULE EVERY 1 DAY
STARTS CURRENT_TIMESTAMP
DO CALL update_overdue_rentals();
