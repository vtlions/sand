package com.vtlions.multithreading.sand;

import java.util.concurrent.Semaphore;

public class Main {

	public static void main(String[] args) {
		Semaphore semaphoreLoader = new Semaphore(1);
		Semaphore semaphoreTransporter = new Semaphore(0);
		Semaphore semaphoreUnloader = new Semaphore(0);

		SandPile sandPile = new SandPile();
		Cart cart = new Cart(semaphoreLoader, semaphoreTransporter, semaphoreUnloader);
		TransportedSandPile transportedSandPile = new TransportedSandPile();

		new Loader(sandPile, cart, semaphoreLoader, semaphoreTransporter);
		Transporter transporter = new Transporter(semaphoreLoader, semaphoreUnloader, cart);
		new Unloader(transportedSandPile, cart, semaphoreUnloader, semaphoreTransporter,
				transporter, sandPile);
	}
}
