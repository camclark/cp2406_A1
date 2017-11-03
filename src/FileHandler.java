import java.io.*;
import java.util.ArrayList;

public class FileHandler {
    public ArrayList<String> scoreList = new ArrayList<>();

    //constructor
    public FileHandler() {


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


    }




    public static void main(String[] args) {
        FileHandler t = new FileHandler();
    }
}