package net.ralphpina.kamcordwatch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import net.ralphpina.kamcordwatch.api.FeedEnvelope;
import net.ralphpina.kamcordwatch.api.FeedRequest;
import net.ralphpina.kamcordwatch.api.video.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static net.ralphpina.kamcordwatch.utils.TimeUtils.getHumanReadableTimeElapsed;
import static net.ralphpina.kamcordwatch.utils.TimeUtils.getHumanReadableTimeElapsedHMMSS;
import static net.ralphpina.kamcordwatch.utils.TimeUtils.getStringForUploadTime;

public class MainActivity extends AppCompatActivity {

    private static final String TAG                 = "MainActivity";
    private static final String VIDEO_OUT_STATE     = "net.ralphpina.kamcordwatch.MainActivity.VIDEO_OUT_STATE";
    private static final String NEXT_PAGE_OUT_STATE = "net.ralphpina.kamcordwatch.MainActivity.NEXT_PAGE_OUT_STATE";

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private VideoAdapter mAdapter;
    private FeedRequest  mFeedRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFeedRequest = KamApplication.get()
                                     .getClient()
                                     .create(FeedRequest.class);
        setUpRecyclerView();
        if (savedInstanceState != null && savedInstanceState.containsKey(VIDEO_OUT_STATE)) {
            mAdapter.addData(savedInstanceState.<Video>getParcelableArrayList(VIDEO_OUT_STATE));
            mAdapter.setNextPage(savedInstanceState.getString(NEXT_PAGE_OUT_STATE));
        } else {
            initialLoad();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(VIDEO_OUT_STATE, mAdapter.getData());
        outState.putString(NEXT_PAGE_OUT_STATE, mAdapter.getNextPage());
    }

    private void setUpRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new VideoAdapter();
        mRecyclerView.setAdapter(mAdapter);
        // infinite scroll!
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() >= (mAdapter.getItemCount() - 5)
                    && mAdapter.getNextPage() != null) {
                    getNextPage();
                }
            }
        });
    }

    private void initialLoad() {
        Call<FeedEnvelope> call = mFeedRequest.getFeed();
        call.enqueue(new Callback<FeedEnvelope>() {
            @Override
            public void onResponse(Response<FeedEnvelope> response, Retrofit retrofit) {
                FeedEnvelope envelope = response.body();
                updateData(envelope);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "=== onFailure() === " + t.getMessage());
            }
        });
    }

    private void getNextPage() {
        Map<String, String> options = new HashMap<>();
        options.put("page", mAdapter.getNextPage());
        Call<FeedEnvelope> call = mFeedRequest.getFeed(options);
        call.enqueue(new Callback<FeedEnvelope>() {
            @Override
            public void onResponse(Response<FeedEnvelope> response, Retrofit retrofit) {
                FeedEnvelope envelope = response.body();
                updateData(envelope);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG, "=== onFailure() === " + t.getMessage());
            }
        });
    }

    private void updateData(FeedEnvelope envelope) {
        mAdapter.setNextPage(envelope.getResponse()
                                     .getVideoList()
                                     .getNextPage());
        mAdapter.addData(envelope.getResponse()
                                 .getVideoList()
                                 .getVideos());
    }

    private class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {

        private List<Video> videos;
        private String      nextPage;

        public void addData(List<Video> update) {
            if (videos == null) {
                videos = update;
            } else {
                videos.addAll(update);
            }
            notifyDataSetChanged();
        }

        @Nullable
        public ArrayList<Video> getData() {
            return new ArrayList<>(videos);
        }

        public String getNextPage() {
            return nextPage;
        }

        public void setNextPage(String nextPage) {
            this.nextPage = nextPage;
        }

        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                                   .inflate(R.layout.list_item_video, parent, false);
            return new VideoViewHolder(v);
        }

        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            holder.configure(videos.get(position));
        }

        @Override
        public int getItemCount() {
            if (videos == null) {
                return 0;
            } else {
                return videos.size();
            }
        }
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.videoView)
        VideoView    mVideoView;
        @Bind(R.id.imageView_thumbnail)
        ImageView    mThumbnail;
        @Bind(R.id.linearLayout_video_info)
        LinearLayout mVideoInfo;
        @Bind(R.id.video_title)
        TextView     mVideoTitle;
        @Bind(R.id.user_and_upload_time)
        TextView     mUsernameAndUpload;
        @Bind(R.id.views_like_count)
        TextView     mViewsLikes;
        @Bind(R.id.timer)
        TextView     mTimer;

        private Video mVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            setOnTouch();
        }

        public void configure(Video video) {
            mVideo = video;
            mVideoView.setVideoURI(Uri.parse(video.getVideoUrl()));
            mVideoView.seekTo(100);
            mVideoTitle.setText(video.getTitle());
            setupUserNameAndUpload();
            setupViewsAndLikes();
            setupTimerText();
            Picasso.with(MainActivity.this)
                   .load(video.getThumbnails()
                              .getRegular())
                   .into(mThumbnail);
        }

        private void setOnTouch() {
            final GestureDetectorCompat mDetector = new GestureDetectorCompat(mVideoView.getContext(),
                                                                              new GestureDetector.SimpleOnGestureListener() {
                                                                                  @Override
                                                                                  public boolean onSingleTapUp(
                                                                                          MotionEvent e) {
                                                                                      if (mVideoView.isPlaying()) {
                                                                                          mVideoView.pause();
                                                                                          mVideoInfo.setVisibility(
                                                                                                  VISIBLE);
                                                                                          mTimer.setVisibility(
                                                                                                  VISIBLE);
                                                                                      } else {
                                                                                          mVideoView.start();
                                                                                          mVideoInfo.setVisibility(
                                                                                                  GONE);
                                                                                          mTimer.setVisibility(
                                                                                                  GONE);
                                                                                          mThumbnail.setVisibility(
                                                                                                  GONE);
                                                                                      }
                                                                                      return true;
                                                                                  }
                                                                              });

            mVideoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mDetector.onTouchEvent(event);
                    return true;
                }
            });

            mThumbnail.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mDetector.onTouchEvent(event);
                    return true;
                }
            });
        }

        @OnClick(R.id.imageButton_maximize)
        public void onClickMaximize() {
            Intent intent = new Intent(MainActivity.this, VideoDetailActivity.class);
            intent.putExtra(VideoDetailActivity.EXTRA_VIDEO_URL, mVideo);
            startActivity(intent);
        }

        private void setupUserNameAndUpload() {
            final String username = mVideo.getUser()
                                          .getUserName();
            final String timeUploaded = getStringForUploadTime(mVideo.getUploadTime());
            mUsernameAndUpload.setText(getString(R.string.by_username_time_ago,
                                                 username,
                                                 timeUploaded));
        }

        private void setupViewsAndLikes() {
            mViewsLikes.setText(getString(R.string.views_likes,
                                          mVideo.getViews(),
                                          mVideo.getLikes()));
        }

        private void setupTimerText() {
            final String displayText;
            if (mVideo.getDuration() < 3599) { // less than 59:59, otherwise switch to h:mm:ss
                displayText = getHumanReadableTimeElapsed(mVideo.getDuration()
                                                                .longValue());
            } else {
                displayText = getHumanReadableTimeElapsedHMMSS(mVideo.getDuration()
                                                                     .longValue());
            }
            mTimer.setText(displayText);
        }
    }
}
