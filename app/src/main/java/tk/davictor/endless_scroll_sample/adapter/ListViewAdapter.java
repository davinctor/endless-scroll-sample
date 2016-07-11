package tk.davictor.endless_scroll_sample.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import tk.davictor.endless_scroll_sample.R;
import tk.davictor.endless_scroll_sample.model.User;

/**
 * 12.07.2016
 * Created by @davinctor.
 */
public class ListViewAdapter extends ArrayAdapter<User> {

    private LayoutInflater layoutInflater;

    public ListViewAdapter(Context context) {
        super(context, 0);
        this.layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @SuppressWarnings("UnusedAssignment")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UserViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_user, parent, false);
            holder = new UserViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UserViewHolder) convertView.getTag();
        }

        if (holder != null) {
            User user = getItem(position);
            holder.ivAvatar.setImageResource(R.drawable.ic_face);
            if (!TextUtils.isEmpty(user.avatarUrl())) {
                Glide.with(getContext())
                        .load(user.avatarUrl())
                        .bitmapTransform(new CropCircleTransformation(getContext()))
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

        return convertView;
    }

    static class UserViewHolder {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvLogin;
        TextView tvCompany;
        TextView tvLocation;

        public UserViewHolder(View itemView) {
            ivAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvLogin = (TextView) itemView.findViewById(R.id.tv_login);
            tvCompany = (TextView) itemView.findViewById(R.id.tv_company);
            tvLocation = (TextView) itemView.findViewById(R.id.tv_location);
        }
    }

}
