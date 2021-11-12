package com.arash.altafi.caffebazar.utility;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class ConvertImage {

    public String convertTostring (Bitmap bitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes = stream.toByteArray();
        String convert = Base64.encodeToString(bytes,Base64.DEFAULT);
        return convert;
    }

}
