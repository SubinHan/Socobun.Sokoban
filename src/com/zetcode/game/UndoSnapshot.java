package com.zetcode.game;

import com.zetcode.model.Actor_Baggage;

public class UndoSnapshot {

	public String dir;
	public Actor_Baggage bag;
	
	public UndoSnapshot(String d, Actor_Baggage b) {
		
		dir = d;
		bag = b;
		
		System.out.println(b);
		if(bag != null) System.out.println(bag.x() + ", " + bag.y());
	}
}
