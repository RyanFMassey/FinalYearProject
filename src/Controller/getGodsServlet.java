package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import model.Move;


@SuppressWarnings("serial")
public class getGodsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			God god = null;
			int level = 1;
			God newGod = null;
			ArrayList<Item> items = new ArrayList<Item>();
			ArrayList<Item> enemyItems = new ArrayList<Item>();
			String damageResult = null;
					
			
			String getBy = request.getParameter("getBy");
			/*Switch:
				1 = get all Gods
				2 = get all God names
				3 = get single god stats
			*/
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			String fill = request.getParameter("fill");
			String[] itemNames = new String[6];
			
			for (int i = 0; i < 6; i++) {
				itemNames[i] = request.getParameter("i" + (i + 1));
			}

			
			String i1stacks = request.getParameter("i1s");
			String i2stacks = request.getParameter("i2s");
			String i3stacks = request.getParameter("i3s");
			String i4stacks = request.getParameter("i4s");
			String i5stacks = request.getParameter("i5s");
			String i6stacks = request.getParameter("i6s");
			
			int i1s = 0;
			int i2s = 0;
			int i3s = 0;
			int i4s = 0;
			int i5s = 0;
			int i6s = 0;
			
			items.add(getItem(itemNames[0]));
			items.add(getItem(itemNames[1]));
			items.add(getItem(itemNames[2]));
			items.add(getItem(itemNames[3]));
			items.add(getItem(itemNames[4]));
			items.add(getItem(itemNames[5]));
			
			if (i1stacks != null) {
				i1s = Integer.parseInt(i1stacks);
			}
			
			if (i2stacks != null) {
				i2s = Integer.parseInt(i2stacks);
			}
			
			if (i3stacks != null) {
				i3s = Integer.parseInt(i3stacks);
			}
			
			if (i4stacks != null) {
				i4s = Integer.parseInt(i4stacks);
			}
			
			if (i5stacks != null) {
				i5s = Integer.parseInt(i5stacks);
			}
			
			if (i6stacks != null) {
				i6s = Integer.parseInt(i6stacks);
			}
			
			List<God> results = new ArrayList<God>();
			List<Move> moveResults = new ArrayList<Move>();
			List<String> nameResults = new ArrayList<String>();
			
			PrintWriter out1 = response.getWriter();
			
			if (getBy != null) {
				switch (getBy) {
				case ("1"):
					results = AppDAO.INSTANCE.getAllGods();
				
					break;
				case ("2"):
					nameResults = AppDAO.INSTANCE.getAllGodsNames();
					break;
				case ("3"):
					if (name!=null){
						results.add(AppDAO.INSTANCE.getGodByName(name));	
					}
				break;
				case ("4"):
					god = AppDAO.INSTANCE.getGodByName(name);
					level = Integer.parseInt(request.getParameter("level"));
					
					newGod = Calculations.calculateStats(god, level, items, i1s, i2s, i3s, i4s, i5s, i6s);
					
					results.add(newGod);
					
					moveResults.add(AppDAO.INSTANCE.getMoveByID(Integer.toString(newGod.getMove1ID())));
					moveResults.add(AppDAO.INSTANCE.getMoveByID(Integer.toString(newGod.getMove2ID())));
					moveResults.add(AppDAO.INSTANCE.getMoveByID(Integer.toString(newGod.getMove3ID())));
					moveResults.add(AppDAO.INSTANCE.getMoveByID(Integer.toString(newGod.getMove4ID())));
				break;
				case ("5"):
					
					//Calculating Player Stats
					god = AppDAO.INSTANCE.getGodByName(name);
					level = Integer.parseInt(request.getParameter("level"));
					newGod = Calculations.calculateStats(god, level, items, i1s, i2s, i3s, i4s, i5s, i6s);

					//Caluclating Enemy Stats
					String eI1Name = request.getParameter("ei1");
					String eI2Name = request.getParameter("ei2");
					String eI3Name = request.getParameter("ei3");
					String eI4Name = request.getParameter("ei4");
					String eI5Name = request.getParameter("ei5");
					String eI6Name = request.getParameter("ei6");
					
					String eI1stacks = request.getParameter("ei1s");
					String eI2stacks = request.getParameter("ei2s");
					String eI3stacks = request.getParameter("ei3s");
					String eI4stacks = request.getParameter("ei4s");
					String eI5stacks = request.getParameter("ei5s");
					String eI6stacks = request.getParameter("ei6s");
					
					int ei1s = 0;
					int ei2s = 0;
					int ei3s = 0;
					int ei4s = 0;
					int ei5s = 0;
					int ei6s = 0;
					
					enemyItems.add(getItem(eI1Name));
					enemyItems.add(getItem(eI2Name));
					enemyItems.add(getItem(eI3Name));
					enemyItems.add(getItem(eI4Name));
					enemyItems.add(getItem(eI5Name));
					enemyItems.add(getItem(eI6Name));
					
					if (eI1stacks != null) {
						ei1s = Integer.parseInt(eI1stacks);
					}
					
					if (eI2stacks != null) {
						ei2s = Integer.parseInt(eI2stacks);
					}
					
					if (eI3stacks != null) {
						ei3s = Integer.parseInt(eI3stacks);
					}
					
					if (eI4stacks != null) {
						ei4s = Integer.parseInt(eI4stacks);
					}
					
					if (eI5stacks != null) {
						ei5s = Integer.parseInt(eI5stacks);
					}
					
					if (eI6stacks != null) {
						ei6s = Integer.parseInt(eI6stacks);
					}
					String enemyName = request.getParameter("enemyName");
					God enemyGod = AppDAO.INSTANCE.getGodByName(enemyName);
					int enemyLevel = Integer.parseInt(request.getParameter("enemyLevel"));
					God newEnemyGod = Calculations.calculateStats(enemyGod, enemyLevel, enemyItems, ei1s, ei2s, ei3s, ei4s, ei5s, ei6s);
					
					String[] moveIDs = (request.getParameterValues("moves"));
					String[] moveLevels = request.getParameterValues("moveLevels");
					
					Boolean deathbringer = false;
					
					for (int i = 0; i < items.size(); i++) {
						if (items.get(i) != null) {
							if (items.get(i).getItemName().equals("Deathbringer")) {
								deathbringer = true;
							}
						}
					}
					
					ArrayList<Move> moveList = Calculations.getMoves(moveIDs, god, level, deathbringer, newEnemyGod);
					
					damageResult = Calculations.calculateTotalDamage(moveList, moveLevels, newGod, level, enemyLevel, items, newEnemyGod);
				break;
					
				}
			}
			
			if ((results.size() != 0) || (nameResults.size() != 0) ||  (damageResult != null)) {
				if (results.size() != 0) {
					out1.print(resultToJSON(results, moveResults));
				} else if (nameResults.size() != 0) {
					out1.print(printList(nameResults));
				} else if (damageResult != null) {
					out1.print(damageResult);
				}
			}
			
			if (fill != null) {
				AppDAO.INSTANCE.fillGods();
				AppDAO.INSTANCE.fillItems();
				AppDAO.INSTANCE.fillMoves();
			}
			
			
			
			
			
			
			
			out1.close();
			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String resultToJSON (List<God> results, List<Move> moveResults) {
		String json = "{ \"gods\":[";
		for (int i = 0; i < results.size(); i++) {
			json = json + "\n\t{";
			json = json + "\n\t\t\"name\":\"" + results.get(i).getGodName() + "\",";
			json = json + "\n\t\t\"pantheon\":\"" + results.get(i).getPantheon() + "\",";
			json = json + "\n\t\t\"godClass\":\"" + results.get(i).getGodClass() + "\",";
			json = json + "\n\t\t\"type\":\"" + results.get(i).getGodType() + "\",";
			json = json + "\n\t\t\"baseHealth\":\"" + results.get(i).getBaseHealth() + "\",";
			json = json + "\n\t\t\"baseMana\":\"" + results.get(i).getBaseMana() + "\",";
			json = json + "\n\t\t\"healthIncrease\":\"" + results.get(i).getHealthIncrease() + "\",";
			json = json + "\n\t\t\"manaIncrease\":\"" + results.get(i).getManaIncrease() + "\",";
			json = json + "\n\t\t\"speed\":\"" + results.get(i).getSpeed() + "\",";
			json = json + "\n\t\t\"range\":\"" + results.get(i).getRange() + "\",";
			json = json + "\n\t\t\"power\":\"" + results.get(i).getPower() + "\",";
			json = json + "\n\t\t\"baseAttacksPerSecond\":\"" + results.get(i).getBaseAttacksPerSecond() + "\",";
			json = json + "\n\t\t\"attacksPerSecondIncrease\":\"" + results.get(i).getAttacksPerSecondInc() + "\",";
			json = json + "\n\t\t\"baseAttackDamage\":\"" + results.get(i).getBaseAttackDamage() + "\",";
			json = json + "\n\t\t\"baseAttackDamageIncrease\":\"" + results.get(i).getBaseAttackDamageInc() + "\",";
			json = json + "\n\t\t\"basePhysicalProtection\":\"" + results.get(i).getBasePhysProt() + "\",";
			json = json + "\n\t\t\"baseMagicalProtection\":\"" + results.get(i).getBaseMagProt() + "\",";
			json = json + "\n\t\t\"critChance\":\"" + results.get(i).getCritChance() + "\",";
			json = json + "\n\t\t\"penetration\":\"" + results.get(i).getPenetration() + "\",";
			json = json + "\n\t\t\"basicDPS\":\"" + calcBasicDPS(results.get(i)) + "\",";
			json = json + "\n\t\t\"cooldownReduction\":\"" + results.get(i).getCooldown() + "\",";
			json = json + "\n\t\t\"move1ID\":\"" + results.get(i).getMove1ID() + "\",";
			json = json + "\n\t\t\"move2ID\":\"" + results.get(i).getMove2ID() + "\",";
			json = json + "\n\t\t\"move3ID\":\"" + results.get(i).getMove3ID() + "\",";
			json = json + "\n\t\t\"move4ID\":\"" + results.get(i).getMove4ID() + "\"";
			json = json + "\n\t},";
			
		}
		json = json.substring(0, json.length() - 1);
		json = json + "\n],\n";
		
		
		
		json = json + "\"moves\": [";
		
		for (int i = 0; i < moveResults.size(); i++) {
			json = json + "\n\t{";
			
			json = json + "\n\t\t\"moveID\":\"" + moveResults.get(i).getMoveID() + "\",";
			json = json + "\n\t\t\"moveName\":\"" + moveResults.get(i).getMoveName() + "\",";
			json = json + "\n\t\t\"damage\":\"" + moveResults.get(i).getDamage() + "\",";
			json = json + "\n\t\t\"damageIncrease\":\"" + moveResults.get(i).getDamageIncrease() + "\",";
			json = json + "\n\t\t\"powerScaling\":\"" + moveResults.get(i).getPowerScaling() + "\",";
			json = json + "\n\t\t\"ticks\":\"" + moveResults.get(i).getTicks() + "\",";
			json = json + "\n\t\t\"tickDamage\":\"" + moveResults.get(i).getTickDamage() + "\",";
			json = json + "\n\t\t\"tickDamageIncrease\":\"" + moveResults.get(i).getTickDamageInc() + "\",";
			json = json + "\n\t\t\"tickScaling\":\"" + moveResults.get(i).getTickScaling() + "\",";
			json = json + "\n\t\t\"tickSpeed\":\"" + moveResults.get(i).getTickSpeed() + "\",";
			json = json + "\n\t\t\"noOfTicks\":\"" + moveResults.get(i).getNoOfTicks() + "\",";
			json = json + "\n\t\t\"cooldown\":\"" + moveResults.get(i).getCooldown().setScale(2, RoundingMode.HALF_UP) + "\"";
			json = json + "\n\t},";
			
		}
		
		json = json.substring(0, json.length() - 2);
		json = json+ "}";
		json = json+ "\n]}";
		
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
	
	private Item getItem (String name) {
		Item i1;
		if (name != null) {
			i1 = AppDAO.INSTANCE.getItemByName(name);
			return i1;
		} else {
			return null;
		}
	}
	
	private String calcBasicDPS(God god) {
		BigDecimal DPS = new BigDecimal(god.getBaseAttackDamage());
		DPS = DPS.multiply(god.getBaseAttacksPerSecond());
		DPS.setScale(2,  RoundingMode.HALF_UP);
		
		return DPS.toString();
	}
	

}
