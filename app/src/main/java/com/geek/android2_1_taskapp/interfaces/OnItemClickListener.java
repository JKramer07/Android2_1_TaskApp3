package com.geek.android2_1_taskapp.interfaces;

import com.geek.android2_1_taskapp.models.Task;

public interface OnItemClickListener {

    void onClick(int position);
    void onLongClick(int position);
}
