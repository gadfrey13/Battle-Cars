package a1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;

public class PromptBox extends JFrame {

	private JPanel contentPane;
	private JRadioButton rdbtnBrown;
	private JRadioButton rdbtnBlue;
	private JRadioButton rdbtnYellow;
	private JCheckBox chckbxFullScreen;
	private int color = 0;
	private boolean selected = false;
	private boolean fullScreen = false;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * Create the frame.
	 */
	public PromptBox() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JLabel lblChooseYourPlayer = new JLabel("CHOOSE YOUR PLAYER");
		sl_contentPane.putConstraint(SpringLayout.NORTH, lblChooseYourPlayer, 10, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, lblChooseYourPlayer, -83, SpringLayout.EAST, contentPane);
		lblChooseYourPlayer.setFont(new Font("Tahoma", Font.BOLD, 22));
		contentPane.add(lblChooseYourPlayer);
		
		JButton btnGo = new JButton("Go!");
		sl_contentPane.putConstraint(SpringLayout.WEST, btnGo, 186, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, btnGo, -10, SpringLayout.SOUTH, contentPane);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chckbxFullScreen.isSelected())
					fullScreen = true;
				if (rdbtnBrown.isSelected())
				{
					color = 0;
					selected = true;
				}
				else if (rdbtnBlue.isSelected())
				{
					color = 1;
					selected = true;
				}
				else if (rdbtnYellow.isSelected())
				{
					color = 2;
					selected = true;
				}
				else
					System.out.println("Choose a color");
				if (selected)
					contentPane.setVisible(false);
			}
		});
		contentPane.add(btnGo);
		
		rdbtnBrown = new JRadioButton("Brown");
		buttonGroup.add(rdbtnBrown);
		rdbtnBrown.setSelected(true);
		sl_contentPane.putConstraint(SpringLayout.NORTH, rdbtnBrown, 6, SpringLayout.SOUTH, lblChooseYourPlayer);
		sl_contentPane.putConstraint(SpringLayout.WEST, rdbtnBrown, 10, SpringLayout.WEST, contentPane);
		contentPane.add(rdbtnBrown);
		
		rdbtnBlue = new JRadioButton("Blue");
		buttonGroup.add(rdbtnBlue);
		sl_contentPane.putConstraint(SpringLayout.NORTH, rdbtnBlue, 0, SpringLayout.NORTH, rdbtnBrown);
		sl_contentPane.putConstraint(SpringLayout.EAST, rdbtnBlue, 0, SpringLayout.EAST, btnGo);
		contentPane.add(rdbtnBlue);
		
		rdbtnYellow = new JRadioButton("Yellow");
		buttonGroup.add(rdbtnYellow);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, rdbtnYellow, 0, SpringLayout.SOUTH, rdbtnBrown);
		sl_contentPane.putConstraint(SpringLayout.EAST, rdbtnYellow, -10, SpringLayout.EAST, contentPane);
		contentPane.add(rdbtnYellow);
		
		chckbxFullScreen = new JCheckBox("Full Screen");
		sl_contentPane.putConstraint(SpringLayout.NORTH, chckbxFullScreen, 59, SpringLayout.SOUTH, rdbtnBlue);
		sl_contentPane.putConstraint(SpringLayout.WEST, chckbxFullScreen, 169, SpringLayout.WEST, contentPane);
		contentPane.add(chckbxFullScreen);
		
	}
	public int getColor(){return color;}
	public boolean isSelected(){return selected;}
	public boolean getFullScreen(){return fullScreen;}
	public JPanel getContentPane(){return contentPane;}
}
