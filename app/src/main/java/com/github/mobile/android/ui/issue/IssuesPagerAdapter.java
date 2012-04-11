package com.github.mobile.android.ui.issue;

import static com.github.mobile.android.util.GitHubIntents.EXTRA_ISSUE_NUMBER;
import static com.github.mobile.android.util.GitHubIntents.EXTRA_REPOSITORY_NAME;
import static com.github.mobile.android.util.GitHubIntents.EXTRA_REPOSITORY_OWNER;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryId;

/**
 * Adapter to page through an {@link Issue} array
 */
public class IssuesPagerAdapter extends FragmentPagerAdapter {

    private final Issue[] issues;

    private final Map<Integer, IssueFragment> fragments = new HashMap<Integer, IssueFragment>();

    /**
     * @param fm
     * @param issues
     */
    public IssuesPagerAdapter(FragmentManager fm, Issue[] issues) {
        super(fm);
        this.issues = issues;
    }

    @Override
    public Fragment getItem(int position) {
        IssueFragment fragment = new IssueFragment();
        Bundle args = new Bundle();
        Issue issue = issues[position];
        RepositoryId repo = RepositoryId.createFromUrl(issue.getHtmlUrl());
        args.putString(EXTRA_REPOSITORY_NAME, repo.getName());
        args.putString(EXTRA_REPOSITORY_OWNER, repo.getOwner());
        args.putInt(EXTRA_ISSUE_NUMBER, issue.getNumber());
        fragment.setArguments(args);
        fragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);

        fragments.remove(position);
    }

    @Override
    public int getCount() {
        return issues.length;
    }

    /**
     * Deliver dialog result to fragment at given position
     *
     * @param position
     * @param requestCode
     * @param resultCode
     * @param arguments
     * @return this adapter
     */
    public IssuesPagerAdapter onDialogResult(int position, int requestCode, int resultCode, Bundle arguments) {
        IssueFragment fragment = fragments.get(position);
        if (fragment != null)
            fragment.onDialogResult(requestCode, resultCode, arguments);
        return this;
    }
}
