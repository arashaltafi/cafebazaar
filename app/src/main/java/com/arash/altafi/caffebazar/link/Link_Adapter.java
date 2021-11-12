package com.arash.altafi.caffebazar.link;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.animation.content.Content;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.indeterminate.IndeterminateRoundCornerProgressBar;
import com.arash.altafi.caffebazar.R;
import com.arash.altafi.caffebazar.home.modelhome.ResponseEducation;
import com.arash.altafi.caffebazar.utility.RuntimePermissionsActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import ir.siaray.downloadmanagerplus.classes.Downloader;
import ir.siaray.downloadmanagerplus.enums.DownloadReason;
import ir.siaray.downloadmanagerplus.enums.Storage;
import ir.siaray.downloadmanagerplus.interfaces.DownloadListener;
import ir.siaray.downloadmanagerplus.model.DownloadItem;
import ir.siaray.downloadmanagerplus.utils.Utils;

public class Link_Adapter extends RecyclerView.Adapter<Link_Adapter.ViewHolder> {

    private List<ResponseLink> responseLinks = new ArrayList<>();
    private Context context;
    private OnClickPlayLink onClickPlayLink;
    private Download download;
    private DownloadItem downloadItem = new DownloadItem();

    public Link_Adapter(List<ResponseLink> responseLinks, Context context, OnClickPlayLink onClickPlayLink, Download download) {
        this.responseLinks = responseLinks;
        this.context = context;
        this.onClickPlayLink = onClickPlayLink;
        this.download = download;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindLink(responseLinks.get(position));
    }

    @Override
    public int getItemCount() {
        return responseLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtSizeLink, txtPercent, txtTitle;
        private IndeterminateRoundCornerProgressBar loading;
        private RoundCornerProgressBar roundCornerProgressBar;
        public ImageView img_ClipBoard, img_VideoPlay, img_Download;
        private DownloadListener listener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_Title_link);
            txtSizeLink = itemView.findViewById(R.id.txt_size_link);
            txtPercent = itemView.findViewById(R.id.txt_percent_link);
            img_ClipBoard = itemView.findViewById(R.id.img_copy_link);
            img_Download = itemView.findViewById(R.id.img_download_link);
            img_VideoPlay = itemView.findViewById(R.id.img_play_link);
            loading = itemView.findViewById(R.id.loading);
            roundCornerProgressBar = itemView.findViewById(R.id.progressbar_link);
        }

        public void bindLink(ResponseLink link) {
            txtTitle.setText(link.getTitle());

            img_ClipBoard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboardManager = (ClipboardManager) itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clipData = ClipData.newPlainText(link.getLink(), link.getLink());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(itemView.getContext(), "لینک کپی شد", Toast.LENGTH_SHORT).show();
                }
            });

            img_Download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    download.download();

                    if (Link_Activity.ok1) {
                        listener = new DownloadListener() {
                            @Override
                            public void onComplete(int totalBytes) {
                                downloadItem.setPercent(100);
                                txtPercent.setText(downloadItem.getPercent() + "%");
                                roundCornerProgressBar.setProgress(100);
                                loading.setVisibility(View.GONE);
                                txtSizeLink.setText(Utils.readableFileSize(totalBytes) + "/" + Utils.readableFileSize(totalBytes) + "اتمام");
                            }

                            @Override
                            public void onPause(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {

                            }

                            @Override
                            public void onPending(int percent, int totalBytes, int downloadedBytes) {
                                roundCornerProgressBar.setProgress(percent);
                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFail(int percent, DownloadReason reason, int totalBytes, int downloadedBytes) {
                                Toast.makeText(context, "دانلود فایل با مشکل مواجه شد", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancel(int totalBytes, int downloadedBytes) {

                            }

                            @Override
                            public void onRunning(int percent, int totalBytes, int downloadedBytes, float downloadSpeed) {
                                roundCornerProgressBar.setProgress(percent);
                                loading.setVisibility(View.VISIBLE);
                                downloadItem.setPercent(percent);
                                txtPercent.setText(downloadItem.getPercent() + "%");
                                txtSizeLink.setText(Utils.readableFileSize(totalBytes) + "/" + Utils.readableFileSize(totalBytes));
                            }
                        };

                        Downloader downloader = Downloader.getInstance(itemView.getContext())
                                .setUrl(link.getLink())
                                .setListener(listener)
                                .setToken(link.getId())
                                .setAllowedOverRoaming(true)
                                .setAllowedOverMetered(true)
                                .setVisibleInDownloadsUi(true)
                                .setDestinationDir(Storage.DIRECTORY_DOWNLOADS, link.getTitle())
//                            .setNotificationTitle(notificationTitle)
//                            .setDescription(description)
//                            .setNotificationVisibility(visibility)
//                            .setKeptAllDownload(allDownloadKept)
                                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
                        downloader.showProgress();
                        downloader.start();
                    }
                }
            });

            img_VideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPlayLink.onClickPlay(link.getLink(), link.getTitle(), link.getImage());
                }
            });
        }

    }

    public interface OnClickPlayLink {
        void onClickPlay(String link, String title, String image);
    }

    public interface Download {
        void download();
    }

}
