package Controller;



import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Collections;

import model.AppDAO;
import model.God;
import model.Item;
import model.Move;
import model.moveDamList;

public class Calculations {
	
	public static God calculateStats(God god, int level, ArrayList<Item> items, int i1s, int i2s, int i3s, int i4s, int i5s, int i6s) {
		DecimalFormat dp2 = new DecimalFormat(".##");
		BigDecimal bd100 = new BigDecimal("100");
		BigDecimal bdLevel = new BigDecimal(level);
		Boolean RoT = false;
		God newGod = god;
		
		int[] stacks = new int[] {i1s, i2s, i3s, i4s, i5s, i6s};
		
		
		//Scaling Stats
		int health = god.getBaseHealth() + (god.getHealthIncrease() * level);
		int mana = god.getBaseMana() + (god.getManaIncrease() * level);
		
		BigDecimal physProt = god.getPhysProtScaling().multiply(bdLevel); //Calculate scaling
		physProt = physProt.add(new BigDecimal(god.getBasePhysProt()));   //Add it to base
		
		BigDecimal magProt = god.getMagProtScaling().multiply(bdLevel); //Calculate scaling
		magProt = magProt.add(new BigDecimal(god.getBaseMagProt()));   //Add it to base
		
		BigDecimal aps = (god.getBaseAttacksPerSecond().divide(bd100)).multiply(bdLevel); 
		aps = god.getAttacksPerSecondInc().multiply(aps); //Calculate scaling amount
		aps = god.getBaseAttacksPerSecond().add(aps); //Add to base APS
			
		BigDecimal baseAttDam = (god.getBaseAttackDamageInc().multiply(bdLevel)); //Calculate level scaling
		baseAttDam = baseAttDam.add(new BigDecimal(god.getBaseAttackDamage())); //Add to base
		
		int speed = god.getSpeed();
		BigDecimal bonusSpeed = new BigDecimal("0");
		BigDecimal power = new BigDecimal("0");
		int critChance = 0;
		int penetration = 0;
		int cooldown = 0;
		
		//Item Boosts w/o Stacks
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) != null) {
				//Health
				if (items.get(i).getHealth() != 0) {
					health = health + items.get(i).getHealth();
				}
				
				//Mana
				if (items.get(i).getMana() != 0) {
					mana = mana + items.get(i).getMana();
				}
				
				//MovementSpeed
				if (items.get(i).getSpeedIncrease() != 0) {
					bonusSpeed = bonusSpeed.add(new BigDecimal(speed * (items.get(i).getSpeedIncrease() / 100.0)));
				}
				
				//Power
				if (items.get(i).getPower() != 0) {
					power = power.add(new BigDecimal(items.get(i).getPower()));
				}
				
				//AttackSpeed
				if (items.get(i).getAttackSpeed() != 0) {
					aps = (god.getBaseAttacksPerSecond().multiply(new BigDecimal(items.get(i).getAttackSpeed() / 100.0))).add(aps);
				}
				
				//Physical Protection
				if (items.get(i).getPhysProt() != 0) {
					physProt = physProt.add(new BigDecimal(items.get(i).getPhysProt()));
				}
				
				//Magical Protection
				if (items.get(i).getMagProt() != 0) {
					magProt = magProt.add(new BigDecimal(items.get(i).getMagProt()));
				}
				
				//Crit Chance
				if (items.get(i).getCritChance() != 0) {
					critChance = critChance + items.get(i).getCritChance();
				}
				
				//Penetration
				if (items.get(i).getFlatPen() != 0) {
					penetration = penetration + items.get(i).getFlatPen();
				}
				
				//Cooldown
				if (items.get(i).getCooldownReduction() != 0) {
					cooldown = cooldown + items.get(i).getCooldownReduction();
				}
			}
		}
		
		bonusSpeed = bonusSpeed.add(new BigDecimal(speed));
		
		//Calculating Movement speed diminishing returns
		if (bonusSpeed.compareTo(new BigDecimal(457)) == 1) {
			if (bonusSpeed.compareTo(new BigDecimal("540.5")) == 1) {
				bonusSpeed = bonusSpeed.subtract(new BigDecimal("540.5"));
				bonusSpeed = bonusSpeed.multiply(new BigDecimal(0.5));
				bonusSpeed = bonusSpeed.add(new BigDecimal("523.8"));
			} else {
				bonusSpeed = bonusSpeed.subtract(new BigDecimal("457"));
				bonusSpeed = bonusSpeed.multiply(new BigDecimal("0.8"));
				bonusSpeed = bonusSpeed.add(new BigDecimal("457"));
			}
		}
			
		bonusSpeed = bonusSpeed.setScale(0, RoundingMode.HALF_UP);
		speed = bonusSpeed.intValueExact();
		
		//Stacks Boost and Passive checks
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i) != null) {
				if (items.get(i).getStacks()) {
					//Book of Thoth
					if (items.get(i).getItemName().equals("Book of Thoth")) {
						mana = mana + (10 * stacks[i]);
						power = power.add(new BigDecimal(mana).multiply(new BigDecimal("0.03")));
					}
				}
				
				//Rod of Tahuti
				if (items.get(i).getItemName().equals("Rod of Tahuti")) {
					RoT = true;
				}
			}
		}
		
		if (RoT) {
			power = power.multiply(new BigDecimal(1.25));
			baseAttDam = baseAttDam.multiply(new BigDecimal(1.25));
		}
		
		
		//Calc base attack damage power scaling
		if (god.getGodType()) {
			baseAttDam = baseAttDam.add(power.multiply(new BigDecimal(1))); //Changed from 0.2 to 1
		} else {
			baseAttDam = baseAttDam.add(power.multiply(new BigDecimal(0.2)));
		}
		
		//Big Decimal Rounding
		aps = aps.setScale(2, RoundingMode.HALF_UP);
		physProt = physProt.setScale(0, RoundingMode.HALF_UP);
		magProt = magProt.setScale(0, RoundingMode.HALF_UP);
		baseAttDam = baseAttDam.setScale(0,  RoundingMode.HALF_UP);
		power = power.setScale(0,  RoundingMode.HALF_UP);
		
		//Cap check
		if (aps.compareTo(new BigDecimal(2.5)) > 0) {
			aps = new BigDecimal(2.5);
		}
		
		if (cooldown > 40) {
			cooldown = 40;
		}
		
		newGod.setBaseHealth(health);
		newGod.setBaseMana(mana);
		newGod.setSpeed(speed);
		newGod.setBaseAttacksPerSecond(aps);
		newGod.setCritChance(critChance);
		newGod.setPenetration(penetration);
		newGod.setCooldown(cooldown);
		
		newGod.setPower(power.intValueExact());
		newGod.setBaseAttackDamage(baseAttDam.intValueExact());
		newGod.setBasePhysProt(physProt.intValueExact());
		newGod.setBaseMagProt(magProt.intValueExact());
		
		return newGod;
	}
	
	
	
	
	public static String calculateTotalDamage(ArrayList<Move> moveList, String[] moveLevels, God god, int godLevel, int enemyLevel, ArrayList<Item> godItems, God enemyGod) {
		String name;
		int damage;
		int cumDamage = 0;
		String moveDamJSON = "{ \"moves\":[";
		
		for (int i = 0; i < moveList.size(); i++) {
			name = moveList.get(i).getMoveName();
			damage = calculateMoveDamage(moveList.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
			cumDamage = cumDamage + damage;
			moveDamJSON = moveDamJSON + "\n\t{";
			
			moveDamJSON = moveDamJSON + "\n\t\t\"number\":\"" + i + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"name\":\"" + name + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"damage\":\"" + damage + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"cumDam\":\"" + cumDamage + "\"";
			
			moveDamJSON = moveDamJSON + "\n\t},";
		}
		
		moveDamJSON = moveDamJSON.substring(0, moveDamJSON.length() - 1);
		moveDamJSON = moveDamJSON+ "\n]}";
		
		return moveDamJSON;
		
	}
	
	

	//Check if god has deathbringer
	public static ArrayList<Move> getMoves(String[] mL, God god, int level, Boolean deathbringer, God enemyGod) {
		
		ArrayList<Move> moveList = new ArrayList<Move>();
		Move move = null;
		for (int i=0; i < mL.length; i++) {
			if (mL[i].equals("999")) {
				moveList.add(calculateBasicAttack(god, level, deathbringer));
			} else if (mL[i].equals("1000")) {
				moveList.add(new Move("1000", "Mystical Mail Tick", 40, 0, 0, false, null, 0, 0, 0, 0, new BigDecimal(1)));
			} else if (mL[i].equals("1001")) {
				moveList.add(new Move("1001", "Polynomicon Proc", calculatePolynomiconDamage(god), 0, 0, false, null, 0, 0, 0, 0, new BigDecimal(3)));
			} else if (mL[i].equals("1002")) {
				moveList.add(new Move("1002", "Qin's Sais Proc", calculateQinsDamage(enemyGod), 0, 0, false, null, 0, 0, 0, 0, god.getBaseAttacksPerSecond()));
			} else if (mL[i].equals("1003")) {
				moveList.add(new Move("1003", "Soul Reaver Proc", calculateSoulReaverDamage(enemyGod), 0, 0, false, null, 0, 0, 0, 0, new BigDecimal(40)));
			} else {
				move = AppDAO.INSTANCE.getMoveByID(mL[i]);
				moveList.add(move);
			}
		}
		
		return moveList;
	}
	
	public static Move calculateBasicAttack(God god, int level, Boolean deathbringer){
		Move basicAttack;
		BigDecimal basAttDam = new BigDecimal(god.getBaseAttackDamage()); //Get Base
		if (god.getGodType()) {
			//Calculate crit w/ Deathbringer
			if (god.getCritChance() > 0) {
				if (deathbringer) {
					BigDecimal critDamage = basAttDam.multiply(new BigDecimal(god.getCritChance()).multiply(new BigDecimal(1.4)).divide(new BigDecimal(100)));
					basAttDam = basAttDam.add(critDamage);
				} else {	//Calculate crit w/o Deathbringer
					BigDecimal critDamage = basAttDam.multiply(new BigDecimal(god.getCritChance()).divide(new BigDecimal(100)));
					basAttDam = basAttDam.add(critDamage);
				}
			}
			basAttDam = basAttDam.setScale(0, RoundingMode.HALF_UP);
			basicAttack = new Move("999", "Basic Attack", basAttDam.intValueExact(), 0, 0, false, null, 0, 0, 0, 0, god.getBaseAttacksPerSecond()); //Create as Move
			
		} else {
			basicAttack = new Move("999", "Basic Attack", basAttDam.intValueExact(), 0, 0, false, null, 0, 0, 0, 0, god.getBaseAttacksPerSecond());
		}
		
		return basicAttack;
	}
	
	
	
	private static int calculateMoveDamage(Move move, int moveLevel, God god, God enemyGod, ArrayList<Item> godItems) {
		
		BigDecimal moveDamage = new BigDecimal(move.getDamage());
		moveDamage = moveDamage.add(new BigDecimal(move.getDamageIncrease()).multiply(new BigDecimal(moveLevel - 1)));
		moveDamage = moveDamage.add(new BigDecimal(move.getPowerScaling() * god.getPower()).divide(new BigDecimal(100)));
		BigDecimal protections;
		
		if (god.getGodType()){
			protections = new BigDecimal(enemyGod.getBasePhysProt());
		} else {
			protections = new BigDecimal(enemyGod.getBaseMagProt());
		}
		
		System.out.println("Enemy protections: " + protections);
		//Percent Penetration
		for (int i = 0; i < godItems.size(); i++) {
			if (godItems.get(i) != null) {
				if (godItems.get(i).getItemName().equals("Titans Bane") || godItems.get(i).getItemName().equals("Obsidian Shard")) {
					BigDecimal t = new BigDecimal(2);//get 0.6 recurring for 66%
					t = t.divide(new BigDecimal(3), 7, RoundingMode.HALF_UP);

					protections = protections.multiply(t);
				}
			}
		}
		System.out.println("Enemy protections after %Pen check: " + protections);
		//Flat Penetration
		protections = protections.subtract(new BigDecimal(god.getPenetration()));
		protections = protections.add(new BigDecimal(100));
		
		System.out.println("Enemy protections after flat pen check: " + protections);
		if (!move.getMoveName().equals("Soul Reaver Proc")) {
			System.out.println("Move is not soul reaver proc");
			moveDamage = moveDamage.multiply(new BigDecimal(100));
			moveDamage = moveDamage.divide(protections, 5, RoundingMode.HALF_UP);
			moveDamage = moveDamage.setScale(0,  RoundingMode.HALF_UP);
		}
		
		
		int intMoveDamage = moveDamage.intValueExact();
		
		return intMoveDamage;
	}
	
	
	private static int calculatePolynomiconDamage(God god) {
		BigDecimal dam = new BigDecimal(god.getPower());
		dam = dam.multiply(new BigDecimal(0.75));
		dam = dam.setScale(0,  RoundingMode.HALF_UP);
		
		return dam.intValueExact();
	}
	
	
	public static int calculateQinsDamage(God enemyGod) {
		BigDecimal dam = new BigDecimal(enemyGod.getBaseHealth());
		dam = dam.multiply(new BigDecimal(0.04));
		dam = dam.setScale(0,  RoundingMode.HALF_UP);
		
		return dam.intValueExact();
	}
	
	
	public static int calculateSoulReaverDamage(God enemyGod) {
		BigDecimal dam = new BigDecimal(enemyGod.getBaseHealth());
		dam = dam.multiply(new BigDecimal(0.1));
		dam = dam.setScale(0,  RoundingMode.HALF_UP);
		
		return dam.intValueExact();
	}

	
	
	public static String calculateDamageTime(ArrayList<moveDamList> moves) {
		String name;
		int damage;
		int cumDamage = 0;
		int time;
		BigDecimal avgDPS; 
		
		Collections.sort(moves, moveDamList.COMPARE_BY_TIME);
		
		String moveDamJSON = "{ \"moves\":[";
		
		name = moves.get(0).getName();
		damage = moves.get(0).getDamage();
		cumDamage = cumDamage + damage;
		time = moves.get(0).getTime();
		
		BigDecimal bdTime = new BigDecimal(time).divide(new BigDecimal(1000));
		bdTime = bdTime.setScale(3, RoundingMode.HALF_UP);
		
		avgDPS = new BigDecimal(cumDamage).divide(bdTime.add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);  //Time + 1 due to Divide by 0 error
		
		moveDamJSON = moveDamJSON + "\n\t{";
		
		moveDamJSON = moveDamJSON + "\n\t\t\"number\":\"" + 0 + "\",";
		moveDamJSON = moveDamJSON + "\n\t\t\"name\":\"" + name + "\",";
		moveDamJSON = moveDamJSON + "\n\t\t\"damage\":\"" + damage + "\",";
		moveDamJSON = moveDamJSON + "\n\t\t\"cumDam\":\"" + cumDamage + "\",";
		moveDamJSON = moveDamJSON + "\n\t\t\"avgDPS\":\"" + avgDPS + "\",";
		moveDamJSON = moveDamJSON + "\n\t\t\"time\":\"" + bdTime.toString() + "\"";
		
		moveDamJSON = moveDamJSON + "\n\t},";
		
		
		for (int i = 1; i < moves.size(); i++) {
			name = moves.get(i).getName();
			damage = moves.get(i).getDamage();
			cumDamage = cumDamage + damage;
			time = moves.get(i).getTime(); 
			
	
			bdTime = new BigDecimal(time).divide(new BigDecimal(1000));
			bdTime = bdTime.setScale(3, RoundingMode.HALF_UP);
			if (bdTime.compareTo(new BigDecimal(0)) == 0) {
				avgDPS = new BigDecimal(cumDamage).divide(bdTime.add(new BigDecimal(1)), 2, RoundingMode.HALF_UP);  
			} else {
				avgDPS = new BigDecimal(cumDamage).divide(bdTime, 2, RoundingMode.HALF_UP);  
			}
			
			
			moveDamJSON = moveDamJSON + "\n\t{";
			
			moveDamJSON = moveDamJSON + "\n\t\t\"number\":\"" + i + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"name\":\"" + name + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"damage\":\"" + damage + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"cumDam\":\"" + cumDamage + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"avgDPS\":\"" + avgDPS + "\",";
			moveDamJSON = moveDamJSON + "\n\t\t\"time\":\"" + bdTime.toString() + "\"";
			
			moveDamJSON = moveDamJSON + "\n\t},";
		}
		
		moveDamJSON = moveDamJSON.substring(0, moveDamJSON.length() - 1);
		moveDamJSON = moveDamJSON+ "\n]}";
		
		return moveDamJSON;
	}
	
	
	public static ArrayList<moveDamList> moveListToMoveDamList (ArrayList<Move> moves, String[] moveLevels, String[] moveTimes, String[] moveInfo, String startTime, God god, int godLevel, int enemyLevel, ArrayList<Item> godItems, God enemyGod) {
		String name;
		int damage;
		BigInteger time;
		int intTime;
		BigInteger BIstartTime = new BigInteger(startTime); 
		
		ArrayList<moveDamList> moveList = new ArrayList<moveDamList>();
		moveDamList move = null;
		
		
		for (int i = 0; i < moves.size(); i++) {
			name = moves.get(i).getMoveName();
			time = new BigInteger(moveTimes[i]).subtract(BIstartTime);
			intTime = time.intValue();
			
			
			if (name.equals("Corpse Explosion")) {
				damage = calculateMoveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);

				move = new moveDamList(name, damage, intTime);
				moveList.add(move);
				
				BigDecimal tempDamage = calculateTickDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
				tempDamage = tempDamage.multiply(new BigDecimal(moveInfo[i]));
				damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
				
				move = new moveDamList("Corpses: " + moveInfo[i], damage, intTime);
				moveList.add(move);
				
			} else if (name.equals("Fleeting Breath")) {
				
				for (int j = 0; j < 4; j++) {
					BigDecimal tempDamage = calculateTickDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
					damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
					
					move = new moveDamList(name, damage, intTime + (j * 1000));
					moveList.add(move);
				}
				
				if (moveInfo[i].equals("true")) {
					damage = calculateMoveDamage(new Move("00", "FBA", 50, 50, 50, false, null, 0, 0, 0, 0, null), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
					
					move = new moveDamList("Fleeting Breath Additional Damage", damage, intTime + 4000);
					moveList.add(move);
				}
				
			} else if (name.equals("Empty the Crypts")) {
				for (int j = 0; j < Integer.parseInt(moveInfo[i]); j++) {
					BigDecimal tempDamage = calculateTickDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
					damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
					
					move = new moveDamList(name, damage, intTime + (j * 300));
					moveList.add(move);
					
				}
				
			} else if (name.equals("Heavenly Reflection")) {
				damage = calculateMoveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
				System.out.println("Heavenly Reflection Final Damage: " + damage);
				move = new moveDamList(name, damage, intTime);
				moveList.add(move);
				
				BigDecimal tempDamage = calculateTickDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
				tempDamage = tempDamage.multiply(new BigDecimal(moveInfo[i]));
				damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
				
				move = new moveDamList("Heavenly Reflection Charge", damage, intTime);
				moveList.add(move);
				
			} else if (name.equals("Dazzling Offensive")) {
				damage = calculateMoveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
				move = new moveDamList(name + " 1st Hit", damage, intTime);
				moveList.add(move);
				
				if (moveInfo[i].equals("2")) {			
					
					
				} else if (moveInfo[i].equals("3")) {
					BigDecimal tempDamage = calculateDazzlingOffensiveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems, 2);
					damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
					
					move = new moveDamList(name + " 2nd Hit", damage, intTime + 1000);
					moveList.add(move);
					
					tempDamage = calculateDazzlingOffensiveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems, 3);
					damage = tempDamage.setScale(0, RoundingMode.HALF_UP).intValue();
					
					move = new moveDamList(name + " 3rd Hit", damage, intTime + 2000);
					moveList.add(move);
					
				}
				
			} else {
				damage = calculateMoveDamage(moves.get(i), Integer.parseInt(moveLevels[i]), god, enemyGod, godItems);
				
				move = new moveDamList(name, damage, intTime);
				moveList.add(move);
			}
			
			
			
			
		}
		
		return moveList;
	}
	
	
private static BigDecimal calculateTickDamage(Move move, int moveLevel, God god, God enemyGod, ArrayList<Item> godItems) {
		
		BigDecimal tickDamage = new BigDecimal(move.getTickDamage());
		tickDamage = tickDamage.add(new BigDecimal(move.getTickDamageInc()).multiply(new BigDecimal(moveLevel - 1)));
		tickDamage = tickDamage.add(new BigDecimal(move.getTickScaling() * god.getPower()).divide(new BigDecimal(100)));

		
		BigDecimal protections;
		
		if (god.getGodType()){
			protections = new BigDecimal(enemyGod.getBasePhysProt());
		} else {
			protections = new BigDecimal(enemyGod.getBaseMagProt());
		}
		
		
		//Percent Penetration
		for (int i = 0; i < godItems.size(); i++) {
			if (godItems.get(i) != null) {
				if (godItems.get(i).getItemName().equals("Titans Bane") || godItems.get(i).getItemName().equals("Obsidian Shard")) {
					BigDecimal t = new BigDecimal(2);//get 0.6 recurring for 66%
					t = t.divide(new BigDecimal(3), 7, RoundingMode.HALF_UP);
					
					protections = protections.multiply(t);
				}
			}
		}
		
		//Flat Penetration
		protections = protections.subtract(new BigDecimal(god.getPenetration()));
		protections = protections.add(new BigDecimal(100));
		
		if (!move.getMoveName().equals("Soul Reaver Proc")) {
			tickDamage = tickDamage.multiply(new BigDecimal(100));
			tickDamage = tickDamage.divide(protections, 5, RoundingMode.HALF_UP);
		}
		
		
		return tickDamage;
	}



	private static BigDecimal calculateDazzlingOffensiveDamage(Move move, int moveLevel, God god, God enemyGod, ArrayList<Item> godItems, int hits) {
		System.out.println("Hits: " + hits);
		BigDecimal tickDamage = new BigDecimal(move.getTickDamage());
		System.out.println("tickdam1: " + tickDamage);
		tickDamage = tickDamage.add(new BigDecimal(move.getTickDamageInc()).multiply(new BigDecimal(moveLevel - 1)));
		System.out.println("tickdam2: " + tickDamage);
		if (hits == 2) {
			tickDamage = tickDamage.multiply(new BigDecimal(1.2));
			System.out.println("tickdam3: " + tickDamage);
		} else if (hits == 3) {
			tickDamage = tickDamage.multiply(new BigDecimal(1.4));
			System.out.println("tickdam3: " + tickDamage);
		}
		
		tickDamage = tickDamage.add(new BigDecimal(move.getPowerScaling() * god.getPower()).divide(new BigDecimal(100)));
		System.out.println("tickdam4: " + tickDamage);
		
		BigDecimal protections;
		
		if (god.getGodType()){
			protections = new BigDecimal(enemyGod.getBasePhysProt());
		} else {
			protections = new BigDecimal(enemyGod.getBaseMagProt());
		}
		
		
		//Percent Penetration
		for (int i = 0; i < godItems.size(); i++) {
			if (godItems.get(i) != null) {
				if (godItems.get(i).getItemName().equals("Titans Bane") || godItems.get(i).getItemName().equals("Obsidian Shard")) {
					BigDecimal t = new BigDecimal(2);//get 0.6 recurring for 66%
					t = t.divide(new BigDecimal(3), 7, RoundingMode.HALF_UP);
					protections = protections.multiply(t);
				}
			}
		}
		System.out.println("tickdam5: " + tickDamage);
		//Flat Penetration
		protections = protections.subtract(new BigDecimal(god.getPenetration()));
		protections = protections.add(new BigDecimal(100));
		

		
		if (!move.getMoveName().equals("Soul Reaver Proc")) {
			tickDamage = tickDamage.multiply(new BigDecimal(100));
			tickDamage = tickDamage.divide(protections, 5, RoundingMode.HALF_UP);
		}
		System.out.println("tickdam7: " + tickDamage);
		
		return tickDamage;
	}
}
