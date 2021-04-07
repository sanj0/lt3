package de.sanj0.chessian;

import de.edgelord.saltyengine.core.GameConfig;

import static de.edgelord.saltyengine.core.Game.*;

public class Main {

    public static final float GAME_WIDTH = 800;
    public static final float GAME_HEIGHT = 800;

    public static ChessScene chessScene;

    public static String INIT_FEN;

    public static void main(String[] args) {
        init(GameConfig.config(GAME_WIDTH, GAME_HEIGHT, "Chessian", GameConfig.NO_FIXED_TICKS));
        setDrawFPS(false);

        if (args.length >= 1) {
            INIT_FEN = args[0];
        } else {
            INIT_FEN = FENParser.STARTING_POSITION;
        }

        chessScene = new ChessScene(Board.fromFEN(INIT_FEN));
        start(30, chessScene);
    }
}
