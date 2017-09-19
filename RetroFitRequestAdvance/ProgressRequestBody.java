package com.test.webservice;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

public class ProgressRequestBody extends RequestBody {
    private File mFile;
    private String mPath;
    private UploadCallbacks mListener;
    private boolean isFirstWrite = true;

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);

        void onError();

        void onFinish();
    }

    public ProgressRequestBody(final File file, final UploadCallbacks listener) {
        mFile = file;
        mListener = listener;
    }


    @Override
    public MediaType contentType() {
        // i want to upload only images
        MediaType MEDIA_TYPE = MediaType.parse(getMimeType(mFile.getAbsolutePath()));
        return MEDIA_TYPE;
    }

    private String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    @Override
    public long contentLength() throws IOException {
        return mFile.length();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
//        Log.e("writeTo", "====writeTo====");
//        Source source = null;
//        long fileLength = mFile.length();
//        try {
//            Handler handler = new Handler(Looper.getMainLooper());
//            source = Okio.source(mFile);
//            sink.writeAll(source);
//            handler.post(new ProgressUpdater(fileLength, fileLength));
//        } finally {
//            Util.closeQuietly(source);
//        }

        Log.e("writeTo", "====writeTo====");
        long fileLength = mFile.length();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;

        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                uploaded += read;
                sink.write(buffer, 0, read);
                // update progress on UI thread
                if (!isFirstWrite) {
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                }
            }
        } finally {
            in.close();
            isFirstWrite = false;
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            if (mListener != null) {
                mListener.onProgressUpdate((int) (100 * mUploaded / mTotal));
            }
        }
    }
}

