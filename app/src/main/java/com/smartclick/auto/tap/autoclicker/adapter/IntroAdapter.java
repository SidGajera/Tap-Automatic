package com.smartclick.auto.tap.autoclicker.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartclick.auto.tap.autoclicker.databinding.IntroRowBinding;
import com.smartclick.auto.tap.autoclicker.model.IntroInfo;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;

import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.IntroViewHolder> {
    private final List<IntroInfo> infoList;

    public IntroAdapter(List<IntroInfo> list) {
        this.infoList = list;
    }

    @NonNull
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public IntroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IntroViewHolder(IntroRowBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(IntroViewHolder introViewHolder, int i) {
        introViewHolder.binding.infoImg.setImageResource(this.infoList.get(introViewHolder.getAdapterPosition()).getInfoSrc());
        introViewHolder.binding.infoTxtHeader.setText(this.infoList.get(introViewHolder.getAdapterPosition()).getInfoHeader());
        introViewHolder.binding.infoTxtDesc.setText(this.infoList.get(introViewHolder.getAdapterPosition()).getInfoTxt());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.infoList.size();
    }

    /* access modifiers changed from: protected */
    public static class IntroViewHolder extends RecyclerView.ViewHolder {
        IntroRowBinding binding;

        public IntroViewHolder(IntroRowBinding introRowBinding) {
            super(introRowBinding.getRoot());
            this.binding = introRowBinding;
            Resizer.setSize(introRowBinding.infoImg, 1080, 800, true);
        }
    }
}
