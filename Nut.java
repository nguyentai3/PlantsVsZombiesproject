import java.awt.event.ActionEvent;
import javax.swing.*;
public class Nut extends Plant{
    public Timer rollTimer;
    public Nut(GamePanel parent, int x, int y) {
        super(parent, x, y);
        health = 2000;
        rollTimer = new Timer(20,(ActionEvent e) -> { 
            if(gp.laneZombies.get(y).size() > 0) {
                if (health < 10)
                {
                gp.laneNuts.get(y).add(new Rollingnut(gp, y, 103 + this.x * 100));
                }
            }
        });
    rollTimer.start();
    }
    @Override
    public void stop(){
        rollTimer.stop();
    }
}
