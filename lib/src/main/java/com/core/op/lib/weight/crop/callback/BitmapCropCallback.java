package com.core.op.lib.weight.crop.callback;

import android.net.Uri;
import android.support.annotation.NonNull;

public interface BitmapCropCallback {

    void onBitmapCropped(@NonNull Uri resultUri, int imageWidth, int imageHeight);

    void onCropFailure(@NonNull Throwable t);

}