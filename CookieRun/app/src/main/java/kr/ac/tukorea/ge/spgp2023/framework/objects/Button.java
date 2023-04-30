package kr.ac.tukorea.ge.spgp2023.framework.objects;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.spgp2023.framework.interfaces.ITouchable;

public class Button implements ITouchable {
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return false;
    }
}
