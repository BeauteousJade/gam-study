package com.example.pby.gam_study.page.about;

import android.os.Handler;
import android.os.Message;

import com.example.pby.gam_study.network.NetWorkManager;
import com.example.pby.gam_study.network.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileDownloadRequest {

    private static final int DEFAULT = 0;
    private static final int START = 1;
    private static final int DOWNLOAD = 2;
    private static final int STOP = 3;

    private String mDownloadUrl;
    private String mSaveFilePath;

    private volatile int mStatus;
    private DownloadHandler mDownloadHandler;

    public FileDownloadRequest(String downloadUrl, String saveFilePath) {
        mDownloadUrl = downloadUrl;
        mSaveFilePath = saveFilePath;
    }

    public void enqueue(DownloadListener downloadListener) {
        if (mStatus > DEFAULT) {
            return;
        }
        mStatus = START;
        mDownloadHandler = new DownloadHandler(downloadListener);
        if (downloadListener != null) {
            downloadListener.onStart();
        }
        Call<ResponseBody> call = NetWorkManager.getService(Service.class).downloadApk(mDownloadUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.body() == null || isStop()) {
                    sendStopMessage(null, null);
                    return;
                }
                mStatus = DOWNLOAD;
                InputStream is = null;
                byte[] buf = new byte[2048];
                FileOutputStream fos = null;
                try {

                    long total = response.body().contentLength();
                    long current = 0;
                    int len;
                    is = response.body().byteStream();
                    File saveFile = new File(mSaveFilePath);
                    fos = new FileOutputStream(saveFile);
                    while ((len = is.read(buf)) != -1 && !isStop()) {
                        current += len;
                        fos.write(buf, 0, len);
                        Message message = Message.obtain();
                        message.arg1 = DOWNLOAD;
                        message.arg2 = (int) ((current * 1.0f / total) * 100);
                        mDownloadHandler.sendMessage(message);
                    }
                    fos.flush();
                    sendStopMessage(null, saveFile);
                } catch (IOException e) {
                    sendStopMessage(e, null);
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sendStopMessage(t, null);
            }
        });
    }

    private void sendStopMessage(Throwable throwable, File file) {
        Message message = Message.obtain();
        message.arg1 = STOP;
        message.obj = new DownloadInfo(file, throwable);
        mDownloadHandler.sendMessage(message);
    }

    public void cancel() {
        mStatus = STOP;
    }

    public boolean isStop() {
        return mStatus == STOP;
    }


    private static class DownloadHandler extends Handler {

        private WeakReference<DownloadListener> mDownloadListenerReference;

        public DownloadHandler(DownloadListener downloadListener) {
            mDownloadListenerReference = new WeakReference<>(downloadListener);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mDownloadListenerReference.get() == null) {
                return;
            }
            switch (msg.arg1) {
                case FileDownloadRequest.DOWNLOAD:
                    mDownloadListenerReference.get().onDownload(msg.arg2);
                    break;
                case FileDownloadRequest.STOP:
                    DownloadInfo downloadInfo = (DownloadInfo) msg.obj;
                    mDownloadListenerReference.get().onStop(downloadInfo.mThrowable, downloadInfo.mFile);
                    break;
            }
        }
    }


    public interface DownloadListener {
        void onStart();

        void onDownload(int progress);

        void onStop(@Nullable Throwable throwable, @Nullable File file);
    }

    public class DownloadInfo {
        private File mFile;
        private Throwable mThrowable;

        public DownloadInfo(File file, Throwable throwable) {
            this.mFile = file;
            this.mThrowable = throwable;
        }
    }
}
