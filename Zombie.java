
import javax.swing.*;

public class Zombie {

    public int health = 1000;
    public int speed = 1;

    protected GamePanel gp;

    public int posX = 1000;
    public int myLane;
    public boolean isMoving = true;

    public Zombie(GamePanel parent,int lane){
        this.gp = parent;
        myLane = lane;
    }

    public void advance(){
        if(isMoving) {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.colliders[i].assignedPlant != null && gp.colliders[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.colliders[i];
                }
            }
            if (!isCollides) {
                if(slowInt>0){
                    if(slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                }else {
                    posX -= 1;
                }
            } else {
                collided.assignedPlant.health -= 10;
                if (collided.assignedPlant.health < 0) {
                    collided.removePlant();
                }
            }
            if (posX < 0) {
                isMoving = false;
                final ImageIcon i = new ImageIcon("images/ZombiesAteYourBrains.png");
                JOptionPane.showMessageDialog(gp,i);
                Maintest.gw.dispose();
                Maintest.gw = new Maintest();
            }
        }
    }

    int slowInt = 0;
    public void slow(){
        slowInt = 1000;
    }
    public static Zombie getZombie(String type,GamePanel parent, int lane) {
        Zombie z = new Zombie(parent,lane);
       switch(type) {
           case "NormalZombie" : z = new NormalZombie(parent,lane);
                                 break;
           case "ConeHeadZombie" : z = new ConeHeadZombie(parent,lane);
                                 break;
           case "NewspaperZombie" : z = new NewspaperZombie(parent,lane);
                                 break;  
           case "BucketZombie" : z = new BucketZombie(parent,lane);
                                 break;                             
    }
       return z;
    }

}
