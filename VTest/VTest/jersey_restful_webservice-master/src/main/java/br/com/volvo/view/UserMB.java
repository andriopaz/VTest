package br.com.volvo.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.volvo.api.data.Department;
import br.com.volvo.api.data.User;

@ManagedBean(name = "userMB")
@ViewScoped
public class UserMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<User> users;
	
	public User userSelected;
	public User newUser;

	public UserMB() {
		this.newUser = new User();
		this.newUser.setDepartment(new Department());
		this.listUsers();
	}

	public void listUsers() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			users = new ArrayList<>();

			URL url = new URL("http://localhost:8080/jrws/rest/user/list");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			java.lang.reflect.Type founderListType = new TypeToken<ArrayList<User>>(){}.getType();

			users = gson.fromJson(br, founderListType);

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteUser(long id) {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/user/delete/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listUsers();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openEditModal(User user) {
		setUserSelected(user);
		setNewUser(new User());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgEditUser').show()");
	}
	
	public void openCreateModal() {
		setNewUser(new User());
		setUserSelected(new User());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgCreateUser').show()");
	}
	
	public void saveUser() {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/user/save/"
					+newUser.getName()+"/"+newUser.getDescription()+
					"/"+newUser.getDepartment().getId());
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listUsers();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void editUser() {
		try {
			
			String urlString = "http://localhost:8080/jrws/rest/user/update/"
					+userSelected.getId()+"/"+userSelected.getName()+"/"+userSelected.getDescription()
					+ "/"+userSelected.getDepartment().getId();

			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listUsers();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User getUserSelected() {
		return userSelected;
	}

	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
	}

	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	
}
