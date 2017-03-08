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

import br.com.volvo.PermissionService;
import br.com.volvo.api.data.Permission;

@Path(value = "permission")
public class PermissionResource {

	private final PermissionService permissionService;

	@Inject
	public PermissionResource(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@GET
	@Path(value = "retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Permission getPermission(@PathParam("id") Long id) {
		return permissionService.retrieve(id);
	}
	
	@GET
	@Path(value = "list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Permission> listPermissions() {
		return permissionService.list();
	}

    @GET
    @Path(value = "delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePermission(@PathParam("id") Long id) {
        permissionService.delete(id);

        return Response.status(Status.OK).entity("permission has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }
	
	@GET
	@Path(value = "update/{id}/{name}/{description}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updatePermission(@PathParam("id") Long id, @PathParam("name") String name, @PathParam("description") String description) {
		Permission permission = new Permission();
		
		permission.setId(id);
		permission.setName(name);
		permission.setDescription(description);
		
		permissionService.save(permission);
		
		return Response.status(Status.OK).entity("permission has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path(value = "save/{name}/{description}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response savePermission(@PathParam("name") String name, @PathParam("description") String description) {
		Permission permission = new Permission();
		
		permission.setName(name);
		permission.setDescription(description);
		
		permissionService.save(permission);
		
		return Response.status(Status.OK).entity("permission has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
}
