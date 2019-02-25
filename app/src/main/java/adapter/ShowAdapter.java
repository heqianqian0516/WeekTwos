package adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.weektwos.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import bean.CommodityListBean;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ViewHolder> {
    private List<CommodityListBean> list;
    private Context context;
    private int ITEM_ONE=0;
    private int ITEM_TWO=1;
    private int ITEM_THRESS=2;
    private View view;

    public ShowAdapter(List<CommodityListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        this.context=parent.getContext();
        int type=getItemViewType(position);
        if (type==0){
             view= LayoutInflater.from(context).inflate(R.layout.my_hot,parent,false);
        }else if (type==1){
             view = LayoutInflater.from(context).inflate(R.layout.my_mlss,parent,false);

        }else if(type==2){
            view = LayoutInflater.from(context).inflate(R.layout.my_pzsh,parent,false);

        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAdapter.ViewHolder holder, final int position) {
         int type=getItemViewType(position);
         if (type==0){
             holder.hotName.setText(list.get(position).getCommodityName());
             holder.hotPrice.setText(list.get(position).getPrice()+"");
             Uri uri=Uri.parse(list.get(position).getMasterPic());
             holder.hotSdv.setImageURI(uri);
         }else if (type==1){
             holder.mlssName.setText(list.get(position).getCommodityName());
             holder.mlssPrice.setText(list.get(position).getPrice()+"");
             Uri uri=Uri.parse(list.get(position).getMasterPic());
             holder.mlssSdv.setImageURI(uri);
         }else if (type==2){
             holder.pzshName.setText(list.get(position).getCommodityName());
             holder.pzshPrice.setText(list.get(position).getPrice()+"");
             Uri uri=Uri.parse(list.get(position).getMasterPic());
             holder.pzshSdv.setImageURI(uri);
         }
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (httpClickListener!=null){
                     httpClickListener.onClickListener(position);
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%3==0){
            return ITEM_ONE;
        }else if(position%3==0){
            return ITEM_TWO;
        }else {
            return ITEM_THRESS;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView hotSdv;
        private final TextView hotName;
        private final TextView hotPrice;
        private final SimpleDraweeView mlssSdv;
        private final TextView mlssName;
        private final TextView mlssPrice;
        private final SimpleDraweeView pzshSdv;
        private final TextView pzshName;
        private final TextView pzshPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotSdv = itemView.findViewById(R.id.hot_sdv);
            hotName = itemView.findViewById(R.id.hot_name);
            hotPrice = itemView.findViewById(R.id.hot_price);

            mlssSdv = itemView.findViewById(R.id.mlss_sdv);
            mlssName = itemView.findViewById(R.id.mlss_name);
            mlssPrice = itemView.findViewById(R.id.mlss_price);

            pzshSdv = itemView.findViewById(R.id.pzsh_sdv);
            pzshName = itemView.findViewById(R.id.pzsh_name);
            pzshPrice = itemView.findViewById(R.id.pzsh_price);
        }
    }
    private HttpClickListener httpClickListener;
    public void setHttpClickListener(HttpClickListener httpClickListener){
       this.httpClickListener =httpClickListener;
    }
    public interface HttpClickListener{
        void onClickListener(int position);
    }
}
