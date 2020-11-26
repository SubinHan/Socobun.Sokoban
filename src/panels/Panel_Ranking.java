package panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.google.firebase.database.DataSnapshot;

import manageUser.FirebaseClass;
import manageUser.UserInfo;

public class Panel_Ranking extends JPanel{
	
	private static final long serialVersionUID = 4962034188446655988L;
	
	Frame_Sokoban frame = null;
	
	JScrollPane rankingListedPanel;
	
	int usersCount;
	LinkedList<UserInfo> rankedUsers;
	
	public Panel_Ranking() {

		frame = Frame_Sokoban.getInstance();
		this.makeRanking();
		
		initLayout();
		makeRanking();
		initCenter();
		
	}
	
	private void initLayout() {

		//Layout
		this.setLayout(new BorderLayout());
		
		//Margin
		JPanel southMargin = new JPanel();
		southMargin.setPreferredSize(new Dimension(0, 300));
		JPanel westMargin = new JPanel();
		westMargin.setPreferredSize(new Dimension(300, 0));
		JPanel eastMargin = new JPanel();
		eastMargin.setPreferredSize(new Dimension(300, 0));

		this.add(southMargin, BorderLayout.SOUTH);
		this.add(westMargin, BorderLayout.WEST);
		this.add(eastMargin, BorderLayout.EAST);

		//Title
		JLabel title = new JLabel("Ranking");
		title.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 300));

		this.add(title, BorderLayout.NORTH);
	}
	
	private void makeRanking() {

		Iterable<DataSnapshot> infoIterable = FirebaseClass.dataSnapshot.child("users").getChildren();
		Iterator<DataSnapshot> infoIterator = infoIterable.iterator();

		rankedUsers = new LinkedList<>();
		
		while( infoIterator.hasNext() ) {
			
			DataSnapshot ds = infoIterator.next();
			UserInfo info = ds.getValue(UserInfo.class);
			
			if(rankedUsers.isEmpty()) {
				
				rankedUsers.add(info);
				
			} else {
				
				for(int i=0 ; i<rankedUsers.size() ; ++i) {
					
					if(rankedUsers.get(i).wholeScore < info.wholeScore) {
						
						if(i == 0) {
							rankedUsers.addFirst(info);
						} else {
							rankedUsers.add(i, info);
						}
						break;
					}
				}
				
			}
		}
	}
	
	private void initCenter() {
		
		for(int i=0 ; i<rankedUsers.size() ; ++i) {
			System.out.println( rankedUsers.get(i).wholeScore);
		}
		
	}
	
	public void refresh() {
		
		makeRanking();
		revalidate();
		repaint();
		
	}
}