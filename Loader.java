package com.vtlions.multithreading.sand;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Loader implements Runnable {

	private SandPile sandPile;
	private Cart cart;
	private Semaphore semaphoreLoader;
	private Semaphore semaphoreTransporter;
	private int weight;
	private int counter;
	private final int SPEED_OF_LOADING = 2;
	private int capacityOfCart;

	public Loader(final SandPile sandPile, final Cart cart, final Semaphore semaphoreLoader,
			final Semaphore semaphoreTransporter) {

		this.sandPile = sandPile;
		this.cart = cart;
		this.semaphoreLoader = semaphoreLoader;
		this.semaphoreTransporter = semaphoreTransporter;

		new Thread(this).start();
	}

	@Override
	public void run() {

		while (sandPile.getWeight() > 0) {

			if (sandPile.getWeight() >= cart.getMAX_CAPACITY()) {
				capacityOfCart = cart.getMAX_CAPACITY();
				weight = SPEED_OF_LOADING;
			} else {
				capacityOfCart = sandPile.getWeight();

				if (sandPile.getWeight() >= SPEED_OF_LOADING) {
					weight = SPEED_OF_LOADING;
				} else {
					weight = sandPile.getWeight();
				}
			}

			if (capacityOfCart % SPEED_OF_LOADING == 0) {
				counter = capacityOfCart / SPEED_OF_LOADING;
			} else {
				counter = (int) (capacityOfCart / SPEED_OF_LOADING + 1);
			}

			for (int i = 0; i < counter; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if (sandPile.getWeight() < weight) {
					weight = sandPile.getWeight();
				}

				cart.addCargo(true, weight);

				if (i == 0) {
					System.out.println("\nLOADER IS LOADING THE CART");
					System.out.println("==========================");
				}

				sandPile.setWeight(sandPile.getWeight() - weight);
				System.out.println("Loader has loaded " + weight + "kg of sand. The cart contains " + cart.getCargo()
						+ "kg. There are left " + sandPile.getWeight() + "kg in sand pile");
				semaphoreLoader.release();
			}

			semaphoreTransporter.release();
			cart.addCargo(false, 0);
		}
	}
}
