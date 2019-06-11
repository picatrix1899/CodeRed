package com.codered.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ManagedFuturePool<T>
{
	private ExecutorService threadPool;
	private CompletionService<T> compService;
	
	private Set<Future<T>> activeFutures = new HashSet<Future<T>>();
	private int pendingFutures = 0;
	
	private Thread routine;
	private boolean isRunning = false;
	
	public ManagedFuturePool(String threadname)
	{
		this();
		
		this.routine.setName(threadname);
	}
	
	public ManagedFuturePool()
	{
		this.threadPool = Executors.newFixedThreadPool(10, (p) -> {
			Thread t = new Thread(p);
			t.setDaemon(true);
			return t;
			});
		this.compService = new ExecutorCompletionService<T>(this.threadPool);
		this.routine = new Thread(() -> run());
		this.routine.setDaemon(true);
	}
	
	public void start()
	{
		this.isRunning = true;
		this.routine.start();
	}
	
	public void stop()
	{
		this.isRunning = false;
		cancel();
		
		this.threadPool.shutdownNow();
	}
	
	public int pendingFutures()
	{
		return this.pendingFutures;
	}
	
	public void run()
	{
		while(this.isRunning)
		{
			try
			{
				Future<T> f = this.compService.take();
				
				this.activeFutures.remove(f);
				this.pendingFutures--;
				
			} catch (InterruptedException e) { }
		}
	}
	
	public void cancel()
	{
		for(Future<T> f : this.activeFutures)
		{
			f.cancel(true);
		}
	}
	
	public void submit(Callable<T> task)
	{
		Future<T> f = this.compService.submit(task);
		this.activeFutures.add(f);
		this.pendingFutures++;
	}
}
