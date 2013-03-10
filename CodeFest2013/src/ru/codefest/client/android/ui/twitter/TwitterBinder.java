package ru.codefest.client.android.ui.twitter;

import ru.codefest.client.android.ImageLoaderSingleton;
import ru.codefest.client.android.R;
import ru.codefest.client.android.model.Tweet;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.petriyov.android.libs.ui.adapters.AdapterBinder;

public class TwitterBinder extends AdapterBinder {
    static class ViewHolder {
        protected TextView userName;

        protected TextView message;

        protected ImageView userIcon;

    }

    private Activity context;

    private DisplayImageOptions options;

    public TwitterBinder(Activity context) {
        super(context);
        this.context = context;
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.ic_error).cacheInMemory()
                .cacheOnDisc().build();
    }

    @Override
    public View bindData(View convertView, Object itemData) {

        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            viewHolder = new ViewHolder();
            convertView = inflator.inflate(R.layout.adt_tweet, null);
            viewHolder.userName = (TextView) convertView
                    .findViewById(R.id.userName);
            viewHolder.message = (TextView) convertView
                    .findViewById(R.id.message);
            viewHolder.userIcon = (ImageView) convertView
                    .findViewById(R.id.userAvatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Tweet currentTweet = (Tweet) itemData;
        viewHolder.userName.setText(currentTweet.userName);
        viewHolder.message.setText(currentTweet.message);
        ImageLoaderSingleton.getImageLoader().displayImage(
                currentTweet.imageUrl, viewHolder.userIcon, options);
        return convertView;
    }
}
