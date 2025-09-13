-- ============================
-- Create table for tracking order sequence
-- ============================

CREATE TABLE IF NOT EXISTS OrderSequence (
    yymm CHAR(4) PRIMARY KEY,
    current_number INT NOT NULL
);

-- ============================
-- Create stored procedure to generate unique order ID (YYMMNNN)
-- ============================

DELIMITER $$

CREATE PROCEDURE GenerateBranchID(OUT generated_id VARCHAR(20))
BEGIN
    DECLARE current_yymm CHAR(4);
    DECLARE next_number INT;
    DECLARE next_number_str CHAR(3);

    -- Get current year and month as YYMM
    SET current_yymm = DATE_FORMAT(CURDATE(), '%y%m');

    -- Try to increment existing counter
    UPDATE OrderSequence
    SET current_number = current_number + 1
    WHERE yymm = current_yymm;

    -- If no row was updated, insert a new row starting from 1
    IF ROW_COUNT() = 0 THEN
        INSERT INTO OrderSequence (yymm, current_number)
        VALUES (current_yymm, 1);
        SET next_number = 1;
    ELSE
        -- Get the updated number
        SELECT current_number INTO next_number
        FROM OrderSequence
        WHERE yymm = current_yymm;
    END IF;

    -- Format number and concatenate with YYMM
    SET next_number_str = LPAD(next_number, 2, '0');
    SET generated_id = CONCAT(current_yymm, next_number_str);
END$$

DELIMITER ;

-- ============================
-- Optional: Call to test the procedure
-- ============================

-- CALL GenerateBranchID(@new_id);
-- SELECT @new_id AS OrderID;
