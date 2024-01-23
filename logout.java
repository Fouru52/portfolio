package admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter pw = null;
	
    public logout() {
        super();
    
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		session.invalidate();
		
		pw = response.getWriter();
		pw.write("<script>"
				+ "alert('로그아웃 되셨습니다.');"
				+ "location.href='./index.jsp';"
				+ "</script>");
		pw.close();
	}
}
