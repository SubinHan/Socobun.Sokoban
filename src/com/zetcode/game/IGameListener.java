package com.zetcode.game;

public interface IGameListener {
	public void moved();
	public void undid();
	public void restarted();
	public void completed();
	
}
