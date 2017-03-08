package br.com.volvo.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import javax.inject.Singleton;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.volvo.DepartmentService;
import br.com.volvo.api.DepartmentResource;
import br.com.volvo.api.data.Department;
import br.com.volvo.api.data.Department;

public class DepartmentResourceTest extends JerseyTest {

	@Mock
	private DepartmentService serviceMock;

	/**
	 * This is executed only once, not before each test.
	 * 
	 * This will enable Mockito Annotations to be used.
	 * This will enable log traffic and message dumping.
	 * This will register the Injectable Provider to the ResourceConfiguration which will
	 * allow for the mock objects and jersey test to be linked.
	 */
	@Override
	protected Application configure() {
		MockitoAnnotations.initMocks(this);
		
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		
		ResourceConfig config = new ResourceConfig(DepartmentResource.class);
		config.register(new InjectableProvider());

		return config;
	}

	/**
	 * Invoke the retrieve department and check the http response is 200.
	 */
	@Test
	public void testDepartmentRetrieveResponse() {
		
		when(serviceMock.retrieve(Mockito.anyLong())).thenReturn(getMockDepartment());

		Response response = target("department/retrieve/1").request().get();

		Department department = response.readEntity(Department.class);
		
		assertEquals(200, response.getStatus());
		assertEquals("1", department.getId().toString());
		assertEquals("Andrio", department.getName());
		assertEquals("Developer", department.getDescription());
	}

    /**
     * Invoke the delete department and check the http response is 200.
     */
    @Test
    public void testDepartmentDeleteResponse() {

        Entity<Long> departmentId = Entity.entity(getMockDepartment().getId(), MediaType.APPLICATION_JSON_TYPE);

        doNothing().when(serviceMock).save(Mockito.any(Department.class));

        Response response = target("department/delete/1").request().get();

        assertEquals(200, response.getStatus());
        assertEquals("department has been successfully deleted", response.readEntity(String.class));
    }
	
	/**
	 * Invoke the save department and check the http response is 200.
	 */
	@Test
	public void testDepartmentSaveResponse() {
		
	    Entity<Department> department = Entity.entity(getMockDepartment(), MediaType.APPLICATION_JSON_TYPE);
	    
	    doNothing().when(serviceMock).save(Mockito.any(Department.class));

		Response response = target("department/save/andrio/andriodescription").request().get();

		assertEquals(200, response.getStatus());
		assertEquals("department has been successfully saved", response.readEntity(String.class));
	}

	
	// ======= Mocking ==========
	
	/**
	 * Mock object that will be returned
	 * 
	 * @return the department object
	 */
	private Department getMockDepartment() {
		Department department = new Department();
		department.setId(1L);
		department.setName("Andrio");
		department.setDescription("Developer");
		
		return department;
	}

	/**
	 * Create an Injectable Provider that with bind this factory to the department service.
	 * When the provide is invoked a mock service object will be returned.
	 * When dispose is invoked the mock service object will be assigned null.
	 * 
	 * @author Andrio Fonseca
	 *
	 */
	class InjectableProvider extends AbstractBinder implements Factory<DepartmentService> {
		
		@Override
		protected void configure() {
			bindFactory(this).to(DepartmentService.class).in(Singleton.class);
		}

		public DepartmentService provide() {
			return serviceMock;
		}

		public void dispose(DepartmentService service) {
			serviceMock = null;
		}
	}
}
