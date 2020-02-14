
import java.io.*;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FetchData implements Runnable{
    URL url;
    File file;

    public FetchData(URL url, File file) {
        this.url = url;
        this.file = file;
    }

    @Override
    public void run() {
        synchronized (this.getClass()) {
            try {
                String contents = readFeed();
                writeFeedToFile(contents);
                System.out.println("Fetching Finished!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String readFeed(){
        BufferedReader myReader = null;
        try {
            URLConnection connect = url.openConnection();
            myReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));

            //Read line by line
            String line = myReader.readLine();
            StringBuilder myBuilder = new StringBuilder();
            do {
                myBuilder.append(line+"\n");
            } while ((line = myReader.readLine()) != null);

            return myBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(myReader != null){
                try {
                    myReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    public void writeFeedToFile(String contents){
        BufferedWriter myWriter = null;
        try { 
            myWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            myWriter.write(contents);  //Update File Contents
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
