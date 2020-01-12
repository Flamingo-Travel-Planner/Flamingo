package sample.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageCacheHandler {

    public static final String CACHE_ROOT = "/Library/Caches/Flamingo/";  // TODO Make Windows friendly
    public static final String EXTENSION = ".jpg";
    public static final int MAX_IMAGE_DIMENSION = 600;

    public static void main(String[] args) {
        System.out.println(retrieve("CmRaAAAAzeV5T7EeHQ0mZq6hyRGFBspHkiCPzlZLNG-msSgmMXBeFxPFlXQnL44_eZXvXqC7ZEHd2aIwoX6yRvoy9Zhz-Dlo027pUuZkSE5N3VtUnlt8sGfFElV3iKLExKeQpuPvEhAHBRmO0CKhvkydO9yu-pNJGhRWs8WUY4QCdgYYXTFH6TFxzY6uJA"));
    }

    /**
     * Retrieves an image from the cache (or the web) based on imageId
     */
    public static Image retrieve(String imageId) {
        if (imageId == null) {
            File file = new File("./src/sample/media/placeholder.jpg");
            return new Image(file.toURI().toString());
        }

        try {
            // If Image is not in cache, put it in
            Path path = Paths.get(getCachePath(imageId));
            if (!Files.exists(path)) {
                System.out.printf("Path %s does not exist\n", path);

                URL url = new URL(getImageUrl(imageId));
                ReadableByteChannel readableByteChannel = Channels.newChannel(url.openStream());

                String cachePath = getCachePath(imageId);
//                System.out.println("Copying from " + getImageUrl(imageId) + "\n to " + cachePath);

                File file = new File(cachePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(cachePath);
                FileChannel fileChannel = fileOutputStream.getChannel();


                fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            }
            else {
                System.out.println("Cache is working");
            }

            // Return image from cache
            BufferedImage bufferedImage = ImageIO.read(Files.newInputStream(path));
            return SwingFXUtils.toFXImage(bufferedImage, null);
        }
        catch (IOException e) {
            System.out.println("Image retrieval failed:");
            e.printStackTrace();
            return new Image("../media/placeholder.jpg");
        }
    }

    private static String getCachePath(String imageId) {
        if (imageId.length() > 100)
            return CACHE_ROOT + imageId.substring(0, 100) + EXTENSION;  // Truncate to shorten path
        return CACHE_ROOT + imageId + EXTENSION;  // Truncate to shorten path
    }

    private static String getImageUrl(String imageId) {
        String apiKey = KeyTool.getApiKey();

        try {
            String host = "https://maps.googleapis.com/maps/api/place/photo?";
            String params = String.format("maxwidth=%d&photoreference=%s&key=%s",
                    MAX_IMAGE_DIMENSION,
                    URLEncoder.encode(imageId, "UTF-8"),
                    URLEncoder.encode(apiKey, "UTF-8")
            );

//            System.out.println("returning " + host + params);
            return host + params;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Something went wrong"); // TODO
        }
    }
}
