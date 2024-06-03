import javax.swing.JFrame;

public class FinestraDiGioco extends JFrame {

	FinestraDiGioco(){
		
		this.add(new Pannello());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
