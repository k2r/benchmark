/**
 * 
 */
package autoscale.benchmark.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Roland
 *
 */
public class BufferPrinter implements Runnable {

	private final InputStream inputStream;

	public BufferPrinter(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	private BufferedReader getBufferedReader(InputStream is) {
        return new BufferedReader(new InputStreamReader(is));
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		BufferedReader br = getBufferedReader(this.inputStream);
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}
