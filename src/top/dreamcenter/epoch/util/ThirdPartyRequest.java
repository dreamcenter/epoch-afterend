package top.dreamcenter.epoch.util;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import top.dreamcenter.todo.Degree;
import top.dreamcenter.todo.TodoB;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

public class ThirdPartyRequest {

    /**
     *GET REQUEST
     * @param urlPath
     * @param datas
     * @return
     * @throws IOException
     */
    public static String getGETRequest(String urlPath,String ...datas) throws IOException {
        return doRequest(urlPath,"GET",datas);
    }

    /**
     * POST request
     * @param urlPath
     * @param datas
     * @return
     * @throws IOException
     */
    // TODO: 2021/7/10  to be test
    @TodoB(progress = 50,degree = Degree.SKIP)
    public static String getPOSTRequest(String urlPath,String ...datas) throws IOException {
        return doRequest(urlPath,"POST",datas);
    }

    /**
     * detail realize of request
     * @param urlPath
     * @param method
     * @param datas
     * @return
     * @throws IOException
     */
    private static String doRequest(String urlPath,String method,String ...datas) throws IOException {
        String dataUrl = StringExtension.concatByChar('&',datas);
        dataUrl = StringExtension.encodingToUrl(dataUrl);

        if (method.equalsIgnoreCase("GET")) urlPath = urlPath + "?" + dataUrl;

        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        connection.setDoInput(true);

        if (method.equalsIgnoreCase("POST")) {
            connection.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream(),"utf-8");
            osw.write(dataUrl);
            osw.flush();
            osw.close();
        }

        return doResponse(connection);
    }

    /**
     * detail realize of response
     * @param connection
     * @return
     * @throws IOException
     */
    private static String doResponse(HttpURLConnection connection) throws IOException {
        String encodingType = connection.getHeaderField("content-encoding");
        String temp;
        if (encodingType == null){
            temp = StringExtension.readFromReader(
                    new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8));
        } else if (encodingType.equals("gzip")){
            temp = StringExtension.readFromGZIPInputStream(
                    new GZIPInputStream(connection.getInputStream()));
        } else {
            temp = "未知的压缩方式";
        }
        return temp;
    }

    /**
     * use apache's http component to send request [code from baidu]
     * @param url
     * @return
     */
    @Deprecated()
    public static String doGetApache(String url){
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        try {
            get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getCode() == HttpStatus.SC_OK){
                //返回json格式
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
