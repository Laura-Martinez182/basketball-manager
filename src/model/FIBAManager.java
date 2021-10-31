package model;

import thread.LoadDataThread;
import tree.BalancedBinaryTree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FIBAManager {

    private int searchTime;

    private final int NAME_INDEX = 0;
    private final int AGE_INDEX = 1;
    private final int TEAM_INDEX = 2;
    private final int POINTS_INDEX = 3;
    private final int REBOUNDS_INDEX = 4;
    private final int ASSISTS_INDEX = 5;
    private final int STEALS_INDEX = 6;
    private final int BLOCKS_INDEX = 7;

    private final String fileURL = "app_data.csv";
    private final String SEPARATOR = ",";
    private File dataFile;

    private BufferedReader reader;
    private PrintWriter writer;

    private BalancedBinaryTree<Integer, Double> pointsTree;
    private BalancedBinaryTree<Integer, Double> reboundsTree;
    private BalancedBinaryTree<Integer, Double> assistsTree;
    private BalancedBinaryTree<Integer, Double> stealsTree;
    private BalancedBinaryTree<Integer, Double> blocksTree;

    public FIBAManager() throws IOException {
        searchTime = 0;
        dataFile = new File(fileURL);
        pointsTree = new BalancedBinaryTree<>();
        reboundsTree = new BalancedBinaryTree<>();
        assistsTree = new BalancedBinaryTree<>();
        stealsTree = new BalancedBinaryTree<>();
        blocksTree = new BalancedBinaryTree<>();
        if(!dataFile.exists()) {
            dataFile.createNewFile();
        }
        writer = new PrintWriter(new FileWriter(fileURL, true));
        loadTreesData();
    }

    public void setSearchTime(int searchTime) {
        this.searchTime = searchTime;
    }

    public int getSearchTime() {
        return searchTime;
    }

    private void loadTreesData() throws IOException {
        LoadDataThread pointsThread = new LoadDataThread(pointsTree, dataFile, POINTS_INDEX, SEPARATOR);
        LoadDataThread reboundsThread = new LoadDataThread(reboundsTree, dataFile, REBOUNDS_INDEX, SEPARATOR);
        LoadDataThread assistsThread = new LoadDataThread(assistsTree, dataFile, ASSISTS_INDEX, SEPARATOR);
        LoadDataThread stealsThread = new LoadDataThread(stealsTree, dataFile, STEALS_INDEX, SEPARATOR);
        LoadDataThread blocksThread = new LoadDataThread(blocksTree, dataFile, BLOCKS_INDEX, SEPARATOR);
        pointsThread.start();
        reboundsThread.start();
        assistsThread.start();
        stealsThread.start();
        blocksThread.start();
    }

    public void addPlayer(String name, int age, String team, double points, double rebounds, double assists, double steals, double blocks) {
        try {
            reader = new BufferedReader(new FileReader(dataFile));
            Player toAdd = new Player(name, age, team, points, rebounds, assists, steals, blocks);
            writer.println(toAdd.getInfoWithSeparator(SEPARATOR));
            int key = (int) reader.lines().count();
            pointsTree.insert(key, points);
            reboundsTree.insert(key, rebounds);
            assistsTree.insert(key, assists);
            stealsTree.insert(key, steals);
            blocksTree.insert(key, blocks);
            reader.close();
        } catch (IOException ignored) {}
    }

    public List<Player> searchPlayersByName(String name) {
        List<Player> playersFound = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(dataFile));
            String line = reader.readLine();
            int key = 1;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                if(parts[NAME_INDEX].trim().equalsIgnoreCase(name.trim())) {
                    Player found = new Player(parts);
                    found.setKey(key);
                    playersFound.add(found);
                }
            }
            reader.close();
        } catch (IOException ignored) {}
        return playersFound;
    }

    public void deletePlayer() {

    }

}
