import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {

        DataPower dp = new DataPower();

        String operation = "createDir";

        String ip ;
        String port;
        String username ;
        String password ;
        String domain ;
        String localPath;
        String startingLocalPath;
        String dpStartingPath;
        boolean include;

        operation = "createDir";
        operation="uploadDir";
        ip = dp.getIp();
        port= dp.getPort();
        username = dp.getUsername();
        password = dp.getPassword();
        domain = "test";



        System.out.println();


//        SomaStrings.getUploadFileXML();

//        if(args.length==0)
//        {
//            System.out.println("for help run prog with --help parameter");
//            System.out.println();
//            System.exit(0);
//        }
//
//
//        if(args[0].equals("--help"))
//        {
//           Help help = new Help();
//
//           help.run();
//        }
//
//        if(args.length!=10)
//        {
//            System.out.println("There should be 10 parameters");
//            System.exit(0);
//        }
//
//        operation=args[0];
//        ip=args[1];
//        port=args[2];
//        username=args[3];
//        password=args[4];
//        domain=args[5];
//        localPath=args[6];
//        startingLocalPath=args[7];
//
//        if(!args[8].equals("null"))
//            dpStartingPath=args[8];
//
//
//        if(args[9].equals("false"))
//            include = false;


        try {

            if(operation.equals("createDir"))
            {
//                CreateDirectoriesControl createDirectoriesControl= new CreateDirectoriesControl();
//
//                createDirectoriesControl.run(ip, port, username, password, localPath, startingLocalPath,
//                        dpStartingPath, domain,  include);
            }


        } catch(Exception e) {
            e.printStackTrace();
        }


    }




   }

   /*
   1) instead of reading all files, u can store last time prog was run, retrieve last time a file was modified
   if time is later then it will be uploaded

   2) create dirs with dom xml builder not using strings

   3) open connection one time only

   4) func for cli arguments

   5) func for automated testing

   6) typing which files were uploaded correctly and which are not

   7) flushing gov xml manager



    */



