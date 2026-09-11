package com.example.spring_ai.service;


import model.Answer;
import model.GetCapitalWithSchemaFormat;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("classpath:templates/get_capital_question_prompttemplate.st")
    private Resource getCapitalPrompt;

    @Value("classpath:templates/get_refined_capital_answer_from_llm.st")
    private Resource getCapitalWithRefinedAnswerPrompt;

    @Value("classpath:templates/get_capital_with_schema_format.st")
    private Resource getCapitalWithSchemaFormatPrompt;

    private final ChatModel chatModel;

    public OpenAIServiceImpl(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

   /*
       The method prompts openAI llm and retrieves response in simple string format
    */
    @Override
    public Answer getAnswers(String question) {
        Prompt prompt = new PromptTemplate(question).create();
        ChatResponse chatResponse = chatModel.call(prompt);
        return new Answer(chatResponse.getResult().getOutput().getText());
    }

    /*
       The method uses prompt template to format question in definitive manner and prompts openAI llm to retrieve response in simple format
   */
    @Override
    public Answer getCapital(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",question));

        System.out.println("prompt:"+  prompt);
        ChatResponse chatResponse = chatModel.call(prompt);
        return new Answer(chatResponse.getResult().getOutput().getText());
    }

    /*
         Retrieves capital from OpenAI LLM using prompt templates and feeds system input/suggestions to llm to refine the output
    */
    @Override
    public Answer getRefinedAnswerForCapital(String question) {
        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithRefinedAnswerPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",question));

        System.out.println("prompt:"+  prompt);
        ChatResponse chatResponse = chatModel.call(prompt);
        return new Answer(chatResponse.getResult().getOutput().getText());
    }

    /*
          Retrieves capital from OpenAI LLM using prompt templates and as per the response schema format defined. This is primarily helpful for programmatic exchange
    */
    @Override
    public GetCapitalWithSchemaFormat getCapitalWithSchemaFormat(String question) {

        BeanOutputConverter<GetCapitalWithSchemaFormat> converter = new BeanOutputConverter<>(GetCapitalWithSchemaFormat.class);
        String format = converter.getFormat();

        System.out.println("format:"+  format);

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalWithSchemaFormatPrompt);
        Prompt prompt = promptTemplate.create(Map.of("stateOrCountry",question,"format",format));

        System.out.println("prompt:"+  prompt);
        ChatResponse chatResponse = chatModel.call(prompt);
        return converter.convert(chatResponse.getResult().getOutput().getText());

    }

    /*
        Retrieves capital with tourism details from OpenAI LLM using user prompt templates and system prompt to let llm impersonate role of tourist guide and share details.
    */
    @Override
    public Answer getCapitalWithTourismDetails(String question) {

       String systemMessage = """
        You are a helpful AI assistant.Your role is of a tourism guide.
        You provide every useful and welcoming information about the city that would motivate the user to plan a tour
        """;

        SystemPromptTemplate systemPrompt = new SystemPromptTemplate(systemMessage);
        Message system = systemPrompt.createMessage();

        PromptTemplate promptTemplate = new PromptTemplate(getCapitalPrompt);
        Message userMessage = promptTemplate.createMessage(Map.of("stateOrCountry",question));

        Prompt prompt = new Prompt(List.of(userMessage,system));
        ChatResponse chatResponse = chatModel.call(prompt);
        return new Answer(chatResponse.getResult().getOutput().getText());

    }
}
