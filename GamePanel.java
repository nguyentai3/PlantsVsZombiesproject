import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JLayeredPane implements MouseMotionListener {

    Image bgImage;
    Image peashooterImage;
    Image freezePeashooterImage;
    Image sunflowerImage;
    Image peaImage;
    Image freezePeaImage;
    Image nutImage;
    Image repeaterImage;

    Image normalZombieImage;
    Image coneHeadZombieImage;
    Image bucketZombieImage;
    Image newspaperZombieImage;
    Collider[] colliders;
    
    ArrayList<ArrayList<Zombie>> laneZombies;
    ArrayList<ArrayList<Pea>> lanePeas;
    ArrayList<ArrayList<Rollingnut>> laneNuts;
    ArrayList<Sun> activeSuns;

    Timer redrawTimer;
    Timer advancerTimer;
    Timer sunProducer;
    Timer zombieProducer;
    JLabel sunScoreboard;

    Maintest.PlantType activePlantingBrush = Maintest.PlantType.None;

    int mouseX , mouseY;

    private int sunScore;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    public GamePanel(JLabel sunScoreboard){
        setSize(1000,752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(150);  //pool avalie

        bgImage  = new ImageIcon(this.getClass().getResource("images/mainBG.png")).getImage();

        peashooterImage = new ImageIcon(this.getClass().getResource("images/plants/peashooter.gif")).getImage();
        freezePeashooterImage = new ImageIcon(this.getClass().getResource("images/plants/freezepeashooter.gif")).getImage();
        sunflowerImage = new ImageIcon(this.getClass().getResource("images/plants/sunflower.gif")).getImage();
        peaImage = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        freezePeaImage = new ImageIcon(this.getClass().getResource("images/freezepea.png")).getImage();
        nutImage = new ImageIcon(this.getClass().getResource("images/plants/nut.gif")).getImage();
        repeaterImage = new ImageIcon(this.getClass().getResource("images/plants/repeater.gif")).getImage();

        normalZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie1.png")).getImage();
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie2.gif")).getImage();
        bucketZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie3.png")).getImage();
        newspaperZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/zombie4.gif")).getImage();

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        laneNuts = new ArrayList<>();
        laneNuts.add(new ArrayList<>()); //line 1
        laneNuts.add(new ArrayList<>()); //line 2
        laneNuts.add(new ArrayList<>()); //line 3
        laneNuts.add(new ArrayList<>()); //line 4
        laneNuts.add(new ArrayList<>()); //line 5

        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i%9)*100,109 + (i/9)*120);
            a.setAction(new PlantActionListener((i%9),(i/9)));
            colliders[i] = a;
            add(a,0);
        }


        activeSuns = new ArrayList<>();

        redrawTimer = new Timer(25,(ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(60,(ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(5000,(ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this,rnd.nextInt(800)+100,0,rnd.nextInt(300)+200);
            activeSuns.add(sta);
            add(sta,1);
        });
        sunProducer.start();

        zombieProducer = new Timer(7000,(ActionEvent e) -> {
            Random rnd = new Random();
            LevelData lvl = new LevelData();
            String [] Level = lvl.Level[Integer.parseInt(lvl.Lvl)-1];
            int [][] LevelValue = lvl.LevelValue[Integer.parseInt(lvl.Lvl)-1];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(100);
            Zombie z = null;
            for(int i = 0;i<LevelValue.length;i++) {
                if(t>=LevelValue[i][0]&&t<=LevelValue[i][1]) {
                    z = Zombie.getZombie(Level[i],GamePanel.this,l);
                }
            }
            laneZombies.get(l).add(z);
        });
        zombieProducer.start();

    }

    private void advance(){
        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }
            for (int j = 0; j < laneNuts.get(i).size(); j++) {
                Rollingnut n = laneNuts.get(i).get(j);
                n.advance();
            }
        }

        for (int i = 0; i < activeSuns.size() ; i++) {
            activeSuns.get(i).advance();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage,0,0,null);

        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if(c.assignedPlant != null){
                Plant p = c.assignedPlant;
                if(p instanceof Peashooter){
                    g.drawImage(peashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof FreezePeashooter){
                    g.drawImage(freezePeashooterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Sunflower){
                    g.drawImage(sunflowerImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Nut){
                    g.drawImage(nutImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
                if(p instanceof Repeater){
                    g.drawImage(repeaterImage,60 + (i%9)*100,129 + (i/9)*120,null);
                }
            }
        }

        for (int i = 0; i < 5 ; i++) {
            for(Zombie z : laneZombies.get(i)){
                if(z instanceof NormalZombie){
                    g.drawImage(normalZombieImage,z.posX,109+(i*120),null);
                }else if(z instanceof ConeHeadZombie){
                    g.drawImage(coneHeadZombieImage,z.posX,109+(i*120),null);
                }
                else if(z instanceof BucketZombie){
                    g.drawImage(bucketZombieImage,z.posX,109+(i*120),null);
                }
                else if(z instanceof NewspaperZombie){
                    g.drawImage(newspaperZombieImage,z.posX,109+(i*120),null);
                }
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                if(p instanceof FreezePea){
                    g.drawImage(freezePeaImage, p.posX, 130 + (i * 120), null);
                }else {
                    g.drawImage(peaImage, p.posX, 130 + (i * 120), null);
                }
            }
            for (int j = 0; j < laneNuts.get(i).size(); j++) {
                Rollingnut n = laneNuts.get(i).get(j);
                if(n instanceof Rollingnut){
                    g.drawImage(nutImage, n.posX, 130 + (i * 120), null);
                }
            }
        }
    }

    class PlantActionListener implements ActionListener {

        int x,y;

        public PlantActionListener(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(activePlantingBrush == Maintest.PlantType.Sunflower){
                if(getSunScore()>=50) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore()-50);
                }
            }
            if(activePlantingBrush == Maintest.PlantType.Peashooter){
                if(getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore()-100);
                }
            }

            if(activePlantingBrush == Maintest.PlantType.FreezePeashooter){
                if(getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore()-175);
                }
            }
            if(activePlantingBrush == Maintest.PlantType.Nut){
                if(getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Nut(GamePanel.this, x, y));
                    setSunScore(getSunScore()-50);
                }
            }
            if(activePlantingBrush == Maintest.PlantType.Repeater){
                if(getSunScore() >= 200) {
                    colliders[x + y * 9].setPlant(new Repeater(GamePanel.this, x, y));
                    setSunScore(getSunScore()-200);
                }
            }
            activePlantingBrush = Maintest.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    static int progress = 0;
    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
        if(progress>=150) {
           if("1".equals(LevelData.Lvl)) {
            JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "Starting next Level");
            Maintest.gw.dispose();
            LevelData.write("2");
            Maintest.gw = new Maintest();
            }  else {
               JOptionPane.showMessageDialog(null,"Level Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
               LevelData.write("1");
               System.exit(0);
           }
           progress = 0;
        }
    }
}
