-- Insert users first
-- INSERT INTO app_user (id, username, password, provider_id, provider_type)
-- VALUES
--     (1, 'aarav', 'password123', 'GOOGLE', 'GOOGLE'),
--     (2, 'diya', 'password123', 'GOOGLE', 'GOOGLE'),
--     (3, 'dishant', 'password123', 'GOOGLE', 'GOOGLE'),
--     (4, 'neha', 'password123', 'GOOGLE', 'GOOGLE'),
--     (5, 'kabir', 'password123', 'GOOGLE', 'GOOGLE');

INSERT INTO patient_tbl (id, name, gender, birth_date, email, blood_group,created_at)
VALUES
    (1, 'Aarav Sharma', 'MALE', '1990-05-10', 'aarav.sharma@example.com', 'O_POSITIVE', NOW()),
    (2, 'Diya Patel', 'FEMALE', '1995-08-20', 'diya.patel@example.com', 'A_POSITIVE', NOW()),
    (3, 'Dishant Verma', 'MALE', '1988-03-15', 'dishant.verma@example.com', 'A_POSITIVE', NOW()),
    (4, 'Neha Iyer', 'FEMALE', '1992-12-01', 'neha.iyer@example.com', 'AB_POSITIVE', NOW()),
    (5, 'Kabir Singh', 'MALE', '1993-07-11', 'kabir.singh@example.com', 'O_POSITIVE', NOW()),

    (6, 'Priya Yadav', 'FEMALE', '1985-04-25', 'priya.yadav@example.com', 'B_NEGATIVE', NOW()),
    (7, 'Rohan Mehra', 'MALE', '1991-11-03', 'rohan.mehra@example.com', 'A_NEGATIVE', NOW()),
    (8, 'Anjali Roy', 'FEMALE', '1989-01-14', 'anjali.roy@example.com', 'O_POSITIVE', NOW()),
    (9, 'Siddharth Nair', 'MALE', '1996-09-29', 'siddharth.nair@example.com', 'AB_NEGATIVE', NOW()),
    (10, 'Tanya Gupta', 'FEMALE', '1994-06-19', 'tanya.gupta@example.com', 'B_POSITIVE', NOW()),

    (11, 'Vikas Jain', 'MALE', '1987-02-07', 'vikas.jain@example.com', 'A_POSITIVE', NOW()),
    (12, 'Zoya Ali', 'FEMALE', '1998-08-01', 'zoya.ali@example.com', 'O_NEGATIVE', NOW()),
    (13, 'Rahul Khanna', 'MALE', '1983-05-18', 'rahul.khanna@example.com', 'A_NEGATIVE', NOW()),
    (14, 'Misha Kapoor', 'FEMALE', '1997-10-10', 'misha.kapoor@example.com', 'B_POSITIVE', NOW()),
    (15, 'Gaurav Das', 'MALE', '1990-03-22', 'gaurav.das@example.com', 'AB_POSITIVE', NOW()),

    (16, 'Sneha Varma', 'FEMALE', '1992-07-05', 'sneha.varma@example.com', 'O_POSITIVE', NOW()),
    (17, 'Arjun Reddy', 'MALE', '1986-12-12', 'arjun.reddy@example.com', 'A_POSITIVE', NOW()),
    (18, 'Isha Singh', 'FEMALE', '1999-04-03', 'isha.singh@example.com', 'B_NEGATIVE', NOW()),
    (19, 'Kunal Menon', 'MALE', '1984-09-17', 'kunal.menon@example.com', 'AB_NEGATIVE', NOW()),
    (20, 'Lina George', 'FEMALE', '1993-01-28', 'lina.george@example.com', 'O_NEGATIVE', NOW()),

    (21, 'Nikhil Sen', 'MALE', '1988-10-09', 'nikhil.sen@example.com', 'A_POSITIVE', NOW()),
    (22, 'Shreya Desai', 'FEMALE', '1996-06-15', 'shreya.desai@example.com', 'B_POSITIVE', NOW()),
    (23, 'Tarun Kumar', 'MALE', '1991-03-08', 'tarun.kumar@example.com', 'O_POSITIVE', NOW()),
    (24, 'Urmi Shah', 'FEMALE', '1985-11-20', 'urmi.shah@example.com', 'A_NEGATIVE', NOW()),
    (25, 'Varun Bhat', 'MALE', '1994-02-27', 'varun.bhat@example.com', 'AB_POSITIVE', NOW()),
    (26, 'Yashika Puri', 'FEMALE', '1997-07-30', 'yashika.puri@example.com', 'B_NEGATIVE', NOW());

INSERT INTO address_tbl (id, patient_id, street, city, state, zip_code)
VALUES
    (1, 1, 'MG Road', 'Indore', 'MP', '452001'),
    (2, 2, 'Ring Road', 'Ahmedabad', 'GJ', '380001'),
    (3, 3, 'Sector 22', 'Chandigarh', 'CH', '160022'),
    (4, 4, 'Baner Road', 'Pune', 'MH', '411045'),
    (5, 5, 'Gachibowli', 'Hyderabad', 'TS', '500032'),

    (6, 6, 'Anna Nagar', 'Chennai', 'TN', '600040'),
    (7, 7, 'Vashi', 'Navi Mumbai', 'MH', '400703'),
    (8, 8, 'Rajaji Nagar', 'Bengaluru', 'KA', '560010'),
    (9, 9, 'Salt Lake', 'Kolkata', 'WB', '700064'),
    (10, 10, 'Civil Lines', 'Delhi', 'DL', '110054'),

    (11, 11, 'Park Street', 'Kolkata', 'WB', '700016'),
    (12, 12, 'Churchgate', 'Mumbai', 'MH', '400020'),
    (13, 13, 'Jubilee Hills', 'Hyderabad', 'TS', '500033'),
    (14, 14, 'New Palasia', 'Indore', 'MP', '452001'),
    (15, 15, 'Sector 62', 'Noida', 'UP', '201301'),

    (16, 16, 'Gandhi Road', 'Coimbatore', 'TN', '641001'),
    (17, 17, 'Indira Nagar', 'Lucknow', 'UP', '226016'),
    (18, 18, 'Banjara Hills', 'Hyderabad', 'TS', '500034'),
    (19, 19, 'Velachery', 'Chennai', 'TN', '600042'),
    (20, 20, 'Saket', 'Delhi', 'DL', '110017'),

    (21, 21, 'Kothrud', 'Pune', 'MH', '411038'),
    (22, 22, 'Maninagar', 'Ahmedabad', 'GJ', '380008'),
    (23, 23, 'Hinjewadi', 'Pune', 'MH', '411057'),
    (24, 24, 'Adyar', 'Chennai', 'TN', '600020'),
    (25, 25, 'BTM Layout', 'Bengaluru', 'KA', '560076'),
    (26, 26, 'Piplani', 'Bhopal', 'MP', '462022');

INSERT INTO visit_tbl (id, patient_id, visit_date, reason)
VALUES
    (1, 1, NOW() - INTERVAL '10 days', 'General Checkup'),
    (2, 1, NOW() - INTERVAL '2 days', 'Fever'),

    (3, 2, NOW() - INTERVAL '8 days', 'Blood Test'),

    (4, 3, NOW() - INTERVAL '15 days', 'Headache Treatment'),
    (5, 3, NOW() - INTERVAL '1 day', 'Follow-up'),

    (6, 4, NOW() - INTERVAL '7 days', 'Skin Allergy'),

    (7, 5, NOW() - INTERVAL '12 days', 'X-Ray'),

    (8, 6, NOW() - INTERVAL '5 days', 'Diabetes Check'),

    (9, 7, NOW() - INTERVAL '9 days', 'Back Pain'),

    (10, 8, NOW() - INTERVAL '20 days', 'Thyroid Checkup'),
    (11, 8, NOW() - INTERVAL '3 days', 'Medication Adjustment'),

    (12, 9, NOW() - INTERVAL '14 days', 'Eye Checkup'),

    (13, 10, NOW() - INTERVAL '11 days', 'Dental Cleaning');

INSERT INTO insurance_tbl (id, provider_name, policy_number)
VALUES
    (1, 'LIC Health Plus', 'LIC-HP-443211'),
    (2, 'Max Bupa MediCare', 'MB-MC-882120'),
    (3, 'HDFC Ergo Shield', 'HDFC-SH-990723'),
    (4, 'Star Health Premium', 'STAR-PR-567812'),
    (5, 'Aditya Birla Active Care', 'AB-AC-341221');
