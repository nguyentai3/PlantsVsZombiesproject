import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
public class NewspaperZombie extends Zombie {
    public NewspaperZombie(GamePanel parent,int lane){
        super(parent,lane);
        health = 2000;
    }
    @Override
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
                    if(health>1000){
                    posX -= 1;
                    }
                    else if(health<=1000)
                    {
                        posX -= 5;
                    }

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
}
