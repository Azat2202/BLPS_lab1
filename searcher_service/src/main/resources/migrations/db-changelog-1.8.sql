CREATE OR REPLACE FUNCTION rating_to_int(rating TEXT) RETURNS INTEGER AS $$
BEGIN
RETURN CASE
           WHEN rating = 'ONE' THEN 1
           WHEN rating = 'TWO' THEN 2
           WHEN rating = 'THREE' THEN 3
           WHEN rating = 'FOUR' THEN 4
           WHEN rating = 'FIVE' THEN 5
           ELSE 0
    END;
END;
$$ LANGUAGE plpgsql;