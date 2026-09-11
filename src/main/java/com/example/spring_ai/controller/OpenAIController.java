package com.example.spring_ai.controller;

import com.example.spring_ai.service.OpenAIService;
import model.Answer;
import model.GetCapitalWithSchemaFormat;
import model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    /*
    Retrieves response from OpenAI LLM
     */
    @PostMapping("/ask")
    public Answer getAnswers(@RequestBody Question question){
        return openAIService.getAnswers(question.question());
    }

    /*
    Retrieves capital from OpenAI LLM using prompt templates
    */
    @PostMapping("/capital")
    public Answer getCapital(@RequestBody Question question){
        return openAIService.getCapital(question.question());
    }

    /*
    Retrieves capital from OpenAI LLM using prompt templates and feeds system input/suggestions to llm to refine the output
    */
    @PostMapping("/refinedAnswerForCapital")
    public Answer getRefinedAnswerForCapital(@RequestBody Question question){
        return openAIService.getRefinedAnswerForCapital(question.question());
    }

    /*
        Retrieves capital from OpenAI LLM using prompt templates and as per the response schema format defined. This is primarily helpful for programmatic exchange
    */
    @PostMapping("/capitalWithSchemaFormat")
    public GetCapitalWithSchemaFormat getCapitalWithSchemaFormat(@RequestBody Question question){
        return openAIService.getCapitalWithSchemaFormat(question.question());
    }

    /*
        Retrieves capital with tourism details from OpenAI LLM using user prompt templates and system prompt to let llm impersonate role of tourist guide and share details.
    */
    @PostMapping("/capitalWithTourismDetails")
    public Answer capitalWithTourismDetails(@RequestBody Question question){
        return openAIService.getCapitalWithTourismDetails(question.question());
    }


}
