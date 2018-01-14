package model;

import java.math.BigDecimal;
import java.util.Comparator;

public class moveDamList {
	String name;
	int damage;
	int time;
	
	public moveDamList(String name, int damage, int time) {
		super();
		this.name = name;
		this.damage = damage;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
	
	
	public static Comparator<moveDamList> COMPARE_BY_TIME = new Comparator<moveDamList>() {
        public int compare(moveDamList one, moveDamList other) {
            return one.time - other.time;
        }
    };

	
	
	
}
