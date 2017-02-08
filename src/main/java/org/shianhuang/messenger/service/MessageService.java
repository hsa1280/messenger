package org.shianhuang.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.shianhuang.messenger.database.DatabaseClass;
import org.shianhuang.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1l, new Message(1, "Hello World", "shian"));
		messages.put(2l, new Message(2, "Hello Jersey", "shian"));
	}
	
	public List<Message> getAllMessages() {
		
		return new ArrayList<Message>(messages.values());
	}
	
	public Message getMessage(long id) {
		return messages.get(id);
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		
		return message;
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPagination(int start, int size) {
		List<Message> list = new ArrayList<>(messages.values());
		if (start + size > list.size())
			return new ArrayList<Message>();
		
		return list.subList(start, start + size);
	}
	
	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		
		messages.put(message.getId(), message);
		
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
