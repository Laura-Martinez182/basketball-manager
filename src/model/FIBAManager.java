package model;

import thread.LoadDataThread;
import tree.BalancedBinaryTree;

import java.io.*;

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
        reader = new BufferedReader(new FileReader(dataFile));
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

    public void addPlayer() {

    }

    public void deletePlayer() {

    }

}
