package com.vtlions.multithreading.sand;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Transporter implements Runnable {
	private Semaphore semaphoreLoader;
	private Semaphore semaphoreUnloader;
	private Cart cart;
	private boolean isForward = true;
	private boolean isStop;

	public Transporter(final Semaphore semaphoreLoader, final Semaphore semaphoreUnloader, final Cart cart) {
		this.semaphoreLoader = semaphoreLoader;
		this.semaphoreUnloader = semaphoreUnloader;
		this.cart = cart;
		new Thread(this).start();
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	@Override
	public void run() {

		while (!isStop) {
			cart.readyToTransport();

			if (!isStop) {

				if (isForward) {
					System.out.println("\n\nTRANSPORTER IS TRANSPORTING THE CART IN FORWARD DIRECTION");
				} else {
					System.out.println("\n\nTRANSPORTER IS TRANSPORTING THE CART IN BACKWARD DIRECTION");
				}

				System.out.println("==============================================================");
				System.out.println("Start transporting of the cart. Cargo = " + cart.getCargo() + "kg");

				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Cart has already been delivered\n");
				if (isForward) {
					semaphoreUnloader.release();
					isForward = false;
				} else {
					semaphoreLoader.release();
					isForward = true;
				}
			}
		}
	}
}
