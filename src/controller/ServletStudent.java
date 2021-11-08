package controller;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

import dao.DaoStudent;
import dao.StudentDAO;
import model.Student;

/**
 * Servlet implementation class ServletStudent
 */

@WebServlet("/")
public class ServletStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DaoStudent studentDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletStudent() {
        this.studentDAO = new DaoStudent();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();


		try {
			switch (action) {
			case "/insert":
				insertUser(request, response);
				break;
			case "/new":
				showNewForm(request, response);
				break;
			case "/delete":
				deleteUser(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateUser(request, response);
				break;
			default:
				listUser(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		String name = request.getParameter("name");
		String gpa = request.getParameter("gpa");
		ServletStudent newUser = new ServletStudent(name, gpa);
		studentDAO.insertUser(newUser);
		response.sendRedirect("list");
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String gpa = request.getParameter("gpa");

		ServletStudent book = new Student(id, name, gpa);
		studentDAO.updateUser(book);
		response.sendRedirect("list");
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		studentDAO.deleteUser(id);
		response.sendRedirect("list");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<ServletStudent> listUser = studentDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-list.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
		dispatcher.forward(request, response);
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		ServletStudent existingUser = studentDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("student-form.jsp");
		request.setAttribute("student", existingUser);
		dispatcher.forward(request, response);

	}
	


