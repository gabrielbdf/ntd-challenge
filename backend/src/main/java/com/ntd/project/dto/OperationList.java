package com.ntd.project.dto;

import java.util.List;

public record OperationList(List<List<String>> operations, UserDetails userDetails) {
}
