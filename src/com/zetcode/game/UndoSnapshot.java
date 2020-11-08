package com.zetcode.game;

import com.zetcode.model.Actor_Baggage;

public class UndoSnapshot {

	public String dir;
	public Actor_Baggage bag;
	
	public UndoSnapshot(String d, Actor_Baggage b) {
		dir = d;
		
		if(b != null) bag = (Actor_Baggage)b.clone();
		else bag = b;
		
		System.out.println(b);
		if(bag != null) System.out.println(bag.x() + ", " + bag.y());
	}
}
