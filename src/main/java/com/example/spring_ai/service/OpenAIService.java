package com.example.spring_ai.service;

import model.Answer;
import model.GetCapitalWithSchemaFormat;

public interface OpenAIService {

    public Answer getAnswers(String question);

    public Answer getCapital(String question);

    public Answer getRefinedAnswerForCapital(String question);

    public GetCapitalWithSchemaFormat getCapitalWithSchemaFormat(String question);

    public Answer getCapitalWithTourismDetails(String question);
}
