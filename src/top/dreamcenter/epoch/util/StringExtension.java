package top.dreamcenter.epoch.util;

import java.io.*;
import java.net.URLEncoder;
import java.util.Random;
import java.util.zip.GZIPInputStream;

public class StringExtension{
    private static final String full = "abcdefghijklmnopqrstuvwxyz1234567890";

    /**
     * concat many equal expression by character mark
     * @param mark
     * @param full
     * @return
     */
    public static String concatByChar(char mark,String ...full) {
        if (full == null) throw new RuntimeException("init datas by null");
        StringBuilder sb = new StringBuilder();
        int len = full.length;
        if (len == 0) return "";
        for (int i = 0;i < len-1; i++) sb.append(full[i]).append(mark);
        sb.append(full[len-1]);
        return sb.toString();
    }

    /**
     * encoding the uri in urlencoding mode
     * @param from
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodingToUrl(String from) throws UnsupportedEncodingException {
        if (from == null) return "";
        from = URLEncoder.encode(from, "utf-8");
        from = from.replaceAll("%3D","=");
        from = from.replaceAll("%26","&");
        return from;
    }

    /**
     * get Reader data as String
     * @param reader
     * @return
     * @throws IOException
     */
    public static String readFromReader(Reader reader) throws IOException {
        String temp;
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(reader);
        while ((temp = br.readLine()) != null) sb.append(temp);

        br.close();
        return sb.toString();
    }

    /**
     * get GZIPInputStream data as String [auto decompressed]
     * @param gis
     * @return
     * @throws IOException
     */
    public static String readFromGZIPInputStream(GZIPInputStream gis) throws IOException {
        int n;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[256];
        while ((n = gis.read(buffer)) >= 0) {
            baos.write(buffer, 0, n);
        }
        String temp = baos.toString("utf-8");
        gis.close();
        baos.close();
        return temp;
    }

    public static String randomTag(int length) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        int index = -1;
        for (int i = 0; i < length; i++) {
            index = Math.abs(random.nextInt())%36;
            builder.append(full.charAt(index));
        }
        return builder.toString();
    }
}
