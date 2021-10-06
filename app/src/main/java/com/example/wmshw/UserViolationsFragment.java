package com.example.wmshw;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserViolationsFragment extends Fragment {
    public UserViolationsFragment() {
        super(R.layout.fragment_user_violations);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String violationCardsJson = UserViolationsFragmentArgs.fromBundle(getArguments()).getUsersViolationLogs();
        ((TextView) view.findViewById(R.id.blablabla)).setText(violationCardsJson);
    }
}