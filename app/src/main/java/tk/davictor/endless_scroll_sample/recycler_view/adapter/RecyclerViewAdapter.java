package tk.davictor.endless_scroll_sample.recycler_view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import tk.davictor.endless_scroll_sample.R;
import tk.davictor.endless_scroll_sample.model.User;

/**
 * 17.07.2016
 * Created by @davinctor.
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserViewHolder> {

    private final Context context;
    private final LayoutInflater layoutInflater;
    private final List<User> users;

    public RecyclerViewAdapter(@NonNull Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.users = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutResId(), parent, false);
        return new UserViewHolder(itemView);
    }

    @LayoutRes
    protected abstract int layoutResId();

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = getItem(position);
        holder.ivAvatar.setImageResource(R.drawable.ic_face);
        if (!TextUtils.isEmpty(user.avatarUrl())) {
            Glide.with(context)
                    .load(user.avatarUrl())
                    .bitmapTransform(new CropCircleTransformation(context))
                    .error(R.drawable.ic_face)
                    .placeholder(R.drawable.ic_face)
                    .into(holder.ivAvatar);
        }
        String value;
        holder.tvName.setText(!TextUtils.isEmpty(value = user.name()) ? value : "");
        holder.tvLogin.setText(!TextUtils.isEmpty(value = user.login()) ? value : "");
        holder.tvCompany.setText(!TextUtils.isEmpty(value = user.company()) ? value : "");
        holder.tvLocation.setText(!TextUtils.isEmpty(value = user.location()) ? value : "");
    }

    public User getItem(int position) {
        return users.get(position);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public boolean addAll(Collection<User> collection) {
        return users.addAll(collection);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void clear() {
        users.clear();
    }

    static final class UserViewHolder extends RecyclerView.ViewHolder {

        ImageView ivAvatar;
        TextView tvName;
        TextView tvLogin;
        TextView tvCompany;
        TextView tvLocation;

        public UserViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvLogin = (TextView) itemView.findViewById(R.id.tv_login);
            tvCompany = (TextView) itemView.findViewById(R.id.tv_company);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
        }
    }
}
