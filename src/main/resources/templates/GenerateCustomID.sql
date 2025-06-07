DROP PROCEDURE IF EXISTS GenerateCustomID;

DELIMITER $$

CREATE PROCEDURE GenerateCustomID(OUT generatedID VARCHAR(20))
BEGIN
    DECLARE currentDate VARCHAR(6);
    DECLARE sequenceNumber INT;

    -- Get the current date in YYMMDD format
    SET currentDate = DATE_FORMAT(CURDATE(), '%y%m%d');

    -- Retrieve the current sequence number for the day
    SELECT IFNULL(MAX(CAST(SUBSTRING(id, 9, 4) AS UNSIGNED)), 0) + 1
    INTO sequenceNumber
    FROM `answer_info`
    WHERE id LIKE CONCAT('HF', currentDate, '%');

    -- Format the sequence number to be 4 digits
    SET generatedID = CONCAT('HF', currentDate, LPAD(sequenceNumber, 4, '0'));
END$$

DELIMITER ;






SELECT question.* FROM question_info question WHERE  Auto_Generate is not null;
``


SELECT a.answerInfo_Id, a.qTag, a.ansValue
FROM answers a
INNER JOIN answer_info ans ON ans.id = a.answerInfo_Id
WHERE a.ansValue IN (1)
  AND ans.status = 0
  AND ans.delete_Status != 1;
  
  
  "SELECT a.answerInfo_Id, a.qTag, a.ansValue\r\n"
		+ " FROM answers a INNER JOIN answer_info ans ON ans.id = a.answerInfo_Id\r\n"
		+ "WHERE  a.qTag IN (:qTags) AND  a.ansValue IN (:ansValues) AND ans.status=0 AND ans.delete_Status !=1
  
  
  
  
  
  
  
SELECT question.* FROM question_info question WHERE Auto_Generate IS NOT NULL AND TRIM(Auto_Generate) != '';
  
