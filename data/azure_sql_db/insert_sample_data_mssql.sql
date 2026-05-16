-- SAMPLE DATA for DEV purposes

USE [dev-db-baemtli];
GO

-- 1. DISABLE ALL CONSTRAINTS (Azure-safe alternative to sp_MSforeachtable)
DECLARE @DisableSql NVARCHAR(MAX) = N'';
SELECT @DisableSql += 'ALTER TABLE ' + QUOTENAME(s.name) + '.' + QUOTENAME(t.name) + ' NOCHECK CONSTRAINT ALL; '
FROM sys.tables t
         JOIN sys.schemas s ON t.schema_id = s.schema_id;

EXEC sp_executesql @DisableSql;
GO

-- DELETE ALL DATA
DELETE FROM [choreassignment];
DELETE FROM [monthassignment];
DELETE FROM [trainee];
DELETE FROM [user];
DELETE FROM [team];
DELETE FROM [chorecategory];
DELETE FROM [month];
DELETE FROM [workday];

-- Optional: Identity-Spalten (Auto-Inkrement) wieder bei 1 starten lassen
-- Note: DBCC CHECKIDENT with 0 means the next insert will be 1
DBCC CHECKIDENT ('[team]', RESEED, 0);
DBCC CHECKIDENT ('[trainee]', RESEED, 0);
DBCC CHECKIDENT ('[chorecategory]', RESEED, 0);

-- 2. RE-ENABLE ALL CONSTRAINTS (Azure-safe alternative to sp_MSforeachtable)
DECLARE @EnableSql NVARCHAR(MAX) = N'';
SELECT @EnableSql += 'ALTER TABLE ' + QUOTENAME(s.name) + '.' + QUOTENAME(t.name) + ' WITH CHECK CHECK CONSTRAINT ALL; '
FROM sys.tables t
         JOIN sys.schemas s ON t.schema_id = s.schema_id;

EXEC sp_executesql @EnableSql;
GO

-- 3. Create 4 teams
SET IDENTITY_INSERT [team] ON;

INSERT INTO [team] ([ID_Team], [Name]) VALUES
                                           (1, 'Alpha Dragons'),
                                           (2, 'Beta Bytes'),
                                           (3, 'Gamma Gladiators'),
                                           (4, 'Team with no Trainees');

SET IDENTITY_INSERT [team] OFF;
GO

-- 4. Create trainees
INSERT INTO [trainee] ([FirstName], [LastName], [Team_ID]) VALUES
-- Team 1 (Alpha Dragons)
('Lukas', 'Müller', 1), ('Sophie', 'Meier', 1), ('Julian', 'Schmid', 1), ('Emma', 'Wagner', 1),
('Liam', 'Becker', 1), ('Mia', 'Schulz', 1), ('Noah', 'Hoffmann', 1), ('Lina', 'Schaefer', 1),
('Finn', 'Koch', 1), ('Mila', 'Bauer', 1), ('Leon', 'Richter', 1), ('Ella', 'Klein', 1),
('Emil', 'Wolf', 1), ('Clara', 'Schröder', 1),
-- Team 2 (Beta Bytes)
('Elias', 'Fischer', 2), ('Sara', 'Weber', 2), ('Ben', 'Meyer', 2), ('Lea', 'Wagner', 2),
('Luis', 'Becker', 2), ('Hanna', 'Schulz', 2), ('Paul', 'Hoffmann', 2), ('Anna', 'Schaefer', 2),
('Jonas', 'Koch', 2), ('Marie', 'Bauer', 2), ('Felix', 'Richter', 2), ('Leni', 'Klein', 2),
('Moritz', 'Wolf', 2), ('Amelie', 'Schröder', 2),
-- Team 3 (Gamma Gladiators)
('Matteo', 'Fischer', 3), ('Laura', 'Weber', 3), ('Luca', 'Meyer', 3), ('Lara', 'Wagner', 3),
('David', 'Becker', 3), ('Ida', 'Schulz', 3), ('Simon', 'Hoffmann', 3), ('Frieda', 'Schaefer', 3),
('Anton', 'Koch', 3), ('Nele', 'Bauer', 3), ('Jakob', 'Richter', 3), ('Romy', 'Klein', 3),
('Kilian', 'Wolf', 3), ('Sina', 'Schröder', 3);
GO

-- 5. Create chore categories
INSERT INTO [chorecategory] ([Name], [Description]) VALUES
                                                        ('Küche', 'Abwaschen und Oberflächen reinigen'),
                                                        ('Müll', 'Entsorgung von Papier, Alu und Restmüll'),
                                                        ('Pflanzen', 'Giessen der Büropflanzen');
GO