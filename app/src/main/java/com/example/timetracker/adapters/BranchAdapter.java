package com.example.timetracker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.timetracker.data.Branch;

import java.util.List;

public class BranchAdapter  extends ArrayAdapter<Branch> {

    private Context context;
    private List<Branch> branches;

    public BranchAdapter(@NonNull Context context, @NonNull List<Branch> branches) {
        super(context, android.R.layout.simple_spinner_item, branches);
        this.context = context;
        this.branches = branches;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_item);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, android.R.layout.simple_spinner_dropdown_item);
    }

    private View createViewFromResource(int position, @Nullable View convertView, @NonNull ViewGroup parent, int resource) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, parent, false);
        }

        Branch branch = branches.get(position);
        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(branch.getName()); // Customize this to display the desired field

        return view;
    }
}
