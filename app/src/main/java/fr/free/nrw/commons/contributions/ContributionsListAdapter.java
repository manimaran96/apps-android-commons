package fr.free.nrw.commons.contributions;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.free.nrw.commons.R;

/**
 * Represents The View Adapter for the List of Contributions  
 */
public class ContributionsListAdapter extends RecyclerView.Adapter<ContributionViewHolder> {

    private Callback callback;
    private List<Contribution> contributions;

    public ContributionsListAdapter(Callback callback) {
        this.callback = callback;
        contributions = new ArrayList<>();
    }

    /**
     * Creates the new View Holder which will be used to display items(contributions)
     * using the onBindViewHolder(viewHolder,position) 
     */
    @NonNull
    @Override
    public ContributionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContributionViewHolder viewHolder = new ContributionViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_contribution, parent, false), callback);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContributionViewHolder holder, int position) {
        final Contribution contribution = contributions.get(position);
        if (TextUtils.isEmpty(contribution.getThumbUrl())
            && contribution.getState() == Contribution.STATE_COMPLETED) {
            callback.fetchMediaUriFor(contribution);
        }

        holder.init(position, contribution);
    }

    @Override
    public int getItemCount() {
        return contributions.size();
    }

    public void setContributions(@NonNull List<Contribution> contributionList) {
        contributions = contributionList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return contributions.get(position)._id;
    }

    public interface Callback {

        void retryUpload(Contribution contribution);

        void deleteUpload(Contribution contribution);

        void openMediaDetail(int contribution);

        Contribution getContributionForPosition(int position);

        void fetchMediaUriFor(Contribution contribution);
    }
}
