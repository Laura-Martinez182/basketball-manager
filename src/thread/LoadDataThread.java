package thread;

import tree.BinarySearchTree;
import tree.TreeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoadDataThread extends Thread {

    private BufferedReader br;

    private BinarySearchTree<Integer, Double> tree;
    private final int index;
    private final String separator;
    private final File data;

    public LoadDataThread(BinarySearchTree<Integer, Double> tree, final File data, final int index, final String separator) {
        this.tree = tree;
        this.data = data;
        this.separator = separator;
        this.index = index;
        try {
            br = new BufferedReader(new FileReader(data));
        } catch (IOException ignored) {}
    }

    @Override
    public void run() {
        String line;
        try {
            int key = 1;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(separator);
                try {
                    double attribute = Double.parseDouble(parts[index]);
                    tree.insert(key, attribute);
                    ++ key;
                } catch(NumberFormatException | IndexOutOfBoundsException ignored) {}
            }
        } catch (IOException ignored) {}
    }

}
