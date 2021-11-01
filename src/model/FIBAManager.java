package model;

import thread.LoadDataThread;
import tree.BalancedBinaryTree;
import tree.BinarySearchTree;
import tree.TreeException;
import tree.TreeInterface;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FIBAManager {

    private long searchTime;

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

    private BinarySearchTree<Integer, Double> pointsTree;
    private BalancedBinaryTree<Integer, Double> reboundsTree;
    private BalancedBinaryTree<Integer, Double> assistsTree;
    private BalancedBinaryTree<Integer, Double> stealsTree;
    private BalancedBinaryTree<Integer, Double> blocksTree;

    public FIBAManager() throws IOException {
        searchTime = 0L;
        dataFile = new File(fileURL);
        pointsTree = new BinarySearchTree<>();
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

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public long getSearchTime() {
        return searchTime;
    }

    private void loadTreesData() {
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

    public boolean addPlayer(String name, int age, String team, double points, double rebounds, double assists, double steals, double blocks) {
        try {
            reader = new BufferedReader(new FileReader(dataFile));
            Player toAdd = new Player(name, age, team, points, rebounds, assists, steals, blocks);
            writer.write(toAdd.getInfoWithSeparator(SEPARATOR) + "\n");
            writer.flush();
            int key = (int) reader.lines().count();
            pointsTree.insert(key, points);
            reboundsTree.insert(key, rebounds);
            assistsTree.insert(key, assists);
            stealsTree.insert(key, steals);
            blocksTree.insert(key, blocks);
            reader.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    public boolean addPlayer(final boolean header, final String filePath, final String s) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = "";
            if(header)
                line = reader.readLine();
            int key = (int) new BufferedReader(new FileReader(dataFile)).lines().count() + 1;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(s);
                Player toAdd = new Player(parts);
                writer.write(toAdd.getInfoWithSeparator(SEPARATOR) + "\n");
                writer.flush();
                pointsTree.insert(key, toAdd.getPoints());
                reboundsTree.insert(key, toAdd.getRebounds());
                assistsTree.insert(key, toAdd.getAssists());
                stealsTree.insert(key, toAdd.getSteals());
                blocksTree.insert(key, toAdd.getBlocks());
                ++ key;
            }
            reader.close();
            return true;
        } catch (IOException ignored) {
            return false;
        }
    }

    public List<Player> searchPlayersByName(String name) {
        searchTime = System.nanoTime();
        List<Player> playersFound = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(dataFile));
            String line;
            int key = 1;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                if(checkNameContent(parts[NAME_INDEX].toLowerCase(), name.toLowerCase().split(" "))) {
                    Player found = new Player(parts);
                    found.setKey(key);
                    playersFound.add(found);
                }
                ++ key;
            }
            reader.close();
        } catch (IOException ignored) {}
        searchTime = (long) ((System.nanoTime() - searchTime) / 1e+6);
        return playersFound;
    }

    private boolean checkNameContent(String toCheck, String[] content) {
        for (String s : content) {
            if (toCheck.contains(s))
                return true;
        }
        return false;
    }

    private TreeInterface<Integer, Double> getTreeToSearch(Criteria criteria) {
        switch(criteria) {
            case POINTS:
                return pointsTree;
            case REBOUNDS:
                return reboundsTree;
            case ASSISTS:
                return assistsTree;
            case STEALS:
                return stealsTree;
            case BLOCKS:
                return blocksTree;
            default:
                return null;
        }
    }

    public List<Player> searchPlayersInRange(Criteria criteria, double min, double max) {
        searchTime = System.nanoTime();
        List<Player> playersFound = new ArrayList<>();
        List<Integer> keys = getTreeToSearch(criteria).getKeysInRange(min, max);
        if(!keys.isEmpty()) {
            Collections.sort(keys);
            try {
                reader = new BufferedReader(new FileReader(dataFile));
                String line;
                int i = 1;
                int j = 0;
                while(j < keys.size() && (line = reader.readLine()) != null) {
                    if(i == keys.get(j)) {
                        String[] parts = line.split(SEPARATOR);
                        Player player = new Player(parts);
                        player.setKey(keys.get(j));
                        playersFound.add(player);
                        ++ j;
                    }
                    ++ i;
                }
                reader.close();
            } catch (IOException ignored) {}
        }
        searchTime = (long) ((System.nanoTime() - searchTime) / 1e+6);
        return playersFound;
    }

    public List<Player> searchPlayersByValue(Criteria criteria, double value) {
        searchTime = System.nanoTime();
        List<Player> playersFound = new ArrayList<>();
        List<Integer> keys = getTreeToSearch(criteria).getKeysEqualTo(value);
        if(!keys.isEmpty()) {
            Collections.sort(keys);
            try {
                reader = new BufferedReader(new FileReader(dataFile));
                String line;
                int i = 1;
                int j = 0;
                while(j < keys.size() && (line = reader.readLine()) != null) {
                    if(i == keys.get(j)) {
                        String[] parts = line.split(SEPARATOR);
                        Player player = new Player(parts);
                        player.setKey(keys.get(j));
                        playersFound.add(player);
                        ++ j;
                    }
                    ++ i;
                }
                reader.close();
            } catch (IOException ignored) {}
        }
        searchTime = (long) ((System.nanoTime() - searchTime) / 1e+6);
        return playersFound;
    }

    public boolean changePlayer(Player old, String name, int age, String team, double points, double rebounds, double assists, double steals, double blocks) {
        int key = old.getKey();
        Player player = new Player(name, age, team, points, rebounds, assists, steals, blocks);
        String newLine = player.getInfoWithSeparator(SEPARATOR);
        try {
            reader = new BufferedReader(new FileReader(dataFile));
            StringBuffer sb = new StringBuffer();
            String line;
            int i = 1;
            boolean found = false;
            while((line = reader.readLine()) != null) {
                if(i == key) {
                    sb.append(newLine);
                    found = true;
                } else {
                    sb.append(line);
                }
                sb.append("\n");
                ++ i;
            }
            reader.close();
            FileOutputStream fos = new FileOutputStream(dataFile);
            fos.write(sb.toString().getBytes());
            fos.close();
            if(found) {
                if(points != old.getPoints())
                    removeAndAdd(pointsTree, key, points);
                if(rebounds != old.getRebounds())
                    removeAndAdd(reboundsTree, key, rebounds);
                if(assists != old.getAssists())
                    removeAndAdd(assistsTree, key, assists);
                if(steals != old.getSteals())
                    removeAndAdd(stealsTree, key, steals);
                if(blocks != old.getBlocks())
                    removeAndAdd(blocksTree, key, blocks);
            }
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    private void removeAndAdd(TreeInterface<Integer, Double> tree, int key, double value) {
        try {
            tree.remove(key);
            tree.insert(key, value);
        } catch(TreeException ignored) {}
    }

    public boolean deletePlayer(int key) {
        try{
            reader = new BufferedReader(new FileReader(dataFile));
            StringBuffer sb = new StringBuffer();
            String line;
            int i = 1;
            boolean found = false;
            List<Integer> keysToChange = new ArrayList<>();
            while((line = reader.readLine()) != null) {
                if(i != key) {
                    sb.append(line);
                    sb.append("\n");
                    if(found)
                        keysToChange.add(i);
                } else
                    found = true;
                ++ i;
            }
            changeKeys(keysToChange);
            reader.close();
            FileOutputStream fos = new FileOutputStream(dataFile);
            fos.write(sb.toString().getBytes());
            fos.close();
            return true;
        } catch(IOException e) {
            return false;
        }
    }

    private void changeKeys(List<Integer> oldKeys) {
        for(int i = 0; i < oldKeys.size(); i ++) {
            int o = oldKeys.get(i);
            int n = o - 1;
            pointsTree.changeKey(o, n);
            reboundsTree.changeKey(o, n);
            assistsTree.changeKey(o, n);
            stealsTree.changeKey(o, n);
            blocksTree.changeKey(o, n);
        }
    }

}
