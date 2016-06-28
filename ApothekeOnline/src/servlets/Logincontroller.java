package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Benutzerverwaltung;
import model.Kunde;
import model.Mitarbeiter;

/**
 * Servlet implementation class Logincontroller
 */
@WebServlet("/Logincontroller")
public class Logincontroller extends HttpServlet {
	private static final long serialVersionUID = 102831973239L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Logincontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Falls ein Get request kommt, soll auf die Loginseite verwiesen werden.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		System.out.println("LoginController: Weiterleiten zum Login!");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * Hier wird geprüft ob die eingegebenen Daten korrekt sind, falls ja, war der Login erfolgreich und es wird zur Hauptseite verwiesen,
	 * falls nein, dann wird die jeweilige Fehlermeldung ausgegeben.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Benutzerverwaltung benver = Benutzerverwaltung.getInstance();
		String username = request.getParameter("username"); 
		String password = request.getParameter("password");
		System.out.println("LoginController: Pruefe Login: '"+username+"' mit PWD:'"+password+".");
		
		request.getSession(true).setAttribute("fehler", "Kein Benutzer mit solch einem Usernamen registriert!");

		
		Kunde k = benver.getCustomerByUname(username);
		Mitarbeiter m = benver.getEmployeeByUname(username);
		if( k!=null){
			if(k.getPassword().equals(password)){
				request.getSession().invalidate();
				System.out.println("LoginController: Erfolgreiche Pruefung(istKunde): Weiterleiten zur Hauptseite des Kunden!");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				session.setAttribute("fehler", null);
				response.sendRedirect(request.getContextPath() + "/ShopController");
				response.setContentType("text/html");
				return;
			}
			request.getSession(true).setAttribute("fehler", "Ihr Passwort ist nicht korrekt!");
			System.out.println("Logincontroller: Falsches Passwort eingegeben von '"+username+"'");
		}
		if(m != null){
			if(m.getPassword().equals(password)){
				request.getSession().invalidate();
				System.out.println("LoginController: Erfolgreiche Pruefung(istMitarbeiter): Weiterleiten zur Hauptseite des Mitarbeiters!");
				HttpSession session = request.getSession(true);
				session.setAttribute("username", username);
				session.setAttribute("fehler", null);
				response.sendRedirect(request.getContextPath() + "/ShopController");
				response.setContentType("text/html");
				return;
			}
			System.out.println("Logincontroller: Falsches Passwort eingegeben von '"+username+"'");
			request.getSession(true).setAttribute("fehler", "Ihr Passwort ist nicht korrekt!");
		}

//Falls nichts von beiden
		System.out.println("LoginController: Weiterleiten zum Login!");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

}
