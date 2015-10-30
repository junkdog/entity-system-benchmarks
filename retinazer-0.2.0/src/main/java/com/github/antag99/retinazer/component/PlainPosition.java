package com.github.antag99.retinazer.component;

import com.github.antag99.retinazer.Component;

public class PlainPosition implements Component {
	public float x;
	public float y;

	public PlainPosition xy(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + "]";
	}
}
