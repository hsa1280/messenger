package org.shianhuang.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.shianhuang.messenger.model.Comment;
import org.shianhuang.messenger.service.CommentService;

// put "/" since we want CommentResource to be referred by another path
// which is getCommentResource() in MessageResource class, @Path is optional
@Path("/")
@Consumes(value = {MediaType.APPLICATION_JSON})
@Produces(value = {MediaType.APPLICATION_JSON})
public class CommentResource {
	
	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}
	
	// we can read the messageId from parent path
	@GET
	@Path("/{commentId}")
	public String test2(@PathParam("commentId") long commentId, @PathParam("messageId") long messageId) {
		return "comment id is " + commentId + ", messageId is " + messageId;
	}
	
	// to add a comment to message class, need to create one message first, then add it to that message
	// otherwise, it won't work
	// http://localhost:8080/messenger/webapi/messages/3/comments
	// also, don't forget to add the body for POST and PUT, and set the
	// Content-Type in the header
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment) {
		comment.setId(commentId);
		return commentService.updateComment(messageId, comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		commentService.removeComment(messageId, commentId);
	}
}
