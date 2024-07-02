package com.example.hsb.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImageLoaderUtil {
    public static void loadImage(Context context, String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
    }

    public static MultipartBody.Part bitmapToPart(Context context, Bitmap image) {
        String randomFileName = UUID.randomUUID().toString() + ".png";
        File imageFile = bitmapToFile(context, image, randomFileName);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), imageFile);
        return MultipartBody.Part.createFormData("profile_image", imageFile.getName(), requestFile);
    }

    public static File bitmapToFile(Context context, Bitmap bitmap, String filename) {
        File file = new File(context.getCacheDir(), filename);
        try {
            file.createNewFile();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
            byte[] bitmapData = bos.toByteArray();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
