package com.ssafy.apm.chat.service;

import com.ssafy.apm.chat.domain.Chat;

public interface ChatService {
  void addChat(String content);
  Chat getChatList();
}
