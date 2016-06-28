package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.jasper.tagplugins.jstl.core.Out;

import management.Benutzerverwaltung;
import management.Produktmanagement;
import model.Produkt;

/**
 * Servlet implementation class ShopController
 */
@WebServlet("/ShopController")
public class ShopController extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShopController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession(true);
		
		StringBuffer prodOut = new StringBuffer();
		
		Produktmanagement prodman = Produktmanagement.getInstance();
		List<Produkt> allProducts = prodman.getAlleProdukt();
		
		for(Produkt product : allProducts) {
			prodOut.append("<div class=\"product\">"
								+ "<h2>" + product.getprodName() + "</h2>" 
								+ "<p class=\"description\">" + product.getprodDescription() + "</p>"
								+ "<p class=\"price\">" + product.getprice() + " € </p>"
			);
			
			prodOut.append("<form action=\"ShopController\" method=\"POST\">"
							+ "<input class=\"btnAdd2Cart\" name=\"zumWarenkorb\" type=\"submit\" value=\"zum Warenkorb\"/>"
							+ "<input type=\"hidden\" name=\"product_id\" value=" + product.getprodID() + ">"
							+ "</form>"
							+ "</div>"
			);
		}
		
	
		session.setAttribute("prodOut", prodOut);
		request.getRequestDispatcher("HauptseiteKunde.jsp").include(request, response);
		response.setContentType("text/html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HttpSession session = request.getSession(true);

		
		// ---------------- logic of shopping cart ------------------
		String productID = request.getParameter("product_id"); 
		
		Map<String, Integer> cart = new HashMap<String, Integer>();

		// if session variable "cart" is already set, store content in local cart
		if ( session.getAttribute("cart") != null ){
			cart = (Map<String, Integer>) session.getAttribute("cart");
		}

		// if product is already in cart increment quantity by 1
		if ( cart.containsKey(productID) ) {
			int quantity = cart.get(productID) + 1;
			cart.put(productID, quantity);
			for (String key : cart.keySet()) {
			    System.out.println("already set: " + key + " " + cart.get(key));
			}
		} else {
			cart.put(productID, 1);
		}
		
		session.setAttribute("cart", cart);

		
		// ---------------- generate output for shopping cart ------------------
		Produktmanagement prodman = Produktmanagement.getInstance();
		StringBuffer cartOut = new StringBuffer();
		
		if ( cart.containsKey(productID) ) {
			int quantity = cart.get(productID) + 1;
			cartOut.append(quantity);
		}
	        	cartOut.append(
	        			"<table class=\"cart\">"
    	        		);
	        	
	        	 // Iterate over all Key-Value-Pairs
	        	 Iterator it = cart.entrySet().iterator();
	        	 
	        	 while (it.hasNext()) {
	        	    	Map.Entry pair = (Map.Entry)it.next();
	        	    	String keyValue = (String) pair.getKey();
	        	        cartOut.append(""
	        	        		+ "<tr>"
	        	        		+ "<td>" + prodman.getProduktByProduktID(keyValue).getprodName() + "</td>"
	        	        		+ "<td>&nbsp; x &nbsp;</td>"
	        	        		+ "<td>" + pair.getValue().toString() + "</td>"
	        	        		+ "</tr>");
	        	    
	        	        
	        	 }
        	 
        	    cartOut.append(
    	        		 "</table>");
		
		session.setAttribute("cartOut", cartOut);
		
		
		
		request.getRequestDispatcher("HauptseiteKunde.jsp").include(request, response);
		response.setContentType("text/html");
		
		return;
		
	
	}
	

}
