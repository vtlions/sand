package com.vtlions.multithreading.sand;

public class SandPile {
	private final int START_WEIGHT = 100;
	private int weight = START_WEIGHT;

	public int getStartWeight() {
		return START_WEIGHT;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}
