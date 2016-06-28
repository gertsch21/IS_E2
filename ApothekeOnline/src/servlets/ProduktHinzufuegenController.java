package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Produktmanagement;

/**
 * Servlet implementation class ProduktHinzufuegenController
 */
@WebServlet("/ProduktHinzufuegenController")
public class ProduktHinzufuegenController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProduktHinzufuegenController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("Im get vom ProduktHinzufuegenController");
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
			if(request.getParameter("produktReg")!=null){
				
				request.getSession().setAttribute("KategorieListe", prover.getAlleCategory());
				System.out.println("ProduktHinzufuegencontroller: Authorisiert zum hinzufuegen --> KategorieAnlegen.jsp");
				request.getRequestDispatcher("ProduktAnlegen.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			
			
		
			String name=request.getParameter("name");
			String description=request.getParameter("description");
			String priceAsString=request.getParameter("price");
			String kategorie=request.getParameter("kategorie");

			
			if(name.isEmpty()){
				System.out.println("ProduktHinzufuegencontroller: Es wurde kein Name für das Produkt eingegeben!");
				request.getSession().setAttribute("fehler", "Es wurde kein Name für das Produkt eingegeben!");
				request.getRequestDispatcher("ProduktAnlegen.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			if(priceAsString.isEmpty()){
				System.out.println("ProduktHinzufuegencontroller: Es wurde kein Preis für das Produkt eingegeben!");
				request.getSession().setAttribute("fehler", "Es wurde kein Preis für das Produkt eingegeben!");
				request.getRequestDispatcher("ProduktAnlegen.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			int kategorieID = 0;
			double price= 0;
			try{
				kategorieID = Integer.parseInt(kategorie);
				price = Double.parseDouble(priceAsString);
			}catch(Exception e){
				request.getSession(true).setAttribute("fehler", "Der Preis muss eine Zahl sein, das Komma muss ein Punkt(.) sein!!");
				System.out.println("ProdukthinzufuegeController: Preis oder Kateogrieid ist keine Zahl!");
				request.getRequestDispatcher("ProduktAnlegen.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			
			
			//Nachdem Benutzer angelegt wurde, wird er automatisch(nicht über Login) zur Hauptseite.jsp weitergeleitet.
			if(prover.produktAnlegen(name, price, description, kategorieID)){
				HttpSession session = request.getSession(true);
				session.setAttribute("message", "Produkt '"+name+"' wurde soeben angelegt!");
				System.out.println("ProdukthinzufuegeController: Produkt angelegt: "+name+", KategorieID: "+kategorie);
				session.setAttribute("fehler", null);
				response.sendRedirect(request.getContextPath() + "/ShopController");//Damit Produktliste in session gleich aktualisiert wird
				response.setContentType("text/html");
				return;
			}
			//eingabe nicht erfolgreich:
			else{
				System.out.println("ProdukthinzufuegeController: Produkt konnte nicht angelegt werden: "+name+", Price: "+price+", Description: "+description);
				request.getSession(true).setAttribute("fehler", "Grober Fehler beim Anlegen des Produktes aufgetreten!");
				response.sendRedirect("ProduktAnlegen.jsp");
			}
			
			
		}
}
