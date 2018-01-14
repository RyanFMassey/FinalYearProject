package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Main Item object
@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String itemID;
	private String itemName;
	private int type;
	private int cost;
	private int health;
	private int mana;
	private int physProt;
	private int magProt;
	private int power;
	private int attackSpeed; //in percentage
	private int flatPen;
	private int critChance;
	private int cooldownReduction;
	private int lifesteal;
	private int speedIncrease; //in percentage
	private Boolean stacks;
	private int maxStacks;
	
	public Item(String itemID, String itemName, int type, int cost, int health, int mana, int physProt,
			int magProt, int power, int attackSpeed, int flatPen, int critChance, int cooldownReduction, int lifesteal,
				int speedIncrease, Boolean stacks, int maxStacks) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.type = type;
		this.cost = cost;
		this.health = health;
		this.mana = mana;
		this.physProt = physProt;
		this.magProt = magProt;
		this.power = power;
		this.attackSpeed = attackSpeed;
		this.flatPen = flatPen;
		this.critChance = critChance;
		this.cooldownReduction = cooldownReduction;
		this.lifesteal = lifesteal;
		this.speedIncrease = speedIncrease;
		this.stacks = stacks;
		this.maxStacks = maxStacks;
	}

	public boolean getStacks() {
		return stacks;
	}

	public void setStacks(Boolean stacks) {
		this.stacks = stacks;
	}

	public int getMaxStacks() {
		return maxStacks;
	}

	public void setMaxStacks(int maxStacks) {
		this.maxStacks = maxStacks;
	}

	public String getItemID() {
		return itemID;
	}
	
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public int getMana() {
		return mana;
	}
	
	public void setMana(int mana) {
		this.mana = mana;
	}
	
	public int getPhysProt() {
		return physProt;
	}
	
	public void setPhysProt(int physProt) {
		this.physProt = physProt;
	}
	
	public int getMagProt() {
		return magProt;
	}
	
	public void setMagProt(int manaProt) {
		this.magProt = manaProt;
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		this.power = power;
	}
	
	public int getAttackSpeed() {
		return attackSpeed;
	}
	
	public void setAttackSpeed(int attackSpeed) {
		this.attackSpeed = attackSpeed;
	}
	
	public int getFlatPen() {
		return flatPen;
	}
	
	public void setFlatPen(int flatPen) {
		this.flatPen = flatPen;
	}
	
	public int getCritChance() {
		return critChance;
	}
	
	public void setCritChance(int critChance) {
		this.critChance = critChance;
	}
	
	public int getCooldownReduction() {
		return cooldownReduction;
	}
	
	public void setCooldownReduction(int cooldownReduction) {
		this.cooldownReduction = cooldownReduction;
	}
	
	public int getLifesteal() {
		return lifesteal;
	}
	
	public void setLifesteal(int lifesteal) {
		this.lifesteal = lifesteal;
	}
	
	public int getSpeedIncrease() {
		return speedIncrease;
	}
	
	public void setSpeedIncrease(int speedIncrease) {
		this.speedIncrease = speedIncrease;
	}

}
