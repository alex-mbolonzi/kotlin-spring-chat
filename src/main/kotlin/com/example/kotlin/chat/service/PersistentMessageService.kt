package com.example.kotlin.chat.service

import com.example.kotlin.chat.mapToViewModel
import com.example.kotlin.chat.repository.ContentType
import com.example.kotlin.chat.repository.Message
import com.example.kotlin.chat.repository.MessageRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.net.URL

@Service
@Primary
class PersistentMessageService(val messageRepository: MessageRepository) : MessageService {

    override fun latest(): List<MessageVM> =
        messageRepository.findLatest()
            .mapToViewModel() // now we can use the mentioned extension on List<Message>

    override fun after(lastMessageId: String): List<MessageVM> =
        messageRepository.findLatest(lastMessageId)
            .mapToViewModel()

    override fun post(message: MessageVM) {
        messageRepository.save(
            with(message) { Message(content, ContentType.PLAIN, sent,
                user.name, user.avatarImageLink.toString()) }
        )
    }
}