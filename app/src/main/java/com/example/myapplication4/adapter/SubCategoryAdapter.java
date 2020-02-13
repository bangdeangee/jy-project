package com.example.myapplication4.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication4.R;
import com.example.myapplication4.vo.RealmVo;

import io.realm.OrderedRealmCollection;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.CategoryViewHolder> {

    private OrderedRealmCollection<RealmVo> mList;

    // 리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener = null ;
    public Typeface mTypeface = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        protected TextView category;
        public View mView;
        public CategoryViewHolder(View view) {
            super(view);
            mView = view;
            this.category = (TextView) view.findViewById(R.id.category_txt);
        }
    }


    public SubCategoryAdapter(OrderedRealmCollection<RealmVo> list) {
        this.mList = list;
    }
    /**
     * 추가 :: dataList를 돌려줍니다.
     */
    public void setDataList(OrderedRealmCollection<RealmVo> dataList) {
        this.mList = dataList;
    }



    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_layout, viewGroup, false);

        CategoryViewHolder viewHolder = new CategoryViewHolder(view);
        if(mTypeface == null){
            mTypeface = Typeface.createFromAsset(viewGroup.getContext().getAssets(), "fonts/BMHANNAPro.ttf");
        }
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder viewholder, final int position) {

        viewholder.category.setText(mList.get(position).getSub_category());
        viewholder.category.setTypeface(mTypeface);
        viewholder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position , mList.get(position).getSub_category()) ;
                }
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClick(View v, int position, String category) ;
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }
}
