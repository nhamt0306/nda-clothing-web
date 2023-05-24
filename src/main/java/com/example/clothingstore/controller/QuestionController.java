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
    public ResponseEntity<?> openAIQuestionResponse(@RequestBody QuestionDTO questionDTO)  {
       OpenAiService openAiService = new OpenAiService(apiKey);
       String prompt = buildPrompt();

       final List<ChatMessage> messages = new ArrayList<>();
       final ChatMessage systemMessage = new ChatMessage(
               ChatMessageRole.SYSTEM.value(),
               prompt
       );
       final ChatMessage userMessage = new ChatMessage(
               ChatMessageRole.USER.value(),
               questionDTO.getQuestion()
       );

       messages.add(systemMessage);
       messages.add(userMessage);

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

   private String buildPrompt() {
       String promptStart = "You are an assistant Q&A bot create by ADNCloth, a clothing shop. \n" +
               "Your role is to answer a question that the customer asks base on the provided json only. " +
               "Vietnamese answers only. " +
               "If the question is not fully clothing-related, gently ask them to try again.\n" +
               "This is the json array of the store information of our product." +
               " \"sold\" is the number of people have bought the product," +
               " \"quantity\" is the number of products in stock," +
               " \"color\" is the color of the product in hex color, you must convert hex to color name, " +
               " \"name\" is the name of the product," +
               " \"size\" is the product size," +
               " . \n";

       String json = "[\n" +
               "    {\n" +
               "        \"name\": \"Áo giữ nhiệt nam Modal Ultra Warm - mặc là ấm, thoáng khí\",\n" +
               "        \"price\": 179000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 50,\n" +
               "        \"quantity\": 10,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Thun Cổ Tròn Đơn Giản Y Nguyên Bản Ver77\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"186287\",\n" +
               "        \"sold\": 50,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Thun Cổ Tròn Đơn Giản Y Nguyên Bản Ver121\",\n" +
               "        \"price\": 179000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 3,\n" +
               "        \"quantity\": 77,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Thun Cổ Tròn Tối Giản M2\",\n" +
               "        \"price\": 149000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 69,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần Dài Vải Đơn Giản Y Nguyên Bản Ver10\",\n" +
               "        \"price\": 249000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"114abd\",\n" +
               "        \"sold\": 40,\n" +
               "        \"quantity\": 20,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần Tây Đơn Giản Y Nguyên Bản Ver26\",\n" +
               "        \"price\": 249000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần Tây Tối Giản HG11\",\n" +
               "        \"price\": 249000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 3,\n" +
               "        \"quantity\": 67,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần Tây Tối Giản HG17\",\n" +
               "        \"price\": 299000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 89,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần Tây Tối Giản HG10\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần short nam thể thao 5 New Ultra (có xẻ gấu ngắn & túi khóa sau)\",\n" +
               "        \"price\": 399000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần short nam thể thao Recycle 5 thoáng khí\",\n" +
               "        \"price\": 139000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 40,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần short nam thể thao ProMax-S1 thoáng khí\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"114abd\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 30,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần short nam thể thao Movement 7 co giãn\",\n" +
               "        \"price\": 399000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"186287\",\n" +
               "        \"sold\": 4,\n" +
               "        \"quantity\": 56,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Quần short 5 chạy bộ Power Coolmate (túi sau có khóa)\",\n" +
               "        \"price\": 499000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 20,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo thun oversize REPRESENT - Trắng\",\n" +
               "        \"price\": 149000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 29,\n" +
               "        \"quantity\": 13,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo thun oversize REPRESENT - Đen\",\n" +
               "        \"price\": 139000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 5,\n" +
               "        \"quantity\": 55,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Tank top thể thao nam thoáng khí-Xanh\",\n" +
               "        \"price\": 249000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"186287\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 60,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Tank top thể thao nam thoáng khí\",\n" +
               "        \"price\": 139000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Tank top thể thao nam Dri-Breathe thoáng mát tối đa\",\n" +
               "        \"price\": 159000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Sơ Mi Tay Ngắn Sợi Poly Đơn Giản Y Nguyên Bản Ver54\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 80,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Khoác Classic Tối Giản Ver6\",\n" +
               "        \"price\": 179000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 69,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Sơ Mi Cổ Lãnh Tụ Sợi Rayon Đơn Giản Y Nguyên Bản Ver28\",\n" +
               "        \"price\": 189000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 89,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Khoác Classic Đơn Giản Y Nguyên Bản Ver57\",\n" +
               "        \"price\": 299000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 10,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Khoác Hoodie Đơn Giản Thần Cổ Đại Horus Ver4\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 30,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Khoác Hoodie Zipper Đơn Giản Y Nguyên Bản Ver64\",\n" +
               "        \"price\": 219000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 50,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Hoodie nam Daily Wear (mũ trùm có dây rút)\",\n" +
               "        \"price\": 199000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"f1f1f1\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 90,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Hoodie oversize 84RISING LOGO TYPOGRAPHY HOODIE GREY\",\n" +
               "        \"price\": 229000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 0,\n" +
               "        \"quantity\": 100,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Hoodie oversize YOUTH CULTURE\",\n" +
               "        \"price\": 189000,\n" +
               "        \"size\": 1,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 69,\n" +
               "    },\n" +
               "    {\n" +
               "        \"name\": \"Áo Hoodie oversize YOUTH CULTURE\",\n" +
               "        \"price\": 189000,\n" +
               "        \"size\": 2,\n" +
               "        \"color\": \"000\",\n" +
               "        \"sold\": 1,\n" +
               "        \"quantity\": 69,\n" +
               "    }\n" +
               "]";
       return promptStart + json;
   }
}
