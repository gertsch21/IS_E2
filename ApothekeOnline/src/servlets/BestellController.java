package servlets;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import management.Produktmanagement;
import management.Benutzerverwaltung;
import management.Bestellungsmanagement;

import model.*;

/**
 * Servlet implementation class BestellController
 */
@WebServlet("/BestellController")
public class BestellController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BestellController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		Produktmanagement prodman = Produktmanagement.getInstance();
		Bestellungsmanagement bestman = Bestellungsmanagement.getInstance();
		Benutzerverwaltung benver = Benutzerverwaltung.getInstance();
		
		Map<String, Integer> cart = new HashMap<String, Integer>();

		//create order-parameters
		long timestamp = System.currentTimeMillis() / 1000;
		String username = (String) session.getAttribute("username");
		Benutzer user = benver.getBenutzerByUname(username);
		int userid = user.getUsrID();
		String orderid = timestamp+username;
		System.out.println(timestamp);
		
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		String date = format.format(curDate);
		System.out.println(date);
		double price = 12.5;
		
		Bestellung order = new Bestellung(orderid, date, price, userid);
		System.out.println("Orderid: " + orderid);
		System.out.println("date: " + date);
		System.out.println("price: " + price);
		System.out.println("userid: " + userid);
		
		bestman.speichereBestellung(order);
		// if session variable "cart" is  set, store content in local cart
		if ( session.getAttribute("cart") != null ){
			cart = (Map<String, Integer>) session.getAttribute("cart");
		}
		
		Iterator it = cart.entrySet().iterator();
   	 	int posNr = 1;
   	 	while (it.hasNext()) {
   	 		@SuppressWarnings("rawtypes")
			Map.Entry pair = (Map.Entry)it.next();
   	    	String mapKey = (String) pair.getKey();
   	        int mapVal = (int) pair.getValue();
   	        double posPrice = prodman.getProduktByProduktID(mapKey).getprice() * mapVal;
   	        
   	    	Position item = new Position(orderid, posNr++, mapVal, posPrice, Integer.parseInt(mapKey));
   	    	bestman.speicherePosition(item);
   	    	
   	 	}
   	 	
   	 	
   	 	request.getRequestDispatcher("BestellungErfolgt.jsp").include(request, response);
		response.setContentType("text/html");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
