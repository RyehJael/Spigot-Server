package com.civai.ryehjael.CivNPC;

import net.citizensnpcs.api.npc.NPC;

public class NPCState {
	String state;
	String IDLE = "idle";
	String HUNGRY = "hungry";

	public NPCState(NPC npc, String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}
	
	
	
	public String setIdle() {
		state = "idle";
		return state;
	}

	public String setHungry() {
		state = "hungry";
		return state;
	}
	
	public String setEating() {
		state = "eating";
		return state;
	}

	public String setAssaulted() {
		state = "assaulted";
		return state;
	}
	
	public String setFighting() {
		state = "fighting";
		return state;
	}
	
	public String setFleeing() {
		state = "fleeing";
		return state;
	}
	
	public String setHunting() {
		state = "hunting";
		return state;
	}
	
	public String setFarming() {
		state = "farming";
		return state;
	}
	
	public String setHarvesting() {
		state = "harvesting";
		return state;
	}
	
	public String setDead() {
		state = "dead";
		return state;
	}
	
}
