package WindowBuilder;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DAO.MemberDAO;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField IDtextField;

	Main_Page main;
	private JPasswordField pwField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() throws ClassNotFoundException, SQLException { // 로그인 화면
		MemberDAO dao = new MemberDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 780);
		setTitle("로그인");
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(252, 247, 241));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		IDtextField = new JTextField(); // ID 입력 Field
		IDtextField.setFont(new Font("굴림", Font.PLAIN, 20));
		IDtextField.setBounds(165, 444, 299, 34);
		IDtextField.setColumns(10);
		IDtextField.setBorder(BorderFactory.createEmptyBorder()); // 테두리 x
		IDtextField.setOpaque(false);
		contentPane.add(IDtextField);
		
		JButton LoginButton = new JButton(""); // 로그인 버튼
		
		LoginButton.setBorderPainted(false); 
		LoginButton.setContentAreaFilled(false); 
		LoginButton.setFocusPainted(false); 
		
		LoginButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // 버튼 클릭 이벤트
				String id = IDtextField.getText();
				String pw = new String(pwField.getPassword());
				
				if(id.isEmpty()) { // ID TextField 빈 값 Check
					JOptionPane.showMessageDialog(null, "ID를 입력해주세요!"); // 빈 값일 경우 Message 표시 창 생성
					
					setVisible(true);
				} else if(pw.isEmpty()) { // PW PasswordField 빈 값 check
					JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요!"); // 빈 값일 경우 Message 표시 창 생성
					
					setVisible(true);
				} else {
					try {
						if(id.equals(dao.login_id(id, pw)) && pw.equals(dao.login_pw(id, pw))) { // DB에서 가져온 ID, PW 값과 입력한 ID, PW 값이 일치하는지 확인
							JOptionPane.showMessageDialog(null, "로그인 성공!");
							
							dispose();
								
							setVisible(false);
							
							main = new Main_Page();
							main.setVisible(true); // 메인 페이지 표시
							main.welcome_label.setText(id + "님 환영합니다!");
							main.str = id;
						} else {
							JOptionPane.showMessageDialog(null, "로그인 실패!");
							
							System.exit(0); // 강제 종료
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "로그인 오류!");
					}
				}
			}
		});
		
		LoginButton.setBackground(SystemColor.activeCaption);
		LoginButton.setForeground(Color.WHITE);
		LoginButton.setFont(new Font("Arial", Font.BOLD, 20));
		LoginButton.setBounds(241, 568, 87, 43);
		contentPane.add(LoginButton);
		
		JButton register_btn = new JButton(""); // 회원가입 버튼
		register_btn.setBorderPainted(false); 
		register_btn.setContentAreaFilled(false); 
		register_btn.setFocusPainted(false); 
		
		register_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) { // 버튼 클릭 이벤트
				dispose();
				
				setVisible(false);
				try {
					new Register().setVisible(true); // 회원가입 창 표시
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		register_btn.setBackground(SystemColor.activeCaption);
		register_btn.setFont(new Font("Arial", Font.BOLD, 20));
		register_btn.setForeground(Color.WHITE);
		register_btn.setBounds(323, 626, 99, 43);
		contentPane.add(register_btn);
		
		pwField = new JPasswordField(); // Password 입력 Field(비밀번호를 보이지 않도록 하기 위해 PasswordField를 사용)
		pwField.setOpaque(false);
		pwField.setFont(new Font("굴림", Font.PLAIN, 20));
		pwField.setBorder(BorderFactory.createEmptyBorder());
		pwField.setBounds(165, 510, 299, 34);
		contentPane.add(pwField);
		
		JLabel image_label = new JLabel("");
		image_label.setIcon(new ImageIcon(Login.class.getResource("/Image/login1.png")));
		image_label.setBounds(0, 0, 598, 741);
		contentPane.add(image_label);
	}
}