package com.jae.radioapp.ui.adapter;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jae.radioapp.R;
import com.jae.radioapp.data.model.Program;
import com.jae.radioapp.databinding.ItemProgramBinding;
import com.jae.radioapp.utils.DateUtils;

import java.util.List;

import timber.log.Timber;

/**
 * Created by alex on 6/27/17.
 */

public class ProgramAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Program> programs;

    public ProgramAdapter(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ItemProgramBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.item_program, viewGroup, false);
        return new ProgramViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemProgramBinding binding = ((ProgramViewHolder)viewHolder).binding;
        Program program = programs.get(position);
        binding.tvTime.setText(program.getConvertedFromTime() + " - " + program.getConvertedToTime());
        binding.tvProgramName.setText(program.title);
    }

    @Override
    public int getItemCount() {
        return programs.size();
    }

    class ProgramViewHolder extends RecyclerView.ViewHolder {

        ItemProgramBinding binding;

        public ProgramViewHolder(ItemProgramBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
