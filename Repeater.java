import java.awt.event.ActionEvent;
import javax.swing.*;
public class Repeater extends Plant {
    public Timer shootTimer;
    public Timer waiTimer;
    
    public Repeater(GamePanel parent,int x,int y) {
        super(parent,x,y);
        shootTimer = new Timer(2000,(ActionEvent e) -> {
            if(gp.laneZombies.get(y).size() > 0) {
                gp.lanePeas.get(y).add(new Pea(gp, y, 103 + this.x * 100));
                gp.lanePeas.get(y).add(new Pea(gp, y, 103 + this.x * 100+100));
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop(){
        shootTimer.stop();
    }
}
