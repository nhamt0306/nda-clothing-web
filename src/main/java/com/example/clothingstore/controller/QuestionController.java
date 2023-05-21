package com.example.clothingstore.controller;

import com.example.clothingstore.dto.QuestionDTO;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RequestMapping
@RestController
public class QuestionController {
   @Value("${openai.api.key}")
   private String apiKey;
   
   @PostMapping("/questions")
    public ResponseEntity<?> openAIQuestionResponse(@RequestBody QuestionDTO questionDTO) throws IOException {
       OpenAiService openAiService = new OpenAiService(apiKey);
       String prompt = buildPrompt(questionDTO.getQuestion());

       final List<ChatMessage> messages = new ArrayList<>();
       final ChatMessage systemMessage = new ChatMessage(
               ChatMessageRole.SYSTEM.value(),
               prompt
       );

       messages.add(systemMessage);

       ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
               .builder()
               .model("gpt-3.5-turbo")
               .messages(messages)
               .n(1)
               .maxTokens(350)
               .logitBias(new HashMap<>())
               .build();

      ChatCompletionChoice choice = openAiService
              .createChatCompletion(chatCompletionRequest)
              .getChoices()
              .get(0);

       return ResponseEntity.ok(choice.getMessage());
   }

   private String buildPrompt(String question) throws IOException {
       String promptStart = "You are an assistant Q&A bot create by ADNCloth, a clothing shop. \n" +
               "Your role is to answer a question that customer ask. \n" +
               "Only answer in Vietnamese";

       String promptEnd = "\n Here is the question: " + question;
       return promptStart + promptEnd;
   }
}
