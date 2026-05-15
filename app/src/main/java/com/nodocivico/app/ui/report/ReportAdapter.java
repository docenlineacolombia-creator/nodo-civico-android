package com.nodocivico.app.ui.report;

import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.nodocivico.app.R;
import com.nodocivico.app.data.model.Report;

public class ReportAdapter extends ListAdapter<Report, ReportAdapter.VH> {

    public interface OnItemClick { void onClick(Report report); }
    private final OnItemClick listener;

    public ReportAdapter(OnItemClick listener) {
        super(DIFF);
        this.listener = listener;
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
        holder.tvMeta.setText(r.category + " · " + r.priority + " · " + r.location);
        holder.tvStatus.setText(r.status);
        holder.itemView.setOnClickListener(v -> listener.onClick(r));
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
