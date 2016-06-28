package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import management.Benutzerverwaltung;
import management.Produktmanagement;
import model.Benutzer;
import model.Produkt;

/**
 * Servlet implementation class Produktverwaltungscontroller
 */
@WebServlet("/Produktverwaltungscontroller")
public class Produktverwaltungscontroller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Produktverwaltungscontroller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Produktverwaltungscontroller:get: Weiterleiten zu Login.jsp");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * Bei einem Postrequest werden alle Kunden des Systems aufgelistet, falls der Button zuLoeschen gedrückt wurde, wird zuerst der entsprechende Kunde gelöscht.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Produktmanagement prover = Produktmanagement.getInstance();
		
		if(request.getParameter("beendeProduktverw")!=null){
			System.out.println("usernameSession: "+request.getSession().getAttribute("username"));
			response.sendRedirect(request.getContextPath() + "/ShopController");//Damit Produktliste in session gleich aktualisiert wird
			response.setContentType("text/html");
			return;
		}
		
		if(request.getParameter("zuLoeschen") != null){
			prover.loescheProdukt(request.getParameter("zuLoeschen"));
		}
		
		
		List<Produkt> alleProdukte = prover.getAlleProdukt();
		
		request.getSession().setAttribute("alleProdukte", alleProdukte); //Benutzer an JSP übergeben
		
		request.getRequestDispatcher("Produktanzeige.jsp").include(request, response);

		response.setContentType("text/html");
		
	}

}
