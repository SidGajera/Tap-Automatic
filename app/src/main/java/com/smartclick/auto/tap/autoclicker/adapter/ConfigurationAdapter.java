package com.smartclick.auto.tap.autoclicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartclick.auto.tap.autoclicker.databinding.ConfigurationLayoutBinding;
import com.smartclick.auto.tap.autoclicker.model.MultiDbModel;

import java.util.List;

public class ConfigurationAdapter extends RecyclerView.Adapter<ConfigurationAdapter.IntroViewHolder> {
    public List<MultiDbModel> arrayList;
    public ClickButton clickButton;
    public Context context;

    public interface ClickButton {
        void click(int i, MultiDbModel multiDbModel, int i2);
    }

    public ConfigurationAdapter(Context context2, List<MultiDbModel> list, ClickButton clickButton2) {
        this.context = context2;
        this.arrayList = list;
        this.clickButton = clickButton2;
    }

    @NonNull
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public IntroViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new IntroViewHolder(ConfigurationLayoutBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(IntroViewHolder introViewHolder, final int i) {
        introViewHolder.binding.name.setText(this.arrayList.get(i).getName());
        introViewHolder.binding.play.setOnClickListener(view -> playOnClick(i, view));
        introViewHolder.binding.copy.setOnClickListener(view -> copyOnClick(i, view));
        introViewHolder.binding.more.setOnClickListener(view -> moreOnClick(i, view));
    }
    public void playOnClick(int i, View view) {
        this.clickButton.click(0, this.arrayList.get(i), i);
    }

    /* access modifiers changed from: package-private */
    public void copyOnClick(int i, View view) {
        this.clickButton.click(1, this.arrayList.get(i), i);
    }

    public void moreOnClick(int i, View view) {
        this.clickButton.click(2, this.arrayList.get(i), i);
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    /* access modifiers changed from: protected */
    public static class IntroViewHolder extends RecyclerView.ViewHolder {
        ConfigurationLayoutBinding binding;

        public IntroViewHolder(ConfigurationLayoutBinding configurationLayoutBinding) {
            super(configurationLayoutBinding.getRoot());
            this.binding = configurationLayoutBinding;
        }
    }

    public void updateList(List<MultiDbModel> list) {
        this.arrayList = list;
        notifyDataSetChanged();
    }
}
