package org.shianhuang.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.shianhuang.messenger.model.Message;
import org.shianhuang.messenger.service.MessageService;


@Path("/messages")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON})
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	// this is the default behavior when accessing "/messages"
//	@GET
//	public List<Message> getMessages() {
//		return messageService.getAllMessages();
//	}
	
	// this is for playing with query params
	// http://localhost:8080/messenger/webapi/messages?start=0&size=1
	// http://localhost:8080/messenger/webapi/messages?year=2017
	@GET
	public List<Message> getMessages(@QueryParam(value = "year") int year, 
									 @QueryParam(value = "start") int start,
									 @QueryParam(value = "size") int size) {
		if (year > 0)
			return messageService.getAllMessagesForYear(year);
		
		if (start >= 0 && size > 0)
			return messageService.getAllMessagesPagination(start, size);
		
		return messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
//		return Response.status(Status.CREATED)
//				.entity(newMessage)
//				.build();
		
		// this will return the message created with status code and header
		return Response.created(uri)
				.entity(newMessage)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id) {
		return messageService.getMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
}
