import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class JpgList implements Runnable{
    File file;

    public JpgList(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        synchronized (this.getClass()) {
            try {
                Document doc = readFromFile();
                ArrayList<String> imgList = getImgRef(doc);
                writeToFile(imgList);
                System.out.println("Update Finished!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public Document readFromFile(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(file);
            return doc;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<String> getImgRef(Document doc){
        NodeList nodeList = doc.getElementsByTagName("media:content");
        ArrayList<String> imgList= new ArrayList<String>();
        for(int i = 0; i < nodeList.getLength(); ++i){
            String s = nodeList.item(i).getAttributes().getNamedItem("url").getNodeValue();
            imgList.add(s);
        }
        return imgList;
    }

    public void writeToFile(ArrayList<String> imgList){
        StringBuilder myBuilder = new StringBuilder();
        for(String s : imgList){
            myBuilder.append(s+"\n");
        }
        //Write To File
        BufferedWriter myWriter = null;
        try {
            myWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            myWriter.write(myBuilder.toString());
            myWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(myWriter != null){
                try {
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
