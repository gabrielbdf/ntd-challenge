




INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (1, 10, 'ADD', 'args.collect{it.toLong()}.sum().toString()') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (2, 20, 'SUBTRACT', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a - b } ?: 0)') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (3, 30, 'MULTIPLY', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a * b } ?: 0)') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (4, 40, 'DIVIDE', 'String.valueOf(args.collect { it.toLong() }.inject { a, b -> a / b } ?: 0)') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (5, 50, 'SQRT', 'String.valueOf(Math.sqrt(args[0].toDouble()))') ON CONFLICT (id) DO NOTHING;
INSERT INTO operation (id, cost, operation, groovy_expression) VALUES (6, 50, 'RAMDOM_STRING', '') ON CONFLICT (id) DO NOTHING;