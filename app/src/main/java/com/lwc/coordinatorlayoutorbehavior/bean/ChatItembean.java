package com.lwc.coordinatorlayoutorbehavior.bean;

/**
 * Created by lingwancai on
 * 2018/9/19 16:09
 */
public class ChatItembean {
    public ChatItembean(int chatId, String content) {
        this.chatId = chatId;
        this.content = content;
    }
    int chatId;

    String content;

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
