import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class Actions {

    int capacity;
    String[] textFile;
    boolean lastCommandQuit = false;

    public Actions(int capacity) {
        this.capacity = capacity;
        textFile = new String[capacity];
    }

    void list() {

        if(!lastCommandQuit) {
            // if last command was not quit, simply print the content of array
            for (String s : textFile) {
                if (s != null)
                    System.out.println(s);
            }
        } else {
            //list the content of text file persisted on disk
            String content = getContentFromFile();
            System.out.println(content);
        }
    }

    void insert(int lineNo, String text) {
        if(lineNo > capacity)
            throw new RuntimeException("Text Editor limit exceeded!");
        else {
            if(lastCommandQuit) {
                getContentFromFile();
            }
            textFile[lineNo-1] = lineNo + ":"+ text;
        }
    }

    void delete(int lineNo) {
        if(lineNo > capacity)
            throw new RuntimeException("No row found with the line no : " + lineNo+ " to be deleted");
        else {
            if(lastCommandQuit) {
                getContentFromFile();
            }
            textFile[lineNo-1] = null;
        }
    }

    void save() {

        //save content of array in text file only if last command was not quit
        if(!lastCommandQuit) {
            //save the content of array in a text file
            Path path = Paths.get("sample.txt");
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {

                //write the content of array to file only if its non-empty else just create empty file
                if(!Arrays.stream(textFile).allMatch(Objects::isNull)) {

                    //while writing to file, ignore null entries of array
                    //and add newline after every entry
                    writer.write(Arrays.stream(textFile).toList().stream()
                            .filter(Objects::nonNull)
                            .reduce((item1,item2) ->  item1 + "\n"  + item2)
                            .get());
                } else {
                    //create empty file if array is empty
                    File file = new File(path.getFileName().toString());
                    file.createNewFile();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    void quit() {
        //empty the content of array once quit is executed
        Arrays.fill(textFile, null);
        lastCommandQuit = true;
    }

    String getContentFromFile() {

        //if text file not present, this method will throw FileNotFoundException
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader("sample.txt"));
            String line = br.readLine();
            while (line != null) {
                //reading from the file and populating in array
                int lineNo = Character.getNumericValue(line.trim().charAt(0));
                textFile[lineNo-1] = line;

                //appending line by line from text file into a string builder
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        lastCommandQuit = false;
        return sb.toString();
    }
}