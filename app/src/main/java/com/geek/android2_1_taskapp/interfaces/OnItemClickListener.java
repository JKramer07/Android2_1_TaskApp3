package com.geek.android2_1_taskapp.interfaces;

import com.geek.android2_1_taskapp.models.Task;

public interface OnItemClickListener {

    void onClick(Task position);
    void onLongClick(Task position);
}
