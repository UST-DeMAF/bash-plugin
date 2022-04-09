package ust.tad.shellplugin.analysisservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

@SpringBootTest
public class AnalysisServiceTest {
    
    private static final Logger LOG =
      LoggerFactory.getLogger(AnalysisServiceTest.class);

    @Test
    public void testetes() throws IOException, URISyntaxException {
        URL webResource = new URL("https://stackoverflow.com/questions/3571223/how-do-i-get-the-file-extension-of-a-file-in-java");
        URL file = new URL("file://Ubuntu/home/ubuntu/fork/kube/azure-start.sh");
        URL fileAlt = new URL("file:/home/ubuntu/fork/kube/azure-start.sh");
        URL directory = new URL("file://folder/stions/3571223");

        String command = "kubectl create -f k8/";

        LOG.info(StringUtils.getFilenameExtension(fileAlt.toString()));
    }
    
}
