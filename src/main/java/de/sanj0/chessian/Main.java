package de.sanj0.chessian;

import de.edgelord.saltyengine.core.GameConfig;

import static de.edgelord.saltyengine.core.Game.*;

public class Main {

    public static final float GAME_WIDTH = 800;
    public static final float GAME_HEIGHT = 800;

    public static void main(String[] args) {
        init(GameConfig.config(GAME_WIDTH, GAME_HEIGHT, "Chessian", GameConfig.NO_FIXED_TICKS));
        start(30);
        setDrawFPS(false);
        getHostAsDisplayManager().getDisplay().setResizable(false);
    }
}
