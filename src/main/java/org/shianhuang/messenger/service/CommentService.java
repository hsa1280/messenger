package org.shianhuang.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.shianhuang.messenger.database.DatabaseClass;
import org.shianhuang.messenger.model.Comment;
import org.shianhuang.messenger.model.Message;

public class CommentService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		return messages.get(messageId).getComments().get(commentId);
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		if (messageId <= 0)
			return null;
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comments.put(comment.getId(), comment);
		
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		return messages.get(messageId).getComments().remove(commentId);
	}
}
