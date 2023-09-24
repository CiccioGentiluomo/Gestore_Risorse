package risorse.Server;

import java.io.*; 
import javax.xml.parsers.*; 
import org.w3c.dom.*;

public class ParserXML {
    public  static Document loadDocument(String fileName){
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse(file);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}