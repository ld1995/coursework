package by.ld1995.coursework.exception

class ChatNotFoundException(chatId: Long) :
        RuntimeException("Chat $chatId not found")