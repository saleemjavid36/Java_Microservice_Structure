-- INSERT INTO appointment_tbl (appointment_time, reason, doctor_id, patient_id)
-- VALUES
--     ('2025-07-01 10:30:00', 'General Checkup', 1, 2),
--     ('2025-07-02 11:00:00', 'Skin Rash', 2, 2),
--     ('2025-07-03 09:45:00', 'Knee Pain', 3, 3),
--     ('2025-07-04 14:00:00', 'Follow-up Visit', 1, 1),
--     ('2025-07-05 16:15:00', 'Consultation', 2, 4),
--     ('2025-07-06 08:30:00', 'Allergy Treatment', 3, 5);

INSERT INTO appointment_tbl (id,appointment_time, reason, patient_id,doctor_id,status)
VALUES
    (1,'2025-07-01 10:30:00', 'General Checkup',1,2,'CREATED'),
    (2,'2025-07-02 11:00:00', 'Skin Rash',2,4,'CREATED'),
    (3,'2025-07-03 09:45:00', 'Knee Pain',3,3,'CREATED'),
    (4,'2025-07-04 14:00:00', 'Follow-up Visit',4,5,'CREATED'),
    (5,'2025-07-05 16:15:00', 'Consultation',5,6,'CREATED'),
    (6,'2025-07-06 08:30:00', 'Allergy Treatment',6,7,'CREATED');