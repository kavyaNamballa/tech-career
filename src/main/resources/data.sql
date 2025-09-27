INSERT INTO job (id, title, salary_range, min_experience) VALUES
                                                              (1, 'Java Developer', '6-12 LPA', '2'),
                                                              (2, 'Full Stack Engineer', '10-18 LPA', '3'),
                                                              (3, 'Backend Engineer - Spring Boot', '8-15 LPA', '3'),
                                                              (4, '.Net Developer', '5-10 LPA', '2'),
                                                              (5, 'React JS Developer', '6-12 LPA', '2'),
                                                              (6, 'Software Engineer - Java & Spring', '7-14 LPA', '2'),
                                                              (7, 'Senior Backend Engineer', '15-25 LPA', '5'),
                                                              (8, 'Junior Software Engineer', '3-6 LPA', '0'),
                                                              (9, 'API Developer', '7-13 LPA', '3'),
                                                              (10, 'Spring Boot Microservices Developer', '9-16 LPA', '4'),
                                                              (11, 'Web Developer', '4-8 LPA', '1'),
                                                              (12, 'Cloud Application Developer', '12-20 LPA', '4'),
                                                              (13, 'Enterprise Java Engineer', '14-22 LPA', '6'),
                                                              (14, 'System Analyst (.Net)', '6-11 LPA', '3'),
                                                              (15, 'Front-End Engineer - React', '7-12 LPA', '2'),
                                                              (16, 'Software Architect', '20-30 LPA', '8'),
                                                              (17, 'Backend Java Developer', '8-14 LPA', '3'),
                                                              (18, 'Spring Framework Specialist', '10-18 LPA', '4'),
                                                              (19, 'React & Java Fullstack Engineer', '9-17 LPA', '3'),
                                                              (20, 'Associate Developer', '4-7 LPA', '1');

-- Job 1: Java Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (1, 1, 'INTERMEDIATE'),
                                                    (1, 4, 'BEGINNER'),
                                                    (1, 5, 'BEGINNER');

-- Job 2: Full Stack Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (2, 1, 'INTERMEDIATE'),
                                                    (2, 2, 'INTERMEDIATE'),
                                                    (2, 5, 'BEGINNER');

-- Job 3: Backend Engineer - Spring Boot
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (3, 1, 'INTERMEDIATE'),
                                                    (3, 4, 'INTERMEDIATE'),
                                                    (3, 5, 'INTERMEDIATE');

-- Job 4: .Net Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
    (4, 3, 'INTERMEDIATE');

-- Job 5: React JS Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
    (5, 2, 'INTERMEDIATE');

-- Job 6: Software Engineer - Java & Spring
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (6, 1, 'INTERMEDIATE'),
                                                    (6, 4, 'BEGINNER');

-- Job 7: Senior Backend Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (7, 1, 'EXPERT'),
                                                    (7, 4, 'EXPERT'),
                                                    (7, 5, 'EXPERT');

-- Job 8: Junior Software Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (8, 1, 'BEGINNER'),
                                                    (8, 2, 'BEGINNER');

-- Job 9: API Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (9, 1, 'INTERMEDIATE'),
                                                    (9, 5, 'INTERMEDIATE');

-- Job 10: Spring Boot Microservices Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (10, 1, 'INTERMEDIATE'),
                                                    (10, 4, 'INTERMEDIATE'),
                                                    (10, 5, 'INTERMEDIATE');

-- Job 11: Web Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
    (11, 2, 'BEGINNER');

-- Job 12: Cloud Application Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (12, 1, 'INTERMEDIATE'),
                                                    (12, 2, 'INTERMEDIATE'),
                                                    (12, 5, 'INTERMEDIATE');

-- Job 13: Enterprise Java Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (13, 1, 'EXPERT'),
                                                    (13, 4, 'INTERMEDIATE'),
                                                    (13, 5, 'INTERMEDIATE');

-- Job 14: System Analyst (.Net)
INSERT INTO job_skill (job_id, skill_id, level) VALUES
    (14, 3, 'INTERMEDIATE');

-- Job 15: Front-End Engineer - React
INSERT INTO job_skill (job_id, skill_id, level) VALUES
    (15, 2, 'INTERMEDIATE');

-- Job 16: Software Architect
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (16, 1, 'EXPERT'),
                                                    (16, 3, 'INTERMEDIATE'),
                                                    (16, 4, 'EXPERT'),
                                                    (16, 5, 'EXPERT');

-- Job 17: Backend Java Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (17, 1, 'INTERMEDIATE'),
                                                    (17, 4, 'INTERMEDIATE');

-- Job 18: Spring Framework Specialist
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (18, 4, 'EXPERT'),
                                                    (18, 5, 'INTERMEDIATE');

-- Job 19: React & Java Fullstack Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (19, 1, 'INTERMEDIATE'),
                                                    (19, 2, 'INTERMEDIATE');

-- Job 20: Associate Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (20, 1, 'BEGINNER'),
                                                    (20, 2, 'BEGINNER'),
                                                    (20, 3, 'BEGINNER');

-- more skills
INSERT INTO skill (id, name) VALUES
                                 (6, 'Python'),
                                 (7, 'Django'),
                                 (8, 'Angular'),
                                 (9, 'Node.js'),
                                 (10, 'AWS'),
                                 (11, 'Kubernetes'),
                                 (12, 'Docker'),
                                 (13, 'Machine Learning'),
                                 (14, 'Data Science'),
                                 (15, 'SQL'),
                                 (16, 'NoSQL'),
                                 (17, 'Android'),
                                 (18, 'iOS'),
                                 (19, 'DevOps'),
                                 (20, 'C++');

--- Jobs ------
INSERT INTO job (id, title, salary_range, min_experience) VALUES
                                                              (21, 'Python Backend Developer', '7-14 LPA', '2'),
                                                              (22, 'Data Scientist', '12-22 LPA', '3'),
                                                              (23, 'Machine Learning Engineer', '14-25 LPA', '4'),
                                                              (24, 'DevOps Engineer', '10-18 LPA', '3'),
                                                              (25, 'Cloud Engineer - AWS', '11-20 LPA', '3'),
                                                              (26, 'Mobile App Developer (Android)', '6-12 LPA', '2'),
                                                              (27, 'iOS Developer', '6-12 LPA', '2'),
                                                              (28, 'Fullstack Node + React Developer', '8-16 LPA', '3'),
                                                              (29, 'Database Engineer', '7-13 LPA', '3'),
                                                              (30, 'C++ Systems Programmer', '9-18 LPA', '4');

-- Job 21: Python Backend Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (21, 6, 'INTERMEDIATE'),
                                                    (21, 7, 'BEGINNER'),
                                                    (21, 15, 'INTERMEDIATE');

-- Job 22: Data Scientist
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (22, 6, 'INTERMEDIATE'),
                                                    (22, 14, 'INTERMEDIATE'),
                                                    (22, 15, 'INTERMEDIATE');

-- Job 23: Machine Learning Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (23, 6, 'INTERMEDIATE'),
                                                    (23, 13, 'INTERMEDIATE'),
                                                    (23, 14, 'INTERMEDIATE'),
                                                    (23, 15, 'INTERMEDIATE');

-- Job 24: DevOps Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (24, 10, 'INTERMEDIATE'),
                                                    (24, 11, 'INTERMEDIATE'),
                                                    (24, 12, 'INTERMEDIATE'),
                                                    (24, 19, 'INTERMEDIATE');

-- Job 25: Cloud Engineer - AWS
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (25, 10, 'INTERMEDIATE'),
                                                    (25, 11, 'BEGINNER'),
                                                    (25, 12, 'INTERMEDIATE');

-- Job 26: Mobile App Developer (Android)
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (26, 17, 'INTERMEDIATE'),
                                                    (26, 6, 'BEGINNER');

-- Job 27: iOS Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (27, 18, 'INTERMEDIATE'),
                                                    (27, 6, 'BEGINNER');

-- Job 28: Fullstack Node + React Developer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (28, 9, 'INTERMEDIATE'),
                                                    (28, 2, 'INTERMEDIATE'),
                                                    (28, 15, 'BEGINNER');

-- Job 29: Database Engineer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (29, 15, 'INTERMEDIATE'),
                                                    (29, 16, 'INTERMEDIATE');

-- Job 30: C++ Systems Programmer
INSERT INTO job_skill (job_id, skill_id, level) VALUES
                                                    (30, 20, 'EXPERT'),
                                                    (30, 15, 'INTERMEDIATE');

