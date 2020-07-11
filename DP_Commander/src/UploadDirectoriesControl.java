import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UploadDirectoriesControl {


    public void run(String ip, String port, String username, String password,
                    String localPath, String startingLocalPath,
                    String dpStartingPath, String domain, boolean include) throws IOException {

        Utils.validate(localPath, startingLocalPath);

        ArrayList<String> filesList = Utils.getDataPowerPathsList(localPath, startingLocalPath, dpStartingPath, domain,
                include, "files");

        System.out.println("finished getting files paths");

        ArrayList<String> filesData = getFilesData(localPath);

        System.out.println("finished getting files data");

        ArrayList<String> params = new ArrayList<>();
        params.addAll(filesList);
        params.addAll(filesData);

        String soapRequest = SomaStrings.getFinalSoapRequest(SomaStrings.operations.upload_file, params);

        SomaConnection DataPowerConnection = new SomaConnection();
        DataPowerConnection.run(soapRequest, ip, port, username, password);
        System.out.println();

        soapRequest = getFlush();

        DataPowerConnection.run(soapRequest, ip, port, username, password);
        System.out.println();

    }


    public ArrayList<String> getFilesData(String localPath) throws IOException {
        ArrayList<String> files = FileUtils.listAllFiles(localPath);

        ArrayList<String> op = new ArrayList<String>();

        for (String file : files) {


            String fileText = FileUtils.readFromFile(file);

            op.add(fileText);

        }

        return op;

    }

    public static String getFlush() {

        String request = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "<env:Body>\n" +
                "<dp:request domain=\"SABB_Dev\"\n" +
                "xmlns:dp=\"http://www.datapower.com/schemas/management\">\n" +
                "<dp:do-action>\n" +
                "<FlushStylesheetCache>\n" +
                "<XMLManager>default</XMLManager>\n" +
                "</FlushStylesheetCache>\n" +
                "</dp:do-action>\n" +
                "</dp:request>\n" +
                "</env:Body>\n" +
                "</env:Envelope>";

        return request;
    }

}
