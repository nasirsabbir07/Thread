import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        ///Thread Executor Service
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(2);

        URL rssUrl = null;
        try {
            rssUrl = new URL("http://rss.cnn.com/rss/edition.rss");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File file = new File("D:\\a.rss");  //A copy included in project

        //Task Fetch
        FetchData fetchData = new FetchData(rssUrl, file);
        executorService.scheduleAtFixedRate(fetchData, 0, 15, TimeUnit.SECONDS);

        //Task Update
        JpgList jpgList = new JpgList(file);
        executorService.scheduleAtFixedRate(jpgList, 0, 20, TimeUnit.SECONDS);

	}

}
