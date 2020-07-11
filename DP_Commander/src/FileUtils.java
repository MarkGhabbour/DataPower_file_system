import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static javafx.scene.input.KeyCode.F;


public class FileUtils {
    public static String readFromFile(String path) throws IOException
    {
        try {

            List lines = Files.readAllLines(Paths.get(path),
                    StandardCharsets.UTF_8);
            StringBuilder sb = new StringBuilder(1024);

            for(int i=0; i<lines.size(); i++)
            {
                String line = (String) lines.get(i) + "\n";
                sb.append(line);
            }

            String fromFile = sb.toString();

            return fromFile;
        }

        catch (MalformedInputException e)
        {
            return null;
        }


    }


    public static void writeToFile(String path, String text) throws IOException
    {
        FileWriter writer = new FileWriter(path);
        writer.write(text);
        writer.close();
    }

    public static void replaceFromFile(String path, String match, String replacement) throws IOException
    {
        //update same file

        String fromFile = readFromFile(path);
        String result = fromFile.replaceAll(match, replacement);
        writeToFile(path, result);
    }

    public static void replaceFromFile(String ipPath, String opPath, String match, String replacement) throws IOException
    {
        //read from file but writes in another

        String fromFile = readFromFile(ipPath);
        String result = fromFile.replaceAll(match, replacement);
        writeToFile(opPath, result);
    }

    public static ArrayList<String> listAllSubDirectories(String directoryName)
    {
        ArrayList<String> Merged = new ArrayList<String>();

        ArrayList<String> op1 = new ArrayList<>();
        ArrayList<String> op2 = new ArrayList<>();

        File directory = new File(directoryName);
        //get all the files from a directory

        int error = 0;


        try {

            File[] fList = directory.listFiles();

            if(fList==null)
            {
                System.out.println( "problem in: " + directoryName);

                throw new Exception();
            }

            for (File file : fList) {

                if (file.isDirectory()) {

                    boolean hasDirChildren = false;

                    File[] childrenfList = file.listFiles();

                    if(childrenfList==null)
                    {
                        System.out.println( "problem in: " + file.getAbsolutePath());
                        continue;
                    }

                    for (File child : childrenfList) {

                        if (child.isDirectory()) {
                            hasDirChildren = true;
                            break;
                        }

                    }

                    if (!hasDirChildren)
                        op1.add(file.getAbsolutePath());

                    else
                        op2.addAll(listAllSubDirectories(file.getAbsolutePath()));

                }
            }

            Merged.addAll(op1);
            Merged.addAll(op2);

            if (Merged.size() == 0)
                Merged.add(directoryName);

            return Merged;
        }
        catch (Exception e)
        {

            Merged.addAll(op1);
            Merged.addAll(op2);

            if (Merged.size() == 0)
                Merged.add(directoryName);

            return Merged;
        }

    }

    //queue method
    public static ArrayList<String> listAllFiles(String directory)
    {

        ArrayList<File> directories = new ArrayList<>();

        ArrayList<String> op = new ArrayList<String>();

         //Root directory
        String rootDir = new File(directory).getAbsolutePath();

        // maintain a queue to store files and directories
        Queue<File> queue = new LinkedList<>();

        // add root directory to the queue
        queue.add(new File(rootDir));

        // loop until queue is empty - all files and directories present
        // inside the root directory are processed
        while (!queue.isEmpty())
        {
            // get next file/directory from the queue
            File current = queue.poll();

            // get list of all files and directories in 'current'
            File[] listOfFilesAndDirectory = current.listFiles();

            // listFiles() returns non-null array if 'current' denotes a dir
            if (listOfFilesAndDirectory != null)
            {
                // iterate over the names of the files and directories in
                // the current directory
                for (File file : listOfFilesAndDirectory)
                {
                    // if file denotes a directory
                    if (file.isDirectory()) {
                        queue.add(file);

                    }
                    // if file denotes a file, print it
                    else {
                        directories.add(file);
                        op.add(file.getAbsolutePath());
                        //System.out.println(file);
                    }
                }
            }
        }
        return op;
    }




}
