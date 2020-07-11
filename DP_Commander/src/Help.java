public class Help {

    public static void run(){

        System.out.println("enter a command like:");
        System.out.println("operation ip port DP_username XML_Interface_PW domain_name folder_local_path starting_local_path dp_starting_path include");

        System.out.println();
        System.out.println("operation should be: createDir or uploadDir");

        System.out.println();
        System.out.println("folder_local_path should be: a valid windows path as copied from the title bar of a windows file explorer");
        System.out.println("folder_local_path represents: the path from which the prog will start to search to find files and folders to upload");

        System.out.println();
        System.out.println("starting_local_path should be: a valid windows path as copied from the title bar of a windows file explorer");
        System.out.println("starting_local_path represents: the path from which the prog will start to upload on DP");

        System.out.println();
        System.out.println("dp_starting_path should be: null or a path like 'Services/B2B'");
        System.out.println("dp_starting_path represents: prefix folder to uploaded Dir");

        System.out.println();
        System.out.println("include should be: true or false");
        System.out.println("include represents: whether to include the current dir name in the uploaded dir ");

        System.out.println();
        System.out.println("Examples:");
        System.out.println();
        System.out.println("createDir 192.168.232.128 5550 admin idgadmin SABB_Dev E:\\SABB\\SABB_TFS\\(Common)\\Development\\SourceCode\\Gateway_DP E:\\SABB\\SABB_TFS\\(Common)\\Development\\SourceCode\\Gateway_DP null true");
        System.out.println();
        System.out.println("uploadDir 192.168.232.128 5550 admin idgadmin SABB_Dev E:\\SABB\\SABB_TFS\\(Common)\\Development\\SourceCode\\Gateway_DP E:\\SABB\\SABB_TFS\\(Common)\\Development\\SourceCode\\Gateway_DP null true");

        System.exit(0);


    }

}
