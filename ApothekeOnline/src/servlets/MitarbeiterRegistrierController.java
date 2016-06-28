package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Benutzerverwaltung;

/**
 * Servlet implementation class MitarbeiterRegistrierController
 */
@WebServlet("/MitarbeiterRegistrierController")
public class MitarbeiterRegistrierController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MitarbeiterRegistrierController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Falls ein Get request kommt, soll auf die Loginseite verwiesen werden.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("MitarbeiterRegistriercontroller: Weiterleiten zu Login.jsp");
		request.getRequestDispatcher("Login.jsp").include(request, response);
		response.setContentType("text/html");
		
	}
	
	/**
	 * Hier wird geprüft ob die eingegebenen Daten korrekt sind, falls ja, war die Registrierung erfolgreich und es wird ein Mitarbeiter angelegt,
	 * falls nein, dann wird die jeweilige Fehlermeldung ausgegeben.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Benutzerverwaltung benver = Benutzerverwaltung.getInstance();
		
		//Hier wird geprüft, ob einfach nur auf das JSP zugegriffen wird und dies Authorisiert erfolgt
		if(request.getParameter("regBest")!=null){
			System.out.println("MitarbeiterRegistrierController: Authorisiert zum Mitarbeiter Registrieren --> RegistrierenMitarbeiter.jsp");
			request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
			response.setContentType("text/html");
			return;
		}
		
		
	//Ab hier wird alles bearbeitet, das vom MitarbeiterRegistrieren.jsp kommt
		if(request.getParameter("username")==null){//registrierbutton gedrückt
			System.out.println("MitarbeiterRegistrierController: keinUsername:Weiterleiten zum Registrieren!");
			request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
			response.setContentType("text/html");
			return;
		}
		else{
			String vorname=request.getParameter("vorname");
			String nachname=request.getParameter("nachname");
			String email=request.getParameter("email");
			String salaryString=request.getParameter("salary");
			
			String land=request.getParameter("land");
			String plzString=request.getParameter("plz");
			String wohnort=request.getParameter("wohnort");
			String strasse=request.getParameter("strasse");
			String hausNrString=request.getParameter("nummer");
			
			String staffNoString = request.getParameter("staffNo");
			
			String username=request.getParameter("username");
			String password=request.getParameter("password");
			String passwordW=request.getParameter("passwordW");
	
			
			int hausNr = 0;
			int plz = 0;
			int staffNo =0;
			Double salaryDouble = null;
			int salary;
			try{
				hausNr = Integer.parseInt(hausNrString);
				plz = Integer.parseInt(plzString);
				staffNo = Integer.parseInt(staffNoString);
				salaryDouble = Double.parseDouble(salaryString) *100; //damit in cent gespeichert
				salary = salaryDouble.intValue();
			}catch(Exception e){
				request.getSession(true).setAttribute("fehler", "Keine Nummer(HausNr oder Plz oder staffNo oder salary) oder zu lang!");
				System.out.println("MitarbeiterRegistrierungsController: Hausnummer oder PLZ oder salary oder staffNo ist keine Nummer!");
				request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			
			if(Integer.MAX_VALUE==salary || Integer.MAX_VALUE==plz || Integer.MAX_VALUE==staffNo || Integer.MAX_VALUE ==hausNr){
				request.getSession(true).setAttribute("fehler", "Nummer(Plz oder HausNr oder staffNo oder salary) ist zu lang!");
				System.out.println("MitarbeiterRegistrierungsController: Hausnummer oder PLZ oder salary oder staffNoist keine Nummer!");
				request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			if(username.length()<=5  || password.length()<=5 ){
				request.getSession(true).setAttribute("fehler", "Fehler: Username od. Passwort zu kurz!");
				System.out.println("MitarbeiterRegistrierungsController: Pwd od. Username  <  5 Zeichen!");
				request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			//Wiederholtes Passwort nicht korrekt
			if(!password.equals(passwordW) ){
				request.getSession(true).setAttribute("fehler", "Fehler: Passwortwiederholung nicht korrekt!");
				System.out.println("MitarbeiterRegistrierungsController: Passwortwiederholung nicht korrekt!!");
				request.getRequestDispatcher("RegistrierenMitarbeiter.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			
			//Username enthält Abstände
			if(username.length()!=username.replaceAll(" ","").length()){
				request.getSession(true).setAttribute("fehler", "Fehler: Username darf keine Leerzeichen enthalten!");
				System.out.println("MitarbeiterRegistrierungsController: Leerzeichen im Username!");
				response.sendRedirect("RegistrierenMitarbeiter.jsp");
				return;
			}
			
			
			//Nachdem Benutzer angelegt wurde, wird er automatisch(nicht über Login) zur Hauptseite.jsp weitergeleitet.
			if(benver.mitarbeiterAnlegen(vorname, nachname, email, land, plz, wohnort, strasse, hausNr, username, passwordW, staffNo, salary)){
				HttpSession session = request.getSession(true);
				session.setAttribute("message", "JUHU, '"+username+"' wurde nun als Mitarbeiter gespeichert");
				System.out.println("MitarbeiterRegistrierungsController: Kunde angelegt: "+vorname+" "+nachname+" "+email+" "+strasse+" "+wohnort+" "+username+" "+password);
				session.setAttribute("fehler", null);
				request.getRequestDispatcher("HauptseiteKunde.jsp").include(request, response);
				response.setContentType("text/html");
				return;
			}
			//eingabe nicht erfolgreich:
			else{
				System.out.println("MitarbeiterRegistrierungsController: Person konnte nicht angelegt werden: "+vorname+" "+nachname+" "+email+" "+strasse+" "+wohnort+" "+username+" "+password);
				request.getSession(true).setAttribute("fehler", "Fehler: Prüfen Sie das Datum(Jahr.Monat.Tag) oder der Username ist schon vergeben!");
				response.sendRedirect("RegistrierenMitarbeiter.jsp");
			}
		}

	}

}
