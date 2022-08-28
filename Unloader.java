package com.vtlions.multithreading.sand;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Unloader implements Runnable {

	private TransportedSandPile transportedSandPile;
	private Semaphore semaphoreUnloader;
	private Semaphore semaphoreTransporter;
	private Cart cart;
	private int speedOfUnloading = 3;
	private int counter;
	private final int QUANTITY_OF_SAND;
	private Transporter transporter;
	private int temp;
	SandPile sandPile;

	public Unloader(final TransportedSandPile transportedSandPile, final Cart cart, final Semaphore semaphoreUnloader,
			final Semaphore semaphoreTransporter, final Transporter transporter, final SandPile sandPile) {

		this.transportedSandPile = transportedSandPile;
		this.cart = cart;
		this.semaphoreUnloader = semaphoreUnloader;
		this.semaphoreTransporter = semaphoreTransporter;
		this.transporter = transporter;
		this.sandPile = sandPile;
		QUANTITY_OF_SAND = sandPile.getStartWeight();

		new Thread(this).start();
	}

	@Override
	public void run() {

		while (transportedSandPile.getWeight() < QUANTITY_OF_SAND) {

			cart.takeCargo(false, 0);

			if (cart.getCargo() % speedOfUnloading != 0) {
				counter = (int) (cart.getCargo() / speedOfUnloading + 1);
			} else {
				counter = cart.getCargo() / speedOfUnloading;
			}

			System.out.println("\nUNLOADER IS WORKING");
			System.out.println("=======================");

			for (int i = 0; i < counter; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				semaphoreUnloader.release();
				temp = cart.takeCargo(true, speedOfUnloading);
				transportedSandPile.setWeight(transportedSandPile.getWeight() + temp);
				System.out.println("Unloader has unloaded " + temp + "kg of sand from the cart. New sand pile has "
						+ transportedSandPile.getWeight() + "kg now");
			}

			if (transportedSandPile.getWeight() == QUANTITY_OF_SAND) {
				transporter.setStop(true);
			}

			semaphoreTransporter.release();
		}

		System.out.println("\n\n!!!!!!!THE WORK HAS BEEN DONE!!!!!!!");
		System.out.println("WHOLE SAND - " + transportedSandPile.getWeight() + "kg - WAS TRANSPORTED");
	}
}
