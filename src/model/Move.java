package model;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//Main Item object
@Entity
public class Move {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String moveID;
	private String moveName;
	private int damage;		//Initial damage			0 if move is only tick based
	private int damageIncrease;
	private int powerScaling;
	private Boolean ticks;
	private BigDecimal tickSpeed;
	private int tickDamage;
	private int tickDamageInc;
	private int tickScaling;
	private int noOfTicks;
	private BigDecimal cooldown;
	
	
	public Move(String moveID, String moveName, int damage, int damageIncrease, int powerScaling, Boolean ticks,
			BigDecimal tickSpeed, int tickDamage, int tickDamageInc, int tickScaling, int noOfTicks, BigDecimal cooldown) {
		super();
		this.moveID = moveID;
		this.moveName = moveName;
		this.damage = damage;
		this.damageIncrease = damageIncrease;
		this.powerScaling = powerScaling;
		this.ticks = ticks;
		this.tickSpeed = tickSpeed;
		this.tickDamage = tickDamage;
		this.tickDamageInc = tickDamageInc;
		this.tickScaling = tickScaling;
		this.noOfTicks = noOfTicks;
		this.cooldown = cooldown;
	}

	



	public String getMoveID() {
		return moveID;
	}





	public void setMoveID(String moveID) {
		this.moveID = moveID;
	}





	public String getMoveName() {
		return moveName;
	}





	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}





	public int getDamage() {
		return damage;
	}





	public void setDamage(int damage) {
		this.damage = damage;
	}





	public int getDamageIncrease() {
		return damageIncrease;
	}





	public void setDamageIncrease(int damageIncrease) {
		this.damageIncrease = damageIncrease;
	}





	public int getPowerScaling() {
		return powerScaling;
	}





	public void setPowerScaling(int powerScaling) {
		this.powerScaling = powerScaling;
	}





	public Boolean getTicks() {
		return ticks;
	}





	public void setTicks(Boolean ticks) {
		this.ticks = ticks;
	}





	public BigDecimal getTickSpeed() {
		return tickSpeed;
	}





	public void setTickSpeed(BigDecimal tickSpeed) {
		this.tickSpeed = tickSpeed;
	}





	public int getTickDamage() {
		return tickDamage;
	}





	public void setTickDamage(int tickDamage) {
		this.tickDamage = tickDamage;
	}





	public int getTickScaling() {
		return tickScaling;
	}





	public void setTickScaling(int tickScaling) {
		this.tickScaling = tickScaling;
	}





	public int getNoOfTicks() {
		return noOfTicks;
	}





	public void setNoOfTicks(int noOfTicks) {
		this.noOfTicks = noOfTicks;
	}





	public BigDecimal getCooldown() {
		return cooldown;
	}





	public void setCooldown(BigDecimal cooldown) {
		this.cooldown = cooldown;
	}





	public int getTickDamageInc() {
		return tickDamageInc;
	}





	public void setTickDamageInc(int tickDamageInc) {
		this.tickDamageInc = tickDamageInc;
	}





	@Override
	public String toString() {
		return "Move [moveID=" + moveID + ", moveName=" + moveName + ", damage=" + damage + ", damageIncrease="
				+ damageIncrease + ", powerScaling=" + powerScaling + ", ticks=" + ticks + ", tickSpeed=" + tickSpeed
				+ ", noOfTicks=" + noOfTicks + "]";
	}
	
	
	
	
}