package com.mauriello.SearchProducts;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mauriello.SearchProducts.model.DBConnection;
import com.sun.tools.javac.main.Main.Result;

/**
 * Servlet implementation class ProductsServlet
 */
public class ProductsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			
			String input = request.getParameter("fname");
			System.out.println("name is: " + input);
			
			InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
			Properties props = new Properties(); props.load(in);
			
			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
			Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rst = stmt.executeQuery("select * from products");
			
			out.println("<table><tr> <th>Name</th> <th>Color</th> <th>Price</th> </tr>");
			
			while (rst.next()) {
				out.println("<tr> <td>"+ rst.getString("name") + "</td> <td>" + rst.getString("color") + "</td> <td>" + rst.getBigDecimal("price")  + "</td> </tr>");
			}
			
			out.println("</table>");
			
			stmt.close();
			
			out.println("</body></html>");
			conn.closeConnection();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			int input = Integer.parseInt(request.getParameter("id"));
			//out.println("Input is: " + input);
			
			//out.println("98");
			//InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
			//out.println("99");
			//Properties props = new Properties(); props.load(in);
			//out.println("100");
			DBConnection conn = new DBConnection();
			Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rst = stmt.executeQuery("select * from products where id = " + input);
			
			
			
			if (!rst.first()) {
				out.println("Object not found!");
			} else {
				out.println("Name: " + rst.getString("name") + " Color: " + rst.getString("color") + " Price: " + rst.getString("price"));
				
			}
			stmt.close();
			out.println("<br/> <a href='index.jsp'>Back to index.</a>");
			conn.closeConnection();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
