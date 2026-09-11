package com.example.spring_ai.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OpenAIServiceImplTest {

    @Autowired
    OpenAIServiceImpl openAIService;

   /* @Test
   void getAnswers() {
        String answer = openAIService.getAnswers("What is the purpose of life?");
        System.out.println("Getting answer from Open AI model");
        System.out.println("=====================================");
        System.out.println(answer);
    }
*/

}