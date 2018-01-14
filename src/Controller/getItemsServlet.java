package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.AppDAO;
import model.God;
import model.Item;


@SuppressWarnings("serial")
public class getItemsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String getBy = request.getParameter("getBy");
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String godName = request.getParameter("name");
			String fill = request.getParameter("fill");
			God god;
			
			List<Item> results = new ArrayList<Item>();
			List<String> nameResults = new ArrayList<String>();
			
			PrintWriter out1 = response.getWriter();
			
			if (getBy != null) {
				switch (getBy) {
				case ("1"): //get all item names and stacks based off of god type
					god = AppDAO.INSTANCE.getGodByName(godName);
					results = AppDAO.INSTANCE.getItemsByType(god.getGodType());
					break;
				case ("2"): //get all item names
					nameResults = AppDAO.INSTANCE.getAllItemNames();
					break;
				case ("3"): //get item by name
					results.add(AppDAO.INSTANCE.getItemByName(name));
				}
				
			}
			
			if ((results.size() != 0) || (nameResults.size() != 0)) {
				if (results.size() != 0) {
					out1.print(resultToJSON(results));
				} else if (nameResults.size() != 0) {
					out1.print(printList(nameResults));
				}
			}
			
			out1.close();
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String resultToJSON (List<Item> results) {
		String json = "{ \"items\":[";
		for (int i = 0; i < results.size(); i++) {
			json = json + "\n\t{";
			json = json + "\n\t\t\"id\":\"" + results.get(i).getItemID() + "\",";
			json = json + "\n\t\t\"name\":\"" + results.get(i).getItemName() + "\",";
			json = json + "\n\t\t\"type\":\"" + results.get(i).getType() + "\",";
			json = json + "\n\t\t\"cost\":\"" + results.get(i).getCost() + "\",";
			json = json + "\n\t\t\"health\":\"" + results.get(i).getHealth() + "\",";
			json = json + "\n\t\t\"mana\":\"" + results.get(i).getMana() + "\",";
			json = json + "\n\t\t\"physProt\":\"" + results.get(i).getPhysProt() + "\",";
			json = json + "\n\t\t\"magProt\":\"" + results.get(i).getMagProt() + "\",";
			json = json + "\n\t\t\"power\":\"" + results.get(i).getPower() + "\",";
			json = json + "\n\t\t\"attackSpeed\":\"" + results.get(i).getAttackSpeed() + "\",";
			json = json + "\n\t\t\"flatPen\":\"" + results.get(i).getFlatPen() + "\",";
			json = json + "\n\t\t\"critChance\":\"" + results.get(i).getCritChance() + "\",";
			json = json + "\n\t\t\"cooldownReduction\":\"" + results.get(i).getCooldownReduction() + "\",";
			json = json + "\n\t\t\"lifesteal\":\"" + results.get(i).getLifesteal() + "\",";
			json = json + "\n\t\t\"speedIncrease\":\"" + results.get(i).getSpeedIncrease() + "\",";
			json = json + "\n\t\t\"stacks\":\"" + results.get(i).getStacks() + "\",";
			json = json + "\n\t\t\"maxStacks\":\"" + results.get(i).getMaxStacks() + "\"";
			
			json = json + "\n\t},";
			
		}
		json = json.substring(0, json.length() - 1);
		json = json + "\n]}";
		return json;
	}
	
	private String printList (List<String> list) {
		String output = "[";
		
		for (int i =0; i < list.size(); i++) {
			output = output + "\"" + list.get(i).toString() + "\", ";
		}
		output = output.substring(0, output.length() - 2);
		output = output + "]";
		return output;
	}
	

}
