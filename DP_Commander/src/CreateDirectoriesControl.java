import java.io.IOException;
import java.util.ArrayList;

public class CreateDirectoriesControl {


    public void run(String ip, String port, String username, String password,
                    String localPath, String startingLocalPath,
                    String dpStartingPath, String domain, boolean include) throws IOException {

        Utils.validate(localPath, startingLocalPath);

        ArrayList<String> folders = Utils.getDataPowerPathsList(localPath, startingLocalPath, dpStartingPath,
                domain, include, "folders");


        String soapRequest = SomaStrings.getFinalSoapRequest(SomaStrings.operations.create_directory, folders);

        System.out.println("sending request to DP");
        SomaConnection DataPowerConnection = new SomaConnection();
        DataPowerConnection.run(soapRequest, ip, port, username, password);

        System.out.println();


    }



}
