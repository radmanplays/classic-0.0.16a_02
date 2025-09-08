package com.mojang.minecraft.gui;

public final class NewLevelScreen extends Screen {
	private Screen parent;

	public NewLevelScreen(Screen var1) {
		this.parent = var1;
	}

	public final void init() {
		this.buttons.clear();
		this.buttons.add(new Button(0, this.y / 2 - 100, this.w / 3, 200, 20, "Small"));
		this.buttons.add(new Button(1, this.y / 2 - 100, this.w / 3 + 32, 200, 20, "Normal"));
		this.buttons.add(new Button(2, this.y / 2 - 100, this.w / 3 + 64, 200, 20, "Huge"));
		this.buttons.add(new Button(3, this.y / 2 - 100, this.w / 3 + 96, 200, 20, "Cancel"));
	}

	protected final void buttonClicked(Button var1) {
		if(var1.id == 3) {
			this.minecraft.setScreen(this.parent);
		} else {
			this.minecraft.generateLevel(var1.id);
			this.minecraft.setScreen((Screen)null);
			this.minecraft.grabMouse();
		}
	}

	public final void render(int var1, int var2) {
		fillGradient(0, 0, this.y, this.w, 1610941696, -1607454624);
		this.drawCenteredString("Generate new level", this.y / 2, 40, 16777215);
		super.render(var1, var2);
	}
}
