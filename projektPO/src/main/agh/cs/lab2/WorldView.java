package agh.cs.lab2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")

public class WorldView extends JPanel{

    private static final int TIMER_DELAY = 200;
    private StartAction startAction = new StartAction();
    private StopAction stopAction = new StopAction();
    private JButton button = new JButton(startAction);
    private DefaultListModel<Integer> model = new DefaultListModel<>();
    private JList<Integer> jList = new JList<>(model);
    private Timer timer = new Timer(TIMER_DELAY, new TimerListener());
    private static World ourFirstWorld = new World();
    private static World ourSecondWorld = new World();
    public int mapWidth = ourFirstWorld.map.parameters.windowWidth/2-50;
    public int mapHeight = ourFirstWorld.map.parameters.windowHeight-100;
    private int cellWidth = mapWidth /(ourFirstWorld.map.width+1);
    private int cellHeight = mapHeight /(ourFirstWorld.map.height+1);
    private JPanel btnPanel = new JPanel();
    JTextField animalNumber = new JTextField();
    JTextField averageEnergy = new JTextField();
    JTextField grassNumber = new JTextField();
    JTextField babiesNumber = new JTextField();
    JTextField commonGen = new JTextField();



    public WorldView() {
        btnPanel.add(button);

        btnPanel.add(new JLabel("Animals:"));
        btnPanel.add(animalNumber);
        btnPanel.add(new JLabel("Avg energy:"));
        btnPanel.add(averageEnergy);
        btnPanel.add(new JLabel("Grass:"));
        btnPanel.add(grassNumber);
        btnPanel.add(new JLabel("Babies:"));
        btnPanel.add(babiesNumber);
        btnPanel.add(new JLabel("Popular gen:"));
        btnPanel.add(commonGen);

        this.setStatistics();
        setLayout(new BorderLayout());
        //add(ourFirstWorld, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.PAGE_END);
    }

    public void paint(Graphics g){

        int jungleWidth = ourFirstWorld.map.jungleWidth*cellWidth;
        int jungleHeight = ourFirstWorld.map.jungleHeight*cellHeight;

        int firstJungleStartX = (ourFirstWorld.map.width/2-ourSecondWorld.map.jungleWidth/2)*cellWidth;
        int firstJungleStartY = (ourFirstWorld.map.height/2-ourSecondWorld.map.jungleHeight/2)*cellHeight;

        g.setColor(Color.YELLOW);
        g.fillRect(0,0, mapWidth, mapHeight);
        g.fillRect(mapWidth + 50,0, mapWidth, mapHeight);

        g.setColor(Color.GREEN);
        g.fillRect(firstJungleStartX,firstJungleStartY, jungleWidth, jungleHeight);
        g.fillRect(firstJungleStartX + mapWidth + 50,firstJungleStartY, jungleWidth, jungleHeight);

        this.paintAnimals(g);
        this.paintGrass(g);
        btnPanel.updateUI();

    }

    public void setStatistics(){
        animalNumber.setText(Integer.toString(ourFirstWorld.stats.currentNumberOfAnimals));
        grassNumber.setText(Integer.toString(ourFirstWorld.stats.currentNumberOfGrass));
        double energyAvg = ourFirstWorld.stats.currentNumberOfAnimals == 0 ? 0 :
                Math.round(100.0*ourFirstWorld.stats.currentSumOfEnergy/ourFirstWorld.stats.currentNumberOfAnimals)/100.0;
        averageEnergy.setText(Double.toString(energyAvg));
        babiesNumber.setText(Integer.toString(ourFirstWorld.stats.currentNumberOfBabies));
        commonGen.setText(ourFirstWorld.stats.currentlyMostCommonGen);
    }

    private void paintAnimals(Graphics g){
        for(Object animal: ourFirstWorld.map.animals.values().toArray()){
            Animal element = (Animal)animal;

            int x = element.getX()*cellWidth+2;
            int y = element.getY()*cellHeight+2;
            g.setColor(element.color());
            g.fillRect(x,y,cellWidth-4,cellHeight-4);
        }

        for(Object animal: ourSecondWorld.map.animals.values().toArray()){
            Animal element = (Animal)animal;

            int x = mapWidth + 50 + element.getX()*cellWidth+2;
            int y = element.getY()*cellHeight+2;
            g.setColor(element.color());
            g.fillRect(x,y,cellWidth-4,cellHeight-4);
        }
    }

    private void paintGrass(Graphics g){
        for(Vector2d grass: ourFirstWorld.map.grass){
            int x = grass.getX()*cellWidth;
            int y = grass.getY()*cellHeight;
            g.setColor(new Color(25, 133, 42));
            g.fillRect(x,y,cellWidth,cellHeight);
        }

        for(Vector2d grass: ourSecondWorld.map.grass){
            int x = mapWidth + 50 + grass.getX()*cellWidth;
            int y = grass.getY()*cellHeight;
            g.fillRect(x,y,cellWidth,cellHeight);
        }
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ourFirstWorld.startBeautifulDay();
            ourSecondWorld.startBeautifulDay();

            setStatistics();
            repaint();
        }
    }

    private class StartAction extends AbstractAction {
        public StartAction() {
            super("Start");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timer.start();
            button.setAction(stopAction);
        }
    }

    private class StopAction extends AbstractAction {
        public StopAction() {
            super("Stop");
            putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            button.setAction(startAction);
        }
    }


    private static void createAndShowGui() {
        JFrame frame = ourFirstWorld.frame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new WorldView());

        frame.setLocationRelativeTo(null);
        ourFirstWorld.startWorld();
        ourSecondWorld.startWorld();

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());

        }
    }
