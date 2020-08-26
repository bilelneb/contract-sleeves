/*INSERT INTO Company (company_name,Company_Description) VALUES
( 'Enmacc','Energy Trade'),
('Wingas','Gas Distribution'),
('Statkraft','Renewable Energy'),
('OMV','Oil and Gas'),
('Engie','Electric Utility'),
('Vattenfall','Power Company') ;

INSERT INTO Contract (id,COMPANY_NAME1 ,COMPANY_NAME2 ) VALUES
(1,'Enmacc','Wingas'),
(2,'Statkraft','Wingas'),
(3,'Engie','OMV'),
(4,'OMV','Vattenfall');
*/
INSERT INTO Company (company_name,Company_Description) VALUES
( 'A','A'),
('B','B'),
('C','C'),
('D','D'),
('E','E'),
('F','F') ,
('G','G') ,
('H','H') ,
('I','I') ,
('J','J') ,
('K','K') ;
INSERT INTO Contract (id,COMPANY_NAME1 ,COMPANY_NAME2 ) VALUES
(1,'A','B'),
(2,'A','C'),
(3,'A','D'),
(4,'A','E'),
(5,'B','F'),
(6,'C','G'),
(7,'C','H'),
(8,'D','I'),
(9,'D','J'),
(10,'D','K'),
(11,'G','D'),
(12,'G','K');