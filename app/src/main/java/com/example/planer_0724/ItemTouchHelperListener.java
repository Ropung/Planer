package com.example.planer_0724;

public interface ItemTouchHelperListener {
    boolean onItemMove(int form_position, int to_position);
    void onItemSwipe(int position);
}
