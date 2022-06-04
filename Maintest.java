import javax.swing.*;
import java.awt.event.ActionEvent;


public class Maintest extends JFrame {

    enum PlantType{
        None,
        Sunflower,
        Peashooter,
        FreezePeashooter,
        Nut,
        Cherry, 
        Threepeater, Repeater
    }

    public Maintest(){
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37,80);
        sun.setSize(60,20);

        GamePanel gp = new GamePanel(sun);
        gp.setLocation(0,0);
        getLayeredPane().add(gp,0);
        
        PlantCard sunflower = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110,8);
        sunflower.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Sunflower;
        });
        getLayeredPane().add(sunflower,Integer.valueOf(3));

        PlantCard peashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175,8);
        peashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Peashooter;
        });
        getLayeredPane().add(peashooter,Integer.valueOf(3));

        PlantCard freezepeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240,8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.FreezePeashooter;
        });
        getLayeredPane().add(freezepeashooter,Integer.valueOf(3));

        PlantCard nut = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_wallnut.png")).getImage());
        nut.setLocation(305,8);
        nut.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Nut;
        });
        getLayeredPane().add(nut,Integer.valueOf(3));

        PlantCard repeater = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_repeater.png")).getImage());
        repeater.setLocation(370,8);
        repeater.setAction((ActionEvent e) -> {
            gp.activePlantingBrush = PlantType.Repeater;
        });
        getLayeredPane().add(repeater,Integer.valueOf(3));

        getLayeredPane().add(sun,Integer.valueOf(2));
        setResizable(false);
        setVisible(true);
    }
    public Maintest(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0,0);
        setSize(1012,785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu,Integer.valueOf(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }
    static Maintest gw;
    public static void begin() {
        gw.dispose();
       gw = new Maintest();
    }
    public static void main(String[] args) {
          gw = new Maintest(true);
    }

}
