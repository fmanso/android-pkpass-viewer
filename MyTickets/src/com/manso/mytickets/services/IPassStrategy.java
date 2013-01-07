package com.manso.mytickets.services;

import android.support.v4.app.Fragment;

public interface IPassStrategy {
	Fragment getFrontFragment();
	Fragment getBackFragment();
}
