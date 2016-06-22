package rikkiesoft.minanokihongo.imageprocess;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

/**
 * Created by tuyenpx on 09/06/2016.
 */

@SuppressWarnings("UnusedDeclaration")
public class ImageUtils {

    private LruCache<String, Bitmap> mLruCache;
    private static ImageUtils sInstance;


    public static ImageUtils getsInstance() {
        if (sInstance == null) {
            sInstance = new ImageUtils();
        }
        return sInstance;
    }


    private ImageUtils() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 4;
        mLruCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mLruCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mLruCache.get(key);
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        if (getBitmapFromMemCache(String.valueOf(resId)) != null) {
            return getBitmapFromMemCache(String.valueOf(resId));
        }
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeResource(res, resId, options);
        if (bitmap != null)
            addBitmapToMemoryCache(String.valueOf(resId), bitmap);
        return bitmap;

    }

    public Bitmap decodeSampledBitmapFromResource(String pathName, int reqWidth, int reqHeight) {

        if (getBitmapFromMemCache(pathName) != null) {
            return getBitmapFromMemCache(pathName);
        }

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(pathName, options);
        if (bitmap != null)
            addBitmapToMemoryCache(pathName, bitmap);
        return bitmap;

    }


    public void clearCacheBitmap() {
        if (mLruCache != null) {
            mLruCache.evictAll();
        }
        if (sInstance != null) {
            sInstance = null;
        }
    }
}
