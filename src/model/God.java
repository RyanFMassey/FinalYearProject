package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

//Main God object
@Entity
public class God {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String godID;
	private String godName;
	private String pantheon;
	private String godClass; //Change from just "class"
	private Boolean godType; //Changed from just "type", 0 = Magical, 1 = Physical
	private int baseHealth;
	private int baseMana;
	private int healthIncrease;
	private int manaIncrease;
	private int speed;
	private int range;
	private int power;
	private BigDecimal baseAttacksPerSecond;
	private BigDecimal attacksPerSecondInc;
	private int baseAttackDamage;
	private BigDecimal baseAttackDamageInc;
	private int basePhysProt;
	private int baseMagProt;
	private BigDecimal physProtScaling;
	private BigDecimal magProtScaling;
	private int critChance;
	private int penetration;
	private int cooldown;
	private int move1ID;
	private int move2ID;
	private int move3ID;
	private int move4ID;


	public God(String id, String name, String panth, String gClass, Boolean gType, int bHealth, int bMana, int hIncrease, int mIncrease,
			int speed, int range, int power, BigDecimal bAttPerSec, BigDecimal attPerSecInc, int bAttDam, BigDecimal baseAttackDamageInc, int bPhysProt,
				int bMagProt, BigDecimal pProtScal, BigDecimal mProtScal, int m1ID, int m2ID, int m3ID, int m4ID) {
		
		setGodID(id);
		setGodName(name);
		setPantheon(panth);
		setGodClass(gClass);
		setGodType(gType);
		setBaseHealth(bHealth);
		setBaseMana(bMana);
		setHealthIncrease(hIncrease);
		setManaIncrease(mIncrease);
		setSpeed(speed);
		setRange(range);
		setPower(power);
		setBaseAttacksPerSecond(bAttPerSec);
		setAttacksPerSecondInc(attPerSecInc);
		setBaseAttackDamage(bAttDam);
		setBaseAttackDamageInc(baseAttackDamageInc);
		setBasePhysProt(bPhysProt);
		setBaseMagProt(bMagProt);
		setPhysProtScaling(pProtScal);
		setMagProtScaling(mProtScal);
		setCritChance(0);
		setPenetration(0);
		setCooldown(0);
		setMove1ID(m1ID);
		setMove2ID(m2ID);
		setMove3ID(m3ID);
		setMove4ID(m4ID);
		
	}


	public String getGodID() {
		return godID;
	}


	public void setGodID(String godID) {
		this.godID = godID;
	}


	public String getGodName() {
		return godName;
	}


	public void setGodName(String godName) {
		this.godName = godName;
	}


	public String getPantheon() {
		return pantheon;
	}


	public void setPantheon(String pantheon) {
		this.pantheon = pantheon;
	}


	public String getGodClass() {
		return godClass;
	}


	public void setGodClass(String godClass) {
		this.godClass = godClass;
	}


	public boolean getGodType() {
		return godType;
	}


	public void setGodType(Boolean godType) {
		this.godType = godType;
	}


	public int getBaseHealth() {
		return baseHealth;
	}


	public void setBaseHealth(int baseHealth) {
		this.baseHealth = baseHealth;
	}


	public int getBaseMana() {
		return baseMana;
	}


	public void setBaseMana(int baseMana) {
		this.baseMana = baseMana;
	}


	public int getHealthIncrease() {
		return healthIncrease;
	}


	public void setHealthIncrease(int healthIncrease) {
		this.healthIncrease = healthIncrease;
	}


	public int getManaIncrease() {
		return manaIncrease;
	}


	public void setManaIncrease(int manaIncrease) {
		this.manaIncrease = manaIncrease;
	}


	public int getSpeed() {
		return speed;
	}


	public void setSpeed(int speed) {
		this.speed = speed;
	}


	public int getRange() {
		return range;
	}


	public void setRange(int range) {
		this.range = range;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}
	
	
	public BigDecimal getBaseAttacksPerSecond() {
		return baseAttacksPerSecond;
	}


	public void setBaseAttacksPerSecond(BigDecimal baseAttacksPerSecond) {
		this.baseAttacksPerSecond = baseAttacksPerSecond;
	}


	public BigDecimal getAttacksPerSecondInc() {
		return attacksPerSecondInc;
	}


	public void setAttacksPerSecondInc(BigDecimal attacksPerSecondInc) {
		this.attacksPerSecondInc = attacksPerSecondInc;
	}


	public int getBaseAttackDamage() {
		return baseAttackDamage;
	}


	public void setBaseAttackDamage(int baseAttackDamage) {
		this.baseAttackDamage = baseAttackDamage;
	}


	public BigDecimal getBaseAttackDamageInc() {
		return baseAttackDamageInc;
	}


	public void setBaseAttackDamageInc(BigDecimal baseAttackDamageInc) {
		this.baseAttackDamageInc = baseAttackDamageInc;
	}


	public int getBasePhysProt() {
		return basePhysProt;
	}


	public void setBasePhysProt(int basePhysProt) {
		this.basePhysProt = basePhysProt;
	}


	public int getBaseMagProt() {
		return baseMagProt;
	}


	public void setBaseMagProt(int baseMagProt) {
		this.baseMagProt = baseMagProt;
	}


	public BigDecimal getPhysProtScaling() {
		return physProtScaling;
	}


	public void setPhysProtScaling(BigDecimal physProtScaling) {
		this.physProtScaling = physProtScaling;
	}


	public BigDecimal getMagProtScaling() {
		return magProtScaling;
	}


	public void setMagProtScaling(BigDecimal magProtScaling) {
		this.magProtScaling = magProtScaling;
	}

	
	public int getCritChance() {
		return critChance;
	}


	public void setCritChance(int critChance) {
		this.critChance = critChance;
	}
	
	
	public int getPenetration() {
		return penetration;
	}


	public void setPenetration(int penetration) {
		this.penetration = penetration;
	}
	
	
	public int getCooldown() {
		return cooldown;
	}


	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	

	public int getMove1ID() {
		return move1ID;
	}


	public void setMove1ID(int move1id) {
		move1ID = move1id;
	}


	public int getMove2ID() {
		return move2ID;
	}


	public void setMove2ID(int move2id) {
		move2ID = move2id;
	}


	public int getMove3ID() {
		return move3ID;
	}


	public void setMove3ID(int move3id) {
		move3ID = move3id;
	}


	public int getMove4ID() {
		return move4ID;
	}


	public void setMove4ID(int move4id) {
		move4ID = move4id;
	}
	
	@Override
	public String toString() {
		String godString = "";
		godString = "[Name: " + godName;
		return godString;
		
	}
}
