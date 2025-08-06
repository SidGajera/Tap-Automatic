package com.smartclick.auto.tap.autoclicker.adapter;

import android.content.Context;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.smartclick.auto.tap.autoclicker.R;
import com.smartclick.auto.tap.autoclicker.databinding.LanguageLayoutBinding;
import com.smartclick.auto.tap.autoclicker.model.LanguageModel;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;
import com.smartclick.auto.tap.autoclicker.utils.SpManager;

import java.util.ArrayList;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.MyViewHolder> {
    private static long mLastClickTime;
    public Context context;
    public ArrayList<LanguageModel> language_list;

    public LanguageAdapter(Context context2, ArrayList<LanguageModel> arrayList) {
        this.context = context2;
        this.language_list = arrayList;
    }

    @NonNull
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LanguageLayoutBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
        Glide.with(this.context).load(Integer.valueOf(this.language_list.get(myViewHolder.getAdapterPosition()).getImage())).into(myViewHolder.binding.languageImage);
        myViewHolder.binding.language.setText(this.language_list.get(i).getLanguage_name());
        myViewHolder.binding.lanLayout.setBackground(this.context.getResources().getDrawable(this.language_list.get(i).getId().equals(SpManager.getLanguageCodeSnip()) ? R.drawable.language_bg_selected : R.drawable.language_bg_unselected));
        myViewHolder.binding.select.setImageDrawable(this.context.getResources().getDrawable(this.language_list.get(i).getId().equals(SpManager.getLanguageCodeSnip()) ? R.drawable.radio_select : R.drawable.radio_unselect));
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (SystemClock.elapsedRealtime() - LanguageAdapter.mLastClickTime >= 1000) {
                    long unused = LanguageAdapter.mLastClickTime = SystemClock.elapsedRealtime();
                    SpManager.setLanguageCodeSnip(LanguageAdapter.this.language_list.get(i).getId());
                    SpManager.setLanguageSelected(true);
                    LanguageAdapter.this.notifyDataSetChanged();
                }
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.language_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final LanguageLayoutBinding binding;

        public MyViewHolder(LanguageLayoutBinding languageLayoutBinding) {
            super(languageLayoutBinding.getRoot());
            this.binding = languageLayoutBinding;
            Resizer.getheightandwidth(LanguageAdapter.this.context);
            Resizer.setSize(languageLayoutBinding.lanLayout, 1000, 160, true);
            Resizer.setSize(languageLayoutBinding.languageImage, 133, 133, true);
            Resizer.setSize(languageLayoutBinding.select, 32, 32, true);
            Resizer.setMargins(languageLayoutBinding.languageImage, 50, 0, 50, 0);
            Resizer.setMargins(languageLayoutBinding.select, 50, 0, 50, 0);
        }
    }
}
