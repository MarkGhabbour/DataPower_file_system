import java.io.File;
import java.util.ArrayList;

public class Utils {

    public static void validate(String localPath, String startingLocalPath)
    {

        File localFolderPath = new File(localPath);
        File startingFolderPath = new File(startingLocalPath);

        if(! localFolderPath.isDirectory() )
        {
            System.out.println("wrong local path");
            System.exit(0);
        }

        if(! startingFolderPath.isDirectory())
        {
            System.out.println("wrong starting local path");
            System.exit(0);
        }

        if(!localPath.contains(startingLocalPath))
        {
            System.out.println("starting local path is not a part of local path");
            System.exit(0);
        }

    }

    public static ArrayList<String> getDataPowerPathsList(String localPath, String startingLocalPath,
                                            String dpStartingPath, String domain, boolean include, String foldersOrFiles)
    {
        ArrayList<String> folders;

        if(foldersOrFiles.equals("files"))
        {
            folders = FileUtils.listAllFiles(localPath);
        }
        else
        {   //else it is folders
            folders = FileUtils.listAllSubDirectories(localPath);
        }

        ArrayList<String> op = new ArrayList<String>();

        int startingLocalPathLength = startingLocalPath.length();

        String inc =  startingLocalPath.substring(startingLocalPath.lastIndexOf("\\")+1);


        for(String folder : folders)
        {

            if(include==true)
            {
                int temp = localPath.lastIndexOf(inc);
                folder = folder.substring(temp);
            }

            else
                folder = folder.substring(startingLocalPathLength + 1);


            String dpFolder = "local:///" + domain + "/" + dpStartingPath + folder.replaceAll("\\\\", "/");

            op.add(dpFolder);

        }

        return op;

    }


}
