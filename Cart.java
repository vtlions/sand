package com.vtlions.multithreading.sand;

import java.util.concurrent.Semaphore;

public class Cart {

	private Semaphore semaphoreLoader;
	private Semaphore semaphoreTransporter;
	private Semaphore semaphoreUnloader;
	private int cargo;
	private int result;
	private final int MAX_CAPACITY = 6;

	public Cart(final Semaphore semaphoreLoader, final Semaphore semaphoreTransporter,
			final Semaphore semaphoreUnloader) {
		this.semaphoreLoader = semaphoreLoader;
		this.semaphoreTransporter = semaphoreTransporter;
		this.semaphoreUnloader = semaphoreUnloader;
	}

	public int getMAX_CAPACITY() {
		return MAX_CAPACITY;
	}

	public int getCargo() {
		return cargo;
	}

	public void readyToTransport() {
		try {
			semaphoreTransporter.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int takeCargo(boolean isUnloading, int speedOfUnload) {
		result = 0;

		try {
			semaphoreUnloader.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (isUnloading) {

			if (speedOfUnload >= cargo) {
				result = cargo;
				cargo = 0;
			}

			if (speedOfUnload < cargo) {
				result = speedOfUnload;
				cargo -= speedOfUnload;
			}
		}

		return result;
	}

	public void addCargo(boolean isLoading, int weight) {

		try {
			semaphoreLoader.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		if (isLoading) {
			cargo += weight;
		}
	}
}
