package br.com.volvo.api;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.volvo.DepartmentService;
import br.com.volvo.UserService;
import br.com.volvo.api.data.User;

@Path(value = "user")
public class UserResource {

	private final UserService userService;
	private final DepartmentService departmentService;

	@Inject
	public UserResource(UserService userService, DepartmentService departmentService) {
		this.userService = userService;
		this.departmentService = departmentService;
	}

	@GET
	@Path(value = "retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") Long id) {
		return userService.retrieve(id);
	}
	
	@GET
	@Path(value = "list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> listUsers() {
		return userService.list();
	}

    @GET
    @Path(value = "delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") Long id) {
        userService.delete(id);

        return Response.status(Status.OK).entity("user has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }
	
	@GET
	@Path(value = "update/{id}/{name}/{description}/{department}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") Long id, @PathParam("name") String name,
			@PathParam("description") String description, @PathParam("department") Long department) {
		User user = new User();
		
		user.setId(id);
		user.setName(name);
		user.setDescription(description);
		
		if (department != null) {
			user.setDepartment(departmentService.retrieve(department));
		}
		
		userService.save(user);
		
		return Response.status(Status.OK).entity("user has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path(value = "save/{name}/{description}/{department}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveUser(@PathParam("name") String name,
			@PathParam("description") String description, @PathParam("department") Long department) {
		User user = new User();
		
		user.setName(name);
		user.setDescription(description);
		
		if (department != null) {
			user.setDepartment(departmentService.retrieve(department));
		}
		
		userService.save(user);
		
		return Response.status(Status.OK).entity("user has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
}
