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

import br.com.volvo.UserService;
import br.com.volvo.api.UserResource;
import br.com.volvo.api.data.User;

public class UserResourceTest extends JerseyTest {

	@Mock
	private UserService serviceMock;

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
		
		ResourceConfig config = new ResourceConfig(UserResource.class);
		config.register(new InjectableProvider());

		return config;
	}

	/**
	 * Invoke the retrieve user and check the http response is 200.
	 */
	@Test
	public void testUserRetrieveResponse() {
		
		when(serviceMock.retrieve(Mockito.anyLong())).thenReturn(getMockUser());

		Response response = target("user/retrieve/1").request().get();

		User user = response.readEntity(User.class);
		
		assertEquals(200, response.getStatus());
		assertEquals("1", user.getId().toString());
		assertEquals("Andrio", user.getName());
		assertEquals("Developer", user.getDescription());
	}

    /**
     * Invoke the delete user and check the http response is 200.
     */
    @Test
    public void testUserDeleteResponse() {

        Entity<Long> userId = Entity.entity(getMockUser().getId(), MediaType.APPLICATION_JSON_TYPE);

        doNothing().when(serviceMock).save(Mockito.any(User.class));

        Response response = target("user/delete/1").request().get();

        assertEquals(200, response.getStatus());
        assertEquals("user has been successfully deleted", response.readEntity(String.class));
    }
	
	/**
	 * Invoke the save user and check the http response is 200.
	 */
	@Test
	public void testUserSaveResponse() {
		
	    Entity<User> user = Entity.entity(getMockUser(), MediaType.APPLICATION_JSON_TYPE);
	    
	    doNothing().when(serviceMock).save(Mockito.any(User.class));

		Response response = target("user/save/andrio/andriodescription/1").request().get();

		assertEquals(200, response.getStatus());
		assertEquals("user has been successfully saved", response.readEntity(String.class));
	}

	// ======= Mocking ==========
	
	/**
	 * Mock object that will be returned
	 * 
	 * @return the user object
	 */
	private User getMockUser() {
		User user = new User();
		user.setId(1L);
		user.setName("Andrio");
		user.setDescription("Developer");
		
		return user;
	}

	/**
	 * Create an Injectable Provider that with bind this factory to the user service.
	 * When the provide is invoked a mock service object will be returned.
	 * When dispose is invoked the mock service object will be assigned null.
	 * 
	 * @author Andrio Fonseca
	 *
	 */
	class InjectableProvider extends AbstractBinder implements Factory<UserService> {
		
		@Override
		protected void configure() {
			bindFactory(this).to(UserService.class).in(Singleton.class);
		}

		public UserService provide() {
			return serviceMock;
		}

		public void dispose(UserService service) {
			serviceMock = null;
		}
	}
}
