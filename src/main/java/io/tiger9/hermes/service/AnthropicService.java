package io.tiger9.hermes.service;

import io.tiger9.hermes.dto.*;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;

import org.springframework.ai.image.ImageResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service("anthropic")
public class AnthropicService implements AIService {


    public ChatResponse generateChatCompletion(
            String apiKey,
            ChatCompletionRequest request) {
        ChatModel chatModel = new AnthropicChatModel(new AnthropicApi(apiKey)); //TODO Set other parameters
        ChatClient chatClient = ChatClient.create(chatModel);

        List<Message> messages =
                request.messages()
                        .stream()
                        .map(this::createMessage)
                        .filter(Objects::nonNull)
                        .toList();

        return chatClient.prompt().messages(messages).call().chatResponse();

    }

    @Override
    public ImageResponse generateImage(String apiKey, ImageGenerationRequest request) {
        throw new UnsupportedOperationException("Image generation not supported by Anthropic Claude.");
    }


    @Override
    public AudioTranscriptionResponse transcribeAudio(String apiKey, AudioTranscriptionRequest request) {
        throw new UnsupportedOperationException("Audio transcription not supported by Anthropic");
    }

    @Override
    public Resource generateSpeech(String apiKey, TextToSpeechRequest request) {
        throw new UnsupportedOperationException("Speech generation not supported by Anthropic");
    }

    /*
    @Override
    public Resource generateSpeech(TextToSpeechRequest request) {
        throw new UnsupportedOperationException("Text to speech not supported by Anthropic");
    }



     */
} 
