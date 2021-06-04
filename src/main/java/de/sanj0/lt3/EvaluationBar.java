package de.sanj0.lt3;

import de.edgelord.saltyengine.core.Game;
import de.edgelord.saltyengine.core.graphics.SaltyGraphics;
import de.edgelord.saltyengine.gameobject.DrawingRoutine;
import de.edgelord.saltyengine.transform.Transform;
import de.edgelord.saltyengine.utils.ColorUtil;
import de.edgelord.saltyengine.utils.GeneralUtil;

import java.awt.*;

public class EvaluationBar extends DrawingRoutine {

    public static float EVAL = 0;
    public static boolean grayedOut = true;
    public static final Color BAR_COLOR = ColorUtil.TEAL_BLUE_COLOR;
    public static final Color TEXT_COLOR = ColorUtil.WHITE;
    public static final Color GRAYED_OUT_COLOR = ColorUtil.withAlpha(Color.GRAY, .75f);
    public static final Transform AREA = new Transform(0, 0, BoardRenderer.boardOrigin.getX(), Game.getGameHeight());

    public EvaluationBar() {
        super(DrawingPosition.BEFORE_GAMEOBJECTS);
    }

    @Override
    public void draw(final SaltyGraphics saltyGraphics) {
        saltyGraphics.setColor(BAR_COLOR);
        float y = AREA.getCentre().getY() - EVAL * 2;
        float textY = GeneralUtil.clamp(y - 7, 25, AREA.getHeight() - 25);
        saltyGraphics.drawRect(AREA.getX(), y, AREA.getWidth(), AREA.getHeight());
        saltyGraphics.setColor(TEXT_COLOR);
        saltyGraphics.setFont(saltyGraphics.getFont().deriveFont(15f).deriveFont(Font.BOLD));
        saltyGraphics.drawText(String.format("%.2f", GeneralUtil.clamp(EVAL, -999, 999)), AREA.getCentre().getX(), textY, SaltyGraphics.TextAnchor.CENTRE);
        if (grayedOut) {
            saltyGraphics.setColor(GRAYED_OUT_COLOR);
            saltyGraphics.drawRect(AREA);
        }
    }

    public static void appendMoveRating(final int rating, final byte color) {
        EvaluationBar.EVAL += color == Pieces.LIGHT ? rating : -rating;
    }
}
