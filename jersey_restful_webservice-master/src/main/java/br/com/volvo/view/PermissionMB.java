package br.com.volvo.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import com.google.gson.Gson;

import br.com.volvo.api.data.Permission;

@ManagedBean(name = "permissionMB")
@ViewScoped
public class PermissionMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Permission> permissions;
	
	public Permission permissionSelected;
	public Permission newPermission;

	public PermissionMB() {
		this.newPermission = new Permission();
		this.listPermissions();
	}

	public void listPermissions() {
		try {
			Gson gson = new Gson();
			permissions = new ArrayList<>();

			URL url = new URL("http://localhost:8080/jrws/rest/permission/list");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			Permission[] mcArray = gson.fromJson(br, Permission[].class);
			permissions = new ArrayList<>(Arrays.asList(mcArray));

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deletePermission(long id) {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/permission/delete/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listPermissions();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openEditModal(Permission permission) {
		setPermissionSelected(permission);
		setNewPermission(new Permission());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgEditPermission').show()");
	}
	
	public void openCreateModal() {
		setNewPermission(new Permission());
		setPermissionSelected(new Permission());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgCreatePermission').show()");
	}
	
	public void savePermission() {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/permission/save/"
					+newPermission.getName()+"/"+newPermission.getDescription());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listPermissions();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void editPermission() {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/permission/update/"
					+permissionSelected.getId()+"/"+permissionSelected.getName()+"/"+permissionSelected.getDescription());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listPermissions();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public Permission getPermissionSelected() {
		return permissionSelected;
	}

	public void setPermissionSelected(Permission permissionSelected) {
		this.permissionSelected = permissionSelected;
	}

	public Permission getNewPermission() {
		return newPermission;
	}

	public void setNewPermission(Permission newPermission) {
		this.newPermission = newPermission;
	}
}
