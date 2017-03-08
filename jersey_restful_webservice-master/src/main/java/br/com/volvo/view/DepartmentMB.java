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

import br.com.volvo.api.data.Department;

@ManagedBean(name = "departmentMB")
@ViewScoped
public class DepartmentMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<Department> departments;
	
	public Department departmentSelected;
	public Department newDepartment;

	public DepartmentMB() {
		this.newDepartment = new Department();
		this.listDepartments();
	}

	public void listDepartments() {
		try {
			Gson gson = new Gson();
			departments = new ArrayList<>();

			URL url = new URL("http://localhost:8080/jrws/rest/department/list");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			Department[] mcArray = gson.fromJson(br, Department[].class);
			departments = new ArrayList<>(Arrays.asList(mcArray));

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deleteDepartment(long id) {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/department/delete/"+id);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listDepartments();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void openEditModal(Department department) {
		setDepartmentSelected(department);
		setNewDepartment(new Department());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgEditDepartment').show()");
	}
	
	public void openCreateModal() {
		setNewDepartment(new Department());
		setDepartmentSelected(new Department());
		
		RequestContext context = RequestContext.getCurrentInstance();	
		context.execute("PF('dlgCreateDepartment').show()");
	}
	
	public void saveDepartment() {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/department/save/"
					+newDepartment.getName()+"/"+newDepartment.getDescription());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listDepartments();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	public void editDepartment() {
		try {
			URL url = new URL("http://localhost:8080/jrws/rest/department/update/"
					+departmentSelected.getId()+"/"+departmentSelected.getName()+"/"+departmentSelected.getDescription());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			this.listDepartments();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	public List<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(List<Department> departments) {
		this.departments = departments;
	}

	public Department getDepartmentSelected() {
		return departmentSelected;
	}

	public void setDepartmentSelected(Department departmentSelected) {
		this.departmentSelected = departmentSelected;
	}

	public Department getNewDepartment() {
		return newDepartment;
	}

	public void setNewDepartment(Department newDepartment) {
		this.newDepartment = newDepartment;
	}
}
