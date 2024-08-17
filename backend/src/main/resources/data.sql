

TRUNCATE TABLE operation RESTART IDENTITY CASCADE;
TRUNCATE TABLE users RESTART IDENTITY CASCADE; 
INSERT INTO operation (cost, operation, groovy_expression) VALUES (10, 'ADD', 'args.collect{it.toLong()}.sum().toString()');
INSERT INTO operation (cost, operation, groovy_expression) VALUES (20, 'SUBTRACT', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a - b } ?: 0)');
INSERT INTO operation (cost, operation, groovy_expression) VALUES (30, 'MULTIPLY', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a * b } ?: 0)');
INSERT INTO operation (cost, operation, groovy_expression) VALUES (40, 'DIVIDE', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a / b } ?: 0)');
INSERT INTO operation (cost, operation, groovy_expression) VALUES (50, 'SQRT', 'String.valueOf(Math.sqrt(args[0].toDouble()))');
INSERT INTO operation (cost, operation, groovy_expression) VALUES (50, 'RAMDOM_STRING', '');