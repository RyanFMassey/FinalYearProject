package model;

import java.util.List;
import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public enum AppDAO {
	INSTANCE;
	
	public List<God> getAllGods() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from God m");
		List<God> allGods = q.getResultList();
		return allGods;
	}
	
	public List<Move> getAllMoves() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Move m");
		List<Move> allMoves = q.getResultList();
		return allMoves;
	}
	
	public List<String> getAllGodsNames() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m.godName from God m");
		List<String> allGodsNames = q.getResultList();
		return allGodsNames;
	}
	
	public List<String> getAllItemNames() {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m.itemName from Item m");
		List<String> allItemNames = q.getResultList();
		return allItemNames;
	}
	
	public God getGodByID(String id) {
		God result;
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from God m where m.godID = :id)");
		q.setParameter("id", id);
		result = (God) q.getSingleResult();
		return result;
	}
	
	public God getGodByName(String name) {
		God result;
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from God m where m.godName = :name");
		q.setParameter("name", name);
		result = (God) q.getSingleResult();
		return result;
	}
	
	public List<Item> getItemsByType(boolean type) {
		int intType;
		
		if (type) {
			intType = 1;
		} else {
			intType = 0;
		}
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Item m where m.type = :type OR m.type = 2");
		q.setParameter("type", intType);
		List<Item> items = q.getResultList();
		return items;
	}
	
	public Item getItemByName(String name) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Item m where m.itemName = :name");
		q.setParameter("name", name);
		Item result = (Item) q.getSingleResult();
		return result;
	}
	
	public Move getMoveByID(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Move m where m.moveID = :id");
		q.setParameter("id", id);
		Move result = (Move) q.getSingleResult();
		return result;
	}
	
	
	
	public void addGod(God god){
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(god);
			em.close();
		}
	}
	
	public void addGod(String godID, String godName, String pantheon, String godClass, Boolean godType, int baseHealth, int baseMana,
			int healthIncrease, int manaIncrease, int speed, int range, int power, BigDecimal baseAttacksPerSecond, BigDecimal attPerSecInc,
				int baseAttackDamage, BigDecimal attDamInc, int basePhysProt, int baseMagProt, BigDecimal physProtScaling, BigDecimal magProtScaling, int move1ID, int move2ID, int move3ID, int move4ID){
		
		God god = new God(godID, godName, pantheon, godClass, godType, baseHealth, baseMana, healthIncrease, manaIncrease, speed, range, power, baseAttacksPerSecond, attPerSecInc,
				baseAttackDamage, attDamInc, basePhysProt, baseMagProt, physProtScaling, magProtScaling, move1ID, move2ID, move3ID, move4ID);
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(god);
			em.close();
		}
		System.out.println("Added");
	}
	
	
	
	public void addItem(Item item){
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(item);
			em.close();
		}
	}
	
	public void addItem(String itemID, String itemName, int type, int cost, int health, int mana, int physProt,
			int magProt, int power, int attackSpeed, int flatPen, int critChance, int cooldownReduction, int lifesteal,
				int speedIncrease, Boolean stacks, int maxStacks) {
		
		Item item = new Item(itemID, itemName, type, cost, health, mana, physProt, magProt, power, attackSpeed, flatPen, critChance, cooldownReduction,
				lifesteal, speedIncrease, stacks, maxStacks);
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(item);
			em.close();
		}
	}
	
	public void addMove(Move move) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(move);
			em.close();
		}
	}
	
	public void addMove(String moveID, String moveName, int damage, int damageIncrease, int powerScaling, boolean ticks, BigDecimal tickSpeed, int tickDamage, int tickDamInc, int tickScaling, int noOfTicks, BigDecimal cooldown) {
		Move move = new Move(moveID, moveName, damage, damageIncrease, powerScaling, ticks, tickSpeed, tickDamage, tickDamInc, tickScaling, noOfTicks, cooldown);
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			em.persist(move);
			em.close();
		}
		
	}
	
	
	
	
	
	//Fill Data
	
	public void fillGods() {
		System.out.println("Filling gods...");
		addGod("1", "Ah Puch", "Mayan", "Mage", false, 400, 265, 75, 55, 365, 55, 0, new BigDecimal(0.86), new BigDecimal(0.95), 35, new BigDecimal(1.5), 9, 30, new BigDecimal(2.7), new BigDecimal(0), 1, 2, 3, 4);
		addGod("2", "Amaterasu", "Japanese", "Warrior", true, 470, 220, 82, 35, 375, 12, 0, new BigDecimal(1), new BigDecimal(1.2), 39, new BigDecimal(2), 18, 30, new BigDecimal(3), new BigDecimal(0.9), 5, 6, 7, 8);
		
		System.out.println("Complete");
	}
	
	public void fillItems() {
		System.out.println("Filling items...");
		addItem("1", "8-Pointed Shuriken", 1, 1500, 0, 0, 0, 0, 15, 10, 0, 15, 0, 0, 0, false, 0);
		addItem("2", "Acorn of Swiftness", 1, 500, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 10, false, 0);
		addItem("3", "Book of Thoth", 0, 2650, 0, 125, 0, 0, 100, 0, 0, 0, 0, 0, 0, true, 75);
		addItem("4", "Rod of Tahuti", 0, 3300, 0, 0, 0, 0, 125, 0, 0, 0, 0, 0, 0, false, 0);
		addItem("5", "Warrior Tabi", 1, 1550, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 18, false, 0);
		addItem("6", "Hastened Fatalis", 2, 2150, 0, 0, 0, 0, 0, 20, 10, 0, 0, 0, 10, false, 0);
		addItem("7", "Shoes of Focus", 0, 1500, 0, 250, 0, 0, 40, 0, 0, 0, 10, 0, 18, false, 0);
		addItem("8", "Obsidian Shard", 0, 2300, 0, 0, 0, 0, 60, 0, 0, 0, 0, 0, 0, false, 0);
		addItem("9", "Deathbringer", 1, 3200, 0, 0, 0, 0, 50, 0, 0, 20, 0, 0, 0, false, 0);
		addItem("10", "Short Sword", 1, 1500, 0, 0, 0, 0, 20, 0, 0, 10, 0, 0, 0, false, 0);
		System.out.println("Complete");
	}
	
	public void fillMoves() {
		System.out.println("Filling moves...");
		
		//Ah Puch
		addMove("1", "Undead Surge", 90, 20, 35, false, null, 0, 0, 0, 0, new BigDecimal(10));
		addMove("2", "Corpse Explosion", 60, 10, 10, true, new BigDecimal("0"), 60, 15, 25, 6, new BigDecimal(8));   //Check if move is used the take stacks as corpses
		addMove("3", "Fleeting Breath", 0, 0, 0, true, new BigDecimal("1"), 30, 5, 20, 4, new BigDecimal(10)); //Assumes no healing done
		addMove("4", "Empty the Crypts", 0, 0, 0, true, new BigDecimal("0.4"), 40, 10, 10, 20, new BigDecimal(90)); //Each wraith is a stack

		//Amaterasu
		addMove("5", "Divine Presence", 0, 0, 0, false, null, 0, 0, 0, 0, new BigDecimal(9));
		addMove("6", "Heavenly Reflection", 50, 40, 60, true, new BigDecimal("1"), 5, 4, 0, 100, new BigDecimal(14)); //ticks is percent charge
		addMove("7", "Glorious Charge", 70, 60, 60, false, null, 0, 0, 0, 0, new BigDecimal(18));
		addMove("8", "Dazzling Offensive", 75, 50, 50, true, new BigDecimal("2"), 75, 50, 0, 3, new BigDecimal(75)); //ticks are number of hits
	
	}
}


//addMove("", "", , , , , , );


