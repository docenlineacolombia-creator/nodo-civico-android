package com.nodocivico.app.ui.report;

import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.nodocivico.app.R;
import com.nodocivico.app.data.model.Report;

public class ReportAdapter extends ListAdapter<Report, ReportAdapter.VH> {

    public ReportAdapter() {
        super(DIFF);
    }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_card, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Report r = getItem(position);
        holder.tvIcon.setText(r.getIconLetter());
        holder.tvTitle.setText(r.title);
        holder.tvMeta.setText(r.category + " · " + r.priority + " · "
                + (r.location != null ? r.location : ""));
        holder.tvStatus.setText(friendlyStatus(r.status));
        applyStatusBackground(holder.tvStatus, r.status);

        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putLong("reportId", r.id);
            Navigation.findNavController(v)
                    .navigate(R.id.action_list_to_detail, args);
        });
    }

    private String friendlyStatus(String s) {
        if (s == null) return "Abierto";
        switch (s) {
            case "IN_PROGRESS": return "En proceso";
            case "CLOSED":      return "Cerrado";
            default:            return "Abierto";
        }
    }

    private void applyStatusBackground(TextView tv, String status) {
        int bg;
        int color;
        if ("CLOSED".equals(status)) {
            bg    = R.drawable.bg_status_closed;
            color = tv.getResources().getColor(R.color.success, null);
        } else if ("IN_PROGRESS".equals(status)) {
            bg    = R.drawable.bg_status_progress;
            color = 0xFF_A16207;
        } else {
            bg    = R.drawable.bg_status_open;
            color = tv.getResources().getColor(R.color.primary_2, null);
        }
        tv.setBackgroundResource(bg);
        tv.setTextColor(color);
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvIcon, tvTitle, tvMeta, tvStatus;
        VH(@NonNull View v) {
            super(v);
            tvIcon   = v.findViewById(R.id.tvReportIcon);
            tvTitle  = v.findViewById(R.id.tvReportTitle);
            tvMeta   = v.findViewById(R.id.tvReportMeta);
            tvStatus = v.findViewById(R.id.tvStatus);
        }
    }

    private static final DiffUtil.ItemCallback<Report> DIFF =
            new DiffUtil.ItemCallback<Report>() {
                @Override public boolean areItemsTheSame(@NonNull Report a, @NonNull Report b) {
                    return a.id == b.id;
                }
                @Override public boolean areContentsTheSame(@NonNull Report a, @NonNull Report b) {
                    return a.updatedAt == b.updatedAt && a.status.equals(b.status);
                }
            };
}
