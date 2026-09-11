package model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalWithSchemaFormat(@JsonPropertyDescription("This is the capital name") String answer) {
}
