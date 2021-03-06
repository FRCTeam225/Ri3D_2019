package org.firstcapital.lib.webapp;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.firstcapital.robot.AutonomousChooser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class AutonomousApplets  {
	public static class AutonomousList extends HttpServlet {
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
			AutonomousChooser chooser = AutonomousChooser.getInstance();
			
			JsonObject info = new JsonObject();
			
			JsonArray autonomi = new JsonArray();
			for ( String auto : AutonomousChooser.getInstance().getAutonomiNames() )
				autonomi.add(auto);
			
			info.add("autonomi", autonomi);
			info.addProperty("selected", chooser.getSelectedAutonomousIndex());
			info.addProperty("name", chooser.getSelectedAutonomousName());
			
			res.setContentType("application/json");
			res.setStatus(HttpServletResponse.SC_OK);
			res.getWriter().println(info.toString());
		}
	}
	
	public static class AutonomousSelect extends HttpServlet {
		protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
			AutonomousChooser chooser = AutonomousChooser.getInstance();
			
			
			JsonObject selected = new JsonObject();
			
			int auto = -1;
			
			try {
				auto = Integer.parseInt(req.getParameter("index"));
				
				if ( auto < 0 || auto > chooser.size() ) {
					selected.addProperty("success", false);
					selected.addProperty("error", "Autonomous index is out of bounds");
				}
				else {
					chooser.setSelectedAutonomous(auto);
					selected.addProperty("success", true);
				}
				
			} catch (Exception e) {
				selected.addProperty("success", false);
				selected.addProperty("error", e.getMessage());
			}
			
			
			
			res.setContentType("application/json");
			res.setStatus(HttpServletResponse.SC_OK);
			res.getWriter().println(selected.toString());
		}
		
	}
}
