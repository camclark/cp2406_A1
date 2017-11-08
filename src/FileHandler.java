import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class FileHandler extends JFrame{
    public ArrayList<String> scoreList = new ArrayList<>();

    //constructor
    public FileHandler() {
        super("Highscores");
        setBounds(1,1,200,200);

        try {
            FileReader fr = new FileReader("highscores.txt");
            BufferedReader br = new BufferedReader(fr);

            String s;
            // do for loop to populate list

            while ((s = br.readLine()) != null) {
                scoreList.add(s);
                System.out.println(s);
            }

            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("File was not found!");
        } catch (IOException e) {
            System.out.println("No file found!");
        }

        try{
            FileWriter fw = new FileWriter("highscores.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(scoreList.get(0));

            for (int i = 1; i <scoreList.size(); i++){
                bw.write("\n"+scoreList.get(i));
            }


            bw.close();

        }

        catch(FileNotFoundException e)
            {
                System.out.println("File was not found!");
            }
        catch(IOException e)
            {
                System.out.println("No file found!");
            }

        JList<String> displayList = new JList<>(scoreList.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(displayList);

        getContentPane().add(scrollPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        FileHandler t = new FileHandler();
    }
}