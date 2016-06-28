package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Benutzerverwaltung;
import management.Produktmanagement;

/**
 * Servlet implementation class KategorieHinzufuegenController
 */
@WebServlet("/KategorieHinzufuegenController")
public class KategorieHinzufuegenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KategorieHinzufuegenController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Im get vom KategorieHinzufuegenController");
		request.getSession().invalidate();
		System.out.println("Weiterleiten zum Login!");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.getSession().setAttribute("fehler", "");
		
		Produktmanagement prover = Produktmanagement.getInstance(); 
		
		//Hier wird geprüft, ob einfach nur auf das JSP zugegriffen wird und dies Authorisiert erfolgt
		if(request.getParameter("kategorieReg")!=null){
			System.out.println("KategorieHinzufuegencontroller: Authorisiert zum hinzufuegen --> KategorieAnlegen.jsp");
			request.getRequestDispatcher("KategorieAnlegen.jsp").include(request, response);
			response.setContentType("text/html");
			return;
		}
		
		
	//Ab hier wird alles bearbeitet, das vom MitarbeiterRegistrieren.jsp kommt
		if(request.getParameter("name")==null){//registrierbutton gedrückt
			System.out.println("KategorieHinzufuegencontroller: kein Name beim Anlegen der Kategorie!");
			request.getSession().setAttribute("fehler","Kein Name beim Anlegen der Kategorie!");
			request.getRequestDispatcher("KategorieAnlegen.jsp").include(request, response);
			response.setContentType("text/html");
			return;
		}
		else{
			String name=request.getParameter("name");
			String description=request.getParameter("description");
			
			
			if(name.isEmpty()){//registrierbutton gedrückt
				System.out.println("KategorieHinzufuegencontroller: kein Name beim Anlegen der Kategorie!");
				request.getSession().setAttribute("fehler","Kein Name beim Anlegen der Kategorie!");
				request.getRequestDispatcher("KategorieAnlegen.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			//Nachdem Benutzer angelegt wurde, wird er automatisch(nicht über Login) zur Hauptseite.jsp weitergeleitet.
			if(prover.categoryAnlegen(name, description)){
				HttpSession session = request.getSession(true);
				session.setAttribute("message", "JUHU, Kategorie '"+name+"' wurde gespeichert");
				System.out.println("KategorieHinzufuegencontroller: Kategorie angelegt: "+name);
				session.setAttribute("fehler", null);
				request.getRequestDispatcher("HauptseiteKunde.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			//eingabe nicht erfolgreich:
			else{
				System.out.println("KategorieHinzufuegencontroller: Kategorie konnte nicht angelegt werden: "+name+", "+description);
				request.getSession(true).setAttribute("fehler", "Fehler beim Anlegen der Kategorie!");
				response.sendRedirect("KategorieAnlegen.jsp");
			}
		}

	}

}
