import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class Pannello extends JPanel implements ActionListener{
	
	static final int LARGHEZZA_SCHERMO = 600;
	static final int ALTEZZA_SCHERMO = 600;
	static final int DIMENSIONE_UNITA = 25;
	static final int UNITA_GIOCO = (LARGHEZZA_SCHERMO*ALTEZZA_SCHERMO)/DIMENSIONE_UNITA;
	static final int DELAY = 80;
	final int x[] = new int [UNITA_GIOCO];
	final int y[] = new int [UNITA_GIOCO];
	int partiCorpo = 6;
	int meleMangiate;
	int melaX;
	int melaY;
	char direzione = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
 Pannello(){
	 
	 random = new Random();
	 this.setPreferredSize(new Dimension(LARGHEZZA_SCHERMO, ALTEZZA_SCHERMO));
	 this.setBackground(Color.black);
	 this.setFocusable(true);
	 this.addKeyListener(new AdattatoreTasti());
	 avviaGioco();
 }
 
 public void avviaGioco() {
	 nuovaMela();
	 running = true;
	 timer = new Timer(DELAY, this);
	 timer.start();
	
 }
 
 public void paintComponent(Graphics g){
	 super.paintComponent(g);
	 draw(g);
	 
 }
 
 public void draw (Graphics g) {
	 
	 if(running) {
	     for(int i=0;i<ALTEZZA_SCHERMO/DIMENSIONE_UNITA;i++) {
		     g.drawLine(i*DIMENSIONE_UNITA, 0, i*DIMENSIONE_UNITA, ALTEZZA_SCHERMO);
		     g.drawLine(0,i*DIMENSIONE_UNITA, LARGHEZZA_SCHERMO, i*DIMENSIONE_UNITA);
	 }
	     
	 g.setColor(Color.red);
	 g.fillOval(melaX, melaY, DIMENSIONE_UNITA, DIMENSIONE_UNITA);
	 
	     for(int i = 0; i< partiCorpo; i++) {
		     if (i == 0) {
			    g.setColor(Color.green);
			    g.fillRect(x[i], y[i], DIMENSIONE_UNITA, DIMENSIONE_UNITA);
		 }
		     else {
			   //g.setColor(new Color(45,180,0)); colore fisso verde
			    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
			    g.fillRect(x[i], y[i], DIMENSIONE_UNITA, DIMENSIONE_UNITA);
		     }
	   }
	     g.setColor(Color.red);
		 g.setFont(new Font("Ink Free", Font.BOLD,40));
		 FontMetrics metrics = getFontMetrics(g.getFont());
		 g.drawString("Punti: " +meleMangiate, (LARGHEZZA_SCHERMO - metrics.stringWidth("Punti: " +meleMangiate))/2, g.getFont().getSize());
	 }
	 else {
		 gameOver(g);
	 }
	 
 }
 
 public void nuovaMela() {
	 melaX = random.nextInt((int)(LARGHEZZA_SCHERMO/DIMENSIONE_UNITA))*DIMENSIONE_UNITA;
	 melaY = random.nextInt((int)(ALTEZZA_SCHERMO/DIMENSIONE_UNITA))*DIMENSIONE_UNITA;
 }
 
 public void muovi () {
	 for(int i=partiCorpo;i>0;i--) {
		 x[i] = x[i-1];
		 y[i] = y[i-1];
	 }
	 
	 switch(direzione) {
	 case 'U':
		 y[0] = y[0] - DIMENSIONE_UNITA;
		 break;
	 case 'D':
		 y[0] = y[0] + DIMENSIONE_UNITA;
		 break;
	 case 'L':
		 x[0] = x[0] - DIMENSIONE_UNITA;
		 break;
	 case 'R':
		 x[0] = x[0] + DIMENSIONE_UNITA;
		 break;
	 }
	 
 }
 
 public void controllaMele() {
	 if((x[0] == melaX) && (y[0] == melaY)) {
		partiCorpo++; 
		meleMangiate++;
		nuovaMela();
	 }
	 
 }
 
 public void controllaScontri() {
	//game over, controlla se la testa tocca con il corpo
	 for(int i = partiCorpo; i>0;i--) {
		 if((x[0] == x[i] && (y[0] == y[i]))) {
			 running = false; 
		 }
	 }
	 //controlla se la testa tocca il bordo sinistro
	 if(x[0] < 0) {
		 running = false;
	 }
	 //controlla se la testa tocca il bordo destro
	 if(x[0] > LARGHEZZA_SCHERMO) {
		 running = false;
	 }
	 //controlla se la testa tocca il bordo superiore
	 if(y[0] < 0) {
		 running = false;
	 }
	 //controlla se la testa tocca il bordo inferiore
	 if(y[0] > ALTEZZA_SCHERMO) {
		 running = false;
	 }
	 
	 if(!running) {
		 timer.stop();
	 }
 }
 
 public void gameOver(Graphics g) {
	 //Punteggio
	 g.setColor(Color.red);
	 g.setFont(new Font("Ink Free", Font.BOLD,40));
	 FontMetrics metrics1 = getFontMetrics(g.getFont());
	 g.drawString("Punti: " +meleMangiate, (LARGHEZZA_SCHERMO - metrics1.stringWidth("Punti: " +meleMangiate))/2, g.getFont().getSize());
	 //Testo Game Over
	 g.setColor(Color.red);
	 g.setFont(new Font("Ink Free", Font.BOLD,75));
	 FontMetrics metrics2 = getFontMetrics(g.getFont());
	 g.drawString("GAME OVER", (LARGHEZZA_SCHERMO - metrics2.stringWidth("GAME OVER"))/2, ALTEZZA_SCHERMO/2);
	 
 }
 @Override
 public void actionPerformed(ActionEvent e) {
		
		if(running) {
			muovi();
			controllaMele();
			controllaScontri();
		}
		repaint();
		
	}
	
	public class AdattatoreTasti extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direzione != 'R') {
					direzione = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direzione != 'L') {
					direzione = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direzione != 'D') {
					direzione = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direzione != 'U') {
					direzione = 'D';
				}
				break;
			}
		}
	}

}
