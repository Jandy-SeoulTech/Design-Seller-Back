INSERT INTO company (company_id, name) values
(1, '유상컴퍼니');

INSERT INTO production (production_id, name, company_id, category_id) values
(1, '제작1', 1, 10);

INSERT INTO production_option (production_option_id, name, price, production_id) values
(1, '옵션1', 10000, 1),
(2, '옵션2', 10000, 1),
(3, '옵션3', 10000, 1),
(4, '옵션4', 10000, 1);

INSERT INTO production (production_id, name, company_id, category_id) values
(2, '제작2', 1, 11),
(3, '제작3', 1, 12),
(4, '제작4', 1, 13);
