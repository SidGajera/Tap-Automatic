package com.smartclick.auto.tap.autoclicker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.smartclick.auto.tap.autoclicker.databinding.SaveListAdapterBinding;
import com.smartclick.auto.tap.autoclicker.model.MultiDbModel;
import com.smartclick.auto.tap.autoclicker.utils.Resizer;

import java.util.List;

public class SaveListDialogAdapter extends RecyclerView.Adapter<SaveListDialogAdapter.IntroViewHolder> {
    public List<MultiDbModel> arrayList;
    public ClickInter clickInter;
    public Context context;

    public interface ClickInter {
        void click(int i, MultiDbModel multiDbModel);
    }

    public SaveListDialogAdapter(Context context2, List<MultiDbModel> list, ClickInter clickInter2) {
        this.context = context2;
        this.arrayList = list;
        this.clickInter = clickInter2;
    }

    @NonNull
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public IntroViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new IntroViewHolder(SaveListAdapterBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false));
    }

    public void onBindViewHolder(IntroViewHolder introViewHolder, final int i) {
        introViewHolder.binding.title.setText(this.arrayList.get(i).getName());
        introViewHolder.binding.title.setOnClickListener(view -> SaveListDialogAdapter.this.titleOnClick(i, view));
    }

    /* access modifiers changed from: package-private */
    public void titleOnClick(int i, View view) {
        this.clickInter.click(i, this.arrayList.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.arrayList.size();
    }

    /* access modifiers changed from: protected */
    public static class IntroViewHolder extends RecyclerView.ViewHolder {
        SaveListAdapterBinding binding;

        public IntroViewHolder(SaveListAdapterBinding saveListAdapterBinding) {
            super(saveListAdapterBinding.getRoot());
            this.binding = saveListAdapterBinding;
            Resizer.setSize(saveListAdapterBinding.title, 526, 115, true);
        }
    }
}
