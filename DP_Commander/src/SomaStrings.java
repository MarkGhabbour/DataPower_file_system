
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;

public class SomaStrings {


    private static String flushStyleSheetCache;
    private static String soapEnvelopeWithoutDomainAttribute;
    private static String soapEnvelopeWithDomainAttribute;


    public enum operations {
        create_directory,
        upload_file,
        flush_stylesheet_cache
    }


    public static void initialze() {
        soapEnvelopeWithoutDomainAttribute = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<env:Body>\n" +
                "\t\t<dp:request xmlns:dp=\"http://www.datapower.com/schemas/management\">\n" +
                "\t\t\tdo-action\n" +
                "\t\t</dp:request>\n" +
                "\t</env:Body>\n" +
                "</env:Envelope>";

        soapEnvelopeWithDomainAttribute = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<env:Envelope xmlns:env=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<env:Body>\n" +
                "\t\t<dp:request domain=\"default\" xmlns:dp=\"http://www.datapower.com/schemas/management\">\n" +
                "\t\t\tdo-action\n" +
                "\t\t</dp:request>\n" +
                "\t</env:Body>\n" +
                "</env:Envelope>";



        flushStyleSheetCache = "<dp:do-action>\n" +
                "\t\t<FlushStylesheetCache>\n" +
                "\t\t\t<XMLManager>Param</XMLManager>\n" +
                "\t\t</FlushStylesheetCache>\n" +
                "\t</dp:do-action>";


    }

    public static String removeTabs(String s) {
        return s.replaceAll("\\t", "");
    }

    public static String removeNewLine(String s) {
        return s.replaceAll("\\n", "");
    }


    public static String fillXMLParameters(String target, ArrayList<String> replacement, String s) {
        for (int i = 0; i < replacement.size(); i++) {
            String s1 = s.substring(0, s.indexOf(target));
            String s2 = s.substring(s.indexOf(target) + target.length());
            s = s1 + replacement.get(i) + s2;

        }

        return s;
    }

    public static String buildSoapEnvelope(String data, String soapEnvelope) {
        return soapEnvelope.replace("do-action", data);

    }


    public static String getFinalSoapRequest(operations operation, ArrayList<String> parameters) {
        initialze();

        String s;

        switch (operation) {
            case create_directory:
                s = getCreateDirectory(parameters);
                break;

            case upload_file:
                s = getUploadFile(parameters);
                break;

            case flush_stylesheet_cache:
                s = flushStyleSheetCache;
                break;

            default:
                //flushing cache is a safe operation
                return flushStyleSheetCache;
        }

        String tmp;

        tmp = removeTabs(s);
        tmp = removeNewLine(tmp);

        String op = tmp;

        return op;
    }



    public static String getCreateDirectory(ArrayList<String> folders) {
        String s = "";

        String createDirOpen = "<CreateDir>";
        String createDirClose = "</CreateDir>";
        String dirOpen = "<Dir>";
        String dirClose = "</Dir>";

        for (int i = 0; i < folders.size(); i++) {
            s += createDirOpen + dirOpen + folders.get(i) + dirClose + createDirClose;
        }

        s = "<dp:do-action>" + s + "</dp:do-action>";

        s= buildSoapEnvelope(s, soapEnvelopeWithoutDomainAttribute);


        return s;
    }

    public static String getUploadFile(ArrayList<String> files) {

        String s = "";


        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            dbFactory.setNamespaceAware(true);

            // root element
            Element rootElement = doc.createElement("dp:request");
            doc.appendChild(rootElement);

            String dpNameSpace = "http://www.datapower.com/schemas/management";
            Attr dpNameSpaceAttr = doc.createAttribute("xmlns:dp");
            dpNameSpaceAttr.setValue(dpNameSpace);
            rootElement.setAttributeNode(dpNameSpaceAttr);


            for (int i = 0; i < files.size()/2; i++) {

                // set file element
                Element operation = doc.createElement("dp:set-file");
                rootElement.appendChild(operation);

                // setting attribute to element
                String name="name"; String fileName=files.get(i);
                Attr nameAttr = doc.createAttribute(name);
                nameAttr.setValue(fileName);
                operation.setAttributeNode(nameAttr);

                String encodedData;

                if(files.get(i+files.size()/2) != null )
                    encodedData = Base64.getEncoder().encodeToString(files.get(i+files.size()/2).getBytes());

                else
                {
                    System.out.println(files.get(i) + " can not be read");
                    System.out.println();
                    continue;
                }

                operation.appendChild(doc.createTextNode(encodedData));

            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File("E:\\cars.xml"));
            transformer.transform(source, result);


            String tmp = XMLToString(doc);

            tmp = tmp.replace("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", "");
            tmp = tmp.replace("<dp:request xmlns:dp=\"http://www.datapower.com/schemas/management\">", "");
            tmp = tmp.replace("</dp:request>", "");

            s= buildSoapEnvelope(tmp, soapEnvelopeWithoutDomainAttribute);

            System.out.println("finished building soap request");


        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }


    public static String XMLToString(Document doc) throws IOException
    {
        String result="";

        try
        {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);

            StringWriter writer = new StringWriter();
            StreamResult consoleResult = new StreamResult(writer);

            transformer.transform(source, consoleResult);

            result = writer.toString();

            return RemoveXSLAmbigousChars(result);

        }

        catch (Exception e)
        {
            System.out.println("Error in transforming XML tree to string");
            e.printStackTrace();
        }
        return result;
    }
    public static String RemoveXSLAmbigousChars(String mapping)
    {
        mapping = mapping.replaceAll("&lt;" , "<");
        mapping = mapping.replaceAll("&gt;" , ">");
        mapping = mapping.replaceAll("&#13;" , "\\n");

        return  mapping;
    }



}


