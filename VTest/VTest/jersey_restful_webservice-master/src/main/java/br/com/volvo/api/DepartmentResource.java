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
import br.com.volvo.api.data.Department;

@Path(value = "department")
public class DepartmentResource {

	private final DepartmentService departmentService;

	@Inject
	public DepartmentResource(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@GET
	@Path(value = "retrieve/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Department getDepartment(@PathParam("id") Long id) {
		return departmentService.retrieve(id);
	}
	
	@GET
	@Path(value = "list")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Department> listDepartments() {
		return departmentService.list();
	}

    @GET
    @Path(value = "delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteDepartment(@PathParam("id") Long id) {
        departmentService.delete(id);

        return Response.status(Status.OK).entity("department has been successfully deleted").type(MediaType.APPLICATION_JSON).build();
    }
	
	@GET
	@Path(value = "update/{id}/{name}/{description}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateDepartment(@PathParam("id") Long id, @PathParam("name") String name, @PathParam("description") String description) {
		Department department = new Department();
		
		department.setId(id);
		department.setName(name);
		department.setDescription(description);
		
		departmentService.save(department);
		
		return Response.status(Status.OK).entity("department has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Path(value = "save/{name}/{description}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response saveDepartment(@PathParam("name") String name, @PathParam("description") String description) {
		Department department = new Department();
		
		department.setName(name);
		department.setDescription(description);
		
		departmentService.save(department);
		
		return Response.status(Status.OK).entity("department has been successfully saved").type(MediaType.APPLICATION_JSON).build();
	}
}
