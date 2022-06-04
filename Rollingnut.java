import java.awt.*;
public class Rollingnut {
    public int posX;
    protected GamePanel gp;
    public int myLane;

    public Rollingnut(GamePanel parent,int lane,int startX){
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
    }
    public void advance(){
        Rectangle nRect = new Rectangle(posX,130+myLane*120,28,28);
        for (int i = 0; i < gp.laneZombies.get(myLane).size(); i++) {
            Zombie z = gp.laneZombies.get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.posX,109 + myLane*120,400,120);
            if(nRect.intersects(zRect)){
                z.health -= 9999;
                boolean exit = false;
                System.out.println("ZOMBIE DIE");
                gp.laneZombies.get(myLane).remove(i);
                GamePanel.setProgress(10);
                exit = true;
                gp.laneNuts.get(myLane).remove(this);
                if(exit) break;
            }
        }
        posX += 15;
    }
}
