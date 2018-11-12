package by.ld1995.coursework.exception

class ChatNotFoundException(val chatId: Long) :
        RuntimeException("Chat ${chatId} not found")