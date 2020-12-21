Basically RotateAnimation with damping

Usage:  

    View view = findView...
    DampingRotateAnimation rotate = new DampingRotateAnimation(-30, 30, 200, 0.8f);
    view.startAnimation(rotate);