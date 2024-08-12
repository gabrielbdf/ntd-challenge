package com.ntd.project.dto;

import java.util.List;
import java.util.stream.Stream;

import com.ntd.project.service.impl.OperationServiceGroovy;

public enum Operation {

    ADD {
        @Override
        public String calculate(Long... args) {
            return String.valueOf(Stream.of(args).mapToLong(Long::longValue).sum());
        }

        @Override
        public Integer numOfArgs() {
            return 2;
        }
    },
    SUBTRACT {
        @Override
        public String calculate(Long... args) {
            return String.valueOf(Stream.of(args).mapToLong(Long::longValue).reduce((a, b) -> a - b).orElse(0));
        }
        @Override
        public Integer numOfArgs() {
            return 2;
        }
    },
    MULTIPLY {
        @Override
        public String calculate(Long... args) {
            return String.valueOf(Stream.of(args).mapToLong(Long::longValue).reduce((a, b) -> a * b).orElse(0));
        }
        @Override
        public Integer numOfArgs() {
            return 2;
        }
    },
    DIVIDE {
        @Override
        public String calculate(Long... args) {
            return String.valueOf(Stream.of(args).mapToLong(Long::longValue).reduce((a, b) -> a / b).orElse(0));
        }
        @Override
        public Integer numOfArgs() {
            return 2;
        }
    },
    SQRT {
        @Override
        public String calculate(Long... args) {
            return String.valueOf(Math.sqrt(args[0]));
        }
        @Override
        public Integer numOfArgs() {
            return 1;
        }
    },
    RAMDOM_STRING {
        @Override
        public String calculate(Long... args) {
            return OperationServiceGroovy.getRandomString();
        }
        @Override
        public Integer numOfArgs() {
            return 0;
        }
    };

    Operation() {
    }

    public abstract String calculate(Long... args);

    public abstract Integer numOfArgs();
}
