package com.example.challenge.Acvtivities.Activities;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.challenge.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterVideo extends RecyclerView.Adapter<AdapterVideo.HolderVideos> {
    //FirebaseAuth fAuth;
    //String ID = fAuth.getCurrentUser().getUid();
    //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
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
        serVideoUrl(modelVideo,holder);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void serVideoUrl(ModelVideo modelVideo, HolderVideos holder) {
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


        public HolderVideos(@NonNull View itemView) {
            super(itemView);

            //init UI Views of row_video.xml
            videoView= itemView.findViewById(R.id.videoView);
            titleTV = itemView.findViewById(R.id.titleTV);
            timeTv = itemView.findViewById(R.id.timeTv);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

    }

}
