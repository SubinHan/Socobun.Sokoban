package panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.google.firebase.database.DataSnapshot;

import manageUser.FirebaseClass;
import manageUser.UserInfo;

public class Panel_Ranking extends JPanel{
	
	Frame_Sokoban frame = null;
	
	//RankingPanel
	JScrollPane scrollingPanel;
	JPanel rankingPanelGrid = new JPanel();
	
	//UserInfos Sorted by wholeScore
	LinkedList<UserInfo> rankedUsers;
	
	public Panel_Ranking() {

		frame = Frame_Sokoban.getInstance();
		this.makeRanking();
		
		initLayout();
		initCenter();
		
	}
	
	private void initLayout() {

		//Layout
		this.setLayout(new BorderLayout());
		
		//Margin
		JPanel southMargin = new JPanel();
		southMargin.setPreferredSize(new Dimension(0, 100));
		JPanel westMargin = new JPanel();
		westMargin.setPreferredSize(new Dimension(300, 0));
		JPanel eastMargin = new JPanel();
		eastMargin.setPreferredSize(new Dimension(300, 0));

		this.add(southMargin, BorderLayout.SOUTH);
		this.add(westMargin, BorderLayout.WEST);
		this.add(eastMargin, BorderLayout.EAST);

		//Title
		JLabel title = new JLabel("Ranking");
		title.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setPreferredSize(new Dimension(0, 300));

		this.add(title, BorderLayout.NORTH);
	}
	
	// LinkedLis<UserInfo>를 UserInfo.wholeScore를 기준으로 정렬된 상태로 채우기
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

		makeRanking();
		addRankingPanel();
		
	}
	
	private void addRankingPanel() {
		
		
		rankingPanelGrid.setLayout(new GridLayout(rankedUsers.size(),1));
		
		// rankingPanelGrid에 랭킹 올리기
		addRankingContent();
		
		scrollingPanel = new JScrollPane(rankingPanelGrid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollingPanel.getVerticalScrollBar().setUnitIncrement(15);
		scrollingPanel.setBorder(null);
		scrollingPanel.setPreferredSize(new Dimension(580, 580));
		
		this.add(scrollingPanel);
	}
	
	private void addRankingContent() {

		int userCnt = rankedUsers.size();
		
		for(int i=0 ; i<userCnt ; ++i) {
			
			LineBorder lb = new LineBorder(Color.black, 1, false);
			UserInfo user = rankedUsers.get(i);
			
			// 랭킹 하나에 대한 틀(mold)
			JPanel eachRanking = new JPanel();
			eachRanking.setLayout(new GridLayout(1,3));
			
			// 1. 순위
			JLabel rank = new JLabel((i+1) + "위");
			rank.setHorizontalAlignment(JLabel.CENTER);
			rank.setBorder(lb);
			eachRanking.add(rank);
			
			// 2. 닉네임
			JLabel nickname = new JLabel(user.nickname);
			nickname.setHorizontalAlignment(JLabel.CENTER);
			nickname.setBorder(lb);
			eachRanking.add(nickname);

			// 3. 점수
			JLabel score = new JLabel(user.wholeScore + " 점");
			score.setHorizontalAlignment(JLabel.CENTER);
			score.setBorder(lb);
			eachRanking.add(score);
			
			rankingPanelGrid.add(eachRanking);
			
		}
		
	}
	
	public void refresh() {
		
		// 제대로 안만들었음
		makeRanking();
		addRankingPanel();
		
		revalidate();
		repaint();
		
	}
}