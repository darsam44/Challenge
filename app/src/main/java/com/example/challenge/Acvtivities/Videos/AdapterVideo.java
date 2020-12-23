package com.example.challenge.Acvtivities.Videos;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.HolderVideos> {
    private Context context;


    private ArrayList<ModelVideo> videoArrayList;

    public AdapterVideo(Context context, ArrayList<ModelVideo> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public HolderVideos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_video.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false);
        return new HolderVideos(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBindViewHolder(@NonNull HolderVideos holder, int position) {
        ModelVideo modelVideo = videoArrayList.get(position);

        String title = modelVideo.getTitle();
        String timestamp = modelVideo.getTimestamp();
        String VideoUrl = modelVideo.getVideoUrl();

        //format timestam
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String formattedDataTime = DateFormat.format("dd/mm/yyyy K:mm a",calendar).toString();

        //set data
        holder.timeTv.setText(title);
        holder.timeTv.setText(formattedDataTime);
        setVideoUrl(modelVideo,holder);

        //handle click, download video
        holder.downloadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadVideo(modelVideo);
            }
        });

        //handle click, delete video
        holder.deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show alert dialog, confirm to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete").setMessage("Are You Sure You Want To Delete This Video"+ title)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //confirmed to delete
                                deleteVideo(modelVideo);

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancelled deleting, dismiss dialog
                        dialog.dismiss();
                    }
                }).show();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setVideoUrl(ModelVideo modelVideo, HolderVideos holder) {
        //show progress
        holder.progressBar.setVisibility(View.VISIBLE);

        //get video url
        String videoUrl = modelVideo.getVideoUrl();

        //Media cotroller for play, pause, seekbar, time etc
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri = Uri.parse(videoUrl);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);

        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });

    holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch(what){
                case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                    //rendering started
                    holder.progressBar.setVisibility(View.VISIBLE);
                    return true;
                }
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                    //buffering started
                    holder.progressBar.setVisibility(View.VISIBLE);
                    return true;
                }
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                    //buffering ended
                    holder.progressBar.setVisibility(View.VISIBLE);
                    return true;
                }
            }
            return false;
        }
    });
    holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.start();
        }
    });
    }


    private void deleteVideo(ModelVideo modelVideo) {
        String videoId = modelVideo.getTimestamp();
        String videoUrl = modelVideo.getVideoUrl();

        //Delete from firebase storage
        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl);
        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //Deleted from firebase storage

                //delete from firebase database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Videos");
                databaseReference.child(videoId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //deleted from firebase database
                        Toast.makeText(context, "Video deleted succesfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed deleting from firebase database
                        Toast.makeText(context, "" +e.getMessage(),  Toast.LENGTH_SHORT).show();
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed deleting from firebase storage
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void downloadVideo(ModelVideo modelVideo) {
        String videoUrl = modelVideo.getVideoUrl(); // url of video, will be used to download video

        //get video reference sing videoo url
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoUrl);
        storageReference.getMetadata()
                .addOnSuccessListener(new OnSuccessListener<StorageMetadata>() {
                    @Override
                    public void onSuccess(StorageMetadata storageMetadata) {
                        //get file/video basic info e.g title, type
                        String fileName = storageMetadata.getName(); // file name in firebase storage
                        String fileType = storageMetadata.getContentType(); // file type in firebase storage
                        String fileDirectory = Environment.DIRECTORY_DOWNLOADS; //video will be saved in this folder i.e Downloads
                        //init downloadManager
                        DownloadManager downloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);

                        //get uri of fileto be download
                        Uri uri = Uri.parse(videoUrl);

                        //create download request, new request for each download - tes we can download multiple files at once
                        DownloadManager.Request request = new DownloadManager.Request(uri);

                        //notification visibility
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        //set destenation path
                        request.setDestinationInExternalPublicDir(""+ fileDirectory,""+ fileName+".mp4");

                        //add request to queue -  can be multiple requests so is adding to queue
                        downloadManager.enqueue(request);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Failed getting info
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return videoArrayList.size();
    }

    //View holder class, holds, inits the UIviews
    class HolderVideos extends RecyclerView.ViewHolder{

        //UI V iews of row_video.xml
        VideoView videoView;
        TextView titleTV ,timeTv;
        ProgressBar progressBar;
        FloatingActionButton deleteFab , downloadFab;


        public HolderVideos(@NonNull View itemView) {
            super(itemView);

            //init UI Views of row_video.xml
            videoView= itemView.findViewById(R.id.VideoView);
            titleTV = itemView.findViewById(R.id.titleTV);
            timeTv = itemView.findViewById(R.id.timeTv);
            progressBar = itemView.findViewById(R.id.progressBar);
            deleteFab = itemView.findViewById(R.id.deleteFab);
            downloadFab = itemView.findViewById(R.id.downloadFab);
        }

    }

}
